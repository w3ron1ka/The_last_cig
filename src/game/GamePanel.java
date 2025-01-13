package game;

import background.MapElement;
import background.MapElementManager;
import entity.Cigarette;
import entity.Entity;
import entity.Player;
import objects.Coin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

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
    public Displayer textDisplayer = new Displayer(this);
    public Displayer barDisplayer = new Displayer(this);
    CoinSetter coinSetter = new CoinSetter(this);

    public JPanel addictionPanel;
    public JProgressBar addictionBar;

    // dzwiek
    Sound melody = new Sound();
    Sound soundEffect = new Sound();

    public CollisionDetector collisionDetector = new CollisionDetector(this);
    // gracz, byty, obiekty
    Player player = new Player(this, keyHandler);

    Cigarette cigarette = new Cigarette(this);
    Cigarette cig2 = new Cigarette(this);
    Cigarette cig3 = new Cigarette(this);
    Cigarette cig4 = new Cigarette(this);

    public Coin coins[] = new Coin[20];

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

//// panel i pasek uzależnienia
//        addictionPanel = new JPanel();
//        addictionBar = new JProgressBar(0, player.max_addiction);
//
//        barDisplayer.initAddictionBar(addictionPanel, addictionBar);
//
//        addictionPanel.setPreferredSize(new Dimension(200,20));
//        add(addictionPanel);

//menu i listener
        menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Menu");
                mainPanel.getComponent(0).requestFocusInWindow();

            }
        });
        setButton("resources/buttons/menu.png", "resources/buttons/menuClick.png");
        this.add(menu);

        this.setVisible(true);
    }

    public void gameSettings(){
        playMelody(0);
        //coinSetter.setCoin();
        // Inicjalizacja tablicy coins
        for (int i = 0; i < coins.length; i++) {
            coins[i] = new Coin(); // Tworzenie nowego obiektu Coin dla każdego elementu
        }
        coinSetter.setRandom();
    }
// wyswietlane
    ImageIcon menuScaledIcon;
    public JButton menu = new JButton(menuScaledIcon);
    public Label label = new Label("Player");
    public Label labelCig = new Label("Cig");



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
            textDisplayer.drawPause(g2d);
        }

    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;    // ma wiecej funkcji 2d
        mapEl.draw(g2d);    // mapa pod graczem
       // coins[0].draw(g2d,this);
        for(int i = 0; i< coins.length; i++){
            if (coins[i] != null){
                coins[i].draw(g2d,this);
            }
        }
        player.draw(g2d);
        cigarette.draw(g2d);
        cig2.draw(g2d);
        cig3.draw(g2d);
        cig4.draw(g2d);

        textDisplayer.drawCoin(g2d);
        textDisplayer.drawCigPack(g2d);
        textDisplayer.drawPause(g2d);
        //displayer.updateAddictionBar();
        g2d.dispose();      // oszczedza pamiec
    }



    public void playMelody(int i){
        melody.setFile(0);
        melody.play();
        melody.loop();
        melody.stop();
    }
    public void playSoundEffect(int i){
        soundEffect.setFile(i);
        soundEffect.play();

    }
    public void pauseMelody(){
        melody.stop();
    }

    public void setButton(String buttonPath, String buttonHoverPath){
        // guziki
        //menu
        ImageIcon menuIcon = new ImageIcon(buttonPath);
        ImageIcon menuIconHover = new ImageIcon(buttonHoverPath);

        Image menuImage = menuIcon.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);
        Image menuImageHover = menuIconHover.getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH);

        menuScaledIcon = new ImageIcon(menuImage);
        ImageIcon menuScaledIconHover = new ImageIcon(menuImageHover);

        //menu.setAlignmentX(CENTER_ALIGNMENT);
        menu.setPreferredSize(new Dimension(100, 50));
        menu.setMaximumSize(new Dimension(100, 50));
        menu.setBorderPainted(false);
        // zmiana guzika
        menu.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                menu.setIcon(menuScaledIconHover);
            }
            public void mouseExited(MouseEvent e) {
                menu.setIcon(menuScaledIcon);
            }
        });
    }
}

class CoinSetter {
    GamePanel gP;

    public CoinSetter(GamePanel gP) {
        this.gP = gP;

    }
    public void setCoin(){
        gP.coins[0] = new Coin();
        gP.coins[0].x = 3 * gP.dispGridSize+ gP.dispGridSize/3;
        gP.coins[0].y = 6 * gP.dispGridSize+ gP.dispGridSize/3;
    }
    public void setRandom(){
        int coinX, coinY;
        Random random = new Random();
        //gP.mapEl.getMap("/maps/map1.txt");

        for (int i =0; i<gP.coins.length; i++){
            coinX = random.nextInt(1,24);
            coinY = random.nextInt(1,13);
            gP.coins[i].x = coinX * gP.dispGridSize+ gP.dispGridSize/3;
            gP.coins[i].y = coinY * gP.dispGridSize+ gP.dispGridSize/3;
        }
    }

//    public void setRandom() {
//        Random random = new Random();
//
//        for (int i = 0; i < gP.coins.length; i++) {
//            int coinX, coinY;
//            boolean validPosition = false;
//
//            while (!validPosition) {
//                // Losowanie współrzędnych
//                coinX = random.nextInt(1,24);
//                coinY = random.nextInt(1,14);
//
//                // Sprawdzenie, czy pole jest dostępne
//                if (!gP.mapEl.mapElements[coinX]) {       //gP.mapEl.map[coinY][coinX].collision     //gP.mapEl.mapElements[coinX][coinY].collision
//                    gP.coins[i] = new Coin();
//                    gP.coins[i].x = coinX * gP.dispGridSize + gP.dispGridSize / 3;
//                    gP.coins[i].y = coinY * gP.dispGridSize + gP.dispGridSize / 3;
//                    validPosition = true;
//                }
//            }
//        }
//    }
}
