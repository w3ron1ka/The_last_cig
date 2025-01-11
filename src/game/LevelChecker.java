package game;

public class LevelChecker {
    int level = 0;

    public void setLevel(int level) {
        this.level = level;
    }
    public int getLevel() {
        return level;
    }

    public void LevelUp(){
        level++;
    }
}
