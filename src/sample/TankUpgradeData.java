package sample;

public class TankUpgradeData {
    private static int speedLevel = 0;
    private static int attackLevel = 0;
    private static int hpLevel = 0;
    private static int aimSpeedLevel = 0;

    private static final int speedMinPrice = 15;
    private static final int attackMinPrice = 25;
    private static final int hpMinPrice = 20;
    private static final int aimSpeedMinPrice = 10;

    private static final int increment = 5;

    public static void setAttackLevel(int attackLevel) {
        TankUpgradeData.attackLevel = attackLevel;
    }

    public static void setHpLevel(int hpLevel) {
        TankUpgradeData.hpLevel = hpLevel;
    }

    public static void setSpeedLevel(int speedLevel) {
        TankUpgradeData.speedLevel = speedLevel;
    }

    public static void setAimSpeedLevel(int aimSpeedLevel) {
        TankUpgradeData.aimSpeedLevel = aimSpeedLevel;
    }

    public static int getAttackLevel() {
        return attackLevel;
    }

    public static int getSpeedLevel() {
        return speedLevel;
    }

    public static int getHpLevel() {
        return hpLevel;
    }

    public static int getAimSpeedLevel() {
        return aimSpeedLevel;
    }

    static int getCurrentAttackUpgradePrice() {
        return attackMinPrice + increment * attackLevel;
    }

    static int getCurrentHPUpgradePrice() {
        return hpMinPrice + increment * hpLevel;
    }

    static int getCurrentSpeedUpgradePrice() {
        return speedMinPrice + increment * speedLevel;
    }

    static int getCurrentAimSpeedUpgradePrice() {
        return aimSpeedMinPrice + increment * aimSpeedLevel;
    }
}
