package World;


import javax.lang.model.type.NullType;


public class World {

    //Scale of the world
    private int worldXSize;
    private int worldYSize;


    //World object;
    private WorldTile[][] worldTiles;


    public World(int xSize, int ySize){
        //Create a new array for the storage of the world tiles
        this.worldXSize = xSize;
        this.worldYSize = ySize;

        this.worldTiles = new WorldTile[xSize][ySize];
        for (int x = 0; x < xSize; x++){
            for (int y = 0; y < ySize; y++){
                this.worldTiles[x][y] = new WorldTile(WorldTile.Tile.Grass);
            }
        }
    }


    public int getWorldXSize() {
        return worldXSize;
    }

    public int getWorldYSize() {
        return worldYSize;
    }


    public void setWorldTile(int x, int y, WorldTile.Tile tileType){
        //Check if in bounds of an array
        if (x < worldXSize && y < worldYSize){
            //Used the tile setting function to update that tiles type
            this.worldTiles[x][y].setTileType(tileType);
        }
    }

    public WorldTile.Tile getWorldTile(World world, int x, int y){
        if (x < worldXSize && y < worldYSize){
            return world.worldTiles[x][y].getTileType();
        }
        else{
            return WorldTile.Tile.Empty;
        }
    }



}
