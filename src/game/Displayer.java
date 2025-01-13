package game;

import objects.Coin;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Displayer {
    GamePanel gP;
    Graphics2D g2d;
    Font arial_40;
    public JPanel addictionPanel;
    JProgressBar addictionBar;
    BufferedImage coinImage;
    BufferedImage cigPackImage;
    BufferedImage emptyCigPackImage;
    BufferedImage image; // do papierosrw

    public Displayer(GamePanel gP) {
        this.gP = gP;

        arial_40 = new Font("Arial", Font.BOLD, 40);
        Coin coin = new Coin();
        coinImage =coin.image;
    }

    // uzaleznienie chyba do wywalenia
    public void initAddictionBar(JPanel addictionPanel, JProgressBar addictionBar) {
        this.addictionPanel = addictionPanel;
        this.addictionBar = addictionBar;

        // panel uzaleznienia
        this.addictionPanel.setPreferredSize(new Dimension(200, 20));
        this.addictionPanel.setLayout(new BorderLayout());
        this.addictionPanel.setBackground(Color.LIGHT_GRAY);

        // pasek uzaleznienia
        this.addictionBar.setStringPainted(true); // Wyświetlanie procentów
        this.addictionBar.setForeground(Color.RED); // Kolor paska
        this.addictionBar.setValue(0); // Początkowa wartość
        this.addictionBar.setDoubleBuffered(true);

        this.addictionPanel.add(this.addictionBar, BorderLayout.CENTER);
        this.addictionPanel.setVisible(true);
    }
    public void updateAddictionBar() {
        if (addictionBar != null) {
            addictionBar.setValue(gP.player.addicted);
            addictionBar.revalidate();
            addictionBar.repaint();
        }
    }
    // ikonka monet

    public void drawCoin(Graphics2D g2d){

        g2d.setFont(arial_40);
        g2d.setColor(Color.YELLOW);
        g2d.drawImage(coinImage, gP.dispGridSize*22, gP.dispGridSize*15 - 24,gP.dispGridSize, gP.dispGridSize, null);
        g2d.drawString("x " + gP.player.wallet, gP.dispGridSize*22 + 50, gP.dispGridSize*15+ 40 -24);
    }
    // ikonka paczki papierosow

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
    public void drawCigPack(Graphics2D g2d) {
        getCigPackImage();

        int totalSlots = 10; // od 9 do 18 kratki
        int startSlot = 9;
        int slotWidth = gP.dispGridSize;
        int slotHeight = gP.dispGridSize;
        int yPosition = gP.dispGridSize * 15 - 24; // dla wszystkich slotow ten sam y

        for (int i = 0; i < totalSlots; i++) {
            int xPosition = gP.dispGridSize * (startSlot + i);

            // Sprawdź, czy slot powinien być pełny
            if (gP.player.addicted >= (i + 1) * 3) {
                g2d.drawImage(cigPackImage, xPosition, yPosition, slotWidth, slotHeight, null);
            } else {
                g2d.drawImage(emptyCigPackImage, xPosition, yPosition, slotWidth, slotHeight, null);
            }
        }
    }


    // napisy na ekranie
    public void drawPause(Graphics2D g2d){
        this.g2d = g2d;
        g2d.setColor(Color.MAGENTA);

        if(gP.gamePhase == gP.playPhase){

        }
        else if (gP.gamePhase == gP.pausedPhase) {
            drawTextOnScreen("PAUSED");
        }

    }
    public void drawTextOnScreen(String text){
        g2d.setFont(g2d.getFont().deriveFont(Font.BOLD,120f));
        int y = gP.screenHeight/2;
        int x = centerText(text);

        g2d.drawString(text, x, y);
    }
    public int centerText(String text){
        int length = (int)g2d.getFontMetrics().getStringBounds(text, g2d).getWidth();
        int x = gP.screenWidth/2 - length/2;
        return x;
    }
}
