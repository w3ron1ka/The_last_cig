package game;

import background.MapElementManager;
import entity.Cigarette;
import entity.Entity;
import entity.Player;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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

    // klawiatury i inne takie
    KeyHandler keyHandler = new KeyHandler(this);
    public Displayer displayer = new Displayer(this);

    public CollisionDetector collisionDetector = new CollisionDetector(this);
    // gracz, byty
    Player player = new Player(this, keyHandler);

    Cigarette cigarette = new Cigarette(this);
    Cigarette cig2 = new Cigarette(this);
    Cigarette cig3 = new Cigarette(this);
    Cigarette cig4 = new Cigarette(this);

    // lista bytow na pozniej
    ArrayList<Entity> entities = new ArrayList<>();
    public ArrayList<Cigarette> cigarettes = new ArrayList<>();

    // mapa
    MapElementManager mapEl = new MapElementManager(this);

    // fazy gry
    public int gamePhase;
    public final int playPhase = 1;
    public final int pausedPhase = 2;

    //konstruktor
    public GamePanel(CardLayout cardLayout, JPanel mainPanel) {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, screenColumn, screenRow));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);    // gra jest caly czas "gotowa" na nacisniecie klawisza
        this.add(label);
        this.add(labelCig);
        gamePhase = playPhase;

        // pasek uzaleznienia
        addictionBar = new JProgressBar(0, player.max_addiction);
        addictionBar.setStringPainted(true); // Wyświetlanie procentów
        addictionBar.setForeground(Color.RED); // Kolor paska
        //addictionBar.setValue(10); // Początkowa wartość
        addictionBar.setDoubleBuffered(true);

        // panel uzaleznienia
        addictionPanel = new JPanel();
        addictionPanel.setPreferredSize(new Dimension(200, 20));
        addictionPanel.setLayout(new BorderLayout());
        addictionPanel.setBackground(Color.LIGHT_GRAY);
        addictionPanel.setVisible(true);
        addictionPanel.setDoubleBuffered(true);

        addictionPanel.add(addictionBar, BorderLayout.CENTER);

        this.add(addictionPanel);

        this.setVisible(true);
    }
    public Label label = new Label("Player");
    public Label labelCig = new Label("Cig");
    //public BarPanel addictionPanel = new BarPanel(this);
    JPanel addictionPanel;
    JProgressBar addictionBar;

    // watek gry
    Thread thread;
    public void stopThread() {
        thread.interrupt();
    }
    public void continueThread() {
        thread.start();
    }
    public void startThread(){
        thread = new Thread(this);
        thread.start();
    }
    @Override // bo z interfejsu Runnable
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
        if (gamePhase == playPhase) {
            player.update();
            cigarette.setPlayer(player);
            cigarette.update();
            cig2.setPlayer(player);
            cig2.update();
            cig3.setPlayer(player);
            cig3.update();
            cig4.setPlayer(player);
            cig4.update();
            cigarettes.add(cigarette);
            cigarettes.add(cig2);
            cigarettes.add(cig3);
            cigarettes.add(cig4);
        }
        else if (gamePhase == pausedPhase) {
            Graphics2D g2d = (Graphics2D) this.getGraphics();
            displayer.draw(g2d);
        }

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;    // ma wiecej funkcji 2d
        mapEl.draw(g2d);    // mapa pod graczem
        player.draw(g2d);
        cigarette.draw(g2d);
        cig2.draw(g2d);
        cig3.draw(g2d);
        cig4.draw(g2d);
        g2d.dispose();      // oszczedza pamiec
    }

    public void updateAddictionBar() {
        if (addictionBar != null) {
            addictionBar.setValue(player.addicted);
            addictionBar.revalidate();
            addictionBar.repaint();
        }
    }
}
