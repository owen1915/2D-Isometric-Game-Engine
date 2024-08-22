package World;

import MainConfig.GameData;

import java.awt.*;
import java.awt.image.BufferedImage;

import Graphics.IsoCordTool;
import MainConfig.ImageLoader;

public class Block {
    private int gridX, gridY, gridZ; // Grid coordinates
    private WorldTile.Tile block;
    private GameData gameData;
    private boolean dirty;
    private IsoCordTool isoCordTool;

    public Block(GameData gameData, int gridX, int gridY, int gridZ, WorldTile.Tile block, boolean dirty) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.dirty = dirty;
        this.gridZ = gridZ;
        this.block = block;
        this.gameData = gameData;
        isoCordTool = new IsoCordTool(gameData);
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isDirty() {
        return dirty;
    }

    public boolean isEmpty() {
        return block == WorldTile.Tile.Empty;
    }

    public Polygon[] getPolygons() {
        return new Polygon[] {getLeftPolygon(), getRightPolygon(), getTopPolygon()};
    }

    public BufferedImage getLeftTopTriangle(int scale) {
        Image block = gameData.imageLoader.getScaledTextures(scale)[getBlockType().ordinal()];
        BufferedImage blockImage = gameData.imageLoader.toBufferedImage(block);
        int tilesize = gameData.camera.getZoomAmnt() + (gameData.camera.getZoomAmnt() * scale);

        // Assuming the top of the isometric tile is triangular
        int[] xPoints = {
                tilesize / 2,
                0,
                tilesize / 2,
        };

        int[] yPoints = {
                0,
                tilesize / 4,
                tilesize / 2
        };

        Polygon topLeftTriangle = new Polygon(xPoints, yPoints, xPoints.length);

        // Create a new BufferedImage for the top triangle
        BufferedImage topLeftTriangleImage = new BufferedImage(tilesize, tilesize / 2, BufferedImage.TYPE_INT_ARGB);

        // Draw the top triangle from the original blockImage
        Graphics2D g2d = topLeftTriangleImage.createGraphics();
        g2d.setClip(topLeftTriangle);
        g2d.drawImage(blockImage, 0, 0, null);
        g2d.dispose();

        return topLeftTriangleImage;
    }

    public BufferedImage getRightTopTriangle(int scale) {
        Image block = gameData.imageLoader.getScaledTextures(scale)[getBlockType().ordinal()];
        BufferedImage blockImage = gameData.imageLoader.toBufferedImage(block);
        int tilesize = gameData.camera.getZoomAmnt() + (gameData.camera.getZoomAmnt() * scale);

        // Assuming the top of the isometric tile is triangular
        int[] xPoints = {
                tilesize / 2,
                tilesize,
                tilesize / 2,
        };

        int[] yPoints = {
                0,
                tilesize / 4,
                tilesize / 2
        };

        Polygon topRightTriangle = new Polygon(xPoints, yPoints, xPoints.length);

        // Create a new BufferedImage for the top triangle
        BufferedImage topRightTriangleImage = new BufferedImage(tilesize, tilesize / 2, BufferedImage.TYPE_INT_ARGB);

        // Draw the top triangle from the original blockImage
        Graphics2D g2d = topRightTriangleImage.createGraphics();
        g2d.setClip(topRightTriangle);
        g2d.drawImage(blockImage, 0, 0, null);
        g2d.dispose();

        return topRightTriangleImage;
    }

    private Polygon getLeftPolygon() {
        isoCordTool = new IsoCordTool(gameData);
        int screenX = isoCordTool.getXIso(gridX, gridY) + gameData.camera.getxOffset();
        int screenY = isoCordTool.getYIso(gridX, gridY) + gameData.camera.getyOffset();
        int zOffset = gridZ * gameData.tileSize/2;

        int[] xPoints = {screenX, screenX + gameData.tileSize/2, screenX + gameData.tileSize/2, screenX};
        int[] yPoints = {screenY + gameData.tileSize/4 - zOffset, screenY + gameData.tileSize/2 - zOffset, screenY + gameData.tileSize - zOffset, screenY + gameData.tileSize - gameData.tileSize/4 - zOffset};

        return new Polygon(xPoints, yPoints, 4);
    }

    public BufferedImage getTop(int scale) {
        Image block = gameData.imageLoader.getScaledTextures(scale)[getBlockType().ordinal()];
        BufferedImage blockImage = gameData.imageLoader.toBufferedImage(block);
        int tilesize = gameData.camera.getZoomAmnt() + (gameData.camera.getZoomAmnt() * scale);

        // Assuming the top of the isometric tile is triangular
        int[] xPoints = {
                tilesize / 2, // Top center
                0,            // Bottom left
                tilesize / 2,
                tilesize      // Bottom right
        };

        int[] yPoints = {
                0,            // Top center
                tilesize / 4, // Bottom left
                tilesize / 2,
                tilesize / 4  // Bottom right
        };

        Polygon topTriangle = new Polygon(xPoints, yPoints, xPoints.length);

        // Create a new BufferedImage for the top triangle
        BufferedImage topImage = new BufferedImage(tilesize, tilesize / 2, BufferedImage.TYPE_INT_ARGB);

        // Draw the top triangle from the original blockImage
        Graphics2D g2d = topImage.createGraphics();
        g2d.setClip(topTriangle);
        g2d.drawImage(blockImage, 0, 0, null);
        g2d.dispose();

        return topImage;
    }

    public BufferedImage getRightSideLeftTriangle(int scale) {
        Image block = gameData.imageLoader.getScaledTextures(scale)[getBlockType().ordinal()];
        BufferedImage blockImage = gameData.imageLoader.toBufferedImage(block);
        int tilesize = gameData.camera.getZoomAmnt() + (gameData.camera.getZoomAmnt() * scale);

        // Assuming the top of the isometric tile is triangular
        int[] xPoints = {
                tilesize / 2,
                tilesize / 2,
                tilesize
        };

        int[] yPoints = {// Top center
                tilesize/2,
                tilesize,
                tilesize - tilesize/4
        };

        Polygon leftTriangle = new Polygon(xPoints, yPoints, xPoints.length);

        // Create a new BufferedImage for the top triangle
        BufferedImage leftImage = new BufferedImage(tilesize, tilesize, BufferedImage.TYPE_INT_ARGB);

        // Draw the top triangle from the original blockImage
        Graphics2D g2d = leftImage.createGraphics();
        g2d.setClip(leftTriangle);
        g2d.drawImage(blockImage, 0, 0, null);
        g2d.dispose();

        return leftImage;
    }

    public BufferedImage getRightSideRightTriangle(int scale) {
        Image block = gameData.imageLoader.getScaledTextures(scale)[getBlockType().ordinal()];
        BufferedImage blockImage = gameData.imageLoader.toBufferedImage(block);
        int tilesize = gameData.camera.getZoomAmnt() + (gameData.camera.getZoomAmnt() * scale);

        // Assuming the top of the isometric tile is triangular
        int[] xPoints = {
                tilesize / 2,
                tilesize,
                tilesize
        };

        int[] yPoints = {// Top center
                tilesize/2,
                tilesize/4,
                tilesize - tilesize/4
        };

        Polygon leftTriangle = new Polygon(xPoints, yPoints, xPoints.length);

        // Create a new BufferedImage for the top triangle
        BufferedImage leftImage = new BufferedImage(tilesize, tilesize, BufferedImage.TYPE_INT_ARGB);

        // Draw the top triangle from the original blockImage
        Graphics2D g2d = leftImage.createGraphics();
        g2d.setClip(leftTriangle);
        g2d.drawImage(blockImage, 0, 0, null);
        g2d.dispose();

        return leftImage;
    }

    public BufferedImage getRightSide(int scale) {
        Image block = gameData.imageLoader.getScaledTextures(scale)[getBlockType().ordinal()];
        BufferedImage blockImage = gameData.imageLoader.toBufferedImage(block);
        int tilesize = gameData.camera.getZoomAmnt() + (gameData.camera.getZoomAmnt() * scale);

        // Assuming the top of the isometric tile is triangular
        int[] xPoints = {
                tilesize / 2, // left
                tilesize / 2,            // Bottom left
                tilesize,
                tilesize      // Bottom right
        };

        int[] yPoints = {// Top center
                tilesize/2,
                tilesize, // Bottom left
                tilesize - tilesize/4,
                tilesize/4
                // Bottom right
        };

        Polygon leftTriangle = new Polygon(xPoints, yPoints, xPoints.length);

        // Create a new BufferedImage for the top triangle
        BufferedImage leftImage = new BufferedImage(tilesize, tilesize, BufferedImage.TYPE_INT_ARGB);

        // Draw the top triangle from the original blockImage
        Graphics2D g2d = leftImage.createGraphics();
        g2d.setClip(leftTriangle);
        g2d.drawImage(blockImage, 0, 0, null);
        g2d.dispose();

        return leftImage;
    }

    public BufferedImage getLeftSideRightTriangle(int scale) {
        Image block = gameData.imageLoader.getScaledTextures(scale)[getBlockType().ordinal()];
        BufferedImage blockImage = gameData.imageLoader.toBufferedImage(block);
        int tilesize = gameData.camera.getZoomAmnt() + (gameData.camera.getZoomAmnt() * scale);

        // Assuming the top of the isometric tile is triangular
        int[] xPoints = {
                0,
                tilesize / 2,
                tilesize / 2
        };

        int[] yPoints = {
                tilesize - tilesize/4,
                tilesize/2,
                tilesize
        };

        Polygon leftTriangle = new Polygon(xPoints, yPoints, xPoints.length);

        // Create a new BufferedImage for the top triangle
        BufferedImage leftImage = new BufferedImage(tilesize/2, tilesize, BufferedImage.TYPE_INT_ARGB);

        // Draw the top triangle from the original blockImage
        Graphics2D g2d = leftImage.createGraphics();
        g2d.setClip(leftTriangle);
        g2d.drawImage(blockImage, 0, 0, null);
        g2d.dispose();

        return leftImage;
    }

    public BufferedImage getLeftSideLeftTriangle(int scale) {
        Image block = gameData.imageLoader.getScaledTextures(scale)[getBlockType().ordinal()];
        BufferedImage blockImage = gameData.imageLoader.toBufferedImage(block);
        int tilesize = gameData.camera.getZoomAmnt() + (gameData.camera.getZoomAmnt() * scale);

        // Assuming the top of the isometric tile is triangular
        int[] xPoints = {
                0,
                0,
                tilesize / 2
        };

        int[] yPoints = {
                tilesize / 4,
                tilesize - tilesize/4,
                tilesize/2
        };

        Polygon leftTriangle = new Polygon(xPoints, yPoints, xPoints.length);

        // Create a new BufferedImage for the top triangle
        BufferedImage leftImage = new BufferedImage(tilesize/2, tilesize, BufferedImage.TYPE_INT_ARGB);

        // Draw the top triangle from the original blockImage
        Graphics2D g2d = leftImage.createGraphics();
        g2d.setClip(leftTriangle);
        g2d.drawImage(blockImage, 0, 0, null);
        g2d.dispose();

        return leftImage;
    }

    public BufferedImage getLeftSide(int scale) {
        Image block = gameData.imageLoader.getScaledTextures(scale)[getBlockType().ordinal()];
        BufferedImage blockImage = gameData.imageLoader.toBufferedImage(block);
        int tilesize = gameData.camera.getZoomAmnt() + (gameData.camera.getZoomAmnt() * scale);

        // Assuming the top of the isometric tile is triangular
        int[] xPoints = {
                0, // left
                0,            // Bottom left
                tilesize / 2,
                tilesize / 2      // Bottom right
        };

        int[] yPoints = {
                tilesize / 4,            // Top center
                tilesize - tilesize/4, // Bottom left
                tilesize,
                tilesize/2
                  // Bottom right
        };

        Polygon leftTriangle = new Polygon(xPoints, yPoints, xPoints.length);

        // Create a new BufferedImage for the top triangle
        BufferedImage leftImage = new BufferedImage(tilesize/2, tilesize, BufferedImage.TYPE_INT_ARGB);

        // Draw the top triangle from the original blockImage
        Graphics2D g2d = leftImage.createGraphics();
        g2d.setClip(leftTriangle);
        g2d.drawImage(blockImage, 0, 0, null);
        g2d.dispose();

        return leftImage;
    }

    private Polygon getTopPolygon() {
        isoCordTool = new IsoCordTool(gameData);
        int screenX = isoCordTool.getXIso(gridX, gridY) + gameData.camera.getxOffset();
        int screenY = isoCordTool.getYIso(gridX, gridY) + gameData.camera.getyOffset();
        int zOffset = gridZ * gameData.tileSize/2;

        int[] xPoints = new int[7];
        xPoints[0] = screenX;
        xPoints[1] = screenX + gameData.tileSize/2;
        xPoints[2] = screenX + gameData.tileSize/2 + 1;
        xPoints[3] = screenX + gameData.tileSize;
        xPoints[4] = screenX + gameData.tileSize/2;
        xPoints[5] = screenX + gameData.tileSize/2 + 1;
        xPoints[6] = screenX;
        int[] yPoints = new int[7];
        yPoints[0] = screenY + gameData.tileSize/4 - zOffset;
        yPoints[1] = screenY - zOffset;
        yPoints[2] = screenY - zOffset;
        yPoints[3] = screenY + gameData.tileSize/4 - zOffset;
        yPoints[4] = screenY + gameData.tileSize/2 - zOffset;
        yPoints[5] = screenY + gameData.tileSize/2 - zOffset;
        yPoints[6] = screenY + gameData.tileSize/4 - zOffset;

        return new Polygon(xPoints, yPoints, 7);
    }

    private Polygon getRightPolygon() {
        isoCordTool = new IsoCordTool(gameData);
        int screenX = isoCordTool.getXIso(gridX, gridY) + gameData.camera.getxOffset() + gameData.tileSize/2;
        int screenY = isoCordTool.getYIso(gridX, gridY) + gameData.camera.getyOffset();
        int zOffset = gridZ * gameData.tileSize/2;

        int[] xPoints = {screenX, screenX + gameData.tileSize/2, screenX + gameData.tileSize/2, screenX};
        int[] yPoints = {screenY + gameData.tileSize/2 - zOffset, screenY + gameData.tileSize/4 - zOffset, screenY + gameData.tileSize - gameData.tileSize/4 - zOffset, screenY + gameData.tileSize - zOffset};

        return new Polygon(xPoints, yPoints, 4);
    }

    public void setBlockType(WorldTile.Tile block) {
        this.block = block;
    }

    public WorldTile.Tile getBlockType() {
        return block;
    }

    public int getGridZ() { return gridZ; }
    public int getGridX() { return gridX; }
    public int getGridY() { return gridY; }
}
