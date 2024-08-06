package Graphics.Camera;

import Graphics.GameGraphics;
import Graphics.Panels.GamePanel;
import MainConfig.GameData;

import javax.swing.*;

public class Camera {
    private GamePanel gamePanel;

    private static int tileHeight = 64;
    private static int tileWidth = 64;

    private int xOffset = GameData.screenWidth/2;
    private int yOffset = GameData.screenHeight/2;

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
