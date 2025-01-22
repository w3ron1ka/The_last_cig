package game;

import java.util.ArrayList;

/**
 * Klasa zarządzająca poziomami
 */
public class LevelManager {
    private ArrayList<Level> levels;
    private int currentLevel;

    /**
     * Konstruktor tworzący listę poziomów i ładujący je
     */
    public LevelManager(){
        levels = new ArrayList<>();
        currentLevel = 0;
        loadLevels();
    }

    /**
     * Metoda ładująca poziomy
     */
    public void loadLevels(){
        levels.add(new Level(1, "/maps/map1.txt", 40, 4));
        levels.add(new Level(2, "/maps/map2.txt", 30, 8));
        levels.add(new Level(3, "/maps/map3.txt", 20, 12));
    }

    /**
     * Metoda pobierająca aktualny poziom
     * @return  aktualny poziom
     */
    public Level getCurrentLevel() {
        return levels.get(currentLevel);
    }

    /**
     * Metoda zmieniająca na kolejny poziom
     * @return  {@code }
     */
    public boolean nextLevel() {
        if (currentLevel  < levels.size() -1) {
            currentLevel++;
            return true;
        }
        System.out.println("Aktualny poziom: " + currentLevel);
        return false;
    }
}
