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
    public MenuPanel mP;
    CoinSetter coinSetter = new CoinSetter(this);
    LevelManager levelManager;
    public int currentLevel = 0;
    public CollisionDetector collisionDetector = new CollisionDetector(this);
    public Displayer displayer;

    public void setMenuPanel(MenuPanel menuPanel) {
        this.mP = menuPanel;
    }


    // dzwiek
    Sound melody = new Sound();
    Sound soundEffect = new Sound();

    // gracz, byty, obiekty
    Player player = new Player(this, keyHandler);

    int cigCount = 1;

    public Coin coins[] = new Coin[40];

    // lista bytow
    public ArrayList<Cigarette> cigarettes = new ArrayList<>(cigCount);     //dodane cigCount moze trza zmienic potem!!!


    // mapa
    MapElementManager mapEl = new MapElementManager(this);

    // fazy gry
    public int gamePhase;
    public final int playPhase = 1;
    public final int pausedPhase = 2;
    public final int nextLevelPhase = 3;
    public final int menuPhase = 4;

    public void updateDisplayerState() {
        mP.displayer = this.displayer;
        mP.repaint();
    }

    //konstruktor
    public GamePanel(CardLayout cardLayout, JPanel mainPanel) {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setLayout(new FlowLayout(FlowLayout.CENTER, screenColumn, screenRow));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);    // gra jest caly czas "gotowa" na nacisniecie klawisza
        //this.add(label);
        //this.add(labelCig);
        this.add(menu);

        //displayer = new Displayer(this);
        this.displayer = new Displayer(this);
        //mP.setDisplayer(this.displayer);

        gamePhase = playPhase;

        levelManager = new LevelManager();
        loadLevel(levelManager.getCurrentLevel());

//listener do menu
        menu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(mainPanel, "Menu");
                mainPanel.getComponent(0).requestFocusInWindow();
                //gamePhase = menuPhase;
            }
        });
        this.setVisible(true);
    }

    public void gameSettings(){
        playMelody(0);
        setButton("resources/buttons/menu.png", "resources/buttons/menuClick.png");
        // Inicjalizacja tablicy coins
        for (int i = 0; i < coins.length; i++) {
            if (coins[i] == null) {
                coins[i] = new Coin(); // trzeba stworzyc obiekty zanim sie je porozklada po planszy
            }
        }
        //coinSetter.setRandom(40);
        coinSetter.setRandom(4);
    }
// wyswietlane
    ImageIcon menuScaledIcon;
    public JButton menu = new JButton(menuScaledIcon);
    public Label label = new Label("Player");
    public Label labelCig = new Label("Cig");

    // watek gry
    Thread thread;
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

        if (mP != null) {
            //mP.repaint();
            SwingUtilities.invokeLater(() -> mP.repaint());
        } else {
            System.err.println("MenuPanel is null in GamePanel!");
        }

        while(thread != null){
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / render;     // metoda delty
            lastTime = currentTime;

            if (delta >= 1 && gamePhase == playPhase) {
                update();   // odswieza info o pozycji gracza np
                repaint();  // rysuje od nowa ekran z ta info z update

                delta = 0;
            }
        }
    }

    public void update(){
        if (gamePhase == playPhase) {
            createCigarettes(1);

            for (Cigarette cig : cigarettes){
                cig.setPlayer(player);
                cig.update();
            }
            player.update();
//            cigarette.setPlayer(player);
//            cigarette.update();
//            cig2.setPlayer(player);
//            cig2.update();
//            cig3.setPlayer(player);
//            cig3.update();
//            cig4.setPlayer(player);
//            cig4.update();

//            cigarettes.add(cigarette);
//            cigarettes.add(cig2);
//            cigarettes.add(cig3);
//            cigarettes.add(cig4);

        }
        else if (gamePhase == pausedPhase) {
            Graphics2D g2d = (Graphics2D) this.getGraphics();
            textDisplayer.drawPause(g2d);
        }
        else if (gamePhase == nextLevelPhase) {
            Graphics2D g2d = (Graphics2D) this.getGraphics();
            textDisplayer.drawNextAddictionState(g2d);
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;    // ma wiecej funkcji 2d
        mapEl.draw(g2d);    // mapa pod graczem
        for (Coin coin : coins) {
            if (coin != null) {
                coin.draw(g2d, this);
            }
        }
        player.draw(g2d);

        for (Cigarette cig : cigarettes) {
            if (cig != null) {
                cig.draw(g2d);
            }
        }

        textDisplayer.drawCoin(g2d);
        textDisplayer.drawCigPack(g2d);
        textDisplayer.drawPause(g2d);
        textDisplayer.drawNextAddictionState(g2d);

        g2d.dispose();      // oszczedza pamiec
    }

    public void createCigarettes(int cigCount){
        this.cigCount = cigCount;

        while (cigarettes.size() < cigCount){
            cigarettes.add(new Cigarette(this));
        }
    }
    public void removeCig(int cigIndex){
        if (cigIndex != 666){
            cigarettes.clear();
        }
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

    public void loadLevel(Level level) {
        // bo wyrzucalo ConcurrentModificationException bez tego, ochrona przed wieloma watkami, blokada?
        synchronized (cigarettes){      // zeby po powrocie z 3lvl nie bylo juz 12 cig a mniej
            cigarettes.clear();
            createCigarettes(level.getCigarettesCount());
        }

        mapEl.getMap(level.getMapFilePath());
        coinSetter.setRandom(level.getCoinsCount());
        player.setDefaultPosition(48,48,2);

        //createCigarettes(level.getCigarettesCount());

        for (int i = 0; i < coins.length; i++){
            coins[i] = null;        // usuwanie starych monet przed nowym lvl
        }
        coinSetter.setRandom(level.getCoinsCount());
    }
    public void nextLevel(){
        Graphics2D g2d = (Graphics2D) this.getGraphics();
        synchronized (cigarettes){
            if (levelManager.nextLevel()){
                loadLevel(levelManager.getCurrentLevel());
                textDisplayer.resetCigPack(g2d);
            }
            else {
                System.out.println("koniec lvl!");
            }
        }

    }

    public void stopGame() {
        if (thread != null && thread.isAlive()) {
            thread.interrupt();                     // zatrzymuje watek
        }
    }

    public void resumeGame() {
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(this);         // robi nowy watek(ten)
            thread.start();
        }
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
    int coinsNum = 10;
    public CoinSetter(GamePanel gP) {
        this.gP = gP;
    }
    public void setCoin(){
        gP.coins[0] = new Coin();
        gP.coins[0].x = 3 * gP.dispGridSize+ gP.dispGridSize/3;
        gP.coins[0].y = 6 * gP.dispGridSize+ gP.dispGridSize/3;
    }

//    public void setRandom(int coinsNum){
//        this.coinsNum = coinsNum;
//        int coinX, coinY;
//        Random random = new Random();
//
//        for (int i =0; i < coinsNum; i++){
//            if (gP.coins[i] == null){
//                gP.coins[i] = new Coin();
//            }
//            coinX = random.nextInt(1,24);
//            coinY = random.nextInt(1,13);
//            gP.coins[i].x = coinX * gP.dispGridSize+ gP.dispGridSize/3;
//            gP.coins[i].y = coinY * gP.dispGridSize+ gP.dispGridSize/3;
//        }
//    }

    public void setRandom(int coinsNum) {
        this.coinsNum = coinsNum;
        Random random = new Random();

        for (int i = 0; i < coinsNum; i++) {
            int coinX, coinY;
            boolean validPosition = false;

            while (!validPosition) {
                // Losowanie współrzędnych
                coinX = random.nextInt(1,24);
                coinY = random.nextInt(1,13);

                int elementIndex = gP.mapEl.map[coinX][coinY];

                // Sprawdzenie, czy pole jest dostępne
                if (!gP.mapEl.mapElements[elementIndex].collision) {
                    gP.coins[i] = new Coin();
                    gP.coins[i].x = coinX * gP.dispGridSize + gP.dispGridSize / 3;
                    gP.coins[i].y = coinY * gP.dispGridSize + gP.dispGridSize / 3;
                    validPosition = true;

                    System.out.println("Moneta: X = " + coinX + ", Y = " + coinY + ", Element = " + elementIndex);
                }
            }
        }
    }

}
