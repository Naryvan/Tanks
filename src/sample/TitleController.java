package sample;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import javafx.util.Duration;

import java.nio.file.Paths;
import java.util.ResourceBundle;

public class TitleController implements Initializable {
    public ImageView backgroundImage;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Pane textPanel;

    public void switchToGarageScene(javafx.scene.input.InputEvent e) {
        FadeTransition fade = new FadeTransition();
        fade.setOnFinished(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    root = FXMLLoader.load(getClass().getResource("garage_window.fxml"));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        });
        fade.setNode(textPanel);
        fade.setDuration(Duration.millis(500));
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.play();
    }

    @FXML
    private Text keyPressPrompt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        backgroundImage.setFocusTraversable(true);
        FadeTransition fade = new FadeTransition();
        fade.setNode(keyPressPrompt);
        fade.setCycleCount(Animation.INDEFINITE);
        fade.setDuration(Duration.millis(900));
        fade.setAutoReverse(true);
        fade.setFromValue(1);
        fade.setToValue(0.3);
        fade.play();
    }


}
