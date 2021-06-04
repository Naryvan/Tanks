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
    protected PlayerTank playerTank;
    protected ArrayList<EnemyTank> enemyTanks = new ArrayList<>();
    protected GraphicsContext gc;

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    protected void addWalls(int currentLevelId) {
        switch (currentLevelId) {
            case 0 -> firstLevelLayout();
            case 1 -> secondLevelLayout();
            case 2 -> thirdLevelLayout();
            case 3 -> fourthLevelLayout();
            case 4 -> fifthLevelLayout();
            case 5 -> sixthLevelLayout();
        }
    }

    private void firstLevelLayout() {
        for (int i = 25; i < 700; i += 50) {
            walls.add(new Wall(i, 25));
            walls.add(new Wall(25, i));
            walls.add(new Wall(675, i));
            walls.add(new Wall(i, 675));
        }
        walls.add(new Wall(2 * 50 + 25, 2 * 50 + 25));
        walls.add(new Wall(2 * 50 + 25, 3 * 50 + 25));
        walls.add(new Wall(2 * 50 + 25, 4 * 50 + 25));
        walls.add(new Wall(2 * 50 + 25, 6 * 50 + 25));
        walls.add(new Wall(1 * 50 + 25, 6 * 50 + 25));
        walls.add(new Wall(4 * 50 + 25, 2 * 50 + 25));
        walls.add(new Wall(4 * 50 + 25, 3 * 50 + 25));
        walls.add(new Wall(4 * 50 + 25, 4 * 50 + 25));
        walls.add(new Wall(4 * 50 + 25, 5 * 50 + 25));
        walls.add(new Wall(4 * 50 + 25, 6 * 50 + 25));
        walls.add(new Wall(4 * 50 + 25, 7 * 50 + 25));
        walls.add(new Wall(4 * 50 + 25, 8 * 50 + 25));
        walls.add(new Wall(4 * 50 + 25, 9 * 50 + 25));
        walls.add(new Wall(3 * 50 + 25, 9 * 50 + 25));
        walls.add(new Wall(2 * 50 + 25, 9 * 50 + 25));
        walls.add(new Wall(2 * 50 + 25, 8 * 50 + 25));
        walls.add(new Wall(2 * 50 + 25, 11 * 50 + 25));
        walls.add(new Wall(3 * 50 + 25, 11 * 50 + 25));
        walls.add(new Wall(4 * 50 + 25, 11 * 50 + 25));
        walls.add(new Wall(5 * 50 + 25, 11 * 50 + 25));
        walls.add(new Wall(6 * 50 + 25, 11 * 50 + 25));
        walls.add(new Wall(7 * 50 + 25, 11 * 50 + 25));
        walls.add(new Wall(8 * 50 + 25, 11 * 50 + 25));
        walls.add(new Wall(9 * 50 + 25, 11 * 50 + 25));
        walls.add(new Wall(10 * 50 + 25, 11 * 50 + 25));
        walls.add(new Wall(11 * 50 + 25, 11 * 50 + 25));
        walls.add(new Wall(10 * 50 + 25, 1 * 50 + 25));
        walls.add(new Wall(10 * 50 + 25, 2 * 50 + 25));
        walls.add(new Wall(10 * 50 + 25, 3 * 50 + 25));
        walls.add(new Wall(11 * 50 + 25, 3 * 50 + 25));
        walls.add(new Wall(12 * 50 + 25, 3 * 50 + 25));

        playerTank = new PlayerTank(this, 525, 525, 0);
        enemyTanks.add(new EnemyTank(this, 75, 75, 0, 2, 2));
        //enemyTanks.add(new EnemyTank(this, 575, 125, 0, 2, 2));
    }

    private void secondLevelLayout() {
        for (int i = 25; i < 700; i += 50) {
            walls.add(new Wall(25, i));
            walls.add(new Wall(675, i));
        }
    }

    private void thirdLevelLayout() {
        for (int i = 25; i < 700; i += 50) {
            walls.add(new Wall(25, i));
            walls.add(new Wall(675, i));
        }
    }

    private void fourthLevelLayout() {
        for (int i = 25; i < 700; i += 50) {
            walls.add(new Wall(25, i));
            walls.add(new Wall(675, i));
        }
    }

    private void fifthLevelLayout() {
        for (int i = 25; i < 700; i += 50) {
            walls.add(new Wall(25, i));
            walls.add(new Wall(675, i));
        }
    }

    private void sixthLevelLayout() {
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

    public PlayerTank getPlayerTank() {
        return playerTank;
    }

    public ArrayList<EnemyTank> getEnemyTanks() {
        return enemyTanks;
    }

}
