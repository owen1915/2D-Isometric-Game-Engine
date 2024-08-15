package Listeners;

import Graphics.IsoCordTool;
import Graphics.Panels.GamePanel;
import MainConfig.GameData;
import World.Block;

import java.awt.event.MouseEvent;

public class MouseMotionListener implements java.awt.event.MouseMotionListener {
    private GameData gameData;
    private int[] mouseWorldCords = new int[2];
    public MouseMotionListener(GameData gameData){
        this.gameData = gameData;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();

        mouseWorldCords[0] = x;
        mouseWorldCords[1] = y;
    }

    public int[] getMouseWorldCords(){
        return mouseWorldCords;
    }
}
