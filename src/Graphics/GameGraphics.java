package Graphics;

import Graphics.Inventory.InventoryGraphics;
import Graphics.Panels.GamePanel;
import Listeners.MouseListener;
import MainConfig.GameData;
import MainConfig.ImageLoader;
import World.Block;
import World.WorldTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameGraphics {

    private IsoCordTool isoCordTool;
    private GameData gameData;
    private ImageLoader imageLoader;
    private BufferedImage bufferedImage;
    private Graphics2D bufferedGraphics;
    private InventoryGraphics inventoryGraphics;

    public GameGraphics(GameData gameData) {
        this.gameData = gameData;
        inventoryGraphics = gameData.inventory.getInventoryGraphics();
        imageLoader = gameData.imageLoader;
        isoCordTool = gameData.isoCordTool;

        bufferedImage = new BufferedImage(gameData.screenWidth, gameData.screenHeight, BufferedImage.TYPE_INT_ARGB);
        bufferedGraphics = (Graphics2D) bufferedImage.getGraphics();
    }

    public void render(Graphics2D g2) {
        bufferedGraphics.clearRect(0, 0, gameData.screenWidth, gameData.screenHeight);
        //bufferedGraphics.drawImage(imageLoader.getBackground()[0], 0, 0, gameData.screenWidth, gameData.screenHeight, null);

        int tileSize = gameData.tileSize;
        // iterate over the (current) world that holds the tiles
        for (int z = 0; z < 3; z++) {
            for (int x = 0; x < gameData.world.getWorldYSize(); x++) {
                for (int y = 0; y < gameData.world.getWorldXSize(); y++) {
                    if (withinBounds(x, y)) {
                        //Get the world tile
                        Block block = gameData.world.getBlockOnGrid(x, y, z);

                        //conditions to be met for it to be rendered
                        //checks if tile is empty or null
                        if (block != null && !block.isEmpty()) {
                            bufferedGraphics.drawImage(imageLoader.getTextures()[block.getBlockType().ordinal()], isoCordTool.getXIso(x, y) + gameData.camera.getxOffset(), isoCordTool.getYIso(x, y) + (gameData.camera.getyOffset() - (z * tileSize/2)), null);
                            //draw hitbox
                            /*bufferedGraphics.setColor(Color.red);
                            Polygon[] polygons = block.getPolygons();
                            for (Polygon polygon : polygons) {
                                bufferedGraphics.drawPolygon(polygon);
                            }*/
                        }
                    }
                }
            }
        }

        int[] mouseWorldCor = gameData.gamePanel.getMouseMotionListener().getMouseWorldCords();
        Block block = gameData.blockManipulator.getBlock(mouseWorldCor[0], mouseWorldCor[1], false);

        //Draw the selection tile
        if (block != null) {
            int x = isoCordTool.getXIso(block.getGridX(), block.getGridY()) + gameData.camera.getxOffset();
            int y = isoCordTool.getYIso(block.getGridX(), block.getGridY()) + gameData.camera.getyOffset() - (block.getGridZ() * tileSize/2);
            if (gameData.blockManipulator.checkBoundary(block.getGridX(), block.getGridY())) {
                bufferedGraphics.drawImage(imageLoader.getTextures()[WorldTile.Tile.Selection.ordinal()], x, y, null);
            }
        } else {
            int x = isoCordTool.getXFromIso(mouseWorldCor[0] - gameData.camera.getxOffset(), mouseWorldCor[1] - gameData.camera.getyOffset());
            int y = isoCordTool.getYFromIso(mouseWorldCor[0] - gameData.camera.getxOffset(), mouseWorldCor[1] - gameData.camera.getyOffset());
            int drawX = isoCordTool.getXIso(x, y) + gameData.camera.getxOffset();
            int drawY = isoCordTool.getYIso(x, y) + gameData.camera.getyOffset() - gameData.tileSize/2;
            if (gameData.blockManipulator.checkBoundary(x, y)) {
                bufferedGraphics.drawImage(imageLoader.getTextures()[WorldTile.Tile.Selection.ordinal()], drawX, drawY, null);
            }
        }
        //draw inventory
        inventoryGraphics.render(bufferedGraphics);

        //draw to panel
        g2.drawImage(bufferedImage, 0, 0, null);
    }

    private boolean withinBounds(int x, int y) {
        // Convert isometric coordinates to screen coordinates
        int screenX = isoCordTool.getXIso(x, y) + gameData.camera.getxOffset();
        int screenY = isoCordTool.getYIso(x, y) + gameData.camera.getyOffset();

        return screenX <= gameData.screenWidth + gameData.tileSize && screenX >= -gameData.tileSize && screenY <= gameData.screenHeight + gameData.tileSize && screenY >= -gameData.tileSize;
    }
}
