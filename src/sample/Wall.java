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
        gc.setFill(Color.rgb(156,36,36));
        gc.save();
        gc.fillRect(x,  y, 50, 50);
        gc.strokeRect(x, y, 50, 50);
        gc.restore();
    }

    public void render(GraphicsContext gc) {
        drawWall(gc);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(x , y, 50, 50);
    }
}
