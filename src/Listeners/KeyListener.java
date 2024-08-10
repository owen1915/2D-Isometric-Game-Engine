package Listeners;

import MainConfig.GameData;

import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {

    public boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false, plusPressed = false, minusPressed = false;
    private GameData gameData;

    public KeyListener(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
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
            gameData.zoomIn();
            plusPressed = false;
        }
        if (minusPressed) {
            gameData.zoomOut();
            minusPressed = false;
        }
    }
}
