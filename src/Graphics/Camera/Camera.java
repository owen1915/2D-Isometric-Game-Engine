package Graphics.Camera;

import MainConfig.GameData;

public class Camera {
    private GameData gameData;
    private int xOffset;
    private int yOffset;
    private int xSpeed = 16;
    private int ySpeed = 16;
    private int zoomAmnt = 32;
    private boolean dirty = false;

    public Camera(GameData gameData) {
        this.gameData = gameData;

        xOffset = gameData.screenWidth/2 - gameData.tileSize/2;
        yOffset = gameData.screenHeight/2 - (gameData.tileSize / 2 * gameData.worldSize/2);
    }

    public int maxScale = 3;
    public int scale = 1;

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

    public void ifLeftPressed() {
        xOffset += xSpeed;
    }

    public void ifRightPressed() {
        xOffset -= xSpeed;
    }

    public void ifUpPressed() {
        yOffset += ySpeed;
    }

    public void ifDownPressed() {
        yOffset -= ySpeed;
    }

    public void zoomIn() {
        if (scale < maxScale - 1) {
            scale++;
            gameData.tileSize += zoomAmnt;
            System.out.println(gameData.tileSize);
        }
    }

    public void zoomOut() {
        if (scale > 0) {
            scale--;
            gameData.tileSize -= zoomAmnt;
            System.out.println(gameData.tileSize);
        }
    }

    public int getZoomAmnt() {
        return zoomAmnt;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
}
