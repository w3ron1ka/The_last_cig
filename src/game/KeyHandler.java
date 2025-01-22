package game;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import game.GamePanel.*;

/**
 * Klasa obsługująca klawiaturę
 */
public class KeyHandler implements KeyListener {

    public boolean goUp, goDown, goLeft, goRight;
    public int key;
    GamePanel gP;

    /**
     * Konstruktor KeyHandler
     * @param gP
     */
    public KeyHandler(GamePanel gP) {
        this.gP = gP;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Metoda obsługująca naciśnięcie odpowiednich klawiszy
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        key = e.getKeyCode();   // zwraca int kodu nacisnietego klawisza z klawiatury
        if (gP.gamePhase == gP.playPhase || gP.gamePhase == gP.pausedPhase) {
            switch (key) {
                case KeyEvent.VK_UP:
                    goUp = true;
                    break;
                case KeyEvent.VK_DOWN:
                    goDown = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    goRight = true;
                    break;
                case KeyEvent.VK_LEFT:
                    goLeft = true;
                    break;
                case KeyEvent.VK_P:
                    if (gP.gamePhase == gP.playPhase) {
                        gP.gamePhase = gP.pausedPhase;
                    } else if (gP.gamePhase == gP.pausedPhase) {
                        gP.gamePhase = gP.playPhase;
                    }

                    break;
            }
        }
        if (gP.gamePhase == gP.nextLevelPhase){
            switch (key) {
                case KeyEvent.VK_UP:
                    gP.textDisplayer.selectOption--;
                    if (gP.textDisplayer.selectOption < 0){
                        gP.textDisplayer.selectOption = 1;
                    }
                    gP.repaint();
                    break;
                case KeyEvent.VK_DOWN:
                    gP.textDisplayer.selectOption++;
                    if (gP.textDisplayer.selectOption > 1){
                        gP.textDisplayer.selectOption = 0;
                    }
                    gP.repaint();
                    break;
                case KeyEvent.VK_ENTER:
                    if (gP.textDisplayer.selectOption == 0){
                        gP.gamePhase = gP.playPhase;
                    }
                    else if (gP.textDisplayer.selectOption == 1){
                        gP.menu.doClick();
                    }
                    break;
            }

        }

    }

    /**
     * Metoda obsługująca odciśnięcie odpowiednich klawiszy
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        //gP.player.isWalking = false;
        key = e.getKeyCode();   // zwraca int kodu nacisnietego klawisza z klawiatury
        switch (key) {
            case KeyEvent.VK_UP:
                goUp = false;
                break;
            case KeyEvent.VK_DOWN:
                goDown = false;
                break;
            case KeyEvent.VK_RIGHT:
                goRight = false;
                break;
            case KeyEvent.VK_LEFT:
                goLeft = false;
                break;
        }
    }
}
