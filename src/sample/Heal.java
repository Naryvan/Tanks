package sample;

import javafx.scene.image.Image;

public class Heal extends Bonus{

    public Heal(int x, int y, int type){

        super(x, y, type);
        sprite = new Image("/images/Heal.png");

    }
    int type = 2;
}
