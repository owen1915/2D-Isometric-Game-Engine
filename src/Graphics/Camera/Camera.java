package Graphics.Camera;

import MainConfig.GameData;

public class Camera {
    private GameData gameData;
    private int xOffset;
    private int yOffset;
    private int xSpeed = 16;
    private int ySpeed = 16;

    public Camera(GameData gameData) {
        this.gameData = gameData;

        xOffset = gameData.screenWidth/2;
        yOffset = gameData.screenHeight/2;
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
        int tileWidth = gameData.tileSize/2;
        if ((gameData.world.getWorldXSize() * tileWidth) * 2 > gameData.screenWidth) {
            if (xOffset < gameData.world.getWorldXSize() * tileWidth) {
                xOffset += xSpeed;
            } else {
                while (xOffset > gameData.world.getWorldXSize() * tileWidth) {
                    xOffset--;
                }
            }
        } else {
            if (xOffset - gameData.screenWidth < -gameData.world.getWorldXSize() * tileWidth) {
                xOffset += xSpeed;
            } else {
                while (xOffset - gameData.screenWidth > gameData.world.getWorldXSize() * tileWidth) {
                    xOffset--;
                }
            }
        }
    }

    public void ifRightPressed() {
        int tileWidth = gameData.tileSize/2;
        if ((gameData.world.getWorldXSize() * tileWidth) * 2 > gameData.screenWidth) {
            if (xOffset - gameData.screenWidth > -gameData.world.getWorldXSize() * gameData.tileSize/2) {
                xOffset -= xSpeed;
            } else {
                while (xOffset - gameData.screenWidth < -gameData.world.getWorldXSize() * gameData.tileSize/2) {
                    xOffset++;
                }
            }
        } else {
            if (xOffset > gameData.world.getWorldXSize() * tileWidth) {
                xOffset -= xSpeed;
            } else {
                while (xOffset < gameData.world.getWorldXSize() * tileWidth) {
                    xOffset++;
                }
            }
        }
    }

    public void ifUpPressed() {
        int tileHeight = gameData.tileSize/4;
        int tileDepthHeight = gameData.tileSize/2;

        if ((gameData.world.getWorldYSize() * tileHeight) * 2 > gameData.screenHeight) {
            int z = gameData.world.getMaxDepthAtTile(0, 0);
            if (yOffset < gameData.world.getWorldYSize() * tileHeight + tileDepthHeight * z) {
                yOffset += ySpeed;
            } else {
                while (yOffset > gameData.world.getWorldYSize() * tileHeight + tileDepthHeight * z) {
                    yOffset--;
                }
            }
        } else {
            if (gameData.camera.getyOffset() + tileDepthHeight - gameData.screenHeight < -gameData.world.getWorldYSize() * tileHeight) {
                yOffset += ySpeed;
            } else {
                while (gameData.camera.getyOffset() + tileDepthHeight - gameData.screenHeight > -gameData.world.getWorldYSize()  * tileHeight) {
                    yOffset--;
                }
            }
        }
    }

    public void ifDownPressed() {
        int tileHeight = gameData.tileSize/4;
        int tileDepthHeight = gameData.tileSize/2;

        if ((gameData.world.getWorldYSize() * tileHeight) * 2 > gameData.screenHeight) {
            if (gameData.camera.getyOffset() + tileDepthHeight - gameData.screenHeight > -gameData.world.getWorldYSize() * tileHeight) {
                yOffset -= ySpeed;
            } else {
                while (gameData.camera.getyOffset() + tileDepthHeight - gameData.screenHeight < -gameData.world.getWorldYSize() * tileHeight)    {
                    yOffset++;
                }
            }
        } else {
            int z = gameData.world.getMaxDepthAtTile(0, 0);
            if (gameData.camera.getyOffset() > gameData.world.getWorldYSize()  * tileHeight + (tileDepthHeight * z)) {
                yOffset -= ySpeed;
            } else {
                while (gameData.camera.getyOffset() < gameData.world.getWorldYSize()  * tileHeight + (tileDepthHeight * z)) {
                    yOffset++;
                }
            }
        }
    }

    public void zoomIn() {
        if (!(gameData.tileSize + 16 > gameData.maxTileSize)) {
            gameData.tileSize += 16;
            gameData.imageLoader.createTextures();
            gameData.gamePanel.getIsoCordTool().updateIso();
        }
    }

    public void zoomOut() {
        if (!(gameData.tileSize - 16 < gameData.minTileSize)) {
            gameData.tileSize -= 16;
            gameData.imageLoader.createTextures();
            gameData.gamePanel.getIsoCordTool().updateIso();
        }
    }


}
