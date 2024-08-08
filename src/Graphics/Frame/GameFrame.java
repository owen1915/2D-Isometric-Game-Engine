package Graphics.Frame;

import MainConfig.GameData;
import MainConfig.GameLoop;
import Graphics.Panels.GamePanel;

import javax.swing.*;

public class GameFrame extends JFrame {

    private GameData gameData;

    public GameFrame() {
        GamePanel gamePanel = new GamePanel();
        this.gameData = gamePanel.getGameData();

        add(gamePanel);
        pack();
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setFocusable(false);
        setVisible(true);

        new GameLoop(gameData);
    }
}
