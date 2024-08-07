package Graphics;

import MainConfig.GameData;

public class IsoCordTool {

    private GameData gameData;
    private int width;
    private int height;
    private double[] xVector;
    private double[] yVector;

    public IsoCordTool(GameData gameData) {
        this.gameData = gameData;
        width = gameData.tileWidth;
        height = gameData.tileHeight;
        xVector = new double[]{0.5 * width, 0.25 * height};
        yVector = new double[]{-0.5 * width, 0.25 * height};
    }


    public int getXIso(int x, int y) {
        double isoX = x * xVector[0] + y * yVector[0];
        return (int) isoX - width/2 + gameData.camera.getxOffset();
    }

    public int getYIso(int x, int y) {
        double isoY = x * xVector[1] + y * yVector[1];
        return (int) isoY + gameData.camera.getyOffset()/2;
    }
}
