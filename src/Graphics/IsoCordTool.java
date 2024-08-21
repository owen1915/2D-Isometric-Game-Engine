package Graphics;

import MainConfig.GameData;

public class IsoCordTool {

    private int width;
    private int height;
    private double[] xVector;
    private double[] yVector;
    private int tilesize;

    public IsoCordTool(GameData gameData) {
        tilesize = gameData.camera.getZoomAmnt() + (gameData.camera.getZoomAmnt() * gameData.camera.scale);
        width = tilesize;
        height = tilesize;
        xVector = new double[]{0.5 * width, 0.25 * height};
        yVector = new double[]{-0.5 * width, 0.25 * height};
    }

    public IsoCordTool(int scale, int zoomAmnt) {
        tilesize = zoomAmnt + (zoomAmnt * scale);
        width = tilesize;
        height = tilesize;
        xVector = new double[]{0.5 * width, 0.25 * height};
        yVector = new double[]{-0.5 * width, 0.25 * height};
    }

    public int getXIso(int x, int y) {
        double isoX = x * xVector[0] + y * yVector[0];
        return (int) isoX;
    }

    public int getYIso(int x, int y) {
        double isoY = x * xVector[1] + y * yVector[1];
        return (int) (isoY);
    }

    public int getXFromIso(int isoX, int isoY) {
        double a = xVector[0];
        double b = yVector[0];
        double c = xVector[1];
        double d = yVector[1];

        double denominator = a * d - b * c;

        if (denominator == 0) {
            throw new ArithmeticException("Matrix is singular and cannot be inverted");
        }

        double x = (isoX * d - isoY * b) / denominator;
        return (int) Math.round(x) - 1;
    }

    public int getYFromIso(int isoX, int isoY) {
        double a = xVector[0];
        double b = yVector[0];
        double c = xVector[1];
        double d = yVector[1];

        double denominator = a * d - b * c;

        if (denominator == 0) {
            throw new ArithmeticException("Matrix is singular and cannot be inverted");
        }

        double y = (isoY * a - isoX * c) / denominator;
        return (int) Math.round(y);
    }
}
