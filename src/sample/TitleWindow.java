package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class TitleWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = new Group();
        try {
            root = FXMLLoader.load(getClass().getResource("title_window.fxml"));
        } catch (Exception e){
            e.printStackTrace();
        }
        Stage stage = new Stage();
        Scene scene = new Scene(root, 600, 600, Color.GREEN.brighter());
        stage.setScene(scene);
        stage.show();

        scene.setOnMouseClicked(e -> {
            GarageWindow garageWindow = new GarageWindow();
            garageWindow.start(new Stage());
            stage.hide();
        });
    }
}
