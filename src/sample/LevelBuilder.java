package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.scene.canvas.Canvas;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class LevelBuilder {

    protected static ArrayList<Wall> walls = new ArrayList<>();
    protected GraphicsContext gc;

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    protected void addWalls(int currentLevelId) {
        switch (currentLevelId) {
            case 0:
                firstLevelLayout();
                break;
            case 1:
                secondLevelLayout();
                break;
            case 2:
                //thirdLevelLayout();
                break;
            case 3:
                //fourthLevelLayout();
                break;
            case 4:
                //fifthLevelLayout();
                break;
            case 5:
                //sixthLevelLayout();
                break;
        }
    }

    private void firstLevelLayout() {
        for (int i = 25; i < 700; i += 50) {
            walls.add(new Wall(i, 25));
            walls.add(new Wall(25, i));
            walls.add(new Wall(675, i));
        }
    }

    private void secondLevelLayout() {
        for (int i = 25; i < 700; i += 50) {
            walls.add(new Wall(25, i));
            walls.add(new Wall(675, i));
        }
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public ArrayList<Wall> getCloseWalls(double xPos, double yPos) {
        ArrayList<Wall> closeWalls = new ArrayList<>();
        Rectangle2D closeRectangle = new Rectangle2D(xPos - 100, yPos - 100, 200, 200);

        for (Wall wall : walls) {
            if (wall.getBoundary().intersects(closeRectangle)) {
                closeWalls.add(wall);
            }
        }

        return closeWalls;
    }

    public GraphicsContext getGraphicsContext() {
        return gc;
    }
}
