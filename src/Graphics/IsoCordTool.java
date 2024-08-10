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
        width = gameData.tileSize;
        height = gameData.tileSize;
        xVector = new double[]{0.5 * width, 0.25 * height};
        yVector = new double[]{-0.5 * width, 0.25 * height};
    }

    public int getXIso(int x, int y) {
        double isoX = x * xVector[0] + y * yVector[0];
        return (int) isoX - width / 2 + gameData.camera.getxOffset();
    }

    public int getYIso(int x, int y) {
        double isoY = x * xVector[1] + y * yVector[1];
        return (int) (isoY) - (height/2 * gameData.world.getWorldYSize()/2) + gameData.camera.getyOffset();
    }

    public int[] getTileFromIso(int isoX, int isoY) {
        double adjustedIsoX = isoX - gameData.camera.getxOffset();
        double adjustedIsoY = isoY - gameData.camera.getyOffset() / 2;

        double determinant = xVector[0] * yVector[1] - xVector[1] * yVector[0];
        double invDet = 1 / determinant;

        double x = invDet * (yVector[1] * adjustedIsoX - yVector[0] * adjustedIsoY);
        double y = invDet * (-xVector[1] * adjustedIsoX + xVector[0] * adjustedIsoY);

        return new int[]{(int) x, (int) y};
    }
}
