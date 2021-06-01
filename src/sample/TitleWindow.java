package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class TitleWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) {
        Level[] levels = StartMenuController.getLevels();
        for(int i = 0; i < levels.length; i++){
            levels[i] = new Level("#level" + i);
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
