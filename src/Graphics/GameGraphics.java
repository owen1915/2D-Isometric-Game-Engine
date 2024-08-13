package Graphics;

import Graphics.Panels.GamePanel;
import Listeners.MouseListener;
import MainConfig.GameData;
import MainConfig.ImageLoader;
import World.WorldTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameGraphics {

    private IsoCordTool isoCordTool;
    private GameData gameData;
    private ImageLoader imageLoader;
    private BufferedImage bufferedImage;
    private Graphics2D bufferedGraphics;

    public GameGraphics(GameData gameData) {
        this.gameData = gameData;
        imageLoader = gameData.imageLoader;
        isoCordTool = gameData.gamePanel.getIsoCordTool();

        bufferedImage = new BufferedImage(gameData.screenWidth, gameData.screenHeight, BufferedImage.TYPE_INT_ARGB);
        bufferedGraphics = (Graphics2D) bufferedImage.getGraphics();
    }

    public void render(Graphics2D g2) {
        bufferedGraphics.clearRect(0, 0, gameData.screenWidth, gameData.screenHeight);
        //bufferedGraphics.drawImage(imageLoader.getBackground()[0], 0, 0, gameData.screenWidth, gameData.screenHeight, null);

        int tileSize = gameData.tileSize;
        // iterate over the (current) world that holds the tiles
        for (int z = 0; z < 3; z++) {
            for (int x = 0; x < gameData.world.getWorldYSize(); x++) {
                for (int y = 0; y < gameData.world.getWorldXSize(); y++) {
                    //Get the world tile
                    WorldTile.Tile tileType = gameData.world.getWorldTileType(x, y, z);

                    //conditions to be met for it to be rendered
                    //checks if tile is empty or null
                    if (tileType != null && tileType != WorldTile.Tile.Empty) {
                        if (withinBounds(x, y)) {
                            //bufferedGraphics.drawImage(imageLoader.getTextures()[tileType.ordinal()], isoCordTool.getXIso(x, y), isoCordTool.getYIso(x, y) - (z * tileSize/2), null);
                            drawFace(tileType, x, y, z, tileSize, "top");
                            drawFace(tileType, x, y, z, tileSize, "right");
                            drawFace(tileType, x, y, z, tileSize, "left");
                        }
                    }
                }
            }
        }

        //Get the mouse world cords from mouse motion listener
        int[] mouseWorldCor = gameData.gamePanel.getMouseMotionListener().getMouseWorldCords();

        //Draw the selection tile
        if (mouseWorldCor[0] - 1 < 0 || mouseWorldCor[1] - 1  < 0 || mouseWorldCor[0] - 1 > gameData.world.getWorldXSize()
                || mouseWorldCor[1] - 1  > gameData.world.getWorldYSize()) {
            bufferedGraphics.drawImage(imageLoader.getTextures()[WorldTile.Tile.RedSelection.ordinal()], isoCordTool.getXIso(mouseWorldCor[0] - 1, mouseWorldCor[1] - 1), isoCordTool.getYIso(mouseWorldCor[0] - 1, mouseWorldCor[1] - 1), null);
        } else {
            bufferedGraphics.drawImage(imageLoader.getTextures()[WorldTile.Tile.Selection.ordinal()], isoCordTool.getXIso(mouseWorldCor[0] - 1, mouseWorldCor[1] - 1), isoCordTool.getYIso(mouseWorldCor[0] - 1, mouseWorldCor[1] - 1), null);
        }

        //draw to panel
        g2.drawImage(bufferedImage, 0, 0, null);
    }

    private void drawFace(WorldTile.Tile tileType, int x, int y, int z, int tileSize, String face) {
        int isoX = isoCordTool.getXIso(x, y);
        int isoY = isoCordTool.getYIso(x, y) - (z * tileSize / 2);

        switch (face) {
            case "top":
                if (checkFaceVisible(x, y, z, face)) {
                    bufferedGraphics.drawImage(gameData.textureManager.getFaceTextures()[tileType.ordinal()][2], isoX, isoY, null);
                    bufferedGraphics.drawImage(gameData.textureManager.getFaceTextures()[tileType.ordinal()][3], isoX, isoY, null);
                }
                break;
            case "right":
                if (checkFaceVisible(x, y, z, face)) {
                    bufferedGraphics.drawImage(gameData.textureManager.getFaceTextures()[tileType.ordinal()][4], isoX, isoY, null);
                    bufferedGraphics.drawImage(gameData.textureManager.getFaceTextures()[tileType.ordinal()][5], isoX, isoY, null);
                }
                break;
            case "left":
                if (checkFaceVisible(x, y, z, face)) {
                    bufferedGraphics.drawImage(gameData.textureManager.getFaceTextures()[tileType.ordinal()][0], isoX, isoY, null);
                    bufferedGraphics.drawImage(gameData.textureManager.getFaceTextures()[tileType.ordinal()][1], isoX, isoY, null);
                }
                break;
        }


    }

    private boolean checkFaceVisible(int x, int y, int z, String face) {
        switch (face) {
            case "top":
                //check if its the top of depth
                if (z == 2) {
                    return true;
                }
                if (gameData.world.getWorldTileType(x, y, z + 1) == WorldTile.Tile.Empty) {
                    return true;
                }
                break;
            case "left":
                if (gameData.world.getWorldTileType(x, y + 1, z) == WorldTile.Tile.Empty) {
                    return true;
                }
                if (y == gameData.world.getWorldYSize() - 1) {
                    return true;
                }
                //check if tile next to it isnt full
                if (gameData.world.getWorldTileType(x, y + 1, z) == WorldTile.Tile.FurnaceOn
                        || gameData.world.getWorldTileType(x, y + 1, z) == WorldTile.Tile.FurnaceOff) {
                    return true;
                }
                break;
            case "right":
                if (gameData.world.getWorldTileType(x + 1, y, z) == WorldTile.Tile.Empty) {
                    return true;
                }
                if (x == gameData.world.getWorldXSize() - 1) {
                    return true;
                }
                //check if tile next to it isnt full
                if (gameData.world.getWorldTileType(x + 1, y, z) == WorldTile.Tile.FurnaceOn
                        || gameData.world.getWorldTileType(x + 1, y, z) == WorldTile.Tile.FurnaceOff) {
                    return true;
                }
                break;
        }
        return false;
    }

    private boolean withinBounds(int x, int y) {
        // Convert isometric coordinates to screen coordinates
        int screenX = isoCordTool.getXIso(x, y);
        int screenY = isoCordTool.getYIso(x, y);

        return screenX <= gameData.screenWidth + gameData.tileSize && screenX >= -gameData.tileSize && screenY <= gameData.screenHeight + gameData.tileSize && screenY >= -gameData.tileSize;
    }
}
