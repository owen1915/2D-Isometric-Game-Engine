package World;


import MainConfig.GameData;

import java.awt.*;


public class World {

    //Scale of the world
    private int worldXSize;
    private int worldYSize;
    private int defaultDepth;

    private Block[][] bottomLayer;
    private Block[][] topLayer;
    private Block[][] middleLayer;

    //World object;
    private Block[][][] worldTiles = new Block[3][][];

    private GameData gameData;

    public World(int worldSize, GameData gameData){
        this.gameData = gameData;
        //Create a new array for the storage of the world tiles
        this.worldXSize = worldSize;
        this.worldYSize = worldSize;
        defaultDepth = 3;

        bottomLayer = new Block[worldXSize][worldYSize];
        middleLayer = new Block[worldXSize][worldYSize];
        topLayer = new Block[worldXSize][worldYSize];

        for (int z = 0; z < defaultDepth; z++) {
            for (int y = 0; y < worldSize; y++) {
                for (int x = 0; x < worldSize; x++) {
                    switch (z) {
                        case 0:
                            bottomLayer[x][y] = new Block(gameData, x, y, z, WorldTile.Tile.Grass, true);
                            break;
                        case 1:
                            middleLayer[x][y] = new Block(gameData, x, y, z, WorldTile.Tile.Empty, true);
                            break;
                        case 2:
                            topLayer[x][y] = new Block(gameData, x, y, z, WorldTile.Tile.Empty, true);
                            break;
                    }
                }
            }
        }

        middleLayer[0][0] = new Block(gameData, 3, 3, 1, WorldTile.Tile.Dirt, true);
        topLayer[0][0] = new Block(gameData, 3, 3, 1, WorldTile.Tile.Dirt, true);

        worldTiles[0] = bottomLayer;
        worldTiles[1] = middleLayer;
        worldTiles[2] = topLayer;
    }

    public Block findBlockByRayCasting(int gridX, int gridY) {
        return getBlockOnGrid(gridX, gridY, 1);
    }

    public Block checkPolygonHitting(Block[] blocks, int mouseX, int mouseY, boolean remove) {
        for (Block block : blocks) {
            if (block != null && !block.isEmpty()) {
                Polygon[] polygons = block.getPolygons();
                if (remove) {
                    if (polygons[0].contains(mouseX, mouseY) || polygons[1].contains(mouseX, mouseY) || polygons[2].contains(mouseX, mouseY)) {
                        return block;
                    }
                }
                if (polygons[0].contains(mouseX, mouseY)) {
                    return getBlockOnGrid(block.getGridX(), block.getGridY() + 1, block.getGridZ());
                }
                if (polygons[1].contains(mouseX, mouseY)) {
                    return getBlockOnGrid(block.getGridX() + 1, block.getGridY(), block.getGridZ());
                }
                if (polygons[2].contains(mouseX, mouseY)) {
                    return getBlockOnGrid(block.getGridX(), block.getGridY(), block.getGridZ() + 1);
                }
            }
        }
        return null;
    }

    public Block[] getBlocksAround(Block block) {
        Block[] blocks = new Block[11];
        blocks[10] = getBlockOnGrid(block.getGridX(), block.getGridY(), block.getGridZ());
        blocks[9] = getBlockOnGrid(block.getGridX() + 1, block.getGridY(), block.getGridZ());
        blocks[8] = getBlockOnGrid(block.getGridX(), block.getGridY() + 1, block.getGridZ());
        blocks[7] = getBlockOnGrid(block.getGridX() + 1, block.getGridY() + 1, block.getGridZ());
        blocks[6] = getBlockOnGrid(block.getGridX(), block.getGridY(), block.getGridZ() + 1);
        blocks[5] = getBlockOnGrid(block.getGridX() + 1, block.getGridY(), block.getGridZ() + 1);
        blocks[4] = getBlockOnGrid(block.getGridX(), block.getGridY() + 1, block.getGridZ() + 1);
        blocks[3] = getBlockOnGrid(block.getGridX() + 1, block.getGridY() + 1, block.getGridZ() + 1);
        blocks[2] = getBlockOnGrid(block.getGridX() + 2, block.getGridY() + 1, block.getGridZ() + 1);
        blocks[1] = getBlockOnGrid(block.getGridX() + 1, block.getGridY() + 2, block.getGridZ() + 1);
        blocks[0] = getBlockOnGrid(block.getGridX() + 2, block.getGridY() + 2, block.getGridZ() + 1);
        return blocks;
    }

    public void rotate() {
        worldTiles[0] = rotate90Degrees(rotate90Degrees(worldTiles[0]));
        worldTiles[1] = rotate90Degrees(rotate90Degrees(worldTiles[1]));
        worldTiles[2] = rotate90Degrees(rotate90Degrees(worldTiles[2]));

        //gameData.camera.setyOffset(-gameData.camera.getyOffset() + gameData.screenHeight);
    }

    /**
     * rotates array 90 degrees
     */
    public Block[][] rotate90Degrees(Block[][] arr) {
        int rows = arr.length;
        int cols = arr[0].length;
        Block[][] rotated = new Block[cols][rows];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = arr[i][j];
            }
        }

        return rotated;
    }

    public int getWorldXSize() {
        return worldXSize;
    }

    public int getWorldYSize() {
        return worldYSize;
    }

    public Block getBlockOnGrid(int x, int y, int z) {
        if (x < worldXSize && y < worldYSize && z < defaultDepth && z >= 0 && x >= 0 && y >= 0) {
            return worldTiles[z][x][y];
        }
        return null;
    }

    public void setBlockOnGrid(int x, int y, int z, WorldTile.Tile type){
        //Check if in bounds of an array
        if (x < worldXSize && y < worldYSize && z < defaultDepth){
            //Used the tile setting function to update that tiles type
            this.worldTiles[z][x][y].setBlockType(type);
        }
    }

    public WorldTile.Tile getWorldTileType(int x, int y, int z){
        if (x < worldXSize && y < worldYSize && z < defaultDepth){
            return worldTiles[z][x][y].getBlockType();
        }
        else {
            return null;
        }
    }

    /**
     * gets max depth at x and y
     */
    public int getMaxDepthAtTile(int x, int y){
        if (worldTiles[2][x][y].getBlockType() != WorldTile.Tile.Empty) {
            return 2;
        }

        if (worldTiles[1][x][y].getBlockType() != WorldTile.Tile.Empty) {
            return 1;
        }

        return 0;
    }

    public WorldTile.Tile getWorldTile(int x, int y, int z){
        if (x < worldXSize && y < worldYSize && z < defaultDepth){
            return worldTiles[z][x][y].getBlockType();
        }
        else {
            return null;
        }
    }



}
