package entity;

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
}
