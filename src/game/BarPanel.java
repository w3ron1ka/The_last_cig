package game;

import entity.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

// CHYBA DO WYWALENIA
public class BarPanel extends JPanel {
    Graphics2D g2d;
    BufferedImage img1, img2, img3;
    GamePanel gP;
    JProgressBar addictionBar;
    //Player player;

    // panel z paskiem
   // JPanel addictionPanel;
    public BarPanel(GamePanel gP) {
        this.gP = gP;
        this.setPreferredSize(new Dimension(400, 50));
        this.setLayout(new BorderLayout());
        this.setBackground(Color.LIGHT_GRAY);
        //setPlayer(gP.player);


        this.add(addictionBar, BorderLayout.CENTER);
        this.setDoubleBuffered(true);
    }

    // Metoda do aktualizacji wartości paska
    public void updateAddictionBar() {
        if (addictionBar != null) {
            addictionBar.setValue(gP.player.addicted);
            addictionBar.revalidate();
            addictionBar.repaint();
        }
    }
    public void setPlayer(Player player) {  // bo player null inaczej

        // Inicjalizacja paska uzależnienia
        addictionBar = new JProgressBar(0, gP.player.max_addiction);
        addictionBar.setStringPainted(true); // Wyświetlanie procentów
        addictionBar.setForeground(Color.RED); // Kolor paska
        addictionBar.setValue(0); // Początkowa wartość
    }

}
