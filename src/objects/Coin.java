package objects;

import game.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Coin {
    public BufferedImage image;
    public int x,y;
    public boolean collision = false;
    public Rectangle collisionBounds = new Rectangle(0,0,16,16);
    public int resetCollisionBoundX = 0;
    public int resetCollisionBoundY = 0;

    public Coin() {
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/coin.png"));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void draw(Graphics2D g2d, GamePanel gP) {
        g2d.drawImage(image,x,y, gP.dispGridSize/3, gP.dispGridSize/3, null);
        g2d.setColor(Color.RED);

        // roboczo sprawdzanie granic do kolizji
        //g2d.drawRect(x + collisionBounds.x, y+ collisionBounds.y, collisionBounds.width, collisionBounds.height);
    }
}
