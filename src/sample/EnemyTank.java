package sample;

import java.awt.*;
import java.util.ArrayList;
import java.util.Objects;

public class EnemyTank extends Tank {

    PlayerTank playerTank;
    Path currentPath;
    int pathCounter = 0;

    Point lastPlayerTankPos;

    public EnemyTank(LevelBuilder levelBuilder, double xPos, double yPos, int direction, double maxSpeed, double gunRotationSpeed) {
        super(levelBuilder, xPos, yPos, direction, maxSpeed, gunRotationSpeed);
        playerTank = levelBuilder.getPlayerTank();
        //findPath();
        //System.out.println(currentPath);
    }

    public void operate() {
        if(pathCounter == 0) {
            pathCounter = 960;
            findPath();
        }
        else /*if(currentPath == null)*/ {
            pathCounter--;
        }

        if(currentPath != null) {
            followPath();
        }

        rotateGun(new Point((int)playerTank.getX(), (int)playerTank.getY()));
    }

    private void followPath() {
        int currentNodeX = currentPath.currentNode.tile.xCord;
        int currentNodeY = currentPath.currentNode.tile.yCord;
        if(Math.abs(currentNodeX - xPos) < 3 && Math.abs(currentNodeY - yPos) < 3) {
            xPos = currentNodeX;
            yPos = currentNodeY;
            if(currentPath.nextNode() == null) {
                isMoving = false;
                currentPath = null;
            }
            else if(currentPath.currentNode.previousNode != currentPath.startNode) {
                if(!isNearWall()) {
                    findPath();
                }
            }
            return;
        }

        if(currentNodeY < yPos) {
            isMoving = true;
            direction = 0;
            if(isTopBlocked) {
                //findPath();
            }
            if(currentNodeX > xPos) {
                direction = 1;
            }
            else if(currentNodeX < xPos) {
                direction = 7;
            }
        }
        else if(currentNodeY > yPos) {
            isMoving = true;
            direction = 4;
            if(isBottomBlocked) {
                //findPath();
            }
            if(currentNodeX > xPos) {
                direction = 3;
            }
            else if(currentNodeX < xPos) {
                direction = 5;
            }
        }
        else if(currentNodeX > xPos) {
            isMoving = true;
            direction = 2;
            if(isRightBlocked) {
                //findPath();
            }
        }
        else if(currentNodeX < xPos) {
            isMoving = true;
            direction = 6;
            if(isLeftBlocked) {
                //findPath();
            }
        }
        else {
            isMoving = false;
        }

        processMovement();
    }

    private void findPath() {
        Path path = new Path(this.getTileCoordinates());
        if(path.findPath(new Tile(playerTank.getTileCoordinates()))) {
            currentPath = path;
            lastPlayerTankPos = playerTank.getTileCoordinates();
        }
        else {
            currentPath = null;
        }
    }

    /*@Override
    protected void checkCollision() {
        super.checkCollision();

        if(getTopBoundary().intersects(playerTank.getBoundary())) {
            isTopBlocked = true;
        }
        if(getBottomBoundary().intersects(playerTank.getBoundary())) {
            isBottomBlocked = true;
        }
        if(getLeftBoundary().intersects(playerTank.getBoundary())) {
            isLeftBlocked = true;
        }
        if(getRightBoundary().intersects(playerTank.getBoundary())) {
            isRightBlocked = true;
        }
    }*/

    class Path {
        ArrayList<Tile> blockedTiles;

        Node startNode;
        Node currentNode;

        public Path(Point point) {
            startNode = new Node(new Tile(point), null);
            currentNode = startNode;
            blockedTiles = getBlockedTiles();
            blockedTiles.add(new Tile(point));
        }

        public Node nextNode() {
            if(!currentNode.isLast) {
                currentNode = currentNode.nextNode;
                return currentNode;
            }
            return null;
        }

        public boolean findPath(Tile destinationTile) {
            while(true) {
                Node nextNode = currentNode.findNextNode(blockedTiles, destinationTile);
                if(nextNode == null) {
                    if(currentNode == startNode) {
                        return false;
                    }
                    currentNode = currentNode.blockedGetPreviousNode();
                }
                else {
                    blockedTiles.add(nextNode.tile);
                    currentNode = nextNode;

                    if(currentNode.tile.equals(destinationTile)) {
                        currentNode.isLast = true;
                        currentNode = startNode;
                        return true;
                    }
                }
            }
        }

        private ArrayList<Tile> getBlockedTiles() {
            ArrayList<Tile> blockedTiles = new ArrayList<>();
            for(Wall wall : levelBuilder.getWalls()) {
                blockedTiles.add(new Tile(wall.x, wall.y));
            }
            return blockedTiles;
        }

        public class Node {
            Tile tile;

            boolean isLast;
            boolean isTopBlocked;
            boolean isLeftBlocked;
            boolean isRightBlocked;
            boolean isBottomBlocked;

            Node nextNode;
            Node previousNode;

            public Node(Tile tile, Node previousNode) {
                this.tile = tile;
                this.previousNode = previousNode;
            }

            public Node findNextNode(ArrayList<Tile> blockedTiles, Tile destinationTile) {
                if(!isTopBlocked) {
                    if(tile.isTopTileBlocked(blockedTiles)) {
                        isTopBlocked = true;
                    }
                    /*else {
                        nextNode = new Node(tile.getTopTile(), this);
                        return nextNode;
                    }*/
                }
                if(!isBottomBlocked) {
                    if(tile.isBottomTileBlocked(blockedTiles)) {
                        isBottomBlocked = true;
                    }
                    /*else {
                        nextNode = new Node(tile.getBottomTile(), this);
                        return nextNode;
                    }*/
                }
                if(!isLeftBlocked) {
                    if(tile.isLeftTileBlocked(blockedTiles)) {
                        isLeftBlocked = true;
                    }
                    /*else {
                        nextNode = new Node(tile.getLeftTile(), this);
                        return nextNode;
                    }*/
                }
                if(!isRightBlocked) {
                    if(tile.isRightTileBlocked(blockedTiles)) {
                        isRightBlocked = true;
                    }
                    /*else {
                        nextNode = new Node(tile.getRightTile(), this);
                        return nextNode;
                    }*/
                }

                if(!isTopBlocked && tile.yCord > destinationTile.yCord) {
                    nextNode = new Node(tile.getTopTile(), this);
                    return nextNode;
                }
                else if(!isBottomBlocked && tile.yCord < destinationTile.yCord) {
                    nextNode = new Node(tile.getBottomTile(), this);
                    return nextNode;
                }
                else if(!isLeftBlocked && tile.xCord > destinationTile.xCord) {
                    nextNode = new Node(tile.getLeftTile(), this);
                    return nextNode;
                }
                else if(!isRightBlocked && tile.xCord < destinationTile.xCord) {
                    nextNode = new Node(tile.getRightTile(), this);
                    return nextNode;
                }
                else if(!isTopBlocked) {
                    nextNode = new Node(tile.getTopTile(), this);
                    return nextNode;
                }
                else if(!isBottomBlocked) {
                    nextNode = new Node(tile.getBottomTile(), this);
                    return nextNode;
                }
                else if(!isLeftBlocked) {
                    nextNode = new Node(tile.getLeftTile(), this);
                    return nextNode;
                }
                else if(!isRightBlocked) {
                    nextNode = new Node(tile.getRightTile(), this);
                    return nextNode;
                }

                return null;
            }

            public Node blockedGetPreviousNode() {
                previousNode.nextNode = null;
                return previousNode;
            }

        }

        public String toString() {
            String string = "";

            Node currentNode = startNode;
            while(!currentNode.isLast) {
                string += "[" + currentNode.tile.xCord + ", " + currentNode.tile.yCord + "], ";
                currentNode = currentNode.nextNode;
            }
            string += "[" + currentNode.tile.xCord + ", " + currentNode.tile.yCord + "]";
            return string;
        }

    }

    class Tile {
        int xCord;
        int yCord;

        public Tile(int x, int y) {
            xCord = x;
            yCord = y;
        }

        public Tile(Point point) {
            xCord = point.x;
            yCord = point.y;
        }

        public Tile getTopTile() {
            return new Tile(xCord, yCord - 50);
        }

        public Tile getBottomTile() {
            return new Tile(xCord, yCord + 50);
        }

        public Tile getLeftTile() {
            return new Tile(xCord - 50, yCord);
        }

        public Tile getRightTile() {
            return new Tile(xCord + 50, yCord);
        }

        public boolean isTileBlocked(ArrayList<Tile> blockedTiles) {
            if(xCord < 0 || xCord > gc.getCanvas().getWidth() || yCord < 0 || yCord > gc.getCanvas().getHeight()) {
                return true;
            }
            for(EnemyTank enemyTank : levelBuilder.enemyTanks) {
                if(enemyTank.getBoundary().contains(xCord, yCord)) {
                    return true;
                }
            }
            return blockedTiles.contains(this);
        }

        public boolean isTopTileBlocked(ArrayList<Tile> blockedTiles) {
            return getTopTile().isTileBlocked(blockedTiles);
        }

        public boolean isBottomTileBlocked(ArrayList<Tile> blockedTiles) {
            return getBottomTile().isTileBlocked(blockedTiles);
        }

        public boolean isLeftTileBlocked(ArrayList<Tile> blockedTiles) {
            return getLeftTile().isTileBlocked(blockedTiles);
        }

        public boolean isRightTileBlocked(ArrayList<Tile> blockedTiles) {
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

        public String toString() {
            return "[" + xCord + ", " + yCord + "]";
        }
    }

}
