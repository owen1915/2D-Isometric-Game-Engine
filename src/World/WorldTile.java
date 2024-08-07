package World;

import javax.swing.*;
import java.awt.*;

public class WorldTile {

    public enum Tile {
        Empty,
        Grass,
        Wall
    }

    private Tile tileType;

    public WorldTile(Tile tileType){
        this.tileType = tileType;
    }

    public void setTileType(Tile newTileType){
        tileType = newTileType;
    }

}
