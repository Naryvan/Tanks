package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;

public class Scenes {
    public static Scene garageWindow;

    static Scene getGarageWindow(Class getClass) {

        try {
            garageWindow = new Scene(FXMLLoader.load(getClass.getResource("garage_window.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return garageWindow;
    }


    public static Scene tankUpgradeWindow;

    static Scene getTankUpgradeWindow(Class getClass) {
        try {
            tankUpgradeWindow = new Scene(FXMLLoader.load(getClass.getResource("tank_upgrade.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tankUpgradeWindow;
    }

    public static Scene startGameMenu;

    static Scene getStartGameMenu(Class getClass) {
        try {
            startGameMenu = new Scene(FXMLLoader.load(getClass.getResource("start_menu.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return startGameMenu;
    }

    public static Scene levelScene;

    static Scene getLevelScene(Class getClass) {
        try {
            levelScene = new Scene(FXMLLoader.load(getClass.getResource("level_layout.fxml")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return levelScene;
    }


}
