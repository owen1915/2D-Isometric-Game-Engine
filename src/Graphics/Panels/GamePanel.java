package Graphics.Panels;

import MainConfig.GameData;
import Graphics.GameGraphics;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    // creating classes
    private MainMenu mainMenu;
    private GameGraphics gameGraphics;

    public GamePanel() {
        setPreferredSize(new Dimension(GameData.width, GameData.height));
        setVisible(true);
        setFocusable(true);
        requestFocusInWindow();

        mainMenu = new MainMenu(this);
        gameGraphics = new GameGraphics();
    }

    /**
     * Update method
     */
    public void update() {
        // repaints the panel
        repaint();
    }

    /**
     * overrides repaint
     */
    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;

        // switch statement to check what to render using GAMESTATE
        switch (GameData.GAMESTATE) {
            case 1:
                mainMenu.render(g2);
                break;
            case 2:
                gameGraphics.render(g2);
                break;
            default:
                if (GameData.debug) {
                    System.out.println("NOT RENDERING NOTHING: GAMESTATE = " + GameData.GAMESTATE);
                }
        }

        // disposes graphics for garbage collection
        g2.dispose();
    }
}
