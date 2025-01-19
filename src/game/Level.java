package game;

public class Level {
    private int level = 0;
    private String mapFilePath;
    private int coinsCount;
    private int cigarettesCount;

    public Level(int level, String mapFilePath, int coinsCount, int cigarettesCount){
        this.level = level;
        this.mapFilePath = mapFilePath;
        this.coinsCount = coinsCount;
        this.cigarettesCount = cigarettesCount;
    }
    public String getMapFilePath(){
            return mapFilePath;
    }
    public int getCoinsCount(){
        return coinsCount;
    }
    public int getCigarettesCount(){
        return cigarettesCount;
    }
    public int getLevel(int level) {
        return level;
    }
}
