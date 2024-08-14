package Graphics;

import Graphics.Panels.GamePanel;
import Listeners.MouseListener;
import MainConfig.GameData;
import MainConfig.ImageLoader;
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

    public GameGraphics(GameData gameData) {
        this.gameData = gameData;
        imageLoader = gameData.imageLoader;
        isoCordTool = gameData.gamePanel.getIsoCordTool();

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
                    //Get the world tile
                    WorldTile.Tile tileType = gameData.world.getWorldTileType(x, y, z);

                    //conditions to be met for it to be rendered
                    //checks if tile is empty or null
                    if (tileType != null && tileType != WorldTile.Tile.Empty) {
                        boolean draw = false;

                        //if it is the top layer
                        if (z == 2) {
                            draw = true;
                        }

                        //checks tiles next to it
                        if (gameData.world.getWorldTileType(x + 1, y, z) == WorldTile.Tile.Empty ||
                                gameData.world.getWorldTileType(x, y + 1, z) == WorldTile.Tile.Empty) {
                            draw = true;
                        }

                        //if it has a tile above it that is empty
                        if ((z == 0 || z == 1) && gameData.world.getWorldTileType(x, y, z + 1) == WorldTile.Tile.Empty) {
                            draw = true;
                        }

                        //if the tile isnt visible because a tile is blocking it
                        if ((z == 0 || z == 1) && gameData.world.getWorldTileType(x + 1, y + 1, z + 1) != WorldTile.Tile.Empty) {
                            draw = false;
                        }

                        //checks if tile is on the world boundary
                        if (x == gameData.world.getWorldXSize() - 1 || y == gameData.world.getWorldYSize() - 1) {
                            draw = true;
                        }

                        if (draw && withinBounds(x, y)) {
                            bufferedGraphics.drawImage(imageLoader.getTextures()[tileType.ordinal()], isoCordTool.getXIso(x, y), isoCordTool.getYIso(x, y) - (z * tileSize/2), null);
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

        //draw to panel
        g2.drawImage(bufferedImage, 0, 0, null);
    }

    private boolean withinBounds(int x, int y) {
        // Convert isometric coordinates to screen coordinates
        int screenX = isoCordTool.getXIso(x, y);
        int screenY = isoCordTool.getYIso(x, y);

        return screenX <= gameData.screenWidth + gameData.tileSize && screenX >= -gameData.tileSize && screenY <= gameData.screenHeight + gameData.tileSize && screenY >= -gameData.tileSize;
    }
}
