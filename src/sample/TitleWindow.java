package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TitleWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }



    @Override
    public void start(Stage primaryStage) {
        LevelMenu[] levels = StartMenuController.getLevels();
        for(int i = 0; i < levels.length; i++){
            levels[i] = new LevelMenu("#level" + i);
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
