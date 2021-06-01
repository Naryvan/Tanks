package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
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

public class LevelWindow extends Application {

    public static int FIELD_WIDTH = 700;
    public static int FIELD_HEIGHT = 700;

    protected ArrayList<Wall> walls = new ArrayList<>();
    protected GraphicsContext gc;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, FIELD_WIDTH, FIELD_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
        Canvas canvas = new Canvas(FIELD_WIDTH, FIELD_HEIGHT);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();

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
                mouseEvent -> mousePos.setLocation(mouseEvent.getX(), mouseEvent.getY())
        );

        PlayerTank tank = new PlayerTank(this, 50, 50, 0, 4, 2);

        addWalls();

        new AnimationTimer() {
            @Override
            public void handle(long CurrentNanoTime) {
                tank.operate(input, mousePos);

                gc.clearRect(0, 0, FIELD_WIDTH, FIELD_HEIGHT);

                tank.render();

                for(Wall wall : walls) {
                    wall.render(gc);
                }
            }
        }.start();
    }

    protected void addWalls() {
        walls.add(new Wall(100, 100));
        walls.add(new Wall(150, 100));
        walls.add(new Wall(200, 100));
        walls.add(new Wall(250, 100));
        walls.add(new Wall(300, 100));
        walls.add(new Wall(350, 100));
        walls.add(new Wall(350, 150));
        walls.add(new Wall(350, 200));
        walls.add(new Wall(350, 250));
        walls.add(new Wall(350, 300));
        walls.add(new Wall(350, 350));
        walls.add(new Wall(250, 200));
        walls.add(new Wall(250, 250));
        walls.add(new Wall(250, 300));
        walls.add(new Wall(250, 350));
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public ArrayList<Wall> getCloseWalls(double xPos, double yPos) {
        ArrayList<Wall> closeWalls = new ArrayList<>();
        Rectangle2D closeRectangle = new Rectangle2D(xPos - 100, yPos - 100, 200, 200);

        for(Wall wall : walls) {
            if(wall.getBoundary().intersects(closeRectangle)) {
                closeWalls.add(wall);
            }
        }

        return closeWalls;
    }

    public GraphicsContext getGraphicsContext() {
        return gc;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
