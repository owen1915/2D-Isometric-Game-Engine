package World;


import javax.lang.model.type.NullType;


public class World {

    //Scale of the world
    private int worldXSize;
    private int worldYSize;
    private int defaultDepth;


    //World object;
    private WorldTile[][][] worldTiles = new WorldTile[3][0][0];


    public World(int xSize, int ySize){
        //Create a new array for the storage of the world tiles
        this.worldXSize = xSize;
        this.worldYSize = ySize;
        defaultDepth = 3;

        WorldTile[][] bottomLayer = new WorldTile[worldXSize][worldYSize];
        WorldTile[][] middleLayer = new WorldTile[worldXSize][worldYSize];
        WorldTile[][] topLayer = new WorldTile[worldXSize][worldYSize];

        for (int i = 0; i < defaultDepth; i++) {
            for (int y = 0; y < xSize; y++) {
                for (int x = 0; x < ySize; x++) {
                    switch (i) {
                        case 0:
                            bottomLayer[x][y] = new WorldTile(WorldTile.Tile.Grass);
                            break;
                        case 1:
                            middleLayer[x][y] = new WorldTile(WorldTile.Tile.Empty);
                            break;
                        case 2:
                            topLayer[x][y] = new WorldTile(WorldTile.Tile.Empty);
                            break;
                    }
                }
            }
        }

        middleLayer[xSize/2 -2][ySize/2 -2] = new WorldTile(WorldTile.Tile.Wall);
        topLayer[xSize/2 -2][ySize/2 -2] = new WorldTile(WorldTile.Tile.Wall);

        middleLayer[xSize/2 - 1][ySize/2] = new WorldTile(WorldTile.Tile.FurnaceOn);
        middleLayer[xSize/2][ySize/2 - 1] = new WorldTile(WorldTile.Tile.FurnaceOn);
        middleLayer[xSize/2 - 1][ySize/2 - 1] = new WorldTile(WorldTile.Tile.FurnaceOn);
        middleLayer[xSize/2][ySize/2] = new WorldTile(WorldTile.Tile.FurnaceOn);
        middleLayer[xSize/2 + 2][ySize/2 - 2] = new WorldTile(WorldTile.Tile.FurnaceOff);
        middleLayer[xSize/2 - 2][ySize/2 + 2] = new WorldTile(WorldTile.Tile.FurnaceOff);

        worldTiles[0] = bottomLayer;
        worldTiles[1] = middleLayer;
        worldTiles[2] = topLayer;
    }

    public int getWorldXSize() {
        return worldXSize;
    }

    public int getWorldYSize() {
        return worldYSize;
    }


    public void setWorldTile(int x, int y, int z, WorldTile.Tile tileType){
        //Check if in bounds of an array
        if (x < worldXSize && y < worldYSize && z < defaultDepth){
            //Used the tile setting function to update that tiles type
            this.worldTiles[z][x][y].setTileType(tileType);
        }
    }

    public WorldTile.Tile getWorldTileType(int x, int y, int z){
        if (x < worldXSize && y < worldYSize && z < defaultDepth){
            return worldTiles[z][x][y].getTileType();
        }
        else {
            return null;
        }
    }

    public WorldTile getWorldTile(int x, int y, int z){
        if (x < worldXSize && y < worldYSize && z < defaultDepth){
            return worldTiles[z][x][y];
        }
        else {
            return null;
        }
    }



}
