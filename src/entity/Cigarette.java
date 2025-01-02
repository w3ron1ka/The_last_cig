package entity;

import game.GamePanel;
import game.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Cigarette extends Entity {
    GamePanel gP;

    public Cigarette(GamePanel gP) {
        this.gP = gP;
       //collisionBounds = new Rectangle(20,18,14,29);

        setDefaultPosition();
        getImage();

    }

    public void setDefaultPosition() {
        x = 96;
        y = 48;
        speed = 4;
        direction = "Down";
    }
    public void getImage(){
        try {
            imageUp1 = ImageIO.read(getClass().getResourceAsStream("/cigarette/cig_back1.png"));
            imageUp2 = ImageIO.read(getClass().getResourceAsStream("/cigarette/cig_back2.png"));
            imageDown1 = ImageIO.read(getClass().getResourceAsStream("/cigarette/cig_front1.png"));
            imageDown2 = ImageIO.read(getClass().getResourceAsStream("/cigarette/cig_front2.png"));
            imageRight1 = ImageIO.read(getClass().getResourceAsStream("/cigarette/cig_prawo1.png"));
            imageRight2 = ImageIO.read(getClass().getResourceAsStream("/cigarette/cig_prawo2.png"));
            imageLeft1 = ImageIO.read(getClass().getResourceAsStream("/cigarette/cig_lewo1.png"));
            imageLeft2 = ImageIO.read(getClass().getResourceAsStream("/cigarette/cig_lewo2.png"));
        } catch (IOException e) {
            //throw new RuntimeException(e);
            e.printStackTrace();
        }
    }
    public void draw(Graphics2D g2d) {
        BufferedImage image = imageDown1;
        switch(direction){
            case "Up":
                if(!isWalking){
                    image = imageUp1;
                }
                else{
                    image = imageUp2;
                }
                break;
            case "Down":
                if(!isWalking){
                    image = imageDown1;
                }
                else{
                    image = imageDown2;
                }
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
