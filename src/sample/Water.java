package sample;

import javafx.scene.image.Image;

public class Water extends Wall{

    public Water(int x, int y){
        super(x, y);
        sprite = new Image("/images/Water.png");
        blocksBullets = false;
    }

}
