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

            for (int drawZ = 0; drawZ < 3; drawZ++) {
                draw(drawZ, x - 2, y - 2, g, i, isoCordTool, tileSize); // very back
                draw(drawZ, x - 2, y - 1, g, i, isoCordTool, tileSize); // left forward of last
                draw(drawZ, x - 1, y - 2, g, i, isoCordTool, tileSize); // right forward of last
                draw(drawZ, x - 1, y - 1, g, i, isoCordTool, tileSize); // diagonal of very back
                draw(drawZ, x - 1, y, g, i, isoCordTool, tileSize); // left forward of last
                draw(drawZ, x, y - 1, g, i, isoCordTool, tileSize); // right forward of last
                draw(drawZ, x, y, g, i, isoCordTool, tileSize); // original tile
                draw(drawZ, x + 1, y - 1, g, i, isoCordTool, tileSize); // right of original
                draw(drawZ,  x - 1, y + 1, g, i, isoCordTool, tileSize); // left of original
                draw(drawZ, x + 1, y, g, i, isoCordTool, tileSize); // right forward of original
                draw(drawZ, x, y + 1, g, i, isoCordTool, tileSize); // left forward of original
                draw(drawZ, x + 1, y + 1, g, i, isoCordTool, tileSize); // diagonal of original
                draw(drawZ, x + 2, y + 1, g, i, isoCordTool, tileSize); // right forward of last
                draw(drawZ, x + 1, y + 2, g, i, isoCordTool, tileSize); // left forward of last
                draw(drawZ, x + 2, y + 2, g, i, isoCordTool, tileSize); // diagonal of last
            }

            // Dispose of the graphics
            g.dispose();
        }
    }

    public void draw(int z, int x, int y, Graphics2D g, int scale, IsoCordTool isoCordTool, int tileSize) {
        if (z < 0 || z > 3 || x < 0 || x >= gameData.chunkSize || y < 0 || y >= gameData.chunkSize) {
            return;
        }

        Block block = chunk[z][x][y];
        if (block != null && !block.isEmpty()) {
            boolean drawTopLeftTri = false;
            boolean drawTopRightTri = false;
            boolean drawRightBotTri = false;
            boolean drawRightTopTri = false;
            boolean drawLeftTopTri = false;
            boolean drawLeftBotTri = false;

            int drawX = isoCordTool.getXIso(x, y) + (gameData.chunkSize * tileSize)/2 - tileSize/2;;
            int drawY = isoCordTool.getYIso(x, y) - (z * tileSize/2) + tileSize;

            boolean yInRange = y > 0 && y < gameData.chunkSize - 1;
            boolean xInRange = x > 0 && x < gameData.chunkSize - 1;

            Block upToLeftBlock = null;
            Block upToRightBlock = null;
            Block blockToLeft = null;
            Block blockToRight = null;
            Block blockDiagonal = null;
            Block blockAbove = null;

            if (z != 2) {
                blockAbove = chunk[z + 1][x][y];
                if (yInRange) {
                    upToLeftBlock = chunk[z + 1][x][y + 1];
                }
                if (xInRange) {
                    upToRightBlock = chunk[z + 1][x + 1][y];
                }
            }

            if (yInRange) {
                blockToLeft = chunk[z][x][y + 1];
            }
            if (xInRange) {
                blockToRight = chunk[z][x + 1][y];
            }
            if (xInRange && yInRange) {
                blockDiagonal = chunk[z][x + 1][y + 1];
            }

            if (upToLeftBlock == null || upToLeftBlock.isEmpty()) {
                drawTopLeftTri = true;
            }

            if (upToRightBlock == null || upToRightBlock.isEmpty()) {
                drawTopRightTri = true;
            }

            if (blockToLeft == null || blockToLeft.isEmpty()) {
                drawLeftTopTri = true;
            }

            if (blockToRight == null || blockToRight.isEmpty()) {
                drawRightTopTri = true;
            }

            if (blockDiagonal == null || blockDiagonal.isEmpty()) {
                drawLeftBotTri = true;
                drawRightBotTri = true;
            }

            if (blockAbove != null && !blockAbove.isEmpty()) {
                drawTopLeftTri = false;
                drawTopRightTri = false;
            }

            if (drawTopLeftTri && drawTopRightTri) {
                g.drawImage(block.getTop(scale), drawX, drawY, null);
            } else if (drawTopLeftTri) {
                g.drawImage(block.getLeftTopTriangle(scale), drawX, drawY, null);
            } else if (drawTopRightTri) {
                g.drawImage(block.getRightTopTriangle(scale), drawX, drawY, null);
            }

            if (drawLeftTopTri && drawLeftBotTri) {
                g.drawImage(block.getLeftSide(scale), drawX, drawY, null);
            } else if (drawLeftTopTri) {
                g.drawImage(block.getLeftSideLeftTriangle(scale), drawX, drawY, null);
            } else if (drawLeftBotTri) {
                g.drawImage(block.getLeftSideRightTriangle(scale), drawX, drawY, null);
            }

            if (drawRightTopTri && drawRightBotTri) {
                g.drawImage(block.getRightSide(scale), drawX, drawY, null);
            } else if (drawRightBotTri) {
                g.drawImage(block.getRightSideLeftTriangle(scale), drawX, drawY, null);
            } else if (drawRightTopTri) {
                g.drawImage(block.getRightSideRightTriangle(scale), drawX, drawY, null);
            }
        }
    }
}
