package entity;

import game.Displayer;
import game.Game;
import game.GamePanel;
import game.KeyHandler;


import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Klasa dziedzicząca po klasie Entity opisująca dodatkowo możliwe zachowania gracza
 */
public class Player extends Entity{
    KeyHandler keyHandler;
    GamePanel gP;
    int smokedCigs = 0;
    public int addicted = 0;
    int health;
    int smokingCounter =0;
    public int max_addiction = 100;
    public int resetCollisionBoundX = 15;
    public int resetCollisionBoundY = 18;
    public int wallet = 0;
    boolean levelUp;
    Displayer displayer;

    /**
     * Konstruktor włączający obsługę klawiszy dla poruszania się gracza i jego granice kolizji
     * @param gP
     * @param kH
     */
    public Player(GamePanel gP, KeyHandler kH) {
        this.gP = gP;
        this.keyHandler = kH;

        collisionBounds = new Rectangle(15,18,21,29);

        setDefaultPosition(48,48,2);
        getImage();
        //displayer = new Displayer(gP);
        displayer = gP.displayer;
    }

    /**
     * Metoda ustawiająca domyślną pozycję gracza
     * @param x     współrzędna x na ekranie
     * @param y     współrzędna y na ekranie
     * @param speed     szybkość poruszania się gracza
     */
    public void setDefaultPosition(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        direction = "Down";
    }

    /**
     * Metoda usuwająca monetę z ekranu, gdy kolizja z graczem i doliczająca ją do jego portfela
     * @param coinIndex wskazuje na index monety która została zebrana
     */
    public void pickUpCoin(int coinIndex){
        if (coinIndex != 666){
            wallet++;
            gP.coins[coinIndex] =null;
            //System.out.println("twoje pieniazki: "+wallet);
        }
    }

    /**
     * Metoda decydująca o przejściu w kolejne stadia uzależnienia gracza, sprawdza czy gracz i papieros są wystarczająco blisko
     * @param cigarettes    przesłana lista papierosów, po której iteruje metoda
     */
    public void smoke(ArrayList<Cigarette> cigarettes){
        synchronized (cigarettes){
            // zwykla petla zamiast foreach nie powoduje wyjatku ConcurrentModificationException
            for (int i = 0; i < cigarettes.size(); i++){
                Cigarette cig = cigarettes.get(i);

                int deltaX = cig.x - x;
                int deltaY = cig.y - y;
                // bylo <3 i <30 i counter 1500
                if ((Math.abs(deltaX) < 10 && Math.abs(deltaY) < 30) || (Math.abs(deltaY) < 3 && Math.abs(deltaX) < 30)){
                    smokingCounter++;
                    if  (smokingCounter == 10) {      //moze razy FPS
                        //System.out.println("deltaX: " + deltaX);
                        //System.out.println("deltaY: " + deltaY);
                        smokedCigs++;
                        //System.out.println("Wypalone szlugi: "+smokedCigs);
                        smokingCounter = 0;
                        addicted = smokedCigs;      /// /3
                        //gP.playSoundEffect(1);
                    }
                }
                if (addicted%30 == 0 && addicted != 0 && !levelUp){
                    gP.displayer.addictionState++;
                    //gP.updateDisplayerState();

                    gP.gamePhase = gP.nextLevelPhase;
                    gP.nextLevel();
                    levelUp = true;
                    smokedCigs = 0;
                    addicted = 0;
                    System.out.println("faza gry:" + gP.gamePhase);
                    //System.out.println("Addiction state updated to: " + gP.displayer.addictionState);
                    //addicted++;
                }
                if (addicted % 30 != 0){
                    levelUp = false;
                }
            }
        }
    }

    /**
     * Metoda pobierająca ikonki dla poszczególnych stanów gracza (w górę, prawo, lewo, zmiana nogi)
     */
    public void getImage(){
        try {
            imageUp1 = ImageIO.read(getClass().getResourceAsStream("/player/imageUp1.png"));
            imageDown1 = ImageIO.read(getClass().getResourceAsStream("/player/imageDown1.png"));
            imageRight1 = ImageIO.read(getClass().getResourceAsStream("/player/imageRight1.png"));
            imageRight2 = ImageIO.read(getClass().getResourceAsStream("/player/imageRight2.png"));
            imageLeft1 = ImageIO.read(getClass().getResourceAsStream("/player/imageLeft1.png"));
            imageLeft2 = ImageIO.read(getClass().getResourceAsStream("/player/imageLeft2.png"));
        } catch (IOException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }

    }

    /**
     * Metoda aktualizująca położenie gracza
     */
    public void update(){
        smoke(gP.cigarettes);

        if(keyHandler.goUp || keyHandler.goDown || keyHandler.goRight || keyHandler.goLeft){
            if(keyHandler.goUp){        // to samo co: keyHandler.goUp == true
                direction = "Up";
            }
            else if(keyHandler.goDown){
                direction = "Down";
            }
            else if(keyHandler.goRight){
                direction = "Right";
            }
            else if(keyHandler.goLeft){
                direction = "Left";
            }
            // sprawdzamy kolizje z elementami mapy i monetami
            collided = false;
            gP.collisionDetector.checkCollision(this);

            int coinIndex = gP.collisionDetector.checkCoin(this);
            pickUpCoin(coinIndex);

            if (!collided) {
                switch (direction) {
                    case "Up":
                        y -= speed;
                        break;
                    case "Down":
                        y += speed;
                        break;
                    case "Right":
                        x += speed;
                        break;
                    case "Left":
                        x -= speed;
                        break;
                }
            }
            // zmiana ikonki zeby postac chodzila zmieniala nogi, sprawdzamy czy idzie?
                walkingTime++;
                if (walkingTime > 11) {
                    if (!isWalking) {
                        isWalking = true;
                    } else if (isWalking) {
                        isWalking = false;
                    }
                    walkingTime = 0;
                }
        }
        //showCoordinates(x,y,gP);
    }

//    public void showCoordinates(int x, int y, GamePanel gP) {
//        x= this.x;
//        y= this.y;
//        this.gP = gP;
//        gP.label.setText("Player coordinates X: " + x + " Y: " + y);
//    }

    /**
     * Metoda rysująca gracza ze zmiennymi iknonkami na każdą stronę
     * @param g2d       Obiekt klasy Graphics2D umożliwiający dostosowanie grafiki gracza
     */
    public void draw(Graphics2D g2d) {
        BufferedImage image = imageDown1;
        switch(direction){
            case "Up":
                image = imageUp1;
                break;
            case "Down":
                image = imageDown1;
                break;
            case "Right":
                if(!isWalking){
                    image = imageRight1;
                }
                else{
                    image = imageRight2;
                }
                break;
            case "Left":
                if(!isWalking){
                    image = imageLeft1;
                }
                else{
                    image = imageLeft2;
                }
                break;
        }
        g2d.drawImage(image, x, y, gP.dispGridSize, gP.dispGridSize, null);
        g2d.setColor(Color.RED);

        // roboczo sprawdzanie granic do kolizji
        //g2d.drawRect(x + collisionBounds.x, y+ collisionBounds.y, collisionBounds.width, collisionBounds.height);
    }
}
