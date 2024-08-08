package World;


import javax.lang.model.type.NullType;


public class World {

    //Scale of the world
    private int worldXSize;
    private int worldYSize;
    private int defaultDepth;


    //World object;
    private WorldTile[][][] worldTiles;


    public World(int xSize, int ySize){
        //Create a new array for the storage of the world tiles
        this.worldXSize = xSize;
        this.worldYSize = ySize;
        defaultDepth = 4;

        this.worldTiles = new WorldTile[xSize][ySize][];
        for (int x = 0; x < xSize; x++){
            for (int y = 0; y < ySize; y++){
                this.worldTiles[x][y] = new WorldTile[defaultDepth];
                for (int z = 0; z < defaultDepth; z++){
                    this.worldTiles[x][y][z] = new WorldTile(WorldTile.Tile.Grass);
                }
            }
        }
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
            this.worldTiles[x][y][z].setTileType(tileType);
        }
    }

    public WorldTile.Tile getWorldTileType(int x, int y, int z){
        if (x < worldXSize && y < worldYSize && z < defaultDepth){
            return worldTiles[x][y][z].getTileType();
        }
        else {
            return null;
        }
    }

    public WorldTile getWorldTile(int x, int y, int z){
        if (x < worldXSize && y < worldYSize && z < defaultDepth){
            return worldTiles[x][y][z];
        }
        else {
            return null;
        }
    }



}
