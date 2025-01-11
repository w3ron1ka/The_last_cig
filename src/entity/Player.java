package entity;

import game.BarPanel;
import game.Game;
import game.GamePanel;
import game.KeyHandler;
import game.BarPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Player extends Entity{
    KeyHandler keyHandler;
    GamePanel gP;
    BarPanel addictionPanel;
    int smokedCigs = 0;
    public int addicted = 0;
    int health;
    int smokingCounter =0;
    public int max_addiction = 100;
    JProgressBar addictionBar;

    public Player(GamePanel gP, KeyHandler kH) {
        this.gP = gP;
        this.keyHandler = kH;

        collisionBounds = new Rectangle(15,18,21,29);

        setDefaultPosition(48,48,2);
        getImage();
    }

    public void setDefaultPosition(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
//        x = 48;
//        y = 48;
//        speed = 2;
        direction = "Down";
    }

    public void smoke(ArrayList<Cigarette> cigarettes){
        for (Cigarette cig : cigarettes){
            int deltaX = cig.x - x;
            int deltaY = cig.y - y;

            if (Math.abs(deltaX) < 3 && Math.abs(deltaY) < 30){
                smokingCounter++;
                if  (smokingCounter == 1500) {
                    System.out.println("deltaX: " + deltaX);
                    System.out.println("deltaY: " + deltaY);
                    smokedCigs++;
                    System.out.println("Wypalone szlugi: "+smokedCigs);
                    smokingCounter = 0;
                    addicted = smokedCigs/2;
                }
            }
            else if (Math.abs(deltaY) < 3 && Math.abs(deltaX) < 30){
                smokingCounter++;
                if  (smokingCounter == 1500) {
                    System.out.println("deltaX: " + deltaX);
                    System.out.println("deltaY: " + deltaY);
                    smokedCigs++;
                    System.out.println("Wypalone szlugi: "+smokedCigs);
                    smokingCounter = 0;
                    addicted = smokedCigs/2;
                }
            }
            if (addicted < max_addiction){
                //addictionPanel.updateAddictionBar();
                gP.updateAddictionBar();
            }
        }
    }

//    public void updateAddictionBar(GamePanel gP){
//        this.gP = gP;
//
//        // dodac ze jesli lvl jakis to wtedy mniej czy wiecej jest max_addiction
//        if (gP.addictionBar != null) {
//            addictionBar.setValue(addicted);
//        }
//        else {                              // nie mozna w kolko tworzyc paska, tylko raz
//            gP.addictionBar = new JProgressBar(0,max_addiction);
//            gP.addictionBar.setStringPainted(true); // procenty
//            //addictionBar.setSize(100,40);
//            //addictionBar.setLocation(48, 48);
//            gP.addictionBar.setBorderPainted(true);
//            gP.addictionBar.setForeground(Color.RED); // Kolor paska
//            gP.addictionBar.setValue(0);
//            gP.addictionBar.setVisible(true);
//            gP.add(addictionBar);
//
//            gP.revalidate();
//            gP.repaint();
//        }
//    }
//    public void updateAddictionBar() {
//        if (addictionBar != null) {
//            addictionBar.setValue(addicted);
//        }
//    }

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

    public void update(){
        smoke(gP.cigarettes);
        //updateAddictionBar(gP);
        if(keyHandler.goUp || keyHandler.goDown || keyHandler.goRight || keyHandler.goLeft){
            if(keyHandler.goUp){        // to samo co: keyHandler.goUp == true
                direction = "Up";
                //y -= speed;
            }
            else if(keyHandler.goDown){
                direction = "Down";
               // y += speed;
            }
            else if(keyHandler.goRight){
                direction = "Right";
               // x += speed;
            }
            else if(keyHandler.goLeft){
                direction = "Left";
               // x -= speed;
            }
            // sprawdzamy kolizje
            collided = false;
            gP.collisionDetector.checkCollision(this);

            if (collided == false) {
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
            // if (keyHandler.keyReleased(keyHandler.key)) mozna pokombinowac zeby jak sie zatrzymujesz to byl statyczny
        }
        showCoordinates(x,y,gP);
    }

    public void showCoordinates(int x, int y, GamePanel gP) {
        x= this.x;
        y= this.y;
        this.gP = gP;
        gP.label.setText("Player coordinates X: " + x + " Y: " + y);
    }
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
    }
}
