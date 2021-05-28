package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

import java.awt.*;

public class Tank {

    double xPos;
    double yPos;
    int direction;

    double maxSpeed;
    double acceleration;
    double currentSpeed = 0;
    boolean isMoving = false;

    double gunDirection;
    double gunRotationSpeed;

    public Tank() {
        this.xPos = 50;
        this.yPos = 50;
        direction = 0;
        gunDirection = 0;
        maxSpeed = 4;
        acceleration = maxSpeed / 40;
        gunRotationSpeed = 2;
    }

    public Tank(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        direction = 0;
        gunDirection = 0;
        maxSpeed = 4;
        acceleration = maxSpeed / 40;
        gunRotationSpeed = 2;
    }

    public Tank(double xPos, double yPos, double maxSpeed, double gunRotationSpeed) {
        this.xPos = xPos;
        this.yPos = yPos;
        direction = 0;
        gunDirection = 0;
        this.maxSpeed = maxSpeed;
        acceleration = maxSpeed / 40;
        this.gunRotationSpeed = gunRotationSpeed;
    }

    protected void processMovement() {
        if(isMoving) {
            accelerate();
        }
        else {
            slowDown();
        }

        switch(direction) {
            case 0:
                if(yPos <= 0){
                    currentSpeed = 0;
                }
                yPos -= currentSpeed;
                break;
            case 1:
                if(!(yPos <= 0)){
                    yPos -= currentSpeed / Math.sqrt(2);
                }
                if(!(xPos >= Main.FIELD_WIDTH)){
                    xPos += currentSpeed / Math.sqrt(2);
                }
                break;
            case 2:
                if(xPos >= Main.FIELD_WIDTH){
                    currentSpeed = 0;
                }
                xPos += currentSpeed;
                break;
            case 3:
                if(!(yPos >= Main.FIELD_HEIGHT)){
                    yPos += currentSpeed / Math.sqrt(2);
                }
                if(!(xPos >= Main.FIELD_WIDTH)){
                    xPos += currentSpeed / Math.sqrt(2);
                }
                break;
            case 4:
                if(yPos >= Main.FIELD_HEIGHT){
                    currentSpeed = 0;
                }
                yPos += currentSpeed;
                break;
            case 5:
                if(!(yPos >= Main.FIELD_HEIGHT)){
                    yPos += currentSpeed / Math.sqrt(2);
                }
                if(!(xPos <= 0)){
                    xPos -= currentSpeed / Math.sqrt(2);
                }
                break;
            case 6:
                if(xPos <= 0){
                    currentSpeed = 0;
                }
                xPos -= currentSpeed;
                break;
            case 7:
                if(!(yPos <= 0)){
                    yPos -= currentSpeed / Math.sqrt(2);
                }
                if(!(xPos <= 0)){
                    xPos -= currentSpeed / Math.sqrt(2);
                }
                break;
        }
    }

    private void accelerate() {
        if(currentSpeed >= maxSpeed) {
            currentSpeed = maxSpeed;
        }
        else {
            currentSpeed += acceleration;
        }
    }

    private void slowDown() {
        if(currentSpeed > 0) {
            currentSpeed -= acceleration * 2;
        }
        if(currentSpeed < 0) {
            currentSpeed = 0;
        }
    }

    protected void rotateGun(Point targetPos) {
        double angle = calculateGunDirection(targetPos);

        if(Math.abs(gunDirection - angle) < gunRotationSpeed) {
            gunDirection = angle;
            return;
        }

        if((gunDirection > 0 && angle < 0) && (gunDirection + Math.abs(angle) > 180)) {
            gunDirection += gunRotationSpeed;
            if(gunDirection > 180) {
                gunDirection = - 360 + gunDirection;
            }
        }
        else if((gunDirection < 0 && angle > 0) && (Math.abs(gunDirection) + angle > 180)) {
            gunDirection -= gunRotationSpeed;
            if(gunDirection < -180) {
                gunDirection = 360 + gunDirection;
            }
        }
        else {
            if(gunDirection < angle) {
                gunDirection += gunRotationSpeed;
            }
            else {
                gunDirection -= gunRotationSpeed;
            }
        }

    }

    public void render(GraphicsContext gc) {
        renderBody(gc);
        renderGun(gc);
    }

    private void renderBody(GraphicsContext gc) {
        gc.save();
        gc.transform(new Affine(new Rotate(direction * 45, xPos, yPos)));
        gc.setFill(javafx.scene.paint.Color.rgb(0, 60, 0));
        gc.fillRoundRect(xPos - 15, yPos - 25, 30, 50, 5, 5);
        gc.strokeRoundRect(xPos - 15, yPos - 25, 30, 50, 5, 5);
        gc.restore();
    }

    private void renderGun(GraphicsContext gc) {
        gc.setFill(javafx.scene.paint.Color.rgb(0, 75, 0));
        gc.save();
        gc.transform(new Affine(new Rotate(gunDirection, xPos, yPos)));
        gc.fillRect(xPos, yPos - 5, 30, 10);
        gc.strokeRect(xPos, yPos - 5, 30, 10);
        gc.restore();

        gc.setFill(Color.rgb(0, 90, 0));
        gc.strokeOval(xPos - 12.5, yPos - 12.5, 25, 25);
        gc.fillOval(xPos - 12.5, yPos - 12.5, 25, 25);
    }

    private double calculateGunDirection(Point targetPos) {
        double length = targetPos.getX() - xPos;
        double height = targetPos.getY() - yPos;

        return Math.toDegrees(Math.atan2(height, length));
    }



}
