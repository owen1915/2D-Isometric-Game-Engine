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
        //return (int) isoX - width / 2 + gameData.camera.getxOffset();
        return (int) isoX + width/2;
    }

    public int getYIso(int x, int y) {
        double isoY = x * xVector[1] + y * yVector[1];
        //return (int) (isoY) - (height/2 * gameData.world.getWorldYSize()/2) + gameData.camera.getyOffset();
        return (int) (isoY) + (height);
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
}
