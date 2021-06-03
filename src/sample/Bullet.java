package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


public class Bullet {

    double bulletX;
    double bulletY;
    double angle;

    GraphicsContext gc;

    public Bullet(double x, double y, double angle, GraphicsContext gc){
        this.bulletX = x;
        this.bulletY = y;
        this.angle = angle;
        this.gc = gc;
    }
//
    //

    public void renderBullet(){
        gc.save();
        gc.setFill(Color.BLACK);
        gc.strokeOval((bulletX-5) + (Math.cos(Math.toRadians(angle)))*30, (bulletY-5) + (Math.sin(Math.toRadians(angle)))*30, 10, 10);
        gc.fillOval((bulletX-5) + (Math.cos(Math.toRadians(angle)))*30, (bulletY-5) + (Math.sin(Math.toRadians(angle)))*30, 10, 10);
    }

    public void moveBullet(){

    }

}
