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
    private Image tileImage;

    public WorldTile(Tile tileType){
        this.tileType = tileType;
        this.tileImage = loadImage(tileType);
    }

    public Image loadImage(Tile tileType) {
        Image image = new ImageIcon("res/grassIso.png").getImage();

        switch (tileType) {
            case Tile.Grass:
                return image;

        }

        return null;
    }

    public void setTileType(Tile newTileType){
        tileType = newTileType;
    }

    public Tile getTileType(){
        return tileType;
    }

    public Image getTileImage() {
        return tileImage;
    }
}
