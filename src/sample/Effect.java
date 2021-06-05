package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.ArrayList;

public abstract class Effect {

    Image sprite;
    GraphicsContext gc;
    ArrayList<Effect> effectsList;
    int x;
    int y;
    int effectTimer;

    public Effect(LevelBuilder levelBuilder, int x, int y) {
        effectsList = levelBuilder.getEffects();
        effectsList.add(this);
        gc = levelBuilder.getGraphicsContext();
        this.x = x;
        this.y = y;
    }

    public abstract void process();

}
