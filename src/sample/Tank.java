package sample;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;

import java.awt.*;
import java.util.ArrayList;

public class Tank {

    final static double WIDTH = 40;
    final static double HEIGHT = 40;

    protected String spriteName;
    private int currentSpriteIteration = 1;
    private int changeSpriteTimer = 10;

    double xPos;
    double yPos;
    int direction;
    double currentDirection;
    boolean penetration = false;

    double maxSpeed;
    double acceleration;
    double currentSpeed = 0;
    boolean isMoving = false;

    double gunDirection;
    double gunRotationSpeed;
    Bullet bullet;
    protected boolean isLoaded = true;
    int reloadTimer = 0;

    boolean isTopBlocked;
    boolean isBottomBlocked;
    boolean isLeftBlocked;
    boolean isRightBlocked;

    boolean isBulletBlocked;

    LevelBuilder levelBuilder;
    GraphicsContext gc;

    public Tank(LevelBuilder levelBuilder) {
        this.xPos = 50;
        this.yPos = 50;
        direction = 0;
        currentDirection = 0;
        gunDirection = 0;
        maxSpeed = 4;
        acceleration = maxSpeed / 40;
        gunRotationSpeed = 2;
        this.levelBuilder = levelBuilder;
        this.gc = levelBuilder.getGraphicsContext();
    }

    public Tank(LevelBuilder levelBuilder, double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        direction = 0;
        currentDirection = 0;
        gunDirection = 0;
        maxSpeed = 4;
        acceleration = maxSpeed / 40;
        gunRotationSpeed = 2;
        this.levelBuilder = levelBuilder;
        this.gc = levelBuilder.getGraphicsContext();

    }

    //For levels
    public Tank(LevelBuilder levelBuilder, double xPos, double yPos, int direction, double maxSpeed, double gunRotationSpeed) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.direction = direction;
        currentDirection = direction * 45;
        gunDirection = 0;
        this.maxSpeed = maxSpeed;
        acceleration = maxSpeed / 40;
        this.gunRotationSpeed = gunRotationSpeed;
        this.levelBuilder = levelBuilder;
        this.gc = levelBuilder.getGraphicsContext();
        bullet = new Bullet(0, 0, 0, levelBuilder.getGraphicsContext());
    }

    //For garage
    public Tank(GraphicsContext gc, double xPos, double yPos, int direction, double maxSpeed, double gunRotationSpeed) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.direction = direction;
        currentDirection = direction * 45;
        gunDirection = 0;
        this.maxSpeed = maxSpeed;
        acceleration = maxSpeed / 40;
        this.gunRotationSpeed = gunRotationSpeed;
        this.levelBuilder = null;
        this.gc = gc;
    }

    protected void processMovement() {
        checkCollision();

        if (isMoving) {
            accelerate();
        } else {
            slowDown();
        }

        switch (direction) {
            case 0 -> {
                if (!isTopBlocked) {
                    yPos -= currentSpeed;
                }
            }
            case 1 -> {
                if (!isTopBlocked) {
                    yPos -= currentSpeed / Math.sqrt(2);
                }
                if (!isRightBlocked) {
                    xPos += currentSpeed / Math.sqrt(2);
                }
            }
            case 2 -> {
                if (!isRightBlocked) {
                    xPos += currentSpeed;
                }
            }
            case 3 -> {
                if (!isBottomBlocked) {
                    yPos += currentSpeed / Math.sqrt(2);
                }
                if (!isRightBlocked) {
                    xPos += currentSpeed / Math.sqrt(2);
                }
            }
            case 4 -> {
                if (!isBottomBlocked) {
                    yPos += currentSpeed;
                }
            }
            case 5 -> {
                if (!isBottomBlocked) {
                    yPos += currentSpeed / Math.sqrt(2);
                }
                if (!isLeftBlocked) {
                    xPos -= currentSpeed / Math.sqrt(2);
                }
            }
            case 6 -> {
                if (!isLeftBlocked) {
                    xPos -= currentSpeed;
                }
            }
            case 7 -> {
                if (!isTopBlocked) {
                    yPos -= currentSpeed / Math.sqrt(2);
                }
                if (!isLeftBlocked) {
                    xPos -= currentSpeed / Math.sqrt(2);
                }
            }
        }
        rotateBody();
    }

    private void accelerate() {
        if (currentSpeed >= maxSpeed) {
            currentSpeed = maxSpeed;
        } else {
            currentSpeed += acceleration;
        }
    }

    private void slowDown() {
        if (currentSpeed > 0) {
            currentSpeed -= acceleration * 2;
        }
        if (currentSpeed < 0) {
            currentSpeed = 0;
        }
    }

    protected void rotateGun(Point targetPos) {
        double angle = calculateGunDirection(targetPos);

        if (Math.abs(gunDirection - angle) < gunRotationSpeed) {
            gunDirection = angle;
            return;
        }

        if ((gunDirection > 0 && angle < 0) && (gunDirection + Math.abs(angle) > 180)) {
            gunDirection += gunRotationSpeed;
            if (gunDirection > 180) {
                gunDirection = -360 + gunDirection;
            }
        } else if ((gunDirection < 0 && angle > 0) && (Math.abs(gunDirection) + angle > 180)) {
            gunDirection -= gunRotationSpeed;
            if (gunDirection < -180) {
                gunDirection = 360 + gunDirection;
            }
        } else {
            if (gunDirection < angle) {
                gunDirection += gunRotationSpeed;
            } else {
                gunDirection -= gunRotationSpeed;
            }
        }

    }

    private void rotateBody() {
        if (direction * 45 == currentDirection) {
            return;
        }

        double rotationLength = 360;
        double rotationSpeed;

        if ((currentDirection < 180 && direction * 45 > 180) && (direction * 45 - currentDirection > 180)) {
            //rotationLength = direction * 45 - currentDirection;
            rotationSpeed = -12;
        } else if ((currentDirection > 180 && direction * 45 < 180) && (currentDirection - direction * 45 > 180)) {
            //rotationLength = currentDirection - direction * 45;
            rotationSpeed = 12;
        } else {
            if (currentDirection < direction * 45) {
                //rotationLength = direction * 45 - currentDirection;
                rotationSpeed = 12;
            } else {
                //rotationLength = currentDirection - direction * 45;
                rotationSpeed = -12;
            }
        }

        if (Math.abs(currentDirection - (direction != 0 ? direction * 45 : 360)) < rotationSpeed) {
            currentDirection = direction * 45;
            return;
        }

        currentDirection += rotationSpeed;

        if (currentDirection > 360) {
            currentDirection -= 360;
        }
        if (currentDirection < 0) {
            currentDirection = 360 + currentDirection;
        }

    }

    protected void checkCollision() {
        isTopBlocked = false;
        isBottomBlocked = false;
        isLeftBlocked = false;
        isRightBlocked = false;

        if (yPos <= 25) {
            isTopBlocked = true;
        }
        if (yPos >= gc.getCanvas().getHeight() - 25) {
            isBottomBlocked = true;
        }
        if (xPos <= 25) {
            isLeftBlocked = true;
        }
        if (xPos >= gc.getCanvas().getWidth() - 25) {
            isRightBlocked = true;
        }

        //garage
        if (levelBuilder == null) {
            return;
        }

        ArrayList<Wall> walls = levelBuilder.getCloseWalls(xPos, yPos);

        for (Wall wall : walls) {
            if (getTopBoundary().intersects(wall.getBoundary())) {
                isTopBlocked = true;
            }
            if (getBottomBoundary().intersects(wall.getBoundary())) {
                isBottomBlocked = true;
            }
            if (getLeftBoundary().intersects(wall.getBoundary())) {
                isLeftBlocked = true;
            }
            if (getRightBoundary().intersects(wall.getBoundary())) {
                isRightBlocked = true;
            }
        }

        ArrayList<EnemyTank> enemyTanks = levelBuilder.getEnemyTanks();
        //enemyTanks.remove(this);

        for (EnemyTank enemyTank : enemyTanks) {
            if (getTopBoundary().intersects(enemyTank.getBoundary())) {
                isTopBlocked = true;
            }
            if (getBottomBoundary().intersects(enemyTank.getBoundary())) {
                isBottomBlocked = true;
            }
            if (getLeftBoundary().intersects(enemyTank.getBoundary())) {
                isLeftBlocked = true;
            }
            if (getRightBoundary().intersects(enemyTank.getBoundary())) {
                isRightBlocked = true;
            }
        }

        if (getTopBoundary().intersects(levelBuilder.getPlayerTank().getBoundary())) {
            isTopBlocked = true;
        }
        if (getBottomBoundary().intersects(levelBuilder.getPlayerTank().getBoundary())) {
            isBottomBlocked = true;
        }
        if (getLeftBoundary().intersects(levelBuilder.getPlayerTank().getBoundary())) {
            isLeftBlocked = true;
        }
        if (getRightBoundary().intersects(levelBuilder.getPlayerTank().getBoundary())) {
            isRightBlocked = true;
        }

    }

    public boolean isNearWall() {
        return isTopBlocked || isBottomBlocked || isLeftBlocked || isRightBlocked;
    }

    public void render() {
        renderBody();
        renderGun();
        if (bullet == null) {
            return;
        }
        bullet.moveBullet();
        bullet.renderBullet();

        if (bullet == null) return;

        checkBulletCollision();

        if (!isBulletBlocked) {
            bullet.moveBullet();
        }

        if (!penetration) {
            bullet.renderBullet();
        }

        if (!isLoaded) {
            reloadTimer++;
        }

        if (reloadTimer > 100) {
            isLoaded = true;
            reloadTimer = 1;
        }
    }

    public void checkBulletCollision() {

        isBulletBlocked = false;

        ArrayList<Wall> walls = levelBuilder.getWalls();

        for (Wall wall : walls) {
            if (getBoundaryOfBullet().intersects(wall.getBoundary())) {
                isBulletBlocked = true;
                penetration = true;
                bullet = new Bullet(-100, -100, 0, levelBuilder.getGraphicsContext());
                if(!wall.isDamaged) {
                    wall.damage();
                }
                else {
                    walls.remove(wall);
                }
                return;
            }
        }

        ArrayList<EnemyTank> enemyTanks = levelBuilder.getEnemyTanks();
        PlayerTank playerTank = levelBuilder.getPlayerTank();
        for (EnemyTank enemyTank : enemyTanks) {
            if (getBoundaryOfBullet().intersects(enemyTank.getBoundary()) && bullet.isPlayerBullet()) {
                isBulletBlocked = true;
                penetration = true;
                bullet = new Bullet(-100, -100, 0, levelBuilder.getGraphicsContext());
                enemyTank.setTotalHP(enemyTank.getTotalHP() - PlayerTank.attack);
                if (enemyTank.getTotalHP() <= 0) {
                    enemyTanks.remove(enemyTank);
                    Money.increaseAmount(15);
                }
                return;
            }

            if (getBoundaryOfBullet().intersects(playerTank.getBoundary()) && !bullet.isPlayerBullet()) {
                PlayerTank.currentHP -= enemyTank.getAttackPower();
                bullet = new Bullet(-100, -100, 0, levelBuilder.getGraphicsContext());
            }
        }
    }

    private void renderBody() {
        gc.save();
        gc.transform(new Affine(new Rotate(currentDirection, xPos, yPos)));

        if (changeSpriteTimer == 0) {
            changeSpriteTimer = 10;
        }
        changeSpriteTimer--;
        if (isMoving && changeSpriteTimer == 0) {
            currentSpriteIteration = currentSpriteIteration == 1 ? 2 : 1;
        }
        gc.drawImage(new Image("images/" + spriteName + currentSpriteIteration + ".png"), xPos - 20, yPos - 20);

        gc.restore();
    }

    private void renderGun() {
        gc.setFill(javafx.scene.paint.Color.rgb(0, 75, 0));
        gc.save();
        gc.transform(new Affine(new Rotate(gunDirection, xPos, yPos)));
        gc.drawImage(new Image("images/" + spriteName + "Barrel.png"), xPos, yPos - 5);
        gc.drawImage(new Image("images/" + spriteName + "Tower.png"), xPos - 20, yPos - 20);
        gc.restore();
    }

    public void createBullet(GraphicsContext graphicsContext2D, boolean playerBullet) {
        this.bullet = new Bullet(xPos, yPos, gunDirection, graphicsContext2D);
        bullet.setPlayerBullet(playerBullet);
    }

    private double calculateGunDirection(Point targetPos) {
        double length = targetPos.getX() - xPos;
        double height = targetPos.getY() - yPos;

        return Math.toDegrees(Math.atan2(height, length));
    }

    public Rectangle2D getBoundary() {
        return new Rectangle2D(xPos - 25, yPos - 25, 50, 50);
    }

    public Rectangle2D getTopBoundary() {
        return new Rectangle2D(xPos - WIDTH / 2, yPos - HEIGHT / 2 - 6, WIDTH, 1);
    }

    public Rectangle2D getBottomBoundary() {
        return new Rectangle2D(xPos - WIDTH / 2, yPos + HEIGHT / 2 + 5, WIDTH, 1);
    }

    public Rectangle2D getLeftBoundary() {
        return new Rectangle2D(xPos - WIDTH / 2 - 6, yPos - HEIGHT / 2, 1, HEIGHT);
    }

    public Rectangle2D getRightBoundary() {
        return new Rectangle2D(xPos + WIDTH / 2 + 5, yPos - HEIGHT / 2, 1, HEIGHT);
    }

    public Rectangle2D getBoundaryOfBullet() {
        return new Rectangle2D(bullet.bulletX, bullet.bulletY, 10, 10);
    }

    public Point getTileCoordinates() {
        int tileX = ((int) xPos / 50 + 1) * 50 - 25;
        int tileY = ((int) yPos / 50 + 1) * 50 - 25;
        return new Point(tileX, tileY);
    }

}