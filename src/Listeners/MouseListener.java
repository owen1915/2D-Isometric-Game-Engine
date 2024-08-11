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

    private boolean leftPressed = false;

    private GameData gameData;

    public MouseListener(GameData gameData){
        this.gameData = gameData;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

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


        //If right remove block
        if (e.getButton() == MouseEvent.BUTTON3){
            //Check if calculate isometric cords are in the worlds bounds
            if (cords[0] >= 0 && cords[0] < gameData.world.getWorldXSize()){
                if (cords[1] >= 0 && cords[1] < gameData.world.getWorldYSize()) {
                    //Check if there is a block at each depth in the desired cords

                    if (gameData.world.getWorldTileType(cords[0] + 2, cords[1] + 2, 2) != WorldTile.Tile.Empty){
                        gameData.world.setWorldTile(cords[0] + 2, cords[1] + 2, 2, WorldTile.Tile.Empty);
                    }
                    else if (gameData.world.getWorldTileType(cords[0] + 1, cords[1] + 1, 1) != WorldTile.Tile.Empty){
                        gameData.world.setWorldTile(cords[0] + 1, cords[1] + 1, 1, WorldTile.Tile.Empty);
                    }
                    System.out.println("MouseToIsoWorldCords = (" + (cords[0]) +", " + (cords[1]) +")");
                }
            }
        }
        //If left | place block
        else if (e.getButton() == MouseEvent.BUTTON1){
            //Check if calculate isometric cords are in the worlds bounds
            if (cords[0] >= 0 && cords[0] < gameData.world.getWorldXSize()){
                if (cords[1] >= 0 && cords[1] < gameData.world.getWorldYSize()) {
                    //Check if there is a block at each depth in the desired cords

                    if (gameData.world.getWorldTileType(cords[0] + 1, cords[1] + 1, 1) != WorldTile.Tile.Empty){
                        gameData.world.setWorldTile(cords[0] + 1, cords[1] + 1, 2, WorldTile.Tile.Wall);

                    }
                    else if (gameData.world.getWorldTileType(cords[0], cords[1], 1) == WorldTile.Tile.Empty){
                        gameData.world.setWorldTile(cords[0], cords[1], 1, WorldTile.Tile.Wall);

                    }
                    System.out.println("MouseToIsoWorldCords = (" + (cords[0]) +", " + (cords[1]) +")");
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
