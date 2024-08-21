package Listeners;

import Graphics.IsoCordTool;
import MainConfig.GameData;
import World.Block;
import World.World;
import World.WorldTile;
import com.sun.source.tree.UsesTree;

import World.Chunk;
import java.awt.event.MouseEvent;


public class MouseListener implements java.awt.event.MouseListener {

    private GameData gameData;

    public MouseListener(GameData gameData){
        this.gameData = gameData;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        /*
        IsoCordTool isoCordTool = new IsoCordTool(gameData.camera.scale);
        int gridX = isoCordTool.getXFromIso(e.getX() - gameData.camera.getxOffset(), e.getY() - gameData.camera.getyOffset());
        int gridY = isoCordTool.getYFromIso(e.getX() - gameData.camera.getxOffset(), e.getY() - gameData.camera.getyOffset());

        int chunkX = gridX / gameData.chunkSize;
        int chunkY = gridY / gameData.chunkSize;
        */
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        if (e.getButton() == MouseEvent.BUTTON3) {
            gameData.blockManipulator.removeBlock(x, y);
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            gameData.blockManipulator.placeBlock(x, y);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
