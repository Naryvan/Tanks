package sample;

public class Level1 extends LevelBuilder {

    protected void addWalls() {
        walls.add(new Wall(250, 200));
        walls.add(new Wall(250, 250));
        walls.add(new Wall(250, 300));
        walls.add(new Wall(250, 350));
    }

    public static void main(String[] args) {
        launch(args);
    }

}