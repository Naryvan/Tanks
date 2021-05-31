package sample;

import java.awt.*;
import java.util.ArrayList;

public class PlayerTank extends Tank {

    public PlayerTank(double xPos, double yPos, int direction, double maxSpeed, double gunRotationSpeed) {
        super(xPos, yPos, direction, maxSpeed, gunRotationSpeed);
    }

    public void operate(ArrayList<String> input, Point mousePos) {
        if(input.contains("W")) {
            isMoving = true;
            direction = 0;
            if(input.contains("D")) {
                direction = 1;
            }
            else if(input.contains("A")) {
                direction = 7;
            }
        }
        else if(input.contains("D")) {
            isMoving = true;
            direction = 2;
            if(input.contains("S")) {
                direction = 3;
            }
            else if(input.contains("W")) {
                direction = 1;
            }
        }
        else if(input.contains("S")) {
            isMoving = true;
            direction = 4;
            if(input.contains("A")) {
                direction = 5;
            }
            else if(input.contains("D")) {
                direction = 3;
            }
        }
        else if(input.contains("A")) {
            isMoving = true;
            direction = 6;
            if(input.contains("W")) {
                direction = 7;
            }
            else if(input.contains("S")) {
                direction = 5;
            }
        }
        else {
            isMoving = false;
        }

        processMovement();
        rotateGun(mousePos);
    }

}
