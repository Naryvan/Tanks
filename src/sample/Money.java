package sample;

public class Money {
    private static int amount = 100;

    public static int getAmount() {
        return amount;
    }

    private static void setAmount(int amount) {
        Money.amount = amount;
    }

    public static void decreaseAmount(int amount) {
        setAmount(getAmount() - amount);
    }

    public static void increaseAmount(int amount) {
        setAmount(getAmount() + amount);
    }
}
