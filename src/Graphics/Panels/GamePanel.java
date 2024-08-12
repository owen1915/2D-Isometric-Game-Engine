package Graphics.Panels;

import Listeners.KeyListener;
import Listeners.MouseListener;
import Listeners.MouseMotionListener;
import Listeners.MouseWheelListener;
import MainConfig.GameData;
import Graphics.GameGraphics;

import javax.swing.*;
import java.awt.*;
import Graphics.IsoCordTool;

public class GamePanel extends JPanel {

    // creating classes
    private GameData gameData;
    private MainMenu mainMenu;
    private GameGraphics gameGraphics;
    private IsoCordTool isoCordTool;
    private KeyListener keyListener;
    private MouseListener mouseListener;

    private MouseMotionListener mouseMotionListener;
    private MouseWheelListener mouseWheelListener;

    // bool for adding listeners once graphics is loaded (so when the main menu button is hit, it doesnt instantly draw a block)
    private boolean listenersAdded = false;

    public GamePanel() {
        gameData = new GameData(this);

        setPreferredSize(new Dimension(gameData.screenWidth, gameData.screenHeight));
        setVisible(true);
        setFocusable(true);
        requestFocusInWindow();

        this.keyListener = new KeyListener(gameData);
        this.mouseListener = new MouseListener(gameData);
        this.mouseMotionListener = new MouseMotionListener(gameData);
        this.mouseWheelListener = new MouseWheelListener(gameData);

        isoCordTool = new IsoCordTool(gameData);
        mainMenu = new MainMenu(gameData);
        gameGraphics = new GameGraphics(gameData);;
    }

    private void addListeners() {
        addMouseWheelListener(mouseWheelListener);
        addKeyListener(keyListener);
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseMotionListener);
        listenersAdded = true;
    }

    private void removeListeners() {
        removeMouseWheelListener(mouseWheelListener);
        removeKeyListener(keyListener);
        removeMouseListener(mouseListener);
        removeMouseMotionListener(mouseMotionListener);
        listenersAdded = false;
    }

    /**
     * Update method
     */
    public void update() {
        switch(gameData.GAMESTATE) {
            case 0:
                System.exit(0);
                break;
            case 1:
                if (listenersAdded) {
                    removeListeners();
                }
                break;
            case 2:
                if (!listenersAdded) {
                    addListeners();
                }
                System.out.println("OFFSET: " + (gameData.camera.getxOffset()));
                System.out.println(gameData.world.getWorldXSize() * gameData.tileSize/2);
                keyListener.update();
                break;
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

    public MouseListener getMouseListener(){
        return this.mouseListener;
    }

    public MouseMotionListener getMouseMotionListener(){
        return this.mouseMotionListener;
    }
}
