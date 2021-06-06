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
    protected  static ArrayList<Bonus> bonuses = new ArrayList<>();
    protected GraphicsContext gc;
    protected ArrayList<Effect> effects = new ArrayList<>();


    private int enemyAttack;
    private int enemyHP;

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
        bonuses = new ArrayList<>();
        enemyTanks = new ArrayList<>();
    }

    private void firstLevelLayout() {
        enemyAttack = 15;
        enemyHP = 100;
        setWallBorder();
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

        bonuses.add(new Freeze(100, 500, 0));
        bonuses.add(new Heal(350, 350, 2));
        bonuses.add(new Haste(500, 100, 1));


        playerTank = new PlayerTank(this, 560, 560, 0);
        PlayerTank.currentHP = PlayerTank.maxHP;
        enemyTanks.add(new EnemyTank(this, 90, 90, 0, 2, 2, enemyAttack, enemyHP));
    }

    private void setWallBorder() {
        walls.add(new Wall(0, 0));
        for (int i = 0; i < 700; i += 50) {
            walls.add(new Wall(i, 0));
            walls.add(new Wall(0, i));
            walls.add(new Wall(650, i));
            walls.add(new Wall(i, 650));
        }
    }

    private void secondLevelLayout() {
        enemyAttack = 20;
        enemyHP = 120;
        setWallBorder();
        for (int i = 50; i < 500; i += 50) {
            walls.add(new Wall(i, 200));
        }

        for (int i = 200; i < 650; i += 50) {
            walls.add(new Wall(i, 400));
        }

        bonuses.add(new Heal(350, 320, 2));
        bonuses.add(new Heal(350, 120, 2));
        bonuses.add(new Haste(350, 550, 1));
        bonuses.add(new Freeze(620, 620, 1));

        playerTank = new PlayerTank(this, 560, 560, 0);
        PlayerTank.currentHP = PlayerTank.maxHP;
        enemyTanks.add(new EnemyTank(this, 90, 90, 0, 2, 2, enemyAttack, enemyHP));
        enemyTanks.add(new EnemyTank(this, 610, 90, 0, 2, 2, enemyAttack, enemyHP));

    }

    private void thirdLevelLayout() {
        enemyAttack = 20;
        enemyHP = 120;
        setWallBorder();

        for (int k = 0; k < 2; k++) {
            for (int i = 100; i < 700; i += 150) {
                walls.add(new Wall(i, 50 + 300 * k));
            }

            for (int i = 150; i < 700; i += 150) {
                walls.add(new Wall(i, 150 + 300 * k));
            }

            for (int i = 50; i < 700; i += 150) {
                walls.add(new Wall(i, 250 + 300 * k));
            }
        }
        bonuses.add(new Freeze(60, 60, 1));
        bonuses.add(new Heal(350, 610, 2));
        bonuses.add(new Heal(610, 110, 2));
        bonuses.add(new Haste(350, 350, 1));


        playerTank = new PlayerTank(this, 610, 610, 0);
        PlayerTank.currentHP = PlayerTank.maxHP;
        enemyTanks.add(new EnemyTank(this, 90, 90, 0, 2, 2, enemyAttack, enemyHP));
        enemyTanks.add(new EnemyTank(this, 90, 610, 0, 2, 2, enemyAttack, enemyHP));
        enemyTanks.add(new EnemyTank(this, 610, 90, 0, 2, 2, enemyAttack, enemyHP));
    }

    private void fourthLevelLayout() {
        enemyAttack = 30;
        enemyHP = 160;
        setWallBorder();

        for (int i = 150; i < 300; i += 50) {
            walls.add(new Wall(i, 150));
            walls.add(new Wall(i + 250, 150));
            walls.add(new Wall(i + 250, 500));
            walls.add(new Wall(i, 500));
        }

        walls.add(new Water(200, 300));
        walls.add(new Water(200, 350));
        walls.add(new Water(450, 300));
        walls.add(new Water(450, 350));

        for (int i = 200; i < 250; i += 50) {
            walls.add(new Wall(150, i));
            walls.add(new Wall(150, i + 250));
            walls.add(new Wall(500, i + 250));
            walls.add(new Wall(500, i));
        }


        playerTank = new PlayerTank(this, 350, 350, 0);
        PlayerTank.currentHP = PlayerTank.maxHP;
        enemyTanks.add(new EnemyTank(this, 90, 90, 0, 2, 2, enemyAttack, enemyHP));
        enemyTanks.add(new EnemyTank(this, 90, 610, 0, 2, 2, enemyAttack, enemyHP));
        enemyTanks.add(new EnemyTank(this, 610, 90, 0, 2, 2, enemyAttack, enemyHP));
        enemyTanks.add(new EnemyTank(this, 610, 610, 0, 2, 2, enemyAttack, enemyHP));
    }

    private void fifthLevelLayout() {
        enemyAttack = 35;
        enemyHP = 180;
        setWallBorder();

        for (int i = 150; i < 350; i += 50) {
            walls.add(new Wall(200, i));
            walls.add(new Wall(450, 650 - i));
            walls.add(new Wall(i, 450));
            walls.add(new Wall(650 - i, 200));
        }

        walls.add(new Water(200, 50));
        walls.add(new Water(200, 100));

        walls.add(new Water(450, 600));
        walls.add(new Water(450, 550));

        walls.add(new Water(50, 450));
        walls.add(new Water(100, 450));

        walls.add(new Water(600, 200));
        walls.add(new Water(550, 200));


        playerTank = new PlayerTank(this, 560, 560, 0);
        PlayerTank.currentHP = PlayerTank.maxHP;
        enemyTanks.add(new EnemyTank(this, 90, 90, 0, 2, 2, enemyAttack, enemyHP));
        enemyTanks.add(new EnemyTank(this, 90, 610, 0, 2, 2, enemyAttack, enemyHP));
        enemyTanks.add(new EnemyTank(this, 610, 90, 0, 2, 2, enemyAttack, enemyHP));
        enemyTanks.add(new EnemyTank(this, 350, 350, 0, 2, 2, enemyAttack, enemyHP));
    }

    private void sixthLevelLayout() {
        enemyAttack = 60;
        enemyHP = 200;
        setWallBorder();

        for (int i = 50; i < 650; i += 50) {
            walls.add(new Wall(i, 250));
            walls.add(new Wall(i, 400));
        }

        for (int i = 50; i < 250; i += 50) {
            walls.add(new Water(250, i));
            walls.add(new Water(250, i + 400));
            walls.add(new Water(450, i));
            walls.add(new Water(450, i + 400));
        }


        playerTank = new PlayerTank(this, 350, 350, 0);
        PlayerTank.currentHP = PlayerTank.maxHP;
        enemyTanks.add(new EnemyTank(this, 150, 80, 0, 2, 2, enemyAttack, enemyHP));
        enemyTanks.add(new EnemyTank(this, 80, 150, 0, 2, 2, enemyAttack, enemyHP));
        enemyTanks.add(new EnemyTank(this, 150, 620, 0, 2, 2, enemyAttack, enemyHP));
        enemyTanks.add(new EnemyTank(this, 80, 570, 0, 2, 2, enemyAttack, enemyHP));
        enemyTanks.add(new EnemyTank(this, 620, 150, 0, 2, 2, enemyAttack, enemyHP));
        enemyTanks.add(new EnemyTank(this, 570, 80, 0, 2, 2, enemyAttack, enemyHP));
        enemyTanks.add(new EnemyTank(this, 620, 570, 0, 2, 2, enemyAttack, enemyHP));
        enemyTanks.add(new EnemyTank(this, 570, 620, 0, 2, 2, enemyAttack, enemyHP));
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public ArrayList<Bonus> getBonuses() {return bonuses;}

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

    public ArrayList<Effect> getEffects() {
        return effects;
    }
}
