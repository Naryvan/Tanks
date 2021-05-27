package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    Random random = new Random();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Group root = new Group();
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
        Canvas canvas = new Canvas(800, 600);
        root.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        ArrayList<String> input = new ArrayList<>();
        Point mousePos = new Point();

        scene.setOnKeyPressed(
                keyEvent -> {
                    String code = keyEvent.getCode().toString();
                    if(!input.contains(code)) {
                        input.add(code);
                    }
                }
        );
        scene.setOnKeyReleased(
                keyEvent -> {
                    String code = keyEvent.getCode().toString();
                    input.remove(code);
                }
        );
        scene.setOnMouseMoved(
                mouseEvent -> {
                    mousePos.setLocation(mouseEvent.getX(), mouseEvent.getY());
                }
        );

        Tank tank = new Tank();

        new AnimationTimer() {
            @Override
            public void handle(long CurrentNanoTime) {
                tank.operate(input, mousePos);

                gc.clearRect(0, 0, 800,600);
                tank.render(gc);
            }
        }.start();
    }

    class Tank {

        double x;
        double y;
        int direction;
        double maxSpeed = 4;
        double speed = 0;
        double gunDirection;
        double rotationSpeed = 2;

        public Tank() {
            x = 50;
            y = 50;
            direction = 0;
        }

        public void operate(ArrayList<String> input, Point mousePos) {
            if(input.contains("W")) {
                direction = 0;
                if(input.contains("D")) {
                    direction = 1;
                }
                else if(input.contains("A")) {
                    direction = 7;
                }
                accelerate();
            }
            else if(input.contains("D")) {
                direction = 2;
                if(input.contains("S")) {
                    direction = 3;
                }
                else if(input.contains("W")) {
                    direction = 1;
                }
                accelerate();
            }
            else if(input.contains("S")) {
                direction = 4;
                if(input.contains("A")) {
                    direction = 5;
                }
                else if(input.contains("D")) {
                    direction = 3;
                }
                accelerate();
            }
            else if(input.contains("A")) {
                direction = 6;
                if(input.contains("W")) {
                    direction = 7;
                }
                else if(input.contains("S")) {
                    direction = 5;
                }
                accelerate();
            }
            else {
                slowDown();
            }
            move();
            rotateGun(mousePos);
        }

        private void accelerate() {
            if(speed >= maxSpeed) {
                speed = maxSpeed;
            }
            else {
                speed += 0.1;
            }
        }

        private void slowDown() {
            if(speed > 0) {
                speed -= 0.2;
            }
            if(speed < 0) {
                speed = 0;
            }
        }

        private void move() {
            if(speed == 0) {
                return;
            }

            switch(direction) {
                case 0:
                    y -= speed;
                    break;
                case 1:
                    x += speed / Math.sqrt(2);
                    y -= speed / Math.sqrt(2);
                    break;
                case 2:
                    x += speed;
                    break;
                case 3:
                    x += speed / Math.sqrt(2);
                    y += speed / Math.sqrt(2);
                    break;
                case 4:
                    y += speed;
                    break;
                case 5:
                    x -= speed / Math.sqrt(2);
                    y += speed / Math.sqrt(2);
                    break;
                case 6:
                    x -= speed;
                    break;
                case 7:
                    x -= speed / Math.sqrt(2);
                    y -= speed / Math.sqrt(2);
                    break;
            }
        }

        private void rotateGun(Point mousePos) {
            double angle = calculateGunDirection(mousePos);

            if(Math.abs(gunDirection - angle) < rotationSpeed) {
                gunDirection = angle;
                return;
            }

            if((gunDirection > 0 && angle < 0) && (gunDirection + Math.abs(angle) > 180)) {
                gunDirection += rotationSpeed;
                if(gunDirection > 180) {
                    gunDirection = - 360 + gunDirection;
                }
            }
            else if((gunDirection < 0 && angle > 0) && (Math.abs(gunDirection) + angle > 180)) {
                gunDirection -= rotationSpeed;
                if(gunDirection < -180) {
                    gunDirection = 360 + gunDirection;
                }
            }
            else {
                if(gunDirection < angle) {
                    gunDirection += rotationSpeed;
                }
                else {
                    gunDirection -= rotationSpeed;
                }
            }

        }

        public void render(GraphicsContext gc) {
            drawBody(gc);
            drawGun(gc);
        }

        private void drawBody(GraphicsContext gc) {
            gc.save();
            gc.transform(new Affine(new Rotate(direction * 45, x, y)));
            gc.setFill(Color.rgb(0, 60, 0));
            gc.fillRoundRect(x - 15, y - 25, 30, 50, 5, 5);
            gc.strokeRoundRect(x - 15, y - 25, 30, 50, 5, 5);
            gc.restore();
        }

        private void drawGun(GraphicsContext gc) {
            gc.setFill(Color.rgb(0, 75, 0));
            gc.save();
            gc.transform(new Affine(new Rotate(gunDirection, x, y)));
            gc.fillRect(x, y - 5, 30, 10);
            gc.strokeRect(x, y - 5, 30, 10);
            gc.restore();

            gc.setFill(Color.rgb(0, 90, 0));
            gc.strokeOval(x - 12.5, y - 12.5, 25, 25);
            gc.fillOval(x - 12.5, y - 12.5, 25, 25);
        }

        private double calculateGunDirection(Point mousePos) {
            double length = mousePos.getX() - x;
            double height = mousePos.getY() - y;

            return Math.toDegrees(Math.atan2(height, length));
        }

    }

    public static void main(String[] args) {
        launch(args);
    }
}
