package World;

import MainConfig.GameData;

import java.awt.event.MouseEvent;

public class BlockManipulator {

    private GameData gameData;

    public BlockManipulator(GameData gameData) {
        this.gameData = gameData;
    }

    public Block getBlock(int mouseX, int mouseY, boolean remove) {
        int gridX = gameData.isoCordTool.getXFromIso(mouseX - gameData.camera.getxOffset(), mouseY - gameData.camera.getyOffset());
        int gridY = gameData.isoCordTool.getYFromIso(mouseX - gameData.camera.getxOffset(), mouseY - gameData.camera.getyOffset());

        World world = gameData.world;
        Block block = gameData.world.findBlockByRayCasting(gridX, gridY);
        int count = 0;
        while (block == null && count < 3) {
            gridX += 1;
            gridY += 1;
            block = gameData.world.findBlockByRayCasting(gridX, gridY);
            count++;
        }
        Block[] blocks = world.getBlocksAround(block);

        return world.checkPolygonHitting(blocks, mouseX, mouseY, remove);
    }

    private boolean checkBoundary(Block block) {
        return block.getGridX() > 0 && block.getGridX() <= gameData.worldSize - 2 && block.getGridY() > 0 && block.getGridY() <= gameData.worldSize - 2;
    }

    public void placeBlock(int mouseX, int mouseY) {
        WorldTile.Tile blockType = gameData.inventory.getInventory()[gameData.selectedSlot].getTileType();
        if (blockType == null) {
            return;
        }

        int gridX = gameData.isoCordTool.getXFromIso(mouseX - gameData.camera.getxOffset(), mouseY - gameData.camera.getyOffset());
        int gridY = gameData.isoCordTool.getYFromIso(mouseX - gameData.camera.getxOffset(), mouseY - gameData.camera.getyOffset());
        Block block = gameData.world.findBlockByRayCasting(gridX, gridY);

        Block targetBlock = getBlock(mouseX, mouseY, false);
        World world = gameData.world;
        if (targetBlock != null && targetBlock.isEmpty() && checkBoundary(targetBlock)) {
            world.setBlockOnGrid(targetBlock.getGridX(), targetBlock.getGridY(), targetBlock.getGridZ(), blockType);
            gameData.inventory.placeItem();
        } else if (block.isEmpty() && checkBoundary(block)) {
            block.setBlockType(blockType);
            gameData.inventory.placeItem();
        }
    }

    public void removeBlock(int mouseX, int mouseY) {
        Block block = getBlock(mouseX, mouseY, true);
        World world = gameData.world;

        WorldTile.Tile blockType = block.getBlockType();

        if (checkBoundary(block)) {
            world.setBlockOnGrid(block.getGridX(), block.getGridY(), block.getGridZ(), WorldTile.Tile.Empty);
            gameData.inventory.removeItem(blockType);
        }
    }
}
