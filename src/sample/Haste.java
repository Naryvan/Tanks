package sample;

import javafx.scene.image.Image;

public class Haste extends Bonus{

    public Haste(int x, int y, int type){

        super(x, y, type);
        sprite = new Image("/images/Haste.png");

    }
    int type = 1;
}
