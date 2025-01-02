package entity;

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

public class Player extends Entity{
    KeyHandler keyHandler;
    GamePanel gP;


    public Player(GamePanel gP, KeyHandler kH) {
        this.gP = gP;
        this.keyHandler = kH;

        collisionBounds = new Rectangle(20,18,14,29);

        setDefaultPosition();
        getImage();

    }

    public void setDefaultPosition() {
        x = 48;
        y = 48;
        speed = 4;
        direction = "Down";
    }

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
