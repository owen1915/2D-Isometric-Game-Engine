package Graphics.Inventory;

import MainConfig.GameData;
import World.WorldTile;

public class Inventory {

    private Item[] inventory;
    private InventoryGraphics inventoryGraphics;
    private GameData gameData;
    public int size = 0;

    public Inventory(GameData gameData) {
        this.gameData = gameData;
        inventory = new Item[gameData.hotbarSize];

        inventory[0] = new Item(WorldTile.Tile.Wall, 64, gameData);
        inventory[1] = new Item(WorldTile.Tile.turret, 10, gameData);
        inventory[2] = new Item(WorldTile.Tile.FurnaceOff, 64, gameData);
        inventory[3] = new Item(WorldTile.Tile.FurnaceOn, 64, gameData);
        inventory[4] = new Item(WorldTile.Tile.Dirt, 64, gameData);
        inventory[5] = new Item(WorldTile.Tile.Belt, 64, gameData);
        size = 5;

        inventoryGraphics = new InventoryGraphics(gameData, this);
    }

    public void placeItem() {
        int selectedSlot = gameData.selectedSlot;
        if (selectedSlot >= 0 && selectedSlot < gameData.hotbarSize && inventory[selectedSlot] != null) {
            Item item = inventory[selectedSlot];
            item.setAmntOf(item.getAmntOf() - 1);
            checkEmpty();
            inventoryGraphics.createHotbarImage(gameData.selectedSlot);
        }
        inventoryGraphics.createHotbarImage(gameData.selectedSlot);
    }

    public WorldTile.Tile getItem() {
        int selectedSlot = gameData.selectedSlot;
        if (selectedSlot >= 0 && selectedSlot < gameData.hotbarSize && inventory[selectedSlot] != null) {
            Item item = inventory[selectedSlot];
            return item.getTileType();
        } else {
            return WorldTile.Tile.Empty;
        }
    }

    public void removeItem(WorldTile.Tile tileType) {
        int slot = getSlot(tileType);
        if (slot != -1) {
            inventory[slot].setAmntOf(inventory[slot].getAmntOf() + 1);
        } else if (size < gameData.hotbarSize){
            inventory[firstOpenSpace()] = new Item(tileType, 1, gameData);
            size++;
        }

        inventoryGraphics.createHotbarImage(gameData.selectedSlot);
    }

    public int firstOpenSpace() {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) {
                return i;
            }
        }

        return -1;
    }

    public InventoryGraphics getInventoryGraphics() {
        return inventoryGraphics;
    }

    public boolean checkWithinHotbar(int check) {
        return check >= 0 && check < gameData.hotbarSize;
    }

    public void checkEmpty() {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                if (inventory[i].getAmntOf() <= 0) {
                    size--;
                    inventory[i] = null;
                }
            }
        }
    }

    public int getSlot(WorldTile.Tile tile) {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null && inventory[i].getTileType() == tile) {
                return i;
            }
        }
        return -1;
    }

    public Item[] getInventory() {
        return inventory;
    }
}
