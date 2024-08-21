package World;

import MainConfig.GameData;
import Graphics.IsoCordTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Chunk {

    public Block[][][] chunk;
    public BufferedImage[] chunkImage;

    private int startIndexX, startIndexY;
    private GameData gameData;

    public Chunk(GameData gameData, int startX, int startY) {
        this.startIndexX = startX;
        this.startIndexY = startY;
        this.gameData = gameData;
        chunkImage = new BufferedImage[gameData.camera.maxScale];
        createChunk();
    }

    public void updateChunk() {
        createChunk();
    }

    private void createChunk() {
        this.chunk = new Block[3][gameData.chunkSize][gameData.chunkSize];
        for (int z = 0; z < 3; z++) {
            int x = 0;
            int y = 0;
            for (int i = startIndexX; i < gameData.chunkSize + startIndexX; i++) {
                for (int j = startIndexY; j < gameData.chunkSize + startIndexY; j++) {
                    chunk[z][x][y] = gameData.world.getBlockOnGrid(i, j, z);
                    y++;
                }
                y = 0;
                x++;
            }
        }
        for (int i = 0; i < chunkImage.length; i++) {
            chunkImage[i] = createImage(i);
        }
    }

    public BufferedImage createImage(int scale) {
        int zoomAmnt = gameData.camera.getZoomAmnt();
        int tileSize = zoomAmnt + (zoomAmnt * scale);
        BufferedImage chunkImage = new BufferedImage(gameData.chunkSize * tileSize, gameData.chunkSize * tileSize/2 + (tileSize/2 * 3), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = chunkImage.createGraphics();
        Image[] textures = gameData.imageLoader.getScaledTextures(scale);
        IsoCordTool isoCordTool = new IsoCordTool(scale, zoomAmnt);
        int width = (gameData.chunkSize * tileSize)/2 - tileSize/2;
        for (int z = 0; z < 3; z++) {
            for (int x = 0; x < gameData.chunkSize; x++) {
                for (int y = 0; y < gameData.chunkSize; y++) {
                    Block block = chunk[z][x][y];
                    if (block != null && !block.isEmpty()) {
                        int drawX = isoCordTool.getXIso(x, y) + width;
                        int drawY = isoCordTool.getYIso(x, y) - (z * tileSize/2) + tileSize;
                        g.drawImage(textures[block.getBlockType().ordinal()], drawX, drawY, null);
                    }
                }
            }
        }

        if (gameData.debug) {
            g.setColor(Color.red);
            g.setStroke(new BasicStroke(2));
            g.drawLine(chunkImage.getWidth()/2, tileSize, 0, (gameData.chunkSize * tileSize/2 + (tileSize/2 * 3))/2 + tileSize/4);
            g.drawLine(0, (gameData.chunkSize * tileSize/2 + (tileSize/2 * 3))/2 + tileSize/4, chunkImage.getWidth()/2, chunkImage.getHeight() - tileSize/2);
            g.drawLine(chunkImage.getWidth()/2, chunkImage.getHeight() - tileSize/2, chunkImage.getWidth(), (gameData.chunkSize * tileSize/2 + (tileSize/2 * 3))/2 + tileSize/4);
            g.drawLine(chunkImage.getWidth(), (gameData.chunkSize * tileSize/2 + (tileSize/2 * 3))/2 + tileSize/4, chunkImage.getWidth()/2, tileSize);
        }

        g.dispose();
        return chunkImage;
    }
}
