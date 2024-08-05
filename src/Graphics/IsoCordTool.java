package Graphics;

import MainConfig.GameData;

import java.awt.*;

public class IsoCordTool {

    public static Point convertToIso(int x, int y) {
        int isoX = (x - y) * (GameData.tileWidth / 2);
        int isoY = (x + y) * (GameData.tileHeight / 2);
        return new Point(isoX, isoY);
    }
}
