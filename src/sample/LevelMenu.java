package sample;

public class LevelMenu {
    private final String id;
    private final String backId;
    private boolean locked;
    private boolean completed;

    LevelMenu(String id, String backId) {
        this.id = id;
        this.backId = backId;
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

    public String getBackId() {
        return backId;
    }
}
