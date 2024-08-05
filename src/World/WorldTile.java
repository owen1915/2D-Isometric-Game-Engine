package World;

public class WorldTile {
    public enum Tile {
        Empty,
        Grass,
        Wall
    }

    private Tile tileType;

    public WorldTile(Tile tileType){
        tileType = Tile.Grass;
    }


    void setTileType(Tile newTileType){
        tileType = newTileType;
    }

    Tile getTileType(){
        return tileType;
    }


}
