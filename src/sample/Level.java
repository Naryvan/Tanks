package sample;

public class Level {
    private String id;
    private boolean locked = true;
    private boolean completed;

    Level(String id) {
        this.id = id;
    }

    public String getLevelPane() {
        return id;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean isCompleted() {
        return completed;
    }

    public boolean isLocked() {
        return locked;
    }
}
