package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MenuPanel extends JPanel {

    // guziki
    JButton startGame = new JButton("Start Game");
    JButton exitGame = new JButton("Exit");

    public MenuPanel(CardLayout cardLayout, JPanel mainPanel) {
        //this.setPreferredSize(new Dimension(gP.screenWidth, gP.screenHeight));
        setPreferredSize(new Dimension(1248, 768));
        this.setDoubleBuffered(true);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        startGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               cardLayout.show(mainPanel, "Game");
               mainPanel.getComponent(1).requestFocusInWindow(); // 1 bo indeksujemy od 0, a gamepanel jako 2 dodany w Game
            }
        });
        exitGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        startGame.setAlignmentX(CENTER_ALIGNMENT); // Wycentrowanie przycisku
        exitGame.setAlignmentX(CENTER_ALIGNMENT);
        this.add(startGame);
        this.add(exitGame);
    }
}
