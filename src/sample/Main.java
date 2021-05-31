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

    public final static int FIELD_WIDTH = 700;
    public final static int FIELD_HEIGHT = 700;

    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, FIELD_WIDTH, FIELD_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
        Canvas canvas = new Canvas(FIELD_WIDTH, FIELD_HEIGHT);
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
                mouseEvent -> mousePos.setLocation(mouseEvent.getX(), mouseEvent.getY())
        );

        PlayerTank tank = new PlayerTank(50, 50, 4, 2);

        new AnimationTimer() {
            @Override
            public void handle(long CurrentNanoTime) {
                tank.operate(input, mousePos);

                gc.clearRect(0, 0, 800,600);

                Wall wall = new Wall(1 , 1);
                wall.render(gc);
                tank.render(gc);
            }
        }.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
