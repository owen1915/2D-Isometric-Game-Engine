package Graphics;

import Graphics.Panels.GamePanel;
import MainConfig.GameData;
import MainConfig.ImageLoader;
import World.WorldTile;

import javax.swing.*;
import java.awt.*;

public class GameGraphics {

    private IsoCordTool isoCordTool;
    private ImageLoader imageLoader = new ImageLoader();
    private GameData gameData;
    private Image[] textures;

    public GameGraphics(GameData gameData) {
        this.gameData = gameData;
        isoCordTool = new IsoCordTool(gameData);
        textures = imageLoader.getTextures();
    }

    public void render(Graphics2D g2) {
        // iterate over the (current) world that holds the tiles
        for (int i = 0; i < gameData.world.getWorldYSize(); i++) {

            for (int j = 0; j < gameData.world.getWorldXSize(); j++) {
                // Get the world tile
                WorldTile.Tile tileType = gameData.world.getWorldTileType(i, j);

                g2.drawImage(imageLoader.getTextures()[tileType.ordinal()], isoCordTool.getXIso(j, i), isoCordTool.getYIso(j, i), null);
            }
        }
    }
}
