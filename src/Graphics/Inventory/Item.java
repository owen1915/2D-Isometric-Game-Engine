package Graphics.Inventory;

import MainConfig.GameData;
import World.WorldTile;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Item {

    private WorldTile.Tile tileType;
    private int amntOf;
    private BufferedImage itemImage;

    public Item(WorldTile.Tile tileType, int amntOf, GameData gameData) {
        this.tileType = tileType;
        this.amntOf = amntOf;

        itemImage = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = itemImage.createGraphics();
        g.drawImage(gameData.imageLoader.getTextures()[tileType.ordinal()].getScaledInstance(64, 64, Image.SCALE_SMOOTH), 0, 0, null);
        g.dispose();
    }

    public WorldTile.Tile getTileType() {
        return tileType;
    }

    public BufferedImage getItemImage() {
        return itemImage;
    }

    public int getAmntOf() {
        return amntOf;
    }

    public void setAmntOf(int amntOf) {
        this.amntOf = amntOf;
    }
}
