package game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import game.GamePanel.*;

public class KeyHandler implements KeyListener {

    public boolean goUp, goDown, goLeft, goRight;
    public int key;
    GamePanel gP;

    public KeyHandler(GamePanel gP) {
        this.gP = gP;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        key = e.getKeyCode();   // zwraca int kodu nacisnietego klawisza z klawiatury
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
                }
                else if (gP.gamePhase == gP.pausedPhase) {
                    gP.gamePhase = gP.playPhase;
                }

                break;
        }
    }

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
