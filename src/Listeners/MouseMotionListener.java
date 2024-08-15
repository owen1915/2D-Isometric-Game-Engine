package Listeners;

import Graphics.IsoCordTool;
import Graphics.Panels.GamePanel;
import MainConfig.GameData;

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
        IsoCordTool isoCordTool = gameData.isoCordTool;
        int[] cords = isoCordTool.screenToIso(x - gameData.camera.getxOffset(), y - gameData.camera.getyOffset());
        cords[0] += gameData.world.getWorldXSize()/2;
        cords[1] += gameData.world.getWorldYSize()/2;
        mouseWorldCords = cords;
    }

    public int[] getMouseWorldCords(){
        return mouseWorldCords;
    }
}
