package World;

import MainConfig.GameData;

import java.awt.*;

public class Block {
    private int gridX, gridY, gridZ; // Grid coordinates
    private WorldTile.Tile block;
    private GameData gameData;
    private boolean dirty;

    public Block(GameData gameData, int gridX, int gridY, int gridZ, WorldTile.Tile block, boolean dirty) {
        this.gridX = gridX;
        this.gridY = gridY;
        this.dirty = dirty;
        this.gridZ = gridZ;
        this.block = block;
        this.gameData = gameData;
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

    private Polygon getLeftPolygon() {

        int screenX = gameData.isoCordTool.getXIso(gridX, gridY) + gameData.camera.getxOffset();
        int screenY = gameData.isoCordTool.getYIso(gridX, gridY) + gameData.camera.getyOffset();
        int zOffset = gridZ * gameData.tileSize/2;

        int[] xPoints = {screenX, screenX + gameData.tileSize/2, screenX + gameData.tileSize/2, screenX};
        int[] yPoints = {screenY + gameData.tileSize/4 - zOffset, screenY + gameData.tileSize/2 - zOffset, screenY + gameData.tileSize - zOffset, screenY + gameData.tileSize - gameData.tileSize/4 - zOffset};

        return new Polygon(xPoints, yPoints, 4);
    }

    private Polygon getTopPolygon() {
        int screenX = gameData.isoCordTool.getXIso(gridX, gridY) + gameData.camera.getxOffset();
        int screenY = gameData.isoCordTool.getYIso(gridX, gridY) + gameData.camera.getyOffset();
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
        int screenX = gameData.isoCordTool.getXIso(gridX, gridY) + gameData.camera.getxOffset() + gameData.tileSize/2;
        int screenY = gameData.isoCordTool.getYIso(gridX, gridY) + gameData.camera.getyOffset();
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

    public int getScreenX() {
        return gameData.isoCordTool.getXIso(gridX, gridY);
    }

    public int getScreenY() {
        return gameData.isoCordTool.getYIso(gridX, gridY) - (gridZ * gameData.tileSize/2);
    }

    public int getGridZ() { return gridZ; }
    public int getGridX() { return gridX; }
    public int getGridY() { return gridY; }
}
