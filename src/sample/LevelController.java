package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LevelController implements Initializable {
    private Parent root;
    public Canvas gameField;
    public GridPane menuWin;
    private ArrayList<Wall> walls;
    private GraphicsContext gc;
    AnimationTimer animationTimer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        LevelBuilder levelBuilder = new LevelBuilder();
        menuWin.setDisable(true);
        gc = gameField.getGraphicsContext2D();
        gameField.setFocusTraversable(true);
        ArrayList<String> input = new ArrayList<>();
        Point mousePos = new Point();

        gameField.setOnKeyPressed(
                keyEvent -> {
                    if (keyEvent.getCode().toString().equals("ESCAPE")) {
                        menu();
                    }
                    String code = keyEvent.getCode().toString();
                    if (!input.contains(code)) {
                        input.add(code);
                    }
                }
        );
        gameField.setOnKeyReleased(
                keyEvent -> {
                    String code = keyEvent.getCode().toString();
                    input.remove(code);
                }
        );
        gameField.setOnMouseMoved(
                mouseEvent -> mousePos.setLocation(mouseEvent.getX(), mouseEvent.getY())
        );

        levelBuilder.addWalls(StartMenuController.getCurrentLevelId());
        walls = levelBuilder.getWalls();
        levelBuilder.setGc(gc);
        PlayerTank tank = new PlayerTank(levelBuilder, 350, 500, 0, 4, 2);


        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long CurrentNanoTime) {
                tank.operate(input, mousePos);

                gc.clearRect(0, 0, gameField.getWidth(), gameField.getHeight());

                tank.render();

                for (Wall wall : walls) {
                    wall.render(gc);
                }
            }
        };
        animationTimer.start();
    }

    private void menu() {
        menuWin.setDisable(false);
        animationTimer.stop();
        menuWin.setOpacity(1);
    }

    public void resumeGame(MouseEvent mouseEvent) {
        menuWin.setDisable(true);
        animationTimer.start();
        menuWin.setOpacity(0);
    }

    public void returnToMenu(MouseEvent mouseEvent) {
        try {
            root = FXMLLoader.load(getClass().getResource("start_menu.fxml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Stage stage = (Stage) gameField.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void exitGame(MouseEvent mouseEvent) {
        Platform.exit();
    }
}
