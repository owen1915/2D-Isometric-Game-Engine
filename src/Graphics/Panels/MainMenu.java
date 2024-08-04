package Graphics.Panels;

import MainConfig.GameData;

import java.awt.*;

/**
 * MainMenu class that is drawn ONTO the GamePanel based on if the GAMESTATE = 1,
 * because I believe this is the most effective way of handling things instead of creating
 * a whole other panel and adding frames to that
 */
public class MainMenu {

    // String array to hold strings to render
    private String[] strArr;

    public MainMenu() {
        strArr = new String[1];
        strArr[0] = "BOTANICAL BLITZ";
    }

    /**
     * Renders the main menu
     */
    public void render(Graphics2D g2) {
        Font font = new Font("DIALOUGE", Font.BOLD, 40);
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();

        int stringWidth = fm.stringWidth(strArr[0]);

        g2.drawString(strArr[0], GameData.width/2 - stringWidth /2, 60);
    }
}
