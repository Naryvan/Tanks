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

    private final int enemyAttack = 15;
    private final int enemyHP = 100;

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
            default -> clearLayout();
        }
    }

    private void clearLayout() {
        walls = new ArrayList<>();
        enemyTanks = new ArrayList<>();
    }

    private void firstLevelLayout() {
        walls.add(new Wall(0,0));
        for (int i = 0; i < 700; i += 50) {
            walls.add(new Wall(i, 0));
            walls.add(new Wall(0, i));
            walls.add(new Wall(650, i));
            walls.add(new Wall(i, 650));
        }
        walls.add(new Wall(200, 50));
        walls.add(new Wall(200, 100));
        walls.add(new Wall(200, 150));
        walls.add(new Wall(200, 200));
        walls.add(new Wall(200, 250));
        walls.add(new Wall(150, 250));


        walls.add(new Wall(450, 600));
        walls.add(new Wall(450, 550));
        walls.add(new Wall(450, 500));
        walls.add(new Wall(450, 450));
        walls.add(new Wall(450, 400));
        walls.add(new Wall(500, 400));


        playerTank = new PlayerTank(this, 560, 560, 0);
        PlayerTank.currentHP = PlayerTank.maxHP;
        enemyTanks.add(new EnemyTank(this, 90, 90, 0, 2, 2, enemyAttack, enemyHP));
        //enemyTanks.add(new EnemyTank(this, 575, 125, 0, 2, 2));
    }

    private void secondLevelLayout() {
        walls.add(new Wall(0,0));
        for (int i = 0; i < 700; i += 50) {
            walls.add(new Wall(i, 0));
            walls.add(new Wall(0, i));
            walls.add(new Wall(650, i));
            walls.add(new Wall(i, 650));
        }




        playerTank = new PlayerTank(this, 560, 560, 0);
        PlayerTank.currentHP = PlayerTank.maxHP;
        enemyTanks.add(new EnemyTank(this, 90, 90, 0, 2, 2, enemyAttack, enemyHP));

    }

    private void thirdLevelLayout() {
        walls.add(new Wall(0,0));
        for (int i = 0; i < 700; i += 50) {
            walls.add(new Wall(i, 0));
            walls.add(new Wall(0, i));
            walls.add(new Wall(650, i));
            walls.add(new Wall(i, 650));
        }




        playerTank = new PlayerTank(this, 560, 560, 0);
        PlayerTank.currentHP = PlayerTank.maxHP;
        enemyTanks.add(new EnemyTank(this, 90, 90, 0, 2, 2, enemyAttack, enemyHP));
    }

    private void fourthLevelLayout() {
        walls.add(new Wall(0,0));
        for (int i = 0; i < 700; i += 50) {
            walls.add(new Wall(i, 0));
            walls.add(new Wall(0, i));
            walls.add(new Wall(650, i));
            walls.add(new Wall(i, 650));
        }




        playerTank = new PlayerTank(this, 560, 560, 0);
        PlayerTank.currentHP = PlayerTank.maxHP;
        enemyTanks.add(new EnemyTank(this, 90, 90, 0, 2, 2, enemyAttack, enemyHP));
    }

    private void fifthLevelLayout() {
        walls.add(new Wall(0,0));
        for (int i = 0; i < 700; i += 50) {
            walls.add(new Wall(i, 0));
            walls.add(new Wall(0, i));
            walls.add(new Wall(650, i));
            walls.add(new Wall(i, 650));
        }




        playerTank = new PlayerTank(this, 560, 560, 0);
        PlayerTank.currentHP = PlayerTank.maxHP;
        enemyTanks.add(new EnemyTank(this, 90, 90, 0, 2, 2, enemyAttack, enemyHP));
    }

    private void sixthLevelLayout() {
        walls.add(new Wall(0,0));
        for (int i = 0; i < 700; i += 50) {
            walls.add(new Wall(i, 0));
            walls.add(new Wall(0, i));
            walls.add(new Wall(650, i));
            walls.add(new Wall(i, 650));
        }




        playerTank = new PlayerTank(this, 560, 560, 0);
        PlayerTank.currentHP = PlayerTank.maxHP;
        enemyTanks.add(new EnemyTank(this, 90, 90, 0, 2, 2, enemyAttack, enemyHP));
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
