package Graphics;

import MainConfig.GameData;

public class IsoCordTool {

    private int width = GameData.tileWidth;
    private int height = GameData.tileHeight;
    private double[] xVector = {0.5 * width, 0.25 * height};
    private double[] yVector = {-0.5 * width, 0.25 * height};

    public int getXIso(int x, int y) {
        double isoX = x * xVector[0] + y * yVector[0];
        return (int) isoX - width/2 + GameData.screenWidth/2;
    }

    public int getYIso(int x, int y) {
        double isoY = x * xVector[1] + y * yVector[1];
        return (int) isoY;
    }
}
