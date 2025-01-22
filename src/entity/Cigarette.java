package entity;

import game.GamePanel;
import game.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Klasa rozszerzająca Entity, odpowiada za tworzenie obiektów, przed którymi ma uciekać gracz - papierosów
 */
public class Cigarette extends Entity {
    GamePanel gP;
    Player player;
    protected int randomNum = 0;

    /**
     * Konstruktor Cigarette ustawiający pozycję domyślną, ikonki i granice kolizji
     * @param gP
     */
    public Cigarette(GamePanel gP) {
        super(gP);
        this.gP = gP;
       // dobre na cig collisionBounds = new Rectangle(20,4,8,40);
        collisionBounds = new Rectangle(12,5,22,40);
        setDefaultPosition(540,386,2);
        getImage();
    }

    /**
     * Metoda ustawiająca gracza, hermetyzuje kod
     * @param player    obiekt klasy Player
     */
    public void setPlayer(Player player) {  // bo player null inaczej
        this.player = player;
    }

    /**
     * Metoda ustawiająca sposób zachowania papierosów
     */
    public void behave() {
        Random random = new Random();
        walkingCounter++;

        if (walkingCounter == 40 || collided) {
            if (player != null) {
               if (collided){
                   randomNum = random.nextInt(4);
                   walkingCounter = 0;
               }
               else {
                    followPlayer(player);
               }
            }
            else {
                randomNum = random.nextInt(4);
            }
            switch (randomNum) {
                case 0:
                    direction = "Up";
                    break;
                case 1:
                    direction = "Down";
                    break;
                case 2:
                    direction = "Right";
                    break;
                case 3:
                    direction = "Left";
                    break;
            }
            walkingCounter = 0;
        }
        //showCoordinates(x,y,gP);
    }

    /**
     * Metoda implementująca sposób śledzenia gracza
     * @param player    obiekt klasy Player, który jest śledzony
     */
    public void followPlayer(Player player) {
        this.player = player;

        int deltaX = player.x - x;
        int deltaY = player.y - y;

        if(Math.abs(deltaX) > Math.abs(deltaY)) {
            if (deltaX > 0) {
                if (!collided) {
                    randomNum = 2;  // Gracz jest po prawej stronie
                }
                else if (deltaY < 0) {
                    randomNum = 1;
                }
                else if (deltaY > 0) {
                    randomNum = 0;
                }
            }
            else {
                if (!collided) {
                    randomNum = 3;  // Gracz jest po lewej stronie
                }
                else if (deltaY < 0) {
                    randomNum = 1;
                }
                else if (deltaY > 0) {
                    randomNum = 0;
                }
            }
        }
        else {
            if (deltaY > 0) {
                if (!collided) {
                    randomNum = 1;  // Gracz jest poniżej
                }
                else if (deltaX < 0) {
                    randomNum = 3;
                }
                else if (deltaX > 0) {
                    randomNum = 2;
                }
            }
            else {
                if (!collided) {
                    randomNum = 0;  // Gracz jest powyżej
                }
                else if (deltaX < 0) {
                    randomNum = 3;
                }
                else if (deltaX > 0) {
                    randomNum = 2;
                }
            }
        }
    }

    /**
     * Metoda ustawiająca pozycję domyślną papierosów
     * @param x     współrzędna x na ekranie
     * @param y     współrzędna y na ekranie
     * @param speed     szybkość poruszania się papierosa
     */
    public void setDefaultPosition(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
//        x = 540;
//        y = 386;
//        speed = 2;
        direction = "Down";
    }

    public void showCoordinates(int x, int y, GamePanel gP) {
        x= this.x;
        y= this.y;
        this.gP = gP;
        gP.labelCig.setText("Cig coordinates X: " + x + " Y: " + y);
    }

    /**
     * Metoda pobierająca ikonki dla poszczególnych stanów papierosa (w górę, prawo, lewo, zmiana nogi)
     */
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

    /**
     * Metoda rysująca papierosy
     * @param g2d   Obiekt klasy Graphics2D umożliwiający dostosowanie grafiki papierosa
     */
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
        g2d.setColor(Color.RED);

        // roboczo sprawdzanie granic do kolizji
        //g2d.drawRect(x + collisionBounds.x, y+ collisionBounds.y, collisionBounds.width, collisionBounds.height);
    }
}
