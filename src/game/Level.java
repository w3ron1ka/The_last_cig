package game;

/**
 * Klasa przechowująca poziomy
 */
public class Level {
    private int level = 0;
    private String mapFilePath;
    private int coinsCount;
    private int cigarettesCount;

    /**
     * Konstruktor Level
     * @param level
     * @param mapFilePath
     * @param coinsCount
     * @param cigarettesCount
     */
    public Level(int level, String mapFilePath, int coinsCount, int cigarettesCount){
        this.level = level;
        this.mapFilePath = mapFilePath;
        this.coinsCount = coinsCount;
        this.cigarettesCount = cigarettesCount;
    }

    /**
     * Metoda pobierająca ścieżkę pliku z mapą
     * @return  ścieżka pliku z mapą
     */
    public String getMapFilePath(){
            return mapFilePath;
    }

    /**
     *Metoda pobierająca liczbę monet
     * @return  liczba monet
     */
    public int getCoinsCount(){
        return coinsCount;
    }

    /**
     * Metoda pobierająca liczbę papierosów
     * @return  liczba papierosów
     */
    public int getCigarettesCount(){
        return cigarettesCount;
    }

}
