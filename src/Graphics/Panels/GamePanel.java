package Graphics.Panels;

import Graphics.Inventory.InventoryGraphics;
import Listeners.KeyListener;
import Listeners.MouseListener;
import Listeners.MouseMotionListener;
import Listeners.MouseWheelListener;
import MainConfig.GameData;
import Graphics.GameGraphics;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

import Graphics.IsoCordTool;

public class GamePanel extends JPanel {

    // creating classes
    private GameData gameData;
    private MainMenu mainMenu;
    private GameGraphics gameGraphics;
    private KeyListener keyListener;
    private MouseListener mouseListener;

    private MouseMotionListener mouseMotionListener;
    private MouseWheelListener mouseWheelListener;

    // bool for adding listeners once graphics is loaded (so when the main menu button is hit, it doesnt instantly draw a block)
    private boolean listenersAdded = false;

    private ArrayList<Long> renderTime = new ArrayList<>();

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

        mainMenu = new MainMenu(gameData);
        gameGraphics = new GameGraphics(gameData);
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

        long currentTime = System.currentTimeMillis();
        long timeTook = 0;
        switch (gameData.GAMESTATE) {
            case 1:
                mainMenu.render(g2);
                timeTook = System.currentTimeMillis() - currentTime;
                break;
            case 2:
                gameGraphics.render(g2);
                timeTook = System.currentTimeMillis() - currentTime;
                break;
        }

        if (gameData.debug) {
            if (gameData.frameCounter % 60 != 0) {
                renderTime.add(timeTook);
            } else {
                if (!renderTime.isEmpty()) {
                    long average = 0;
                    long max = renderTime.getFirst();;
                    long min = renderTime.getFirst();
                    for (Long l : renderTime) {
                        if (l > max) max = l;
                        if (l < min) min = l;
                        average += l;
                    }

                    average /= renderTime.size();
                    //System.out.println("Min Render Time " + min + "ms\nMax Render Time " + max + "ms");
                    System.out.println("Render time " + average + "ms");
                    int count = gameGraphics.getCount();
                    if (count != 0) {
                        System.out.println("Rendered " + count + " chunks");
                    }
                    System.out.println("-------------------------------------------");
                    renderTime.clear();
                    gameData.frameCounter = 0;
                }
            }
        }

        // disposes graphics for garbage collection
        g2.dispose();
    }

    public GameData getGameData() {
        return gameData;
    }

    public MouseMotionListener getMouseMotionListener(){
        return this.mouseMotionListener;
    }
}
