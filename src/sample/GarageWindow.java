package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.awt.*;

public class GarageWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Parent root = new Group();
        try {
            root = FXMLLoader.load(getClass().getResource("garage_window.fxml"));
        } catch (Exception e){
            e.printStackTrace();
        }
        PlayerTank playerTank = new PlayerTank(20, 20, 3,4);
        Stage stage = new Stage();
        Scene scene = new Scene(root, 600, 600, Color.BLUE.brighter());

        stage.setScene(scene);
        stage.show();



    }
}
