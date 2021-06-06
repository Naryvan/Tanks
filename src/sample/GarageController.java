package sample;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GarageController implements Initializable {

    public BorderPane instructionsPane;
    public Text currencyAmount;
    public Rectangle startGamePoint;
    public Rectangle upgradeTankPoint;
    public Rectangle exitGamePoint;
    private PlayerTank playerTank;
    private AnimationTimer animationTimer;
    private static boolean instructionsGiven;
    GraphicsContext gc;
    ArrayList<String> input = new ArrayList<>();
    Point mousePos = new Point();
    FadeTransition fadeTransition;
    @FXML
    private Canvas tankEntity;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.gc();
        fadeTransition = new FadeTransition();
        currencyAmount.setText(Money.getAmount() + "$");
        tankEntity.setFocusTraversable(true);
        gc = tankEntity.getGraphicsContext2D();
        playerTank = new PlayerTank(gc, 350, 450, 0);
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


        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long CurrentNanoTime) {
                playerTank.operate(input, mousePos);
                gc.clearRect(0, 0, 700, 700);
                playerTank.render();
            }
        };
        animationTimer.start();
        if (!instructionsGiven) {
            showInstructions();
        }
    }

    private void showInstructions() {
        animationTimer.stop();
        instructionsPane.setDisable(false);
        instructionsPane.setOpacity(1);
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
                fadeTransition.setOnFinished(actionEvent -> changeScene(0));
            } else if (upgradeTankPoint.equals(rectangle)) {
                fadeTransition.setOnFinished(actionEvent -> changeScene(1));
            }
            fadeTransition.play();
        }
    }

    private void changeScene(int index) {
        Stage stage = (Stage) tankEntity.getScene().getWindow();
        switch (index) {
            case 0 -> stage.setScene(Scenes.getStartGameMenu(getClass()));
            case 1 -> stage.setScene(Scenes.getTankUpgradeWindow(getClass()));
        }
        animationTimer.stop();
        tankEntity = null;
        playerTank = null;

    }

    public void hideInstructions(MouseEvent mouseEvent) {
        animationTimer.start();
        instructionsPane.setDisable(true);
        instructionsPane.setOpacity(0);
        instructionsGiven = true;
    }
}
