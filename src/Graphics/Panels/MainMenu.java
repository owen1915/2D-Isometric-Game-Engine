package Graphics.Panels;

import Graphics.UI.OButton;
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
    // OButton array to hold buttons to render
    private OButton[] buttons;

    private GamePanel gamePanel;

    public MainMenu(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        strArr = new String[1];
        strArr[0] = "BOTANICAL BLITZ";

        buttons = new OButton[1];
        buttons[0] = new OButton(gamePanel, "NEW GAME", 0, 200);
    }

    /**
     * Renders the main menu
     */
    public void render(Graphics2D g2) {
        //set color of text
        g2.setColor(Color.black);
        Font font = new Font("DIALOUGE", Font.BOLD, 60);
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();

        // title string
        int stringWidth = fm.stringWidth(strArr[0]);
        g2.drawString(strArr[0], GameData.screenWidth /2 - stringWidth/2, 60);

        //draw buttons
        //change font for buttons
        font = new Font("DIALOUGE", Font.BOLD, 40);
        g2.setFont(font);
        for (OButton button : buttons) {
            button.render(g2);
        }
    }
}
