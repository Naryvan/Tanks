package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Timer;
import java.util.TimerTask;


public class Bullet {
    private boolean playerBullet;
    double bulletX;
    double bulletY;
    double angle;
    int bulletSpeed = 15;

    GraphicsContext gc;

    public boolean isPlayerBullet() {
        return playerBullet;
    }

    public void setPlayerBullet(boolean playerBullet) {
        this.playerBullet = playerBullet;
    }

    public Bullet(double x, double y, double angle, GraphicsContext gc) {
        this.bulletX = x;
        this.bulletY = y;
        this.angle = angle;
        this.gc = gc;
    }

    public void renderBullet() {
        gc.save();
        gc.setFill(Color.BLACK);
        gc.strokeOval((bulletX - 5) + (Math.cos(Math.toRadians(angle))) * 30, (bulletY - 5) + (Math.sin(Math.toRadians(angle))) * 30, 10, 10);
        gc.fillOval((bulletX - 5) + (Math.cos(Math.toRadians(angle))) * 30, (bulletY - 5) + (Math.sin(Math.toRadians(angle))) * 30, 10, 10);
    }

    public void moveBullet() {
        bulletX += (Math.cos(Math.toRadians(angle))) * bulletSpeed;
        bulletY += (Math.sin(Math.toRadians(angle))) * bulletSpeed;
    }

}