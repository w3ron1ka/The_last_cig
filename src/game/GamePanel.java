package game;

import background.MapElementManager;
import entity.Player;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable { //runnable jest do thread

    // wyswietlanie panelu, wielkosc kratek
    final int gridSize = 16;    //wielkość kratki 16x16
    final int scale = 3;
    public final int dispGridSize = gridSize * scale;  //widziana wielkość kratki po skalowaniu 16x3 =48

    public final int screenColumn = 26;
    public final int screenRow = 16;
    public final int screenWidth = screenColumn * dispGridSize;
    public final int screenHeight = screenRow * dispGridSize;
    int FPS = 60;

    // zmienne gracza i klawiatury
    KeyHandler keyHandler = new KeyHandler();
    Player player = new Player(this, keyHandler);

    // mapa
    MapElementManager mapEl = new MapElementManager(this);

    //konstruktor
    public GamePanel() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);    // gra jest caly czas "gotowa" na nacisniecie klawisza

    }

    // watek gry
    Thread thread;
    public void startThread(){
        thread = new Thread(this);
        thread.start();
    }
    @Override
    public void run() {     //tu bedzie game loop
        double render = 1000000000 / FPS;       // co 0,017s sie odswieza i rysuje
       // double nextRender = System.nanoTime() + render;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(thread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / render;     // metoda delty
            lastTime = currentTime;

            if (delta >= 1) {
                update();   // odswieza info o pozycji gracza np
                repaint();  // rysuje od nowa ekran z ta info z update
                delta = 0;
            }

        }
    }
    public void update(){
        player.update();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;    // ma wiecej funkcji 2d
        mapEl.draw(g2d);    // mapa pod graczem
        player.draw(g2d);

        g2d.dispose();      // oszczedza pamiec

    }
}
