package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bonus {

    int x;
    int y;
    int type;
    protected Image sprite;

    public Bonus(int x, int y, int type){

        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getType(){
        return(type);
    }

    private void drawBonus(GraphicsContext gc){
        gc.drawImage(sprite, x, y);
    }

    public void render(GraphicsContext gc) { drawBonus(gc); }

    public Rectangle2D getBoundaryOfBonus() {
        return new Rectangle2D(x , y, 50, 50);
    }
}
