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

    public GameGraphics(GameData gameData) {
        this.gameData = gameData;
        imageLoader = gameData.imageLoader;
        isoCordTool = gameData.gamePanel.getIsoCordTool();
    }

    public void render(Graphics2D g2) {
        BufferedImage bufferedImage = new BufferedImage(gameData.screenWidth, gameData.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D bufferedGraphics = (Graphics2D) bufferedImage.getGraphics();
        int tileSize = gameData.tileSize;
        // iterate over the (current) world that holds the tiles
        for (int z = 0; z < 3; z++) {
            for (int i = 0; i < gameData.world.getWorldYSize(); i++) {
                for (int j = 0; j < gameData.world.getWorldXSize(); j++) {
                    // Get the world tile
                    WorldTile.Tile tileType = gameData.world.getWorldTileType(j, i, z);

                    if (tileType != WorldTile.Tile.Empty) {
                        bufferedGraphics.drawImage(imageLoader.getTextures()[tileType.ordinal()], isoCordTool.getXIso(j, i), isoCordTool.getYIso(j, i) - (z * tileSize/2), null);
                    }
                }
            }
        }

        // Get the mouse world cords from mouse motion listener
        int[] mouseWorldCor = gameData.gamePanel.getMouseMotionListener().getMouseWorldCords();


        // Draw the selection tile
        bufferedGraphics.drawImage(imageLoader.getTextures()[WorldTile.Tile.Selection.ordinal()], isoCordTool.getXIso(mouseWorldCor[0] - 1, mouseWorldCor[1] - 1), isoCordTool.getYIso(mouseWorldCor[0] - 1, mouseWorldCor[1] - 1), null);

        // dispose graphics
        bufferedGraphics.dispose();

        // draw to panel
        g2.drawImage(bufferedImage, 0, 0, null);
    }
}
