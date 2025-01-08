package entity;

import game.GamePanel;
import game.KeyHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Entity {
    public int x, y;
    public int speed;

    public BufferedImage imageRight1, imageLeft1, imageUp1, imageDown1, imageRight2, imageLeft2, imageUp2, imageDown2;
    public String direction;
    public int walkingTime = 0;
    public boolean isWalking;

    public Rectangle collisionBounds;
    public boolean collided = false;
    public int walkingCounter = 0;

    GamePanel gP;
    public Entity(GamePanel gP) {
        this.gP = gP;
    }
    public Entity(){

    }

    public void update(){
        behave();
        collided = false;
        gP.collisionDetector.checkCollision(this);

        if (!collided)
            {
                switch (direction)
                {
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
        walkingTime++;
        if (walkingTime > 11)
            {
                if (!isWalking) {
                    isWalking = true;
                } else if (isWalking) {
                    isWalking = false;
                }
                walkingTime = 0;
            }
    }
    public void behave(){}
}
