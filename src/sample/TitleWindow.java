package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Paths;

public class TitleWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    MediaPlayer mediaPlayer;
    private void music(){
        String name = "src/music/main_theme.mp3";
        Media media = new Media(Paths.get(name).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    @Override
    public void start(Stage primaryStage) {
        music();
        LevelMenu[] levels = StartMenuController.getLevels();
        for (int i = 0; i < levels.length; i++) {
            levels[i] = new LevelMenu("#level" + i, "#backLevelPane" + i);
        }
        levels[0].setLocked(false);
        Parent root = new Group();
        Stage stage = new Stage();
        try {
            root = FXMLLoader.load(getClass().getResource("title_window.fxml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        stage.setResizable(false);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
