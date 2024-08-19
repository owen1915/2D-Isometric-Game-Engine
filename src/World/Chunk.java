package World;

import MainConfig.GameData;
import Graphics.IsoCordTool;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Chunk {

    public Block[][][] chunk;
    public BufferedImage chunkImage;

    private int startIndex;
    private GameData gameData;

    public Chunk(GameData gameData, int start) {
        this.startIndex = start;
        this.gameData = gameData;
        createChunk();
        createImage();
    }

    public void createChunk() {
        this.chunk = new Block[3][gameData.chunkSize][gameData.chunkSize];
        for (int z = 0; z < 3; z++) {
            int x = 0;
            int y = 0;
            for (int i = startIndex; i < gameData.chunkSize + startIndex; i++) {
                for (int j = startIndex; j < gameData.chunkSize + startIndex; j++) {
                    chunk[z][x][y] = gameData.world.getBlockOnGrid(i, j, z);
                    y++;
                }
                y = 0;
                x++;
            }
        }
        createImage();
    }

    public void createImage() {
        chunkImage = new BufferedImage(gameData.chunkSize * gameData.tileSize, gameData.chunkSize * gameData.tileSize/2 + (gameData.tileSize/2 * 3), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = chunkImage.createGraphics();
        Image[] textures = gameData.imageLoader.getTextures();
        IsoCordTool isoCordTool = gameData.isoCordTool;
        int width = (gameData.chunkSize * gameData.tileSize)/2 - gameData.tileSize/2;
        for (int z = 0; z < 3; z++) {
            for (int x = 0; x < gameData.chunkSize; x++) {
                for (int y = 0; y < gameData.chunkSize; y++) {
                    Block block = chunk[z][x][y];
                    if (block != null && !block.isEmpty()) {
                        int drawX = isoCordTool.getXIso(x, y) + width;
                        int drawY = isoCordTool.getYIso(x, y) - (z * gameData.tileSize/2) + gameData.tileSize;
                        g.drawImage(textures[block.getBlockType().ordinal()], drawX, drawY, null);
                    }
                }
            }
        }
    }
}
