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

    public void updateIso() {
        width = gameData.tileSize;
        height = gameData.tileSize;
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

    public int[] screenToIso(int xCor, int yCor){
        int[] cordArray = new int[2];

        float isoX = (float) (xCor + 2 * yCor) / (gameData.tileSize);
        float isoY = (float) (2 * yCor - xCor) / (gameData.tileSize);

        if (isoX < 0){
            isoX--;
        }
        if (isoY < 0){
            isoY--;
        }

        cordArray[0] = (int) isoX;
        cordArray[1] = (int) isoY;

        return cordArray;
    }

    public int[] isometricToGrid(int isoX, int isoY) {
        int gridX =  isoX / gameData.tileSize;
        int gridY =  isoY / gameData.tileSize;
        return new int[] {gridX, gridY};
    }
}
