package Graphics.Camera;

import Graphics.GameGraphics;
import Graphics.Panels.GamePanel;
import MainConfig.GameData;

import javax.swing.*;

public class Camera {
    private GameData gameData;
    private int xOffset;
    private int yOffset;

    public Camera(GameData gameData) {
        this.gameData = gameData;

        xOffset = gameData.screenWidth/2;
        yOffset = gameData.screenHeight/2;
    }

    float scale = 1;

    public Camera(){

    }

    public int getyOffset() {
        return yOffset;
    }

    public int getxOffset() {
        return xOffset;
    }

    public void setxOffset(int xOffset) {
        this.xOffset = xOffset;
    }

    public void setyOffset(int yOffset) {
        this.yOffset = yOffset;
    }
}
