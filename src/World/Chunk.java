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
        for (int i = 0; i < chunkImage.length; i++) {
            chunkImage[i] = createImage(i);
        }
    }

    public void updateChunk(Block block) {
        int x = block.getGridX() % gameData.chunkSize;
        int y = block.getGridY() % gameData.chunkSize;

        //update block array
        chunk[block.getGridZ()][x][y] = block;

        //update image
        updateImage(x, y, block.getGridZ());
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
    }

    public BufferedImage createImage(int scale) {
        int zoomAmnt = gameData.camera.getZoomAmnt();
        int tileSize = zoomAmnt + (zoomAmnt * scale);
        BufferedImage chunkImage = new BufferedImage(gameData.chunkSize * tileSize, gameData.chunkSize * tileSize/2 + (tileSize/2 * 3), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = chunkImage.createGraphics();
        IsoCordTool isoCordTool = new IsoCordTool(scale, zoomAmnt);
        for (int z = 0; z < 3; z++) {
            for (int x = 0; x < gameData.chunkSize; x++) {
                for (int y = 0; y < gameData.chunkSize; y++) {
                    draw(z, x, y, g, scale, isoCordTool, tileSize);
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

    private void updateImage(int x, int y, int z) {
        for (int i = 0; i < chunkImage.length; i++) {
            Graphics2D g = chunkImage[i].createGraphics();
            int zoomAmnt = gameData.camera.getZoomAmnt();
            int tileSize = zoomAmnt + (zoomAmnt * i);
            IsoCordTool isoCordTool = new IsoCordTool(i, zoomAmnt);

            int drawX = isoCordTool.getXIso(x, y) + (gameData.chunkSize * tileSize)/2 - tileSize/2;;
            int drawY = isoCordTool.getYIso(x, y) - (z * tileSize/2) + tileSize;

            g.setComposite(AlphaComposite.Clear);

            // Calculate the isometric coordinates for the polygon
            int[] xPoints = {
                    drawX + tileSize / 2,
                    drawX,
                    drawX,
                    drawX + tileSize / 2,
                    drawX + tileSize,
                    drawX + tileSize
            };

            int[] yPoints = {
                    drawY,
                    drawY + tileSize / 4,
                    drawY + tileSize - tileSize / 4,
                    drawY + tileSize,
                    drawY + tileSize - tileSize / 4,
                    drawY + tileSize / 4
            };

            // Clear the polygon area
            g.fillPolygon(xPoints, yPoints, xPoints.length);

            g.setComposite(AlphaComposite.SrcOver);

            draw(z - 1, x - 1, y - 1, g, i, isoCordTool, tileSize);
            draw(z - 1, x - 1, y, g, i, isoCordTool, tileSize);
            draw(z - 1, x, y - 1, g, i, isoCordTool, tileSize);
            draw(z - 1, x, y, g, i, isoCordTool, tileSize);
            draw(z, x, y, g, i, isoCordTool, tileSize);

            // Dispose of the graphics context
            g.dispose();
        }
    }

    public void draw(int z, int x, int y, Graphics2D g, int scale, IsoCordTool isoCordTool, int tileSize) {
        Block block = chunk[z][x][y];
        if (block != null && !block.isEmpty()) {
            boolean drawTop = false;
            boolean drawRight = false;
            boolean drawLeft = false;

            int drawX = isoCordTool.getXIso(x, y) + (gameData.chunkSize * tileSize)/2 - tileSize/2;;
            int drawY = isoCordTool.getYIso(x, y) - (z * tileSize/2) + tileSize;

            Block blockAbove = null;
            Block blockForwardLeft = null;
            Block blockForwardRight = null;

            if (z != 2) {
                blockAbove = chunk[z + 1][x][y];
            }

            if (y != gameData.chunkSize - 1) {
                blockForwardLeft = chunk[z][x][y + 1];
            }

            if (x != gameData.chunkSize - 1) {
                blockForwardRight = chunk[z][x + 1][y];
            }

            if (blockAbove != null && blockAbove.isEmpty()) {
                drawTop = true;
            }

            if (blockForwardLeft == null || blockForwardLeft.isEmpty()) {
                drawLeft = true;
            }

            if (blockForwardRight == null || blockForwardRight.isEmpty()) {
                drawRight = true;
            }

            if (drawTop) {
                g.drawImage(block.getTop(scale), drawX, drawY, null);
            }
            if (drawLeft) {
                g.drawImage(block.getLeftSide(scale), drawX, drawY, null);
            }
            if (drawRight) {
                g.drawImage(block.getRightSide(scale), drawX, drawY, null);
            }
        }
    }
}
