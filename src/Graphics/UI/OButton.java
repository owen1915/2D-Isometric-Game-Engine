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
    //TO BE FINISHED LATER

    private String str;
    private int x, y;
    private int buffer = 10;
    private Rectangle rect;
    private Color color = Color.black;

    public OButton(GamePanel gamePanel, String str, int x, int y) {
        this.str = str;
        this.x = x;
        this.y = y;

        //add mouse listener to said button
        gamePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (rect != null && rect.contains(e.getX(), e.getY())) {
                    GameData.GAMESTATE = 2;
                    if (GameData.debug) {
                        System.out.println("BUTTON CLICKED");
                    }
                }
            }
        });
        gamePanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (rect != null && rect.contains(e.getX(), e.getY())) {
                    // color text is gonna change to when mouse is hovering
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

        int middleX = (GameData.width / 2 - stringWidth / 2);

        int rectX = 0;
        int rectY = y;
        if (x == 0) {
            rectX = middleX - buffer/2;
        }

        rect = new Rectangle(rectX, rectY, stringWidth + buffer, stringHeight);
        g2.draw(rect);
        g2.setColor(Color.LIGHT_GRAY);
        g2.fill(rect);
        g2.setColor(color);
        g2.drawString(str, middleX, y + (stringHeight/2 + stringHeight/3) - buffer/2);
    }
}
