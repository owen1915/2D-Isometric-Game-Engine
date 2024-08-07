package Graphics.Panels;

import Graphics.Frame.GameFrame;
import Listeners.MouseListener;
import MainConfig.GameData;
import Graphics.GameGraphics;

import javax.swing.*;
import java.awt.*;
import Graphics.IsoCordTool;
import World.WorldTile;

public class GamePanel extends JPanel {

    // creating classes
    private GameData gameData;
    private MainMenu mainMenu;
    private GameGraphics gameGraphics;
    private MouseListener mouseListener;
    private IsoCordTool isoCordTool;

    public GamePanel(GameFrame gameFrame) {
        gameData = new GameData(this);

        setPreferredSize(new Dimension(gameData.screenWidth, gameData.screenHeight));
        setVisible(true);
        setFocusable(true);
        requestFocusInWindow();

        mouseListener = new MouseListener();
        isoCordTool = new IsoCordTool(gameData);
        mainMenu = new MainMenu(gameData);
        gameGraphics = new GameGraphics(gameData);;

        this.addMouseListener(mouseListener);
    }

    /**
     * Update method
     */
    public void update() {
        // repaints the panel
        repaint();

        if (mouseListener.isLeftPressed()) {
            int[] tileCords = isoCordTool.getTileFromIso(mouseListener.getX(), mouseListener.getY());
            gameData.world.setWorldTile(tileCords[1], tileCords[0], WorldTile.Tile.Wall);
        }
    }

    /**
     * overrides repaint
     */
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        // switch statement to check what to render using GAMESTATE
        switch (gameData.GAMESTATE) {
            case 1:
                mainMenu.render(g2);
                break;
            case 2:
                gameGraphics.render(g2);
                break;
            default:
                if (gameData.debug) {
                    System.out.println("NOT RENDERING NOTHING: GAMESTATE = " + gameData.GAMESTATE);
                }
        }

        // disposes graphics for garbage collection
        g2.dispose();
    }

    public IsoCordTool getIsoCordTool() {
        return isoCordTool;
    }

    public GameData getGameData() {
        return gameData;
    }
}
