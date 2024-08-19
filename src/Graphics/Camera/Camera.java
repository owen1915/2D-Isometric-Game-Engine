package Graphics.Camera;

import MainConfig.GameData;

public class Camera {
    private GameData gameData;
    private int xOffset;
    private int yOffset;
    private int xSpeed = 16;
    private int ySpeed = 16;
    private int zoomAmnt = 8;
    private boolean dirty = false;

    public Camera(GameData gameData) {
        this.gameData = gameData;

        xOffset = gameData.screenWidth/2 - gameData.tileSize/2;
        yOffset = gameData.screenHeight/2 - (gameData.tileSize / 2 * gameData.worldSize/2);
    }

    float scale = 1;

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
        dirty = true;
    }

    public void ifRightPressed() {
        xOffset -= xSpeed;
        dirty = true;
    }

    public void ifUpPressed() {
        yOffset += ySpeed;
        dirty = true;
    }

    public void ifDownPressed() {
        yOffset -= ySpeed;
        dirty = true;
    }

    public void zoomIn() {
        if (!(gameData.tileSize + zoomAmnt > gameData.maxTileSize)) {
            gameData.tileSize += zoomAmnt;
            gameData.imageLoader.createTextures();
            gameData.isoCordTool.updateIso();
        }
        dirty = true;
    }

    public void zoomOut() {
        if (!(gameData.tileSize - zoomAmnt < gameData.minTileSize)) {
            gameData.tileSize -= zoomAmnt;
            gameData.imageLoader.createTextures();
            gameData.isoCordTool.updateIso();
        }
        dirty = true;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }
}
