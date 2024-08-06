package Graphics.Panels;

import MainConfig.GameData;
import Graphics.GameGraphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel {

    // creating classes
    private MainMenu mainMenu;
    private GameGraphics gameGraphics;

    public GamePanel() {
        setPreferredSize(new Dimension(GameData.screenWidth, GameData.screenHeight));
        setVisible(true);
        setFocusable(true);
        requestFocusInWindow();

        mainMenu = new MainMenu(this);
        gameGraphics = new GameGraphics();

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_W) {
                    GameData.camera.setyOffset(GameData.camera.getyOffset() - 16);
                }
                if (e.getKeyCode() == KeyEvent.VK_S) {
                    GameData.camera.setyOffset(GameData.camera.getyOffset() + 16);
                }
                if (e.getKeyCode() == KeyEvent.VK_A) {
                    GameData.camera.setxOffset(GameData.camera.getxOffset() - 16);
                }
                if (e.getKeyCode() == KeyEvent.VK_D) {
                    GameData.camera.setxOffset(GameData.camera.getxOffset() + 16);
                }
            }
        });
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
