package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Wall {

    int x;
    int y;

    public Wall(int x, int y){
        this.x = x;
        this.y = y;
    }

    private void drawWall(GraphicsContext gc){
        gc.setFill(Color.rgb(156,36,36));
        gc.save();
        gc.fillRect(x - 25,  y - 25, 50, 50);
        gc.strokeRect(x - 25, y - 25, 50, 50);
        gc.restore();
    }

    public void render(GraphicsContext gc) {
        drawWall(gc);
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(x - 25, y - 25, 50, 50);
    }
}
