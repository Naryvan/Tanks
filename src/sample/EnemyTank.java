package sample;

import com.sun.javafx.geom.Line2D;
import com.sun.javafx.geom.Point2D;
import com.sun.javafx.geom.RectBounds;
import javafx.scene.image.Image;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class EnemyTank extends Tank {

    private final static int NEW_PATH_COUNTER = 60;
    private final static int NEW_IDLE_DIRECTION_COUNTER = 240;
    private final static int FREEZE_COUNTER = 480;

    PlayerTank playerTank;
    private Path currentPath;

    private int newPathCounter = 0;
    private int newIdleDirectionCounter = 0;
    private int frozenCounter = 0;

    private boolean frozen = false;

    Random random = new Random();

    private int attackPower;
    private int totalHP;

    public int getAttackPower() {
        return attackPower;
    }

    public int getTotalHP() {
        return totalHP;
    }

    public void setTotalHP(int totalHP) {
        this.totalHP = totalHP;
    }

    public void setAttackPower(int attackPower) {
        this.attackPower = attackPower;
    }

    public EnemyTank(LevelBuilder levelBuilder, double xPos, double yPos, int direction, double maxSpeed, double gunRotationSpeed, int attackPower, int totalHP) {
        super(levelBuilder, xPos, yPos, direction, maxSpeed, gunRotationSpeed);
        playerTank = levelBuilder.getPlayerTank();
        spriteName = "EnemyTank";
        this.attackPower = attackPower;
        this.totalHP = totalHP;
    }

    public void operate() {
        if (frozen) {
            if (frozenCounter == 0) {
                frozen = false;
            } else {
                frozenCounter--;
                isMoving = false;
                return;
            }
        }

        if (newPathCounter == 0) {
            newPathCounter = NEW_PATH_COUNTER;
            if (currentPath == null) {
                findPath();
            }
        } else {
            newPathCounter--;
        }

        if (currentPath != null) {
            followPath();
        } else {
            idleMovement();
        }

        if (isLoaded && hasLineOfSight()) {
            createBullet(gc, false);
            isLoaded = false;
        }

        rotateGun(new Point((int) playerTank.getX(), (int) playerTank.getY()));
    }

    public void freeze() {
        frozen = true;
        frozenCounter = FREEZE_COUNTER;
    }

    private void followPath() {
        int currentNodeX = currentPath.currentNode.tile.xCord;
        int currentNodeY = currentPath.currentNode.tile.yCord;
        if (Math.abs(currentNodeX - xPos) < 3 && Math.abs(currentNodeY - yPos) < 3) {
            xPos = currentNodeX;
            yPos = currentNodeY;
            if (currentPath.nextNode() == null) {
                isMoving = false;
                currentPath = null;
            } else if (currentPath.currentNode.previousNode != currentPath.startNode) {
                findPath();
            }
            return;
        }

        if (currentNodeY < yPos) {
            isMoving = true;
            direction = 0;
            if (currentNodeX > xPos) {
                direction = 1;
            } else if (currentNodeX < xPos) {
                direction = 7;
            }
        } else if (currentNodeY > yPos) {
            isMoving = true;
            direction = 4;
            if (currentNodeX > xPos) {
                direction = 3;
            } else if (currentNodeX < xPos) {
                direction = 5;
            }
        } else if (currentNodeX > xPos) {
            isMoving = true;
            direction = 2;
        } else if (currentNodeX < xPos) {
            isMoving = true;
            direction = 6;
        } else {
            isMoving = false;
        }

        processMovement();
    }

    private void idleMovement() {
        if (isTopBlocked && isLeftBlocked && isRightBlocked && isBottomBlocked) {
            isMoving = false;
            return;
        }

        if (newIdleDirectionCounter == 0) {
            newIdleDirectionCounter = NEW_IDLE_DIRECTION_COUNTER;
            while (true) {
                direction = random.nextInt(4) * 2;
                if (direction == 0 && isTopBlocked) {
                    continue;
                } else if (direction == 2 && isRightBlocked) {
                    continue;
                } else if (direction == 4 && isBottomBlocked) {
                    continue;
                } else if (direction == 6 && isLeftBlocked) {
                    continue;
                }
                break;
            }
        } else {
            newIdleDirectionCounter--;
        }

        isMoving = (direction != 0 || !isTopBlocked) && (direction != 2 || !isRightBlocked) &&
                (direction != 4 || !isBottomBlocked) && (direction != 6 || !isLeftBlocked);
        processMovement();
    }

    private boolean hasLineOfSight() {
        Line lineOfSight = new Line(xPos, yPos, playerTank.xPos, playerTank.yPos);

        ArrayList<Wall> walls = levelBuilder.getWalls();
        for (Wall wall : walls) {
            if(!wall.blocksBullets) {
                continue;
            }
            Rectangle wallBoundary = new Rectangle(wall.x, wall.y, 50, 50);
            if (lineOfSight.getBoundsInParent().intersects(wallBoundary.getBoundsInParent())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void render() {
        super.render();
    }

    private void findPath() {
        Path path = new Path(this.getTileCoordinates());
        if (path.findPath(new Tile(playerTank.getTileCoordinates()))) {
            currentPath = path;
        } else {
            currentPath = null;
        }
    }

    private class Path {
        ArrayList<Tile> blockedTiles;

        Node startNode;
        Node currentNode;

        private Path(Point startPoint) {
            startNode = new Node(new Tile(startPoint), null, Node.START);
            currentNode = startNode;
            blockedTiles = getBlockedTiles();
            blockedTiles.add(new Tile(startPoint));
        }

        private Node nextNode() {
            if (!currentNode.isLast) {
                currentNode = currentNode.nextNode;
                return currentNode;
            }
            return null;
        }

        private boolean findPath(Tile destinationTile) {
            while (true) {
                assert currentNode != null;
                Node nextNode = currentNode.findNextNode(blockedTiles, destinationTile);
                if (nextNode == null) {
                    if (currentNode.isNodeBlocked()) {
                        if (currentNode == startNode) {
                            return false;
                        }
                        currentNode = currentNode.blockedGetPreviousNode();
                    } else {
                        if (currentNode == startNode) {
                            currentNode.notBlockedGetPreviousNode();
                            return true;
                        }
                        currentNode = currentNode.notBlockedGetPreviousNode();
                        if (currentNode == startNode) {
                            blockedTiles = getBlockedTiles();
                        }
                    }
                } else {
                    if (nextNode.tile.equals(destinationTile)) {
                        currentNode.isLast = true;
                        if (currentNode == startNode) {
                            return true;
                        }
                        currentNode = currentNode.notBlockedGetPreviousNode();
                    } else {
                        blockedTiles.add(nextNode.tile);
                        currentNode = nextNode;
                    }
                }
            }
        }

        private ArrayList<Tile> getBlockedTiles() {
            ArrayList<Tile> blockedTiles = new ArrayList<>();
            for (Wall wall : levelBuilder.getWalls()) {
                blockedTiles.add(new Tile(wall.x + 25, wall.y + 25));
            }
            return blockedTiles;
        }

        private class Node {
            private static final int START = 0;
            private static final int TOP = 1;
            private static final int BOTTOM = 2;
            private static final int LEFT = 3;
            private static final int RIGHT = 4;

            Tile tile;

            int nodeType;

            boolean isLast;

            int stepsTopToDestination = 0;
            int stepsBottomToDestination = 0;
            int stepsLeftToDestination = 0;
            int stepsRightToDestination = 0;

            Node nextNode;
            Node previousNode;

            Node nextTopNode;
            Node nextBottomNode;
            Node nextLeftNode;
            Node nextRightNode;

            private Node(Tile tile, Node previousNode, int nodeType) {
                this.tile = tile;
                this.previousNode = previousNode;
                this.nodeType = nodeType;
            }

            private Node findNextNode(ArrayList<Tile> blockedTiles, Tile destinationTile) {
                ArrayList<Node> possibleNodes = new ArrayList<>();
                if (stepsTopToDestination == 0) {
                    if (tile.isTopTileBlocked(blockedTiles)) {
                        stepsTopToDestination = -1;
                    } else {
                        possibleNodes.add(new Node(tile.getTopTile(), this, TOP));
                    }
                }
                if (stepsBottomToDestination == 0) {
                    if (tile.isBottomTileBlocked(blockedTiles)) {
                        stepsBottomToDestination = -1;
                    } else {
                        possibleNodes.add(new Node(tile.getBottomTile(), this, BOTTOM));
                    }
                }
                if (stepsLeftToDestination == 0) {
                    if (tile.isLeftTileBlocked(blockedTiles)) {
                        stepsLeftToDestination = -1;
                    } else {
                        possibleNodes.add(new Node(tile.getLeftTile(), this, LEFT));
                    }
                }
                if (stepsRightToDestination == 0) {
                    if (tile.isRightTileBlocked(blockedTiles)) {
                        stepsRightToDestination = -1;
                    } else {
                        possibleNodes.add(new Node(tile.getRightTile(), this, RIGHT));
                    }
                }

                if (possibleNodes.size() == 0) {
                    return null;
                }

                Node bestNode = getBestNode(possibleNodes, destinationTile);

                switch (bestNode.nodeType) {
                    case TOP -> {
                        nextTopNode = bestNode;
                        return nextTopNode;
                    }
                    case BOTTOM -> {
                        nextBottomNode = bestNode;
                        return nextBottomNode;
                    }
                    case LEFT -> {
                        nextLeftNode = bestNode;
                        return nextLeftNode;
                    }
                    case RIGHT -> {
                        nextRightNode = bestNode;
                        return nextRightNode;
                    }
                }

                return null;
            }

            private Node getBestNode(ArrayList<Node> possibleNodes, Tile destinationTile) {
                Node bestNode = possibleNodes.get(0);

                int distance = Math.abs(destinationTile.xCord - bestNode.tile.xCord) +
                        Math.abs(destinationTile.yCord - bestNode.tile.yCord);
                for (int i = 1; i < possibleNodes.size(); i++) {
                    int newDistance = Math.abs(destinationTile.xCord - possibleNodes.get(i).tile.xCord) +
                            Math.abs(destinationTile.yCord - possibleNodes.get(i).tile.yCord);
                    if (newDistance <= distance) {
                        distance = newDistance;
                        bestNode = possibleNodes.get(i);
                    }
                }
                return bestNode;
            }

            private Node blockedGetPreviousNode() {
                switch (nodeType) {
                    case TOP -> {
                        previousNode.nextTopNode = null;
                        previousNode.stepsTopToDestination = -1;
                        return previousNode;
                    }
                    case BOTTOM -> {
                        previousNode.nextBottomNode = null;
                        previousNode.stepsBottomToDestination = -1;
                        return previousNode;
                    }
                    case LEFT -> {
                        previousNode.nextLeftNode = null;
                        previousNode.stepsLeftToDestination = -1;
                        return previousNode;
                    }
                    case RIGHT -> {
                        previousNode.nextRightNode = null;
                        previousNode.stepsRightToDestination = -1;
                        return previousNode;
                    }
                }
                return previousNode;
            }

            private Node notBlockedGetPreviousNode() {
                int stepsToDest = -1;

                if (isLast) {
                    stepsToDest = 0;
                } else {
                    if (stepsTopToDestination != -1) {
                        stepsToDest = stepsTopToDestination;
                        nextNode = nextTopNode;
                    }
                    if (stepsBottomToDestination != -1 && (stepsToDest == -1 || stepsBottomToDestination < stepsToDest)) {
                        stepsToDest = stepsBottomToDestination;
                        nextNode = nextBottomNode;
                    }
                    if (stepsLeftToDestination != -1 && (stepsToDest == -1 || stepsLeftToDestination < stepsToDest)) {
                        stepsToDest = stepsLeftToDestination;
                        nextNode = nextLeftNode;
                    }
                    if (stepsRightToDestination != -1 && (stepsToDest == -1 || stepsRightToDestination < stepsToDest)) {
                        stepsToDest = stepsRightToDestination;
                        nextNode = nextRightNode;
                    }
                }

                switch (nodeType) {
                    case TOP -> {
                        previousNode.stepsTopToDestination = stepsToDest + 1;
                        return previousNode;
                    }
                    case BOTTOM -> {
                        previousNode.stepsBottomToDestination = stepsToDest + 1;
                        return previousNode;
                    }
                    case LEFT -> {
                        previousNode.stepsLeftToDestination = stepsToDest + 1;
                        return previousNode;
                    }
                    case RIGHT -> {
                        previousNode.stepsRightToDestination = stepsToDest + 1;
                        return previousNode;
                    }
                    default -> {
                        return null;
                    }
                }
            }

            private boolean isNodeBlocked() {
                return stepsTopToDestination == -1 && stepsBottomToDestination == -1 &&
                        stepsLeftToDestination == -1 && stepsRightToDestination == -1;
            }

        }

    }

    private class Tile {
        int xCord;
        int yCord;

        private Tile(int x, int y) {
            xCord = x;
            yCord = y;
        }

        private Tile(Point point) {
            xCord = point.x;
            yCord = point.y;
        }

        private Tile getTopTile() {
            return new Tile(xCord, yCord - 50);
        }

        private Tile getBottomTile() {
            return new Tile(xCord, yCord + 50);
        }

        private Tile getLeftTile() {
            return new Tile(xCord - 50, yCord);
        }

        private Tile getRightTile() {
            return new Tile(xCord + 50, yCord);
        }

        private boolean isTileBlocked(ArrayList<Tile> blockedTiles) {
            if (xCord < 0 || xCord > gc.getCanvas().getWidth() || yCord < 0 || yCord > gc.getCanvas().getHeight()) {
                return true;
            }
            for (EnemyTank enemyTank : levelBuilder.enemyTanks) {
                if (enemyTank.getBoundary().contains(xCord, yCord)) {
                    return true;
                }
            }
            return blockedTiles.contains(this);
        }

        private boolean isTopTileBlocked(ArrayList<Tile> blockedTiles) {
            return getTopTile().isTileBlocked(blockedTiles);
        }

        private boolean isBottomTileBlocked(ArrayList<Tile> blockedTiles) {
            return getBottomTile().isTileBlocked(blockedTiles);
        }

        private boolean isLeftTileBlocked(ArrayList<Tile> blockedTiles) {
            return getLeftTile().isTileBlocked(blockedTiles);
        }

        private boolean isRightTileBlocked(ArrayList<Tile> blockedTiles) {
            return getRightTile().isTileBlocked(blockedTiles);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Tile tile = (Tile) o;
            return xCord == tile.xCord && yCord == tile.yCord;
        }

        @Override
        public int hashCode() {
            return Objects.hash(xCord, yCord);
        }

    }

}
