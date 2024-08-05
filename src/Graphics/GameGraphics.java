package Graphics;

import Graphics.Panels.GamePanel;
import MainConfig.GameData;
import World.WorldTile;

import javax.swing.*;
import java.awt.*;

public class GameGraphics {

    private IsoCordTool isoCordTool = new IsoCordTool();

    public GameGraphics() {
        for (int y = 0; y < GameData.world.getWorldYSize(); y++) {
            for (int i = 0; i < GameData.world.getWorldXSize(); i++) {
                GameData.world.setWorldTile(y, i, WorldTile.Tile.Grass);
            }
        }
        System.out.println(isoCordTool.getXIso(3, 1));
        System.out.println(isoCordTool.getYIso(3, 1));
    }

    public void drawTile(Graphics2D g, int x, int y) {

    }

    public void render(Graphics2D g2) {

        // more testing trying to figure out how to use iso rendering
        Image grassTile = new ImageIcon("res/grassIso.png").getImage();

        for (int i = 0; i < GameData.world.getWorldYSize(); i++) {

            for (int j = 0; j < GameData.world.getWorldXSize(); j++) {
                g2.drawImage(grassTile, isoCordTool.getXIso(j, i), isoCordTool.getYIso(j, i), null);
            }
        }
    }
}
