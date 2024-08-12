package Listeners;

import Graphics.IsoCordTool;
import MainConfig.GameData;
import World.WorldTile;

import java.awt.event.MouseEvent;
import java.text.spi.BreakIteratorProvider;


public class MouseListener implements java.awt.event.MouseListener {

    private int x;
    private int y;

    private int mouseIsoX;
    private int mouseIsoY;

    public WorldTile.Tile selectedTile = WorldTile.Tile.Wall;

    private boolean leftPressed = false;

    private GameData gameData;

    public MouseListener(GameData gameData){
        this.gameData = gameData;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }




    //get an array off possible Tiles mouse could be hitting
    public  WorldTile.Tile[] getTilesOfMouse(int xIsoCor, int yIsoCor){
        WorldTile.Tile[] tilesOfMouseArray = new WorldTile.Tile[3];
        //Loop through each lair to construct array
        for (int lair = 0; lair < 3; lair++){
            tilesOfMouseArray[lair] = gameData.world.getWorldTileType(xIsoCor + lair, yIsoCor + lair, lair);
        }
        return tilesOfMouseArray;

    }


    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();

        //Calculate world cords of mouse
        IsoCordTool isoCordTool = gameData.gamePanel.getIsoCordTool();
        int[] cords = isoCordTool.screenToIso(x - gameData.camera.getxOffset(), y - gameData.camera.getyOffset());
        cords[0] += gameData.world.getWorldXSize()/2;
        cords[1] += gameData.world.getWorldYSize()/2;

        this.mouseIsoX = cords[0];
        this.mouseIsoY = cords[1];
        leftPressed = true;

        WorldTile.Tile[] worldTilesOfMouseArray = getTilesOfMouse(cords[0], cords[1]);

        WorldTile.Tile[] tiles = WorldTile.Tile.values();
        WorldTile.Tile selectedTile = tiles[gameData.selectedTile];

        if (e.getButton() == MouseEvent.BUTTON3){
            for (int lair = 2; lair >= 0; lair--){
                WorldTile.Tile tile = worldTilesOfMouseArray[lair];
                if (tile != WorldTile.Tile.Empty){
                    System.out.println(worldTilesOfMouseArray[lair] + " | " + lair);
                    gameData.world.setWorldTile(cords[0] + lair, cords[1] + lair, lair, WorldTile.Tile.Empty);
                    break;
                }
            }
        }

        if (e.getButton() == MouseEvent.BUTTON1){
            for (int lair = 2; lair >= 0; lair--){
                WorldTile.Tile tile = worldTilesOfMouseArray[lair];
                if (tile != WorldTile.Tile.Empty){
                    System.out.println(worldTilesOfMouseArray[lair] + " | " + lair);
                    gameData.world.setWorldTile(cords[0] + lair, cords[1] + lair, lair + 1, selectedTile);
                    break;
                }
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        leftPressed = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        IsoCordTool isoCordTool = gameData.gamePanel.getIsoCordTool();
        int[] cords = isoCordTool.screenToIso(x - gameData.camera.getxOffset(), y - gameData.camera.getyOffset());
        cords[0] += gameData.world.getWorldXSize()/2;
        cords[1] += gameData.world.getWorldYSize()/2;

        this.mouseIsoX = cords[0];
        this.mouseIsoY = cords[1];
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }

    public int getIsoY() {
        return mouseIsoY;
    }

    public int getIsoX() {
        return mouseIsoX;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

}
