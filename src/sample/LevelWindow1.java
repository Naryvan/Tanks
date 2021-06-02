package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class LevelWindow1 {

    public static int FIELD_WIDTH = 700;
    public static int FIELD_HEIGHT = 700;

    protected static ArrayList<Wall> walls = new ArrayList<>();
    protected GraphicsContext gc;

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    /*public void start(Stage primaryStage) {
        Group root = new Group();
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, FIELD_WIDTH, FIELD_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
        Canvas canvas = new Canvas(FIELD_WIDTH, FIELD_HEIGHT);
        root.getChildren().add(canvas);
        gc = canvas.getGraphicsContext2D();

        ArrayList<String> input = new ArrayList<>();
        Point mousePos = new Point();

        scene.setOnKeyPressed(
                keyEvent -> {
                    String code = keyEvent.getCode().toString();
                    if(!input.contains(code)) {
                        input.add(code);
                    }
                }
        );
        scene.setOnKeyReleased(
                keyEvent -> {
                    String code = keyEvent.getCode().toString();
                    input.remove(code);
                }
        );
        scene.setOnMouseMoved(
                mouseEvent -> mousePos.setLocation(mouseEvent.getX(), mouseEvent.getY())
        );

        PlayerTank tank = new PlayerTank(this, 50, 50, 0, 4, 2);

        addWalls();

        new AnimationTimer() {
            @Override
            public void handle(long CurrentNanoTime) {
                tank.operate(input, mousePos);

                gc.clearRect(0, 0, FIELD_WIDTH, FIELD_HEIGHT);

                tank.render();

                for(Wall wall : walls) {
                    wall.render(gc);
                }
            }
        }.start();
    }*/

    protected void addWalls(int currentLevelId) {
        switch (currentLevelId) {
            case 0:
                firstLevelLayout();
                break;
            case 1:
                secondLevelLayout();
                break;
            case 2:
                //thirdLevelLayout();
                break;
            case 3:
                //fourthLevelLayout();
                break;
            case 4:
                //fifthLevelLayout();
                break;
            case 5:
                //sixthLevelLayout();
                break;
        }
    }

    private void firstLevelLayout() {
        for (int i = 25; i < 700; i += 50) {
            walls.add(new Wall(i, 25));
            walls.add(new Wall(25, i));
            walls.add(new Wall(675, i));
        }
    }

    private void secondLevelLayout() {
        for (int i = 25; i < 700; i += 50) {
            walls.add(new Wall(25, i));
            walls.add(new Wall(675, i));
        }
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public ArrayList<Wall> getCloseWalls(double xPos, double yPos) {
        ArrayList<Wall> closeWalls = new ArrayList<>();
        Rectangle2D closeRectangle = new Rectangle2D(xPos - 100, yPos - 100, 200, 200);

        for (Wall wall : walls) {
            if (wall.getBoundary().intersects(closeRectangle)) {
                closeWalls.add(wall);
            }
        }

        return closeWalls;
    }

    public GraphicsContext getGraphicsContext() {
        return gc;
    }
}
