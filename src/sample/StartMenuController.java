package sample;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartMenuController implements Initializable {
    public Pane mainPanel;
    private Parent root;
    private static final Level[] levels = new Level[6];

    public static Level[] getLevels() {
        return levels;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < levels.length; i++) {
            if (levels[i].isLocked()) {
                mainPanel.lookup(levels[i].getLevelPane()).setCursor(Cursor.DEFAULT);
            } else {
                if (i == 0) continue;
                mainPanel.lookup("#lockLevel" + i).setOpacity(0);
            }
            if (levels[i].isCompleted()) {
                SplitPane splitPane = (SplitPane) mainPanel.lookup(levels[i].getLevelPane());
                splitPane.setOpacity(0.1);
                ((Text) splitPane.getItems().get(0)).setFill(Color.GREEN);
                ((Text) splitPane.getItems().get(1)).setFill(Color.GREEN);
                ((Text) splitPane.getItems().get(1)).setText("Completed");
            }
        }
    }

    public void returnToGarage(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("garage_window.fxml"));
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showBorder(MouseEvent mouseEvent) {
        boolean canChangeBorder = false;
        Pane pane = (Pane) mouseEvent.getSource();
        for (Level level : levels) {
            if (("#" + pane.getId()).equals(level.getLevelPane()) && !level.isLocked()) {
                canChangeBorder = true;
            }
        }

        if (canChangeBorder) {
            pane.setBorder(new Border(new BorderStroke(Color.web("#b50909"),
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        }
    }

    public void hideBorder(MouseEvent mouseEvent) {
        ((Pane) mouseEvent.getSource()).setBorder(null);
    }

    public void setLevelLayout(MouseEvent mouseEvent) {
        boolean canStartLevel = false;
        Pane pane = (Pane) mouseEvent.getSource();
        for (Level level : levels) {
            if (("#" + pane.getId()).equals(level.getLevelPane()) && !level.isLocked()) {
                canStartLevel = true;
            }
        }

        if (canStartLevel) {
            System.out.println("Go to another level");
        } else {
            System.out.println("Level locked");
        }
    }
}
