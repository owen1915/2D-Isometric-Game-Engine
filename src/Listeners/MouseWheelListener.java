package Listeners;

import MainConfig.GameData;

import java.awt.event.MouseWheelEvent;

public class MouseWheelListener implements java.awt.event.MouseWheelListener {

    private GameData gameData;

    public MouseWheelListener(GameData gameData) {
        this.gameData = gameData;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getWheelRotation() < 0) {
            gameData.camera.zoomIn();
        }
        if (e.getWheelRotation() > 0) {
            gameData.camera.zoomOut();
        }
    }
}
