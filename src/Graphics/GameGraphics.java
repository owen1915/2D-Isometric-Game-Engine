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

        //Get the mouse world cords from mouse motion listener
        int[] mouseWorldCor = gameData.gamePanel.getMouseMotionListener().getMouseWorldCords();

        //Draw the selection tile
        if (mouseWorldCor[0] - 1 < 0 || mouseWorldCor[1] - 1  < 0 || mouseWorldCor[0] - 1 > gameData.world.getWorldXSize()
                || mouseWorldCor[1] - 1  > gameData.world.getWorldYSize()) {
            bufferedGraphics.drawImage(imageLoader.getTextures()[WorldTile.Tile.RedSelection.ordinal()], isoCordTool.getXIso(mouseWorldCor[0] - 1, mouseWorldCor[1] - 1), isoCordTool.getYIso(mouseWorldCor[0] - 1, mouseWorldCor[1] - 1), null);
        } else {
            bufferedGraphics.drawImage(imageLoader.getTextures()[WorldTile.Tile.Selection.ordinal()], isoCordTool.getXIso(mouseWorldCor[0] - 1, mouseWorldCor[1] - 1), isoCordTool.getYIso(mouseWorldCor[0] - 1, mouseWorldCor[1] - 1), null);
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
