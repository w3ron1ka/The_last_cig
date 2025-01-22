package game;

import objects.Coin;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Klasa wyświetlająca na panelach grafiki, teksty
 */
public class Displayer {
    GamePanel gP;
    MenuPanel mP;
    Graphics2D g2d;
    Font arial_40;
    public JPanel addictionPanel;
    JProgressBar addictionBar;
    BufferedImage coinImage;
    BufferedImage cigPackImage;
    BufferedImage emptyCigPackImage;
    BufferedImage avatarImage1, avatarImage2, avatarSmokingImage1, avatarSmokingImage2, avatarDegradedImage1, avatarDegradedImage2;
    int avatarSprite = 1;
    int avatarCounter = 0;
    public int addictionState = 0;
    public int selectOption = 0;

    /**
     * Konstruktor Displayer wczytujący ikonki avatara, monet
     * @param gP
     */
    public Displayer(GamePanel gP) {
        this.gP = gP;

        arial_40 = new Font("Arial", Font.BOLD, 40);
        Coin coin = new Coin();
        coinImage =coin.image;
        readAvatarImage();
    }
//    public Displayer() {
//
//        arial_40 = new Font("Arial", Font.BOLD, 40);
//        Coin coin = new Coin();
//        coinImage =coin.image;
//        readAvatarImage();
//    }

    // ikonka monet

    /**
     * Metoda rysująca monety na panelu gry
     * @param g2d   Obiekt klasy Graphics2D umożliwiający dostosowanie grafiki
     */
    public void drawCoin(Graphics2D g2d){

        g2d.setFont(arial_40);
        g2d.setColor(Color.YELLOW);
        g2d.drawImage(coinImage, gP.dispGridSize*22, gP.dispGridSize*15 - 24,gP.dispGridSize, gP.dispGridSize, null);
        g2d.drawString("x " + gP.player.wallet, gP.dispGridSize*22 + 50, gP.dispGridSize*15+ 40 -24);
    }
    // ikonka paczki papierosow

    /**
     * Metoda pobierająca ikonki paczek papierosów
     */
    public void getCigPackImage(){
        BufferedImage imageFull;
        BufferedImage imageEmpty;
        try {
            imageFull = ImageIO.read(getClass().getResourceAsStream("/objects/cigPack.png"));
            cigPackImage = imageFull;
            imageEmpty = ImageIO.read(getClass().getResourceAsStream("/objects/emptyCigPack.png"));
            emptyCigPackImage = imageEmpty;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Metoda rysująca ikonki paczek papierosów
     * @param g2d   Obiekt klasy Graphics2D umożliwiający dostosowanie grafiki
     */
    public void drawCigPack(Graphics2D g2d) {
        getCigPackImage();

        int totalSlots = 10; // od 9 do 18 kratki
        int startSlot = 9;
        int slotWidth = gP.dispGridSize;
        int slotHeight = gP.dispGridSize;
        int yPosition = gP.dispGridSize * 15 - 24; // dla wszystkich slotow ten sam y

        for (int i = 0; i < totalSlots; i++) {
            int xPosition = gP.dispGridSize * (startSlot + i);

            // czy slot ma byc pelny
            if (gP.player.addicted >= (i + 1) * 3) {
                g2d.drawImage(cigPackImage, xPosition, yPosition, slotWidth, slotHeight, null);
            }
            else {
                g2d.drawImage(emptyCigPackImage, xPosition, yPosition, slotWidth, slotHeight, null);
            }
        }

    }

    /**
     * Metoda resetująca (na puste sloty) ikonki paczek papierosów
     * @param g2d   Obiekt klasy Graphics2D umożliwiający dostosowanie grafiki
     */
    public void resetCigPack(Graphics2D g2d){
        getCigPackImage();
        int totalSlots = 10; // od 9 do 18 kratki
        int startSlot = 9;
        int slotWidth = gP.dispGridSize;
        int slotHeight = gP.dispGridSize;
        int yPosition = gP.dispGridSize * 15 - 24; // dla wszystkich slotow ten sam y

        for (int i = 0; i < totalSlots; i++) {
            int xPosition = gP.dispGridSize * (startSlot + i);
            g2d.drawImage(emptyCigPackImage, xPosition, yPosition, slotWidth, slotHeight, null);
        }
    }


    // napisy na ekranie

    /**
     * Metoda rysująca ekran przed kolejnym poziomem z wyborem opcji
     * @param g2d   Obiekt klasy Graphics2D umożliwiający dostosowanie grafiki
     */
    public void drawNextAddictionState(Graphics2D g2d){
        int x,y;
        String text;
        if (gP.gamePhase == gP.nextLevelPhase){

            //przyciemnienie
            g2d.setColor(new Color(0, 0, 0, 200));
            g2d.fillRect(0,0,gP.screenWidth, gP.screenHeight);

            x = centerText("You smoked ... much");
            y = gP.dispGridSize*5;

            text = "You smoked ... much";
            g2d.setColor(Color.BLACK);
            g2d.drawString(text, x, y);
            g2d.setColor(Color.MAGENTA);
            g2d.drawString(text, x-2, y-2);
            g2d.setColor(Color.WHITE);
            g2d.drawString(text, x-4, y-4);

            //idziemy do menu
            text = "I want to smoke more";
            g2d.setColor(Color.BLACK);
            g2d.drawString(text, x, y*2);
            if (selectOption == 0){
                g2d.setColor(Color.RED);
                g2d.drawString(">", x-gP.dispGridSize, y*2);
                g2d.drawString(text, x, y*2-4);
               // g2d.drawString(text, x, y*2);
            }

            //exit xd
            text = "I want to stop";
            g2d.setColor(Color.BLACK);
            g2d.drawString(text, x, y*2 +gP.dispGridSize+5);
            if (selectOption == 1){
                g2d.setColor(Color.RED);
                g2d.drawString(">", x-gP.dispGridSize, y*2+gP.dispGridSize+5);
                g2d.drawString(text, x, y*2-4+gP.dispGridSize+5);
                //g2d.drawString(text, x, y*2);
            }

            System.out.println("Faza gry: " + gP.gamePhase);
            //g2d.setColor(new Color(0, 0, 0, 80));

        }
    }

    /**
     * Metoda rysująca pauze w grze
     * @param g2d   Obiekt klasy Graphics2D umożliwiający dostosowanie grafiki
     */
    public void drawPause(Graphics2D g2d){
        this.g2d = g2d;
        g2d.setColor(Color.MAGENTA);

        if(gP.gamePhase == gP.playPhase){

        }
        else if (gP.gamePhase == gP.pausedPhase) {
            drawTextOnScreen("PAUSED", 120f);
            g2d.setColor(new Color(0, 0, 0, 80));
            g2d.fillRect(0,0,gP.screenWidth, gP.screenHeight);
        }

    }

    /**
     * Metoda rysująca tekst na ekranie
     * @param text  Napis, który chcemy wyświetlić na ekranie
     * @param textSize  Rozmiar wypisywanego tekstu
     */
    public void drawTextOnScreen(String text, float textSize){
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD,textSize));
        int y = gP.screenHeight/2;
        int x = centerText(text);

        g2d.drawString(text, x, y);
    }

    /**
     * Metoda centrująca tekst na ekranie
     * @param text  Napis który chcemy wycentrować
     * @return  współrzędna x na ekranie
     */
    public int centerText(String text){
        int length = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        int x = gP.screenWidth/2 - length/2;
        return x;
    }

    /**
     * Metoda rysująca avatar
     * @param g2d   Obiekt klasy Graphics2D umożliwiający dostosowanie grafiki
     */
    public void drawAvatar(Graphics2D g2d){
        BufferedImage image = avatarImage1;
        System.out.println("Drawing avatar with addictionState: " + addictionState);
        if (addictionState == 0){
            switch(avatarSprite){
                case 1:
                    image = avatarImage1;
                    break;
                case 2:
                    image = avatarImage2;
                    break;
            }
        }

        else if (addictionState == 1){
            image = avatarSmokingImage1;
            switch(avatarSprite){
                case 1:
                    image = avatarSmokingImage1;
                    break;
                case 2:
                    image = avatarSmokingImage2;
                    break;
            }
        }
        else if (addictionState >= 2){
            image = avatarDegradedImage1;
            switch(avatarSprite){
                case 1:
                    image = avatarDegradedImage1;
                    break;
                case 2:
                    image = avatarDegradedImage2;
                    break;
            }
        }
        // AvatarArea
        int avatarX = gP.dispGridSize - 100;
        int avatarY = gP.dispGridSize * 4+ 40;
        g2d.drawImage(image, avatarX, avatarY, gP.dispGridSize*10, gP.dispGridSize*10, null);
    }

    /**
     * Metoda zmieniająca sprite avatara
     */
    public void moveAvatar(){
        avatarCounter++;

        if (avatarCounter == 3) {
            if (avatarSprite == 1) {
                avatarSprite = 2;
            } else {
                avatarSprite = 1;
            }
            avatarCounter = 0;
            //mP.repaint();
        }
    }

    /**
     * Metoda wczytująca ikonki avatara
     */
    public void readAvatarImage(){
        try {
            avatarImage1 = ImageIO.read(getClass().getResourceAsStream("/avatar/sprite1.png"));
            avatarImage2 = ImageIO.read(getClass().getResourceAsStream("/avatar/sprite2up.png"));
            avatarSmokingImage1 = ImageIO.read(getClass().getResourceAsStream("/avatar/snoking1.png"));
            avatarSmokingImage2 = ImageIO.read(getClass().getResourceAsStream("/avatar/smoking2.png"));
            avatarDegradedImage1 = ImageIO.read(getClass().getResourceAsStream("/avatar/degraded1.png"));
            avatarDegradedImage2 = ImageIO.read(getClass().getResourceAsStream("/avatar/degraded2.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}


