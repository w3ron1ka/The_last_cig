package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class MenuPanel extends JPanel {

    GamePanel gP;
    LevelManager lM;
    Graphics2D g2d;
    Displayer displayer;

    private Timer avatarTimer;

    private int buttonWidth = 225;
    private int buttonHeight = 75;

    // IKONKI GUZIKOW
    //start
    ImageIcon startIcon = new ImageIcon("resources/buttons/play.png");
    ImageIcon startIconHover = new ImageIcon("resources/buttons/playClick.png");

    Image startImage = startIcon.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
    Image startImageHover = startIconHover.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);

    ImageIcon startScaledIcon = new ImageIcon(startImage);
    ImageIcon startScaledIconHover = new ImageIcon(startImageHover);

    //exit
    ImageIcon exitIcon = new ImageIcon("resources/buttons/exit.png");
    ImageIcon exitIconHover = new ImageIcon("resources/buttons/exitClick.png");

    Image exitImage = exitIcon.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
    Image exitImageHover = exitIconHover.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);

    ImageIcon exitScaledIcon = new ImageIcon(exitImage);
    ImageIcon exitScaledIconHover = new ImageIcon(exitImageHover);

    //first level
    ImageIcon lvl1Icon = new ImageIcon("resources/buttons/lvl1.png");
    ImageIcon lvl1IconHover = new ImageIcon("resources/buttons/lvl1Click.png");

    Image lvl1Image = lvl1Icon.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
    Image lvl1ImageHover = lvl1IconHover.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);

    ImageIcon lvl1ScaledIcon = new ImageIcon(lvl1Image);
    ImageIcon lvl1ScaledIconHover = new ImageIcon(lvl1ImageHover);

    //second level
    ImageIcon lvl2Icon = new ImageIcon("resources/buttons/lvl2.png");
    ImageIcon lvl2IconHover = new ImageIcon("resources/buttons/lvl2Click.png");

    Image lvl2Image = lvl2Icon.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
    Image lvl2ImageHover = lvl2IconHover.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);

    ImageIcon lvl2ScaledIcon = new ImageIcon(lvl2Image);
    ImageIcon lvl2ScaledIconHover = new ImageIcon(lvl2ImageHover);

    //third level
    ImageIcon lvl3Icon = new ImageIcon("resources/buttons/lvl3.png");
    ImageIcon lvl3IconHover = new ImageIcon("resources/buttons/lvl3Click.png");

    Image lvl3Image = lvl3Icon.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);
    Image lvl3ImageHover = lvl3IconHover.getImage().getScaledInstance(buttonWidth, buttonHeight, Image.SCALE_SMOOTH);

    ImageIcon lvl3ScaledIcon = new ImageIcon(lvl3Image);
    ImageIcon lvl3ScaledIconHover = new ImageIcon(lvl3ImageHover);

    // TWORZENIE GUZIKOW
    JButton startGame = new JButton(startScaledIcon);
    JButton exitGame = new JButton(exitScaledIcon);
    JButton firstLevel = new JButton(lvl1ScaledIcon);
    JButton secondLevel = new JButton(lvl2ScaledIcon);
    JButton thirdLevel = new JButton(lvl3ScaledIcon);

    public void setGamePanel(GamePanel gP){
        this.gP = gP;
    }
    public MenuPanel(CardLayout cardLayout, JPanel mainPanel) {
        //this.setPreferredSize(new Dimension(gP.screenWidth, gP.screenHeight));
        setPreferredSize(new Dimension(1248, 768));
        this.setDoubleBuffered(true);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        avatarTimer = new Timer(200, e -> {
            if (displayer != null) {
                displayer.moveAvatar(); // Zmiana sprite'a
            }
            repaint(); // Odśwież panel, aby pokazać zmiany
        });
        avatarTimer.start(); // Uruchom Timer

        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gP.stopGame();
                gP.loadLevel(new Level(1, "/maps/map1.txt", 40, 4));
                cardLayout.show(mainPanel, "Game");
                mainPanel.getComponent(1).requestFocusInWindow(); // 1 bo indeksujemy od 0, a gamepanel jako 2 dodany w Game
                gP.resumeGame();
            }
        });
        firstLevel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gP.stopGame();
                gP.loadLevel(new Level(1, "/maps/map1.txt", 40, 4));
                cardLayout.show(mainPanel, "Game");
                mainPanel.getComponent(1).requestFocusInWindow();
                gP.resumeGame();
            }
        });
        secondLevel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gP.stopGame();
                gP.loadLevel(new Level(2, "/maps/map2.txt", 30, 8));
                cardLayout.show(mainPanel, "Game");
                mainPanel.getComponent(1).requestFocusInWindow();
                gP.resumeGame();
            }
        });
        thirdLevel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                gP.stopGame();
                gP.loadLevel(new Level(3, "/maps/map3.txt", 20, 12));
                cardLayout.show(mainPanel, "Game");
                mainPanel.getComponent(1).requestFocusInWindow();
                gP.resumeGame();
            }
        });
        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        // USTAWIENIA GUZIKOW

        // start
        startGame.setAlignmentX(CENTER_ALIGNMENT); // Wycentrowanie przycisku
        startGame.setPreferredSize(new Dimension(225, 75));
        startGame.setMaximumSize(new Dimension(225, 75));
        startGame.setBorderPainted(false);
        startGame.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                startGame.setIcon(startScaledIconHover);
            }
            public void mouseExited(MouseEvent e) {
                startGame.setIcon(startScaledIcon);
            }
        });

        // exit
        exitGame.setAlignmentX(CENTER_ALIGNMENT);
        exitGame.setPreferredSize(new Dimension(225, 75));
        exitGame.setMaximumSize(new Dimension(225, 75));
        exitGame.setBorderPainted(false);
        exitGame.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                exitGame.setIcon(exitScaledIconHover);
            }
            public void mouseExited(MouseEvent e) {
                exitGame.setIcon(exitScaledIcon);
            }
        });

        //first level
        firstLevel.setAlignmentX(CENTER_ALIGNMENT);
        firstLevel.setPreferredSize(new Dimension(225, 75));
        firstLevel.setMaximumSize(new Dimension(225, 75));
        firstLevel.setBorderPainted(false);
        firstLevel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                firstLevel.setIcon(lvl1ScaledIconHover);
            }
            public void mouseExited(MouseEvent e) {
                firstLevel.setIcon(lvl1ScaledIcon);
            }
        });

        //second level
        secondLevel.setAlignmentX(CENTER_ALIGNMENT);
        secondLevel.setPreferredSize(new Dimension(225, 75));
        secondLevel.setMaximumSize(new Dimension(225, 75));
        secondLevel.setBorderPainted(false);
        secondLevel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                secondLevel.setIcon(lvl2ScaledIconHover);
            }
            public void mouseExited(MouseEvent e) {
                secondLevel.setIcon(lvl2ScaledIcon);
            }
        });

        //third level
        thirdLevel.setAlignmentX(CENTER_ALIGNMENT);
        thirdLevel.setPreferredSize(new Dimension(225, 75));
        thirdLevel.setMaximumSize(new Dimension(225, 75));
        thirdLevel.setBorderPainted(false);
        thirdLevel.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                thirdLevel.setIcon(lvl3ScaledIconHover);
            }
            public void mouseExited(MouseEvent e) {
                thirdLevel.setIcon(lvl3ScaledIcon);
            }
        });

        this.add(Box.createRigidArea(new Dimension(0, 250)));
        this.add(startGame);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(exitGame);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(firstLevel);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(secondLevel);
        this.add(Box.createRigidArea(new Dimension(0, 20)));
        this.add(thirdLevel);


        MouseListener listener = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);

            }
        };
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawAvatarArea(g2d);
    }
    public void drawAvatarArea(Graphics2D g2d){
        g2d.setColor(Color.BLACK);
        g2d.drawRect(gP.dispGridSize,gP.dispGridSize*4+40,gP.dispGridSize*8,gP.dispGridSize*10);

        if (displayer == null){
            displayer = new Displayer(gP);
        }
        displayer.drawAvatar(g2d);
    }


}
