package World;

import MainConfig.GameData;

import java.awt.event.MouseEvent;

public class BlockManipulator {

    private GameData gameData;
    private Block[] emptyBlocks = new Block[2];

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

        if (block == null) {
            return null;
        }

        Block[] blocks = world.getBlocksAround(block);
        emptyBlocks[0] = blocks[0];
        emptyBlocks[1] = blocks[4];
        return world.checkPolygonHitting(blocks, mouseX, mouseY, remove);
    }

    public boolean checkBoundary(int x, int y) {
        return x > 0 && x <= gameData.worldSize - 2 && y > 0 && y <= gameData.worldSize - 2;
    }

    public void placeBlock(int mouseX, int mouseY) {
        if (!gameData.inventory.checkWithinHotbar(gameData.selectedSlot) || gameData.inventory.getInventory()[gameData.selectedSlot] == null) {
            return;
        }
        WorldTile.Tile blockType = gameData.inventory.getInventory()[gameData.selectedSlot].getTileType();

        int gridX = gameData.isoCordTool.getXFromIso(mouseX - gameData.camera.getxOffset(), mouseY - gameData.camera.getyOffset());
        int gridY = gameData.isoCordTool.getYFromIso(mouseX - gameData.camera.getxOffset(), mouseY - gameData.camera.getyOffset());
        Block block = gameData.world.findBlockByRayCasting(gridX, gridY);

        Block targetBlock = getBlock(mouseX, mouseY, false);
        World world = gameData.world;
        if (targetBlock != null && targetBlock.isEmpty() && checkBoundary(targetBlock.getGridX(), targetBlock.getGridY())) {
            world.setBlockOnGrid(targetBlock.getGridX(), targetBlock.getGridY(), targetBlock.getGridZ(), blockType);
            gameData.inventory.placeItem();
        } else if (block.isEmpty() && checkBoundary(block.getGridX(), block.getGridY()) && (emptyBlocks[0].isEmpty() && emptyBlocks[1].isEmpty())) {
            block.setBlockType(blockType);
            gameData.inventory.placeItem();
        }
    }

    public void removeBlock(int mouseX, int mouseY) {
        Block block = getBlock(mouseX, mouseY, true);
        World world = gameData.world;

        if (block != null) {
            WorldTile.Tile blockType = block.getBlockType();
            if (checkBoundary(block.getGridX(), block.getGridY())) {
                world.setBlockOnGrid(block.getGridX(), block.getGridY(), block.getGridZ(), WorldTile.Tile.Empty);
                gameData.inventory.removeItem(blockType);
            }
        }
    }
}
