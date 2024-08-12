package Graphics.Panels;

import Graphics.UI.OButton;
import MainConfig.GameData;

import java.awt.*;
import java.awt.image.BufferedImage;

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

    private GameData gameData;

    // number for random background img
    private int randomIndex;

    public MainMenu(GameData gameData) {
        this.gameData = gameData;

        Runnable newGame = () -> {
            gameData.GAMESTATE = 2;
        };

        Runnable exitGame = () -> {
            gameData.GAMESTATE = 0;
        };

        buttons = new OButton[2];
        buttons[0] = new OButton(gameData, "NEW GAME", 0, 0, newGame);
        buttons[1] = new OButton(gameData, "QUIT", 0, 1, exitGame);

        randomIndex = gameData.random.nextInt(1);
    }

    /**
     * Renders the main menu
     */
    public void render(Graphics2D panelGraphics) {
        BufferedImage bufferedImage = new BufferedImage(gameData.screenWidth, gameData.screenHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();

        // draw background image
        Image scaledImage = gameData.imageLoader.getBackground()[randomIndex].getScaledInstance(gameData.screenWidth, gameData.screenHeight, Image.SCALE_DEFAULT);
        g2.drawImage(scaledImage, 0, 0,null);

        //set color of text
        g2.setColor(Color.black);
        Font font = new Font("DIALOUGE", Font.BOLD, 60);
        g2.setFont(font);
        FontMetrics fm = g2.getFontMetrics();

        //draw buttons
        //change font for buttons
        font = new Font("DIALOUGE", Font.BOLD, 40);
        g2.setFont(font);
        for (OButton button : buttons) {
            button.render(g2);
        }

        panelGraphics.drawImage(bufferedImage, 0, 0, null);
    }
}
