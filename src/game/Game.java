package game;

import javax.swing.*;

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

        GamePanel gPanel = new GamePanel();
        window.add(gPanel);
        //window.pack();  // okno bedzie dostosowywac sie do rzomiaru panelu
       // gPanel.add("C:/Users/Lenovo/Documents/MyGame/resources/fronx1.png");
        window.setVisible(true);
        gPanel.startThread();

    }
}