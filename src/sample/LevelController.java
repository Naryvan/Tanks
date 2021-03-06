package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class LevelController implements Initializable {
    public ProgressBar hpBar;
    public ProgressBar reloadBar;
    public Pane levelFailed;
    public Pane levelCompleted;
    public Text levelCompletedText;
    private Parent root;
    public Canvas gameField;
    public GridPane menuWin;
    private ArrayList<Wall> walls;
    private ArrayList<Bonus> bonuses;
    private GraphicsContext gc;
    private LevelBuilder levelBuilder;
    AnimationTimer animationTimer;

    private PlayerTank playerTank;
    private ArrayList<EnemyTank> enemyTanks;
    protected ArrayList<Effect> effects;

    AudioClip winSound = new AudioClip(getClass().getResource("/sounds/win_sound.mp3").toExternalForm());
    AudioClip gameCompletedSound = new AudioClip(getClass().getResource("/sounds/game_completed.mp3").toExternalForm());
    AudioClip loseSound = new AudioClip(getClass().getResource("/sounds/lose_sound.mp3").toExternalForm());

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hpBar.setProgress(1);
        hpBar.setStyle("-fx-accent: #9acd32");
        reloadBar.setProgress(1);
        levelBuilder = new LevelBuilder();
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

        gameField.setOnMouseDragged(
                mouseEvent -> mousePos.setLocation(mouseEvent.getX(), mouseEvent.getY())
        );

        levelBuilder.setGc(gc);
        levelBuilder.addWalls(StartMenuController.getCurrentLevelId());
        walls = levelBuilder.getWalls();
        bonuses = levelBuilder.getBonuses();
        enemyTanks = levelBuilder.getEnemyTanks();
        playerTank = levelBuilder.getPlayerTank();
        effects = levelBuilder.getEffects();
        levelBuilder.setGc(gc);

        //enemyTanks.get(0).freeze();
        gameField.setOnMouseClicked(
                mouseEvent -> {
                    if (playerTank.isLoaded) {
                        playerTank.penetration = false;
                        playerTank.createBullet(gameField.getGraphicsContext2D(), true);
                        reloadBarAnimation();

                    }
                    playerTank.isLoaded = false;
                }
        );

        animationTimer = new AnimationTimer() {
            @Override

            public void handle(long CurrentNanoTime) {

                if (PlayerTank.currentHP <= 0) {
                    animationTimer.stop();
                    openLevelFailedWindow();
                }

                if (enemyTanks.size() == 0) {
                    animationTimer.stop();
                    openLevelCompletedWindow();
                }
                hpBar.setProgress((double) PlayerTank.currentHP / PlayerTank.maxHP);
                walls = levelBuilder.getWalls();
                bonuses = levelBuilder.getBonuses();
                playerTank.operate(input, mousePos);
                for (EnemyTank enemyTank : enemyTanks) {
                    enemyTank.operate();
                }

                gc.clearRect(0, 0, gameField.getWidth(), gameField.getHeight());

                for (Wall wall : walls) {
                    wall.render(gc);
                }

                for (Bonus bonus : bonuses) {
                    bonus.render(gc);
                }

                //gc.drawImage(new Image("images/FreezeBonus.png"), 50, 50);


                for (EnemyTank enemyTank : enemyTanks) {
                    enemyTank.render();
                }

                playerTank.render();

                ArrayList<Effect> effectsTemp = new ArrayList<>(effects);
                for (Effect effect : effectsTemp) {
                    effect.process();
                }
            }
        };
        animationTimer.start();
    }

    private void reloadBarAnimation() {
        new AnimationTimer() {

            @Override
            public void handle(long l) {
                if (playerTank == null) {
                    stop();
                    return;
                }
                if (!playerTank.isLoaded) {
                    reloadBar.setProgress((double) playerTank.reloadTimer / 80.00);
                }

            }
        }.start();
    }

    private void menu() {
        playerTank.moveSound.stop();
        menuWin.setDisable(false);
        animationTimer.stop();
        menuWin.setOpacity(1);
    }

    public void resumeGame(MouseEvent mouseEvent) {
        playerTank.moveSound.play();
        menuWin.setDisable(true);
        animationTimer.start();
        menuWin.setOpacity(0);
    }

    public void returnToMenu(MouseEvent mouseEvent) {
        levelBuilder.addWalls(-1);
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

    private void openLevelCompletedWindow() {
        playerTank.moveSound.stop();

        int currentLvlId = StartMenuController.getCurrentLevelId();
        animationTimer.stop();
        if (currentLvlId == 5) {
            gameCompletedSound.play();
            levelCompletedText.setText("Congratulations!!!\nGame completed! :) ");
        } else {
            winSound.play();
            levelCompletedText.setText("Congratulations!!!\nLevel completed!");
        }
        levelCompleted.setDisable(false);
        levelCompleted.setOpacity(1);
    }

    private void openLevelFailedWindow() {
        playerTank.moveSound.stop();
        loseSound.play();
        animationTimer.stop();
        levelFailed.setDisable(false);
        levelFailed.setOpacity(1);
    }

    public void levelCompletedAction(MouseEvent mouseEvent) {
        int currentLvlId = StartMenuController.getCurrentLevelId();
        levelCompleted.setDisable(true);
        levelCompleted.setOpacity(0);
        levelBuilder.addWalls(-1);
        StartMenuController.getLevels()[currentLvlId].setCompleted(true);
        if (currentLvlId != 5) {
            StartMenuController.getLevels()[currentLvlId + 1].setLocked(false);
        }
        animationTimer.stop();
        playerTank = null;
        returnToGarage();
    }

    public void levelFailedAction(MouseEvent mouseEvent) {
        levelFailed.setDisable(true);
        levelFailed.setOpacity(0);
        levelBuilder.addWalls(-1);
        animationTimer.stop();
        playerTank = null;
        returnToGarage();
    }

    private void returnToGarage() {
        Stage stage = (Stage) gameField.getScene().getWindow();
        stage.setScene(Scenes.getGarageWindow(getClass()));
    }
}
