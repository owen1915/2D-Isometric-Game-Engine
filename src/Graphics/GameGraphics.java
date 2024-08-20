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

        /*for (int z = 0; z < 3; z++) {
            for (int x = topLeftCorner; x < bottomRightCorner; x++) {
                for (int y = topRightCorner; y < bottomLeftCorner; y++) {
                    Block block = gameData.world.getBlockOnGrid(x, y, z);

                    if (block != null && !block.isEmpty()) {
                        int xIso = isoCordTool.getXIso(x, y) + cameraXOffset;
                        int yIso = isoCordTool.getYIso(x, y) + cameraYOffset - (z * tileSize / 2);
                        Image blockImage = imageLoader.getTextures()[block.getBlockType().ordinal()];

                        if (xIso + tileSize > 0 && xIso < gameData.screenWidth && yIso + tileSize > 0 && yIso < gameData.screenHeight) {
                            bufferedGraphics.drawImage(blockImage, xIso, yIso, null);
                        }
                    }

                    if (gameData.debug) {
                        count++;
                    }
                }
            }
        }*/

        ArrayList<Chunk> chunks = gameData.world.getChunks();
        int index = 0;
        int chunksize = gameData.chunkSize;
        int indexing = gameData.worldSize/chunksize;
        int scale = gameData.camera.scale;
        for (Chunk chunk : chunks) {
            int x = cameraXOffset - chunk.chunkImage[scale].getWidth() / 2 + tileSize / 2;
            int y = cameraYOffset - tileSize;
            if (index < indexing) {
                bufferedGraphics.drawImage(chunk.chunkImage[scale], x - ((tileSize * chunksize/2) * index), y + (tileSize * index), null);
            } else if (index < indexing * 2) {
                bufferedGraphics.drawImage(chunk.chunkImage[scale], x - ((tileSize * chunksize/2) * (index - ((indexing) - 1))) + tileSize * (chunksize), y + (tileSize * (index - 7)), null);
            } else if (index < indexing * 3) {
                bufferedGraphics.drawImage(chunk.chunkImage[scale], x - ((tileSize * chunksize/2) * (index - ((indexing * 2) - 2))) + tileSize * (chunksize * 2), y + (tileSize * (index - 14)), null);
            } else if (index < indexing * 4) {
               bufferedGraphics.drawImage(chunk.chunkImage[scale], x - ((tileSize * chunksize/2) * (index - ((indexing * 3) - 3))) + tileSize * (chunksize * 3), y + (tileSize * (index - 21)), null);
            } else if (index < indexing * 5) {
                bufferedGraphics.drawImage(chunk.chunkImage[scale], x - ((tileSize * chunksize/2) * (index - ((indexing * 4) - 4))) + tileSize * (chunksize * 4), y + (tileSize * (index - 28)), null);
            } else if (index < indexing * 6) {
                bufferedGraphics.drawImage(chunk.chunkImage[scale], x - ((tileSize * chunksize/2) * (index - ((indexing * 5) - 5))) + tileSize * (chunksize * 5), y + (tileSize * (index - 35)), null);
            } else if (index < indexing * 7) {
                bufferedGraphics.drawImage(chunk.chunkImage[scale], x - ((tileSize * chunksize/2) * (index - ((indexing * 6) - 6))) + tileSize * (chunksize * 6), y + (tileSize * (index - 42)), null);
            } else if (index < indexing * 8) {
                bufferedGraphics.drawImage(chunk.chunkImage[scale], x - ((tileSize * chunksize/2) * (index - ((indexing * 7) - 7))) + tileSize * (chunksize * 7), y + (tileSize * (index - 49)), null);
            }
            index++;
        }

        /*
        chunks.get(0).updateChunk();
        chunks.get(1).updateChunk();
        chunks.get(8).updateChunk();

        bufferedGraphics.drawImage(chunks.get(0).chunkImage, cameraXOffset - chunks.get(0).chunkImage.getWidth()/2 + tileSize/2, cameraYOffset - tileSize, null);
        bufferedGraphics.drawImage(chunks.get(1).chunkImage, cameraXOffset - chunks.get(1).chunkImage.getWidth()/2 + tileSize/2 - tileSize * 2, cameraYOffset - tileSize + tileSize, null);
        bufferedGraphics.drawImage(chunks.get(8).chunkImage, cameraXOffset - chunks.get(8).chunkImage.getWidth()/2 + tileSize/2 + tileSize * 2, cameraYOffset - tileSize + tileSize, null);
        */

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
