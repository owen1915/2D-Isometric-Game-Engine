package Graphics.UI;

import Graphics.Panels.GamePanel;
import MainConfig.GameData;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * custom button class because JButtons are terrible
 */
public class OButton {
    private GameData gameData;
    private String str;
    private int x, y;
    private int buffer = 10;
    private Rectangle rect;
    private Color color = Color.black;
    private Runnable onClick;

    public OButton(GameData gameData, String str, int x, int y, Runnable onClick) {
        this.str = str;
        this.x = x;
        this.y = y;
        this.onClick = onClick;

        this.gameData = gameData;

        //add mouse listener to said button
        gameData.gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (gameData.GAMESTATE == 1 && rect != null && rect.contains(e.getX(), e.getY())) {
                    if (onClick != null) {
                        onClick.run();
                    }
                    if (gameData.debug) {
                        System.out.println("BUTTON CLICKED");
                    }
                }
            }
        });
        gameData.gamePanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (rect != null && rect.contains(e.getX(), e.getY())) {
                    //color text is gonna change to when mouse is hovering
                    color = Color.gray;
                } else {
                    color = Color.black;
                }
            }
        });
    }

    public void render(Graphics2D g2) {
        g2.setStroke(new BasicStroke(5));
        FontMetrics fm = g2.getFontMetrics();

        int stringWidth = fm.stringWidth(str);
        int stringHeight = fm.getHeight() + buffer;

        int middleX = (gameData.screenWidth / 2 - stringWidth / 2);

        int rectX = 0;
        int rectY = 0;
        if (y == 0) {
            rectY = gameData.screenHeight/4;
        } else if (y == 1) {
            rectY = gameData.screenHeight - gameData.screenHeight/4;
        }
        if (x == 0) {
            rectX = middleX - buffer/2;
        }

        rect = new Rectangle(rectX, rectY, stringWidth + buffer, stringHeight);
        g2.draw(rect);
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRect(rect.x + 1, rect.y + 1, rect.width - 1, rect.height - 1);
        g2.setColor(color);
        g2.drawString(str, middleX, rectY + (stringHeight/2 + stringHeight/3) - buffer/2);

        g2.setColor(Color.BLACK);
    }
}
