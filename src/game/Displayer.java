package game;

import java.awt.*;

public class Displayer {
    GamePanel gP;
    Graphics2D g2d;
    Font arial_40;
    public Displayer(GamePanel gP) {
        this.gP = gP;

        arial_40 = new Font("Arial", Font.BOLD, 40);
    }

    public void displayAddictionBar() {

    }

    public void draw(Graphics2D g2d){
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
