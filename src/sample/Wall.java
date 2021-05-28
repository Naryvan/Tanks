package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Wall {

    int a;
    int b;

    public Wall(int a, int b){
        this.a = a;
        this.b = b;
    }

    private void drawWall(GraphicsContext gc){
        gc.setFill(Color.rgb(156,36,36));
        gc.save();
        gc.fillRect((a-1)*50,  (b-1)*50, a*50, b*50);
        gc.strokeRect((a-1)*50, (b-1)*50, a*50, b*50);
        gc.restore();

    }
    public void render(GraphicsContext gc) {
        drawWall(gc);
    }
}

