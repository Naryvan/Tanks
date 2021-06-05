package sample;

import javafx.scene.image.Image;

public class Heal extends Bonus{

    public Heal(int x, int y){
        super(x, y, 2);
        sprite = new Image("images/HealthBonus.png");
    }

}
