package sample;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GarageController implements Initializable {

    private Parent root;
    public Rectangle startGamePoint;
    public Rectangle upgradeTankPoint;
    public Rectangle exitGamePoint;
    private PlayerTank playerTank;
    GraphicsContext gc;
    ArrayList<String> input = new ArrayList<>();
    Point mousePos = new Point();
    FadeTransition fadeTransition = new FadeTransition();
    @FXML
    private Canvas tankEntity;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tankEntity.setFocusTraversable(true);
        gc = tankEntity.getGraphicsContext2D();
        playerTank = new PlayerTank(gc, 350, 450, 0, 4, 3);
        tankEntity.setOnKeyPressed(
                keyEvent -> {
                    checkRect(getNeededRectangle());
                    String code = keyEvent.getCode().toString();
                    if (!input.contains(code)) {
                        input.add(code);
                    }
                }
        );
        tankEntity.setOnKeyReleased(
                keyEvent -> {
                    checkRect(getNeededRectangle());
                    String code = keyEvent.getCode().toString();
                    input.remove(code);
                }
        );
        tankEntity.setOnMouseMoved(
                mouseEvent -> mousePos.setLocation(mouseEvent.getX(), mouseEvent.getY())
        );


        AnimationTimer animationTimer = new AnimationTimer() {
            @Override
            public void handle(long CurrentNanoTime) {
                playerTank.operate(input, mousePos);
                gc.clearRect(0, 0, 700, 700);
                playerTank.render();
            }
        };
        animationTimer.start();
    }

    private Rectangle getNeededRectangle() {
        if (startGamePoint.contains(new Point2D((int) playerTank.getX(), (int) playerTank.getY()))) {
            return startGamePoint;
        }
        if (exitGamePoint.contains(new Point2D((int) playerTank.getX(), (int) playerTank.getY()))) {
            return exitGamePoint;
        }

        if (upgradeTankPoint.contains(new Point2D((int) playerTank.getX(), (int) playerTank.getY()))) {
            return upgradeTankPoint;
        }
        fadeTransition.setOnFinished(null);
        fadeTransition.stop();
        fadeTransition.setToValue(0);
        fadeTransition.play();
        return null;
    }

    private void checkRect(Rectangle rectangle) {
        if (rectangle == null) {
            return;
        }

        if (rectangle.contains(new Point2D((int) playerTank.getX(), (int) playerTank.getY()))) {
            fadeTransition.stop();
            fadeTransition.setNode(rectangle);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(0.7);
            fadeTransition.setDuration(Duration.millis(800));
            if (exitGamePoint.equals(rectangle)) {
                fadeTransition.setOnFinished(actionEvent -> Platform.exit());
            } else if (startGamePoint.equals(rectangle)) {
                fadeTransition.setOnFinished(actionEvent -> changeScene("start_menu.fxml"));
            } else if (upgradeTankPoint.equals(rectangle)) {
                fadeTransition.setOnFinished(actionEvent -> changeScene("tank_upgrade_menu.fxml"));
            }
            fadeTransition.play();
        }
    }

    private void changeScene(String s) {
        try {
            root = FXMLLoader.load(getClass().getResource(s));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Stage stage = (Stage) tankEntity.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
