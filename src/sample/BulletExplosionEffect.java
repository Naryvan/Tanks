package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;

public class BulletExplosionEffect extends Effect {

    Tank tank;
    double tankOffsetX;
    double tankOffsetY;

    public BulletExplosionEffect(LevelBuilder levelBuilder, Tank tank, int x, int y) {
        super(levelBuilder, x, y);
        this.tank = tank;
        tankOffsetX = tank.xPos - x;
        tankOffsetY = tank.yPos - y;
        effectTimer = 20;
        sprite = new Image("images/Explosion.png");
    }

    @Override
    public void process() {
        if(effectTimer == 0 || tank == null) {
            effectsList.remove(this);
            return;
        }

        double xPos = tank.xPos - tankOffsetX;
        double yPos = tank.yPos - tankOffsetY;

        if((effectTimer / 5) % 2 == 0) {
            gc.drawImage(sprite, xPos - 20, yPos - 20);
        }
        else {
            gc.save();
            gc.transform(new Affine(new Rotate(180, xPos, yPos)));
            gc.drawImage(sprite, xPos - 20, yPos - 20);
            gc.restore();
        }

        effectTimer--;
    }
}
