package Listeners;

import Graphics.IsoCordTool;
import MainConfig.GameData;
import World.Block;
import World.World;
import World.WorldTile;
import com.sun.source.tree.UsesTree;

import java.awt.event.MouseEvent;


public class MouseListener implements java.awt.event.MouseListener {

    private GameData gameData;

    public MouseListener(GameData gameData){
        this.gameData = gameData;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON3) {
            gameData.blockManipulator.removeBlock(e.getX(), e.getY());
        }
        if (e.getButton() == MouseEvent.BUTTON1) {
            gameData.blockManipulator.placeBlock(e.getX(), e.getY());
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
