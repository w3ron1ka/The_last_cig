package game;

import java.util.ArrayList;

public class LevelManager {
    private ArrayList<Level> levels;
    private int currentLevel;

    public LevelManager(){
        levels = new ArrayList<>();
        currentLevel = 0;
        loadLevels();
    }
    public void loadLevels(){
        levels.add(new Level(1, "/maps/map1.txt", 40, 4));
        levels.add(new Level(2, "/maps/map2.txt", 30, 8));
        levels.add(new Level(3, "/maps/map3.txt", 20, 12));
    }
    public Level getCurrentLevel() {
        return levels.get(currentLevel);
    }
    public boolean nextLevel() {
        if (currentLevel  < levels.size() -1) {
            currentLevel++;
            return true;
        }
        System.out.println("Aktualny poziom: " + currentLevel);
        return false;
    }
}
