import MainConfig.GameLoop;
import Visual.GamePanel;

import javax.swing.*;

public class Frame extends JFrame {

    public Frame() {
        GamePanel gamePanel = new GamePanel();

        add(gamePanel);
        pack();
        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setFocusable(false);
        setVisible(true);

        new GameLoop();
    }

    public static void main(String[] args) {
        new Frame();
    }
}
