package Graphics;

import MainConfig.GameData;
import World.WorldTile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Chunk {
    private int worldX, worldY, chunkSize, tileSize;
    private GameData gameData;
    private BufferedImage chunkImage;

    public Chunk(int worldX, int worldY, GameData gameData) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.chunkSize = gameData.chunkSize;
        this.gameData = gameData;
        createChunk();
    }

    private void createChunk() {
        int tileSize = gameData.tileSize;
        int imageWidth = (chunkSize / 2) * tileSize;
        int imageHeight = (chunkSize / 2) * tileSize + (tileSize / 2);

        chunkImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = chunkImage.createGraphics();

        for (int z = 0; z < 3; z++) {
            for (int x = worldX; x < worldX + chunkSize / 2; x++) {
                for (int y = worldY; y < worldY + chunkSize / 2; y++) {
                    WorldTile.Tile tileType = gameData.world.getWorldTileType(x, y, z);

                    if (tileType != null && tileType != WorldTile.Tile.Empty) {
                        int isoX = gameData.gamePanel.getIsoCordTool().getXIso(x - worldX, y - worldY);
                        int isoY = gameData.gamePanel.getIsoCordTool().getYIso(x - worldX, y - worldY) - (z * tileSize / 2);

                        g.drawImage(gameData.imageLoader.getTextures()[tileType.ordinal()], isoX, isoY, null);
                    }
                }
            }
        }

        g.drawRect(0, 0, imageWidth - 1, imageHeight - 1);
        g.dispose();
    }

    public BufferedImage getChunkImage() {
        return chunkImage;
    }
}
