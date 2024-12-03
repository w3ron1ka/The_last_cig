package background;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MapElementManager {
    GamePanel gP;
    MapElement[] mapElements;
    int [][] map;

    public MapElementManager(GamePanel gP) {
        this.gP = gP;
        mapElements = new MapElement[9];
        map = new int [gP.dispGridSize][gP.dispGridSize];
        getImage();
        getMap("/maps/mapkaProbna.txt");
    }
    public void getImage(){
        try {
            mapElements[0] = new MapElement();
            mapElements[0].image = ImageIO.read(getClass().getResourceAsStream("/background/concrete.png"));
            mapElements[1] = new MapElement();
            mapElements[1].image = ImageIO.read(getClass().getResourceAsStream("/background/grass.png"));
            mapElements[2] = new MapElement();
            mapElements[2].image = ImageIO.read(getClass().getResourceAsStream("/background/brick.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getMap(String mapPath) {
        try {
            InputStream iStream = getClass().getResourceAsStream(mapPath);
            BufferedReader bReader = new BufferedReader(new InputStreamReader(iStream));        // czytanie wnetrza pliku .txt

            int col = 0, row = 0;
            while (col < gP.screenColumn && row < gP.screenRow) {
                String line = bReader.readLine();
                while (col < gP.screenColumn) {
                    String numStr[] = line.split(" ");     // do tablicy numStr[] wczytujemy po kolei liczby z pliku bez spacji
                    int num = Integer.parseInt(numStr[col]);
                    map[col][row] = num;
                    col++;
                }
                if (col == gP.screenColumn) {
                    col = 0;
                    row++;
                }
            }
            bReader.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d){
       // g2d.drawImage(mapElements[0].image,0,0, gP.dispGridSize, gP.dispGridSize, null);

        int col = 0, row = 0, x = 0, y = 0;

        while (col < gP.screenColumn && row < gP.screenRow) {

            int elementNum = map[col][row];

            g2d.drawImage(mapElements[elementNum].image,x,y, gP.dispGridSize, gP.dispGridSize, null);
            col++;
            x += gP.dispGridSize;
            if (col == gP.screenColumn) {
                col = 0;
                x = 0;
                row++;
                y += gP.dispGridSize;
            }
        }
        g2d.drawImage(mapElements[2].image,48,48, gP.dispGridSize, gP.dispGridSize, null);

    }
}