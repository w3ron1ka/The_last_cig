package game;

import entity.Entity;
import entity.Player;

import java.awt.*;

/**
 * Klasa odpowiadająca za kolizje bytów z elementami mapy lub monetami
 */
public class CollisionDetector {

    GamePanel gamePanel;

    /**
     * Konstruktor CollisionDetector
     * @param gamePanel
     */
    public CollisionDetector(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    /**
     * Metoda sprawdzająca kolizje bytów z elementami mapy
     * @param entity    Byt którego kolizja jest sprawdzana
     */
    public void checkCollision(Entity entity) {

        int entityLeftX = entity.x + entity.collisionBounds.x;
        int entityRightX = entity.x + entity.collisionBounds.x + entity.collisionBounds.width;
        int entityTopY = entity.y + entity.collisionBounds.y;
        int entityBottomY = entity.y + entity.collisionBounds.y + entity.collisionBounds.height;

        int entityLeftCol = entityLeftX/gamePanel.dispGridSize;
        int entityRightCol = entityRightX/gamePanel.dispGridSize;
        int entityTopRow = entityTopY/gamePanel.dispGridSize;
        int entityBottomRow = entityBottomY/gamePanel.dispGridSize;

        int mapElementNumber1, mapElementNumber2;
        switch (entity.direction) {
            case "Up":
                entityTopRow = (entityTopY - entity.speed)/gamePanel.dispGridSize;
                mapElementNumber1 = gamePanel.mapEl.map[entityLeftCol][entityTopRow];
                mapElementNumber2 = gamePanel.mapEl.map[entityRightCol][entityTopRow];
                if (gamePanel.mapEl.mapElements[mapElementNumber1].collision == true || gamePanel.mapEl.mapElements[mapElementNumber2].collision == true) {
                    entity.collided = true;
                }
                break;
            case "Down":
                entityBottomRow = (entityBottomY + entity.speed)/gamePanel.dispGridSize;
                mapElementNumber1 = gamePanel.mapEl.map[entityLeftCol][entityBottomRow];
                mapElementNumber2 = gamePanel.mapEl.map[entityRightCol][entityBottomRow];
                if (gamePanel.mapEl.mapElements[mapElementNumber1].collision == true || gamePanel.mapEl.mapElements[mapElementNumber2].collision == true) {
                    entity.collided = true;
                }
                break;
            case "Left":
                entityLeftCol = (entityLeftX - entity.speed)/gamePanel.dispGridSize;
                mapElementNumber1 = gamePanel.mapEl.map[entityLeftCol][entityBottomRow];
                mapElementNumber2 = gamePanel.mapEl.map[entityLeftCol][entityTopRow];
                if (gamePanel.mapEl.mapElements[mapElementNumber1].collision == true || gamePanel.mapEl.mapElements[mapElementNumber2].collision == true) {
                    entity.collided = true;
                }
                break;
            case "Right":
                entityRightCol = (entityRightX + entity.speed)/gamePanel.dispGridSize;
                mapElementNumber1 = gamePanel.mapEl.map[entityRightCol][entityBottomRow];
                mapElementNumber2 = gamePanel.mapEl.map[entityRightCol][entityTopRow];
                if (gamePanel.mapEl.mapElements[mapElementNumber1].collision == true || gamePanel.mapEl.mapElements[mapElementNumber2].collision == true) {
                    entity.collided = true;
                }
                break;
        }


    }
//moze jednak tego nie robie v
//    public void checkEntityCollision(Entity entity, Entity e2) {
//
//    }

    /**
     * Metoda sprawdzająca kolizję gracza z monetami
     * @param player    gracz zbierający monety
     * @return  zwracany jest index (która z kolei) moneta została zebrana
     */
    public int checkCoin(Player player){

        int index = 666;

        for (int i =0; i<gamePanel.coins.length; i++){
            if(gamePanel.coins[i] != null){
                // granice gracza
                player.collisionBounds.x = player.x + player.collisionBounds.x;
                player.collisionBounds.y = player.y + player.collisionBounds.y;
                // granice monet
                gamePanel.coins[i].collisionBounds.x = gamePanel.coins[i].x + gamePanel.coins[i].collisionBounds.x;
                gamePanel.coins[i].collisionBounds.y = gamePanel.coins[i].y + gamePanel.coins[i].collisionBounds.y;

                switch(player.direction){       // intersect sprawdza czy nachodza na siebie rect monety i gracza
                    case "Up":
                        player.collisionBounds.y -= player.speed;
                       if(player.collisionBounds.intersects(gamePanel.coins[i].collisionBounds)){
                           index = i;
                       }
                       break;
                    case "Down":
                        player.collisionBounds.y += player.speed;
                        if(player.collisionBounds.intersects(gamePanel.coins[i].collisionBounds)){
                            index = i;
                        }
                        break;
                    case "Right":
                        player.collisionBounds.x += player.speed;
                        if(player.collisionBounds.intersects(gamePanel.coins[i].collisionBounds)){
                            index = i;
                        }
                        break;
                    case "Left":
                        player.collisionBounds.x -= player.speed;
                        if(player.collisionBounds.intersects(gamePanel.coins[i].collisionBounds)){
                            index = i;
                        }
                        break;
                }
                player.collisionBounds.x = player.resetCollisionBoundX;
                player.collisionBounds.y = player.resetCollisionBoundY;
                gamePanel.coins[i].collisionBounds.x = gamePanel.coins[i].resetCollisionBoundX;
                gamePanel.coins[i].collisionBounds.y = gamePanel.coins[i].resetCollisionBoundY;
            }
        }
        return index;
    }
}
