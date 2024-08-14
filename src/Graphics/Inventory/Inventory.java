package Graphics.Inventory;

import MainConfig.GameData;
import World.WorldTile;

public class Inventory {

    private Item[] inventory;

    public Inventory(GameData gameData) {
        inventory = new Item[gameData.hotbarSize];

        inventory[0] = new Item(WorldTile.Tile.Wall, 64, gameData);
        inventory[1] = new Item(WorldTile.Tile.turret, 10, gameData);
        inventory[2] = new Item(WorldTile.Tile.Dirt, 64, gameData);
        inventory[3] = new Item(WorldTile.Tile.FurnaceOff, 64, gameData);
        inventory[4] = new Item(WorldTile.Tile.FurnaceOn, 64, gameData);
        inventory[5] = new Item(WorldTile.Tile.Belt, 64, gameData);
    }

    public void checkEmpty() {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                if (inventory[i].getAmntOf() <= 0) {
                    inventory[i] = null;
                }
            }
        }
    }

    public Item[] getInventory() {
        return inventory;
    }
}
