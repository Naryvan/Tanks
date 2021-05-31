package sample;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GarageController implements Initializable {
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
        playerTank = new PlayerTank(30, 30, 4, 3);
        gc = tankEntity.getGraphicsContext2D();
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


        new AnimationTimer() {
            @Override
            public void handle(long CurrentNanoTime) {
                playerTank.operate(input, mousePos);
                gc.clearRect(0, 0, 700, 700);
                playerTank.render(gc);
            }
        }.start();
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
            fadeTransition.setOnFinished(actionEvent -> Platform.exit());
            fadeTransition.play();
        }
    }
}
