package sample;

import javafx.scene.canvas.GraphicsContext;

import java.awt.*;
import java.util.ArrayList;

public class PlayerTank extends Tank {

    public static int attack = 25;
    public static int hp = 100;
    public static int maxSpeed = 2;
    public static int aimSpeed = 2;

    //For garage
    public PlayerTank(GraphicsContext gc, double xPos, double yPos, int direction) {
        super(gc, xPos, yPos, direction, maxSpeed, aimSpeed);
    }

    //For levels
    public PlayerTank(LevelBuilder levelBuilder, double xPos, double yPos, int direction) {
        super(levelBuilder, xPos, yPos, direction, maxSpeed, aimSpeed);
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    public void operate(ArrayList<String> input, Point mousePos) {
        if (input.contains("W")) {
            isMoving = true;
            direction = 0;
            if (input.contains("D")) {
                direction = 1;
            } else if (input.contains("A")) {
                direction = 7;
            }
        } else if (input.contains("D")) {
            isMoving = true;
            direction = 2;
            if (input.contains("S")) {
                direction = 3;
            } else if (input.contains("W")) {
                direction = 1;
            }
        } else if (input.contains("S")) {
            isMoving = true;
            direction = 4;
            if (input.contains("A")) {
                direction = 5;
            } else if (input.contains("D")) {
                direction = 3;
            }
        } else if (input.contains("A")) {
            isMoving = true;
            direction = 6;
            if (input.contains("W")) {
                direction = 7;
            } else if (input.contains("S")) {
                direction = 5;
            }
        } else {
            isMoving = false;
        }

        processMovement();
        rotateGun(mousePos);
    }

}
