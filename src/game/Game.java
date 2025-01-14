package game;

import entity.Cigarette;
import entity.Player;

import javax.swing.*;
import java.awt.*;

public class Game {

    public static void main(String[] args) {
        System.out.println("Hello World!");

        // tworzenie okna
        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize(1280, 1024);
        //window.setResizable(false);
        window.setTitle("Game");
        window.setLocationRelativeTo(null);
        ImageIcon icon = new ImageIcon("C:/Users/Lenovo/Documents/MyGame/resources/cig.png");
        window.setIconImage(icon.getImage());

        // przechodzenie miedzy ekranami gry i menu
        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);
        mainPanel.setPreferredSize(new Dimension(1248, 768));

        MenuPanel menuPanel = new MenuPanel(cardLayout, mainPanel);
        GamePanel gPanel = new GamePanel(cardLayout, mainPanel);
        GamePanel gPanel2 = new GamePanel(cardLayout, mainPanel);
        LevelChecker levelChecker = new LevelChecker();

        gPanel2.mapEl.getMap("/maps/mapkaProbna.txt");
        gPanel2.player.setDefaultPosition(212,142,3);
        gPanel2.cigarette.setDefaultPosition(428,382,3);
//        for(Cigarette cig : gPanel2.cigarettes){
//            gPanel2.cig.setDefaultPosition();
//        }


        GamePanel gPanel3 = new GamePanel(cardLayout, mainPanel);


        gPanel.gameSettings();
        gPanel.startThread();

       // gPanel2.startThread();

        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(gPanel, "Game");
        mainPanel.add(gPanel2, "Game2");
        mainPanel.add(gPanel3, "Game3");
//        mainPanel.add(addictionBar, "Addiction");
        cardLayout.show(mainPanel, "Menu");
        window.add(mainPanel);
        window.pack();      // okno bedzie dostosowywac sie do rzomiaru panelu
        window.setVisible(true);



    }
}