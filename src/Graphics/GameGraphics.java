package Graphics;

import Graphics.Inventory.InventoryGraphics;
import Graphics.Panels.GamePanel;
import Listeners.MouseListener;
import MainConfig.GameData;
import MainConfig.ImageLoader;
import World.Block;
import World.Chunk;
import World.WorldTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class GameGraphics {

    private IsoCordTool isoCordTool;
    private GameData gameData;
    private ImageLoader imageLoader;
    private BufferedImage bufferedImage;
    private Graphics2D bufferedGraphics;
    private InventoryGraphics inventoryGraphics;
    private BufferedImage background;
    private Block selectionBlock;
    private int drawX, drawY;
    private int count = 0;

    public GameGraphics(GameData gameData) {
        this.gameData = gameData;
        inventoryGraphics = gameData.inventory.getInventoryGraphics();
        imageLoader = gameData.imageLoader;

        bufferedImage = new BufferedImage(gameData.screenWidth, gameData.screenHeight, BufferedImage.TYPE_INT_ARGB);
        background = new BufferedImage(gameData.screenWidth, gameData.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics graphics = background.getGraphics();
        graphics.drawImage(gameData.imageLoader.getBackground()[0].getScaledInstance(gameData.screenWidth, gameData.screenHeight, Image.SCALE_SMOOTH), 0, 0, null);
        bufferedGraphics = (Graphics2D) bufferedImage.getGraphics();

        gameData.world.createChunks();
    }

    public void render(Graphics2D g2) {
        bufferedGraphics.clearRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());

        int cameraXOffset = gameData.camera.getxOffset();
        int cameraYOffset = gameData.camera.getyOffset();
        isoCordTool = new IsoCordTool(gameData.camera.scale);

        // Calculate corners

        int topLeftCorner = Math.max(0, isoCordTool.getXFromIso(-cameraXOffset, -cameraYOffset));
        int topRightCorner = Math.max(0, isoCordTool.getYFromIso(-cameraXOffset + gameData.screenWidth, -cameraYOffset));
        int bottomRightCorner = Math.min(gameData.worldSize, isoCordTool.getXFromIso(-cameraXOffset + gameData.screenWidth, -cameraYOffset + gameData.screenHeight) + 1);
        int bottomLeftCorner = Math.min(gameData.worldSize, isoCordTool.getYFromIso(-cameraXOffset, -cameraYOffset + gameData.screenHeight) + 1);

        int tileSize = gameData.tileSize;

        if (gameData.debug) {
            count = 0;
        }

        // rendering chunks
        ArrayList<Chunk> chunks = gameData.world.getChunks();
        int index = 0;
        int chunksize = gameData.chunkSize;
        int scale = gameData.camera.scale;
        int tilesPerRow = gameData.worldSize / chunksize;
        for (Chunk chunk : chunks) {
            int row = index / tilesPerRow;
            int col = index % tilesPerRow;

            BufferedImage image = chunk.chunkImage[scale];

            int x = cameraXOffset - image.getWidth()/2 + tileSize/2;
            int y = cameraYOffset - tileSize;

            int chunkHalfWidth = image.getWidth()/2;

            int drawX = x - ((chunkHalfWidth * col)) + (chunkHalfWidth * row);
            int drawY = y + ((chunksize * tileSize)/(chunksize/2) * col) + (chunksize * tileSize)/(chunksize/2) * row;

            bufferedGraphics.drawImage(chunk.chunkImage[scale], drawX, drawY, null);
            index++;
            count++;
        }

        //selection block
        int[] mouseWorldCor = gameData.gamePanel.getMouseMotionListener().getMouseWorldCords();
        selectionBlock = gameData.blockManipulator.getBlock(mouseWorldCor[0], mouseWorldCor[1], false);

        if (selectionBlock != null) {
            drawX = isoCordTool.getXIso(selectionBlock.getGridX(), selectionBlock.getGridY()) + cameraXOffset;
            drawY = isoCordTool.getYIso(selectionBlock.getGridX(), selectionBlock.getGridY()) + cameraYOffset - (selectionBlock.getGridZ() * tileSize / 2);
            if (gameData.blockManipulator.checkBoundary(selectionBlock.getGridX(), selectionBlock.getGridY())) {
                bufferedGraphics.drawImage(imageLoader.getScaledTextures(gameData.camera.scale)[WorldTile.Tile.Selection.ordinal()], drawX, drawY, null);
            }
        } else {
            int x = isoCordTool.getXFromIso(mouseWorldCor[0] - cameraXOffset, mouseWorldCor[1] - cameraYOffset);
            int y = isoCordTool.getYFromIso(mouseWorldCor[0] - cameraXOffset, mouseWorldCor[1] - cameraYOffset);
            drawX = isoCordTool.getXIso(x, y) + cameraXOffset;
            drawY = isoCordTool.getYIso(x, y) + cameraYOffset - gameData.tileSize / 2;
            if (gameData.blockManipulator.checkBoundary(x, y)) {
                bufferedGraphics.drawImage(imageLoader.getScaledTextures(gameData.camera.scale)[WorldTile.Tile.Selection.ordinal()], drawX, drawY, null);
            }
        }

        inventoryGraphics.render(bufferedGraphics);
        gameData.camera.setDirty(false);
        g2.drawImage(bufferedImage, 0, 0, null);
    }

    public int getCount() {
        return count;
    }
}
