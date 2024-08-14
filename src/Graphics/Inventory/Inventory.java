package Graphics.Inventory;

import MainConfig.GameData;
import World.WorldTile;

public class Inventory {

    private Item[] inventory;
    public int size = 0;

    public Inventory(GameData gameData) {
        inventory = new Item[gameData.hotbarSize];

        inventory[0] = new Item(WorldTile.Tile.Wall, 64, gameData);
        inventory[1] = new Item(WorldTile.Tile.turret, 10, gameData);
        inventory[2] = new Item(WorldTile.Tile.Dirt, 64, gameData);
        inventory[3] = new Item(WorldTile.Tile.FurnaceOff, 64, gameData);
        inventory[4] = new Item(WorldTile.Tile.FurnaceOn, 64, gameData);
        inventory[5] = new Item(WorldTile.Tile.Belt, 64, gameData);

        size = 5;
    }

    public int firstOpenSpace() {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                return i;
            }
        }

        return -1;
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

    public int getSlot(WorldTile.Tile tile) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null && inventory[i].getBlock() == tile) {
                return i;
            }
        }
        return -1;
    }

    public Item[] getInventory() {
        return inventory;
    }
}
