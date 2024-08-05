package Graphics;

import Graphics.Panels.GamePanel;
import MainConfig.GameData;

import java.awt.*;

public class GameGraphics {

    private int[][] tileMatrix = new int[GameData.MAXROWS][GameData.MAXCOLS];
    private int counter = 0;

    public GameGraphics() {
        for (int y = 0; y < GameData.MAXROWS; y++) {
            for (int i = 0; i < GameData.MAXCOLS; i++) {
                tileMatrix[y][i] = 0;
            }
        }
    }

    public void drawTile(Graphics2D g, int x, int y) {
        if (counter % 2 == 0) {
            g.setColor(Color.black);
        } else {
            g.setColor(Color.red);
        }
        int[] xPoints = {x, x + GameData.tileWidth / 2, x, x - GameData.tileWidth / 2};
        int[] yPoints = {y, y + GameData.tileHeight / 2, y + GameData.tileHeight, y + GameData.tileHeight / 2};
        g.fillPolygon(xPoints, yPoints, 4);
        counter++;
    }

    public void render(Graphics2D g2, GamePanel gp) {
        for (int i = 0; i < tileMatrix.length; i++) {
            for (int j = 0; j < tileMatrix[0].length; j++) {
                Point isoPoint = IsoCordTool.convertToIso(i, j);
                drawTile(g2, isoPoint.x + gp.getWidth() / 2, isoPoint.y);
            }
        }
        counter = 0;
    }
}
