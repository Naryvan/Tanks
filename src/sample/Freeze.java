package sample;

import javafx.scene.image.Image;

public class Freeze extends Bonus {

    public Freeze(int x, int y, int type){

        super(x, y, type);
        sprite = new Image("/images/Ice.png");

    }
    int type = 0;
}
