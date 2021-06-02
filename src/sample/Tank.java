package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

import java.awt.*;
import java.util.ArrayList;

public class Tank {

    final static double WIDTH = 40;
    final static double HEIGHT = 40;

    double xPos;
    double yPos;
    int direction;
    double currentDirection;

    double maxSpeed;
    double acceleration;
    double currentSpeed = 0;
    boolean isMoving = false;

    double gunDirection;
    double gunRotationSpeed;

    boolean isTopBlocked;
    boolean isBottomBlocked;
    boolean isLeftBlocked;
    boolean isRightBlocked;

    LevelBuilder levelWindow;
    GraphicsContext gc;

    public Tank(LevelBuilder levelWindow) {
        this.xPos = 50;
        this.yPos = 50;
        direction = 0;
        currentDirection = 0;
        gunDirection = 0;
        maxSpeed = 4;
        acceleration = maxSpeed / 40;
        gunRotationSpeed = 2;
        this.levelWindow = levelWindow;
        this.gc = levelWindow.getGraphicsContext();
    }

    public Tank(LevelBuilder levelWindow, double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        direction = 0;
        currentDirection = 0;
        gunDirection = 0;
        maxSpeed = 4;
        acceleration = maxSpeed / 40;
        gunRotationSpeed = 2;
        this.levelWindow = levelWindow;
        this.gc = levelWindow.getGraphicsContext();
    }

    //For levels
    public Tank(LevelBuilder levelWindow, double xPos, double yPos, int direction, double maxSpeed, double gunRotationSpeed) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.direction = direction;
        currentDirection = direction * 45;
        gunDirection = 0;
        this.maxSpeed = maxSpeed;
        acceleration = maxSpeed / 40;
        this.gunRotationSpeed = gunRotationSpeed;
        this.levelWindow = levelWindow;
        this.gc = levelWindow.getGraphicsContext();
    }

    //For garage
    public Tank(GraphicsContext gc, double xPos, double yPos, int direction, double maxSpeed, double gunRotationSpeed) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.direction = direction;
        currentDirection = direction * 45;
        gunDirection = 0;
        this.maxSpeed = maxSpeed;
        acceleration = maxSpeed / 40;
        this.gunRotationSpeed = gunRotationSpeed;
        this.levelWindow = null;
        this.gc = gc;
    }

    protected void processMovement() {
        checkCollision();

        if(isMoving) {
            accelerate();
        }
        else {
            slowDown();
        }

        switch (direction) {
            case 0 -> {
                if(!isTopBlocked) {
                    yPos -= currentSpeed;
                }
            }
            case 1 -> {
                if(!isTopBlocked) {
                    yPos -= currentSpeed / Math.sqrt(2);
                }
                if(!isRightBlocked) {
                    xPos += currentSpeed / Math.sqrt(2);
                }
            }
            case 2 -> {
                if(!isRightBlocked) {
                    xPos += currentSpeed;
                }
            }
            case 3 -> {
                if(!isBottomBlocked) {
                    yPos += currentSpeed / Math.sqrt(2);
                }
                if(!isRightBlocked) {
                    xPos += currentSpeed / Math.sqrt(2);
                }
            }
            case 4 -> {
                if(!isBottomBlocked) {
                    yPos += currentSpeed;
                }
            }
            case 5 -> {
                if(!isBottomBlocked) {
                    yPos += currentSpeed / Math.sqrt(2);
                }
                if(!isLeftBlocked) {
                    xPos -= currentSpeed / Math.sqrt(2);
                }
            }
            case 6 -> {
                if(!isLeftBlocked) {
                    xPos -= currentSpeed;
                }
            }
            case 7 -> {
                if(!isTopBlocked) {
                    yPos -= currentSpeed / Math.sqrt(2);
                }
                if(!isLeftBlocked) {
                    xPos -= currentSpeed / Math.sqrt(2);
                }
            }
        }
        rotateBody();
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

    private void rotateBody() {
        if(direction * 45 == currentDirection) {
            return;
        }

        double rotationLength = 360;
        double rotationSpeed;

        if((currentDirection < 180 && direction * 45 > 180) && (direction * 45 - currentDirection > 180)) {
            //rotationLength = direction * 45 - currentDirection;
            rotationSpeed = - 12;
        }
        else if((currentDirection > 180 && direction * 45 < 180) && (currentDirection - direction * 45 > 180)) {
            //rotationLength = currentDirection - direction * 45;
            rotationSpeed = 12;
        }
        else {
            if(currentDirection < direction * 45) {
                //rotationLength = direction * 45 - currentDirection;
                rotationSpeed = 12;
            }
            else {
                //rotationLength = currentDirection - direction * 45;
                rotationSpeed = - 12;
            }
        }

        if(Math.abs(currentDirection - (direction != 0 ? direction * 45 : 360)) < rotationSpeed) {
            currentDirection = direction * 45;
            return;
        }

        currentDirection += rotationSpeed;

        if(currentDirection > 360) {
            currentDirection -= 360;
        }
        if(currentDirection < 0) {
            currentDirection = 360 + currentDirection;
        }

    }

    private void checkCollision() {
        isTopBlocked = false;
        isBottomBlocked = false;
        isLeftBlocked = false;
        isRightBlocked = false;

        if(yPos <= 25) {
            isTopBlocked = true;
        }
        if(yPos >= gc.getCanvas().getHeight() - 25) {
            isBottomBlocked = true;
        }
        if(xPos <= 25) {
            isLeftBlocked = true;
        }
        if(xPos >= gc.getCanvas().getWidth() - 25) {
            isRightBlocked = true;
        }

        //garage
        if(levelWindow == null) {
            return;
        }

        ArrayList<Wall> walls = levelWindow.getCloseWalls(xPos, yPos);

        for(Wall wall : walls) {
            if(getTopBoundary().intersects(wall.getBoundary())) {
                isTopBlocked = true;
            }
            if(getBottomBoundary().intersects(wall.getBoundary())) {
                isBottomBlocked = true;
            }
            if(getLeftBoundary().intersects(wall.getBoundary())) {
                isLeftBlocked = true;
            }
            if(getRightBoundary().intersects(wall.getBoundary())) {
                isRightBlocked = true;
            }
        }
    }

    public void render() {
        renderBody();
        renderGun();
    }

    private void renderBody() {
        gc.save();
        gc.transform(new Affine(new Rotate(currentDirection, xPos, yPos)));
        gc.setFill(javafx.scene.paint.Color.rgb(0, 60, 0));
        gc.fillRoundRect(xPos - WIDTH / 2, yPos - HEIGHT / 2, WIDTH, HEIGHT, 5, 5);
        gc.strokeRoundRect(xPos - WIDTH / 2, yPos - HEIGHT / 2, WIDTH, HEIGHT, 5, 5);
        gc.restore();
    }

    private void renderGun() {
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

    public Rectangle2D getBoundary() {
        return new Rectangle2D(xPos - 25, yPos - 25, 50, 50);
    }

    public Rectangle2D getTopBoundary() {
        return new Rectangle2D(xPos - WIDTH / 2, yPos - HEIGHT / 2 - 6, WIDTH, 1);
    }

    public Rectangle2D getBottomBoundary() {
        return new Rectangle2D(xPos - WIDTH / 2, yPos + HEIGHT / 2 + 5, WIDTH, 1);
    }

    public Rectangle2D getLeftBoundary() {
        return new Rectangle2D(xPos - WIDTH / 2 - 6, yPos - HEIGHT / 2, 1, HEIGHT);
    }

    public Rectangle2D getRightBoundary() {
        return new Rectangle2D(xPos + WIDTH / 2 + 5, yPos - HEIGHT / 2, 1, HEIGHT);
    }

}