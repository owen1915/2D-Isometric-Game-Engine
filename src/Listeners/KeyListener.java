package Listeners;

import Graphics.Inventory.Inventory;
import Graphics.Inventory.InventoryGraphics;
import MainConfig.GameData;
import World.WorldTile;

import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {

    public boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false, plusPressed = false, minusPressed = false;
    private GameData gameData;
    private InventoryGraphics inventoryGraphics;

    public KeyListener(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        this.inventoryGraphics = gameData.inventory.getInventoryGraphics();
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                upPressed = true;
                break;
            case KeyEvent.VK_S:
                downPressed = true;
                break;
            case KeyEvent.VK_A:
                leftPressed = true;
                break;
            case KeyEvent.VK_D:
                rightPressed = true;
                break;
            case 61:
                plusPressed = true;
                break;
            case KeyEvent.VK_MINUS:
                minusPressed = true;
                break;
            case KeyEvent.VK_R:
                gameData.world.rotate();
                break;
            case KeyEvent.VK_M:
                gameData.GAMESTATE = 1;
                break;
            case KeyEvent.VK_1:
                inventoryGraphics.createHotbarImage(0);
                break;
            case KeyEvent.VK_2:
                inventoryGraphics.createHotbarImage(1);
                break;
            case KeyEvent.VK_3:
                inventoryGraphics.createHotbarImage(2);
                break;
            case KeyEvent.VK_4:
                inventoryGraphics.createHotbarImage(3);
                break;
            case KeyEvent.VK_5:
                inventoryGraphics.createHotbarImage(4);
                break;
            case KeyEvent.VK_6:
                inventoryGraphics.createHotbarImage(5);
                break;
            case KeyEvent.VK_7:
                inventoryGraphics.createHotbarImage(6);
                break;
            case KeyEvent.VK_8:
                inventoryGraphics.createHotbarImage(7);
                break;
            case KeyEvent.VK_9:
                inventoryGraphics.createHotbarImage(8);
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
                upPressed = false;
                break;
            case KeyEvent.VK_S:
                downPressed = false;
                break;
            case KeyEvent.VK_A:
                leftPressed = false;
                break;
            case KeyEvent.VK_D:
                rightPressed = false;
                break;
            case 61:
                plusPressed = false;
                break;
            case KeyEvent.VK_MINUS:
                minusPressed = false;
                break;
            case KeyEvent.VK_X:
                gameData.GAMESTATE = 0;
                break;
        }
    }

    public void update() {
        if (upPressed) {
            gameData.camera.ifUpPressed();
        }
        if (downPressed) {
            gameData.camera.ifDownPressed();
        }
        if (leftPressed) {
            gameData.camera.ifLeftPressed();
        }
        if (rightPressed) {
            gameData.camera.ifRightPressed();
        }
        if (plusPressed) {
            gameData.camera.zoomIn();
            plusPressed = false;
        }
        if (minusPressed) {
            gameData.camera.zoomOut();
            minusPressed = false;
        }
    }
}
