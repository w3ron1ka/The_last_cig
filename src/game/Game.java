package game;

import entity.Cigarette;
import entity.Player;

import javax.imageio.ImageIO;
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

        //Displayer avatarDisplayer = new Displayer();

        MenuPanel menuPanel = new MenuPanel(cardLayout, mainPanel);
        GamePanel gPanel = new GamePanel(cardLayout, mainPanel);

        menuPanel.setGamePanel(gPanel);
        gPanel.setMenuPanel(menuPanel);

        gPanel.gameSettings();
        gPanel.startThread();

        mainPanel.add(menuPanel, "Menu");
        mainPanel.add(gPanel, "Game");
        cardLayout.show(mainPanel, "Menu");
        //menuPanel.repaint();
        window.add(mainPanel);
        window.pack();      // okno bedzie dostosowywac sie do rzomiaru panelu
        window.setVisible(true);
    }
}