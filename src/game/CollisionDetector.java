package game;

import entity.Entity;

import java.awt.*;

public class CollisionDetector {

    GamePanel gamePanel;
    public CollisionDetector(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

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
}
