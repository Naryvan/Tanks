package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Wall {

    int x;
    int y;

    public boolean isDamaged = false;

    public boolean blocksBullets = true;

    protected Image sprite;

    public Wall(int x, int y){
        this.x = x;
        this.y = y;
        sprite = new Image("/images/Wall.png");
    }

    private void drawWall(GraphicsContext gc){
        gc.drawImage(sprite, x - 25, y - 25);
    }

    public void damage() {
        isDamaged = true;
        sprite = new Image("/images/DamagedWall.png");
    }

    public void render(GraphicsContext gc) {
        drawWall(gc);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(x - 25, y - 25, 50, 50);
    }
}
