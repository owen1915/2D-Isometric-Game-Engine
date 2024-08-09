package Graphics;

import Graphics.Panels.GamePanel;
import MainConfig.GameData;
import MainConfig.ImageLoader;
import World.WorldTile;

import javax.swing.*;
import java.awt.*;

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
        // iterate over the (current) world that holds the tiles
        for (int z = 0; z < 3; z++) {
            for (int i = 0; i < gameData.world.getWorldYSize(); i++) {
                for (int j = 0; j < gameData.world.getWorldXSize(); j++) {
                    // Get the world tile

                    WorldTile.Tile tileType = gameData.world.getWorldTileType(j, i, z);

                    if (tileType != WorldTile.Tile.Empty) {
                        g2.drawImage(imageLoader.getTextures()[tileType.ordinal()], isoCordTool.getXIso(j, i), isoCordTool.getYIso(j, i) - (z * 32), null);
                    }
                }
            }
        }
    }
}
