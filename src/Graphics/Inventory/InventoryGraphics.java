package Graphics.Inventory;

import MainConfig.GameData;

import java.awt.*;
import java.awt.image.BufferedImage;

public class InventoryGraphics {

    private int rectSize = 64;
    private int hotbarSize;
    private int startX, startY;
    private GameData gameData;
    private Inventory inventory;
    private BufferedImage hotbar;

    public InventoryGraphics(GameData gameData, Inventory inventory) {
        this.gameData = gameData;
        this.hotbarSize = gameData.hotbarSize;
        this.startX = gameData.screenWidth/2 - (rectSize * hotbarSize/2);
        this.startY = gameData.screenHeight - gameData.screenHeight/4;
        this.inventory = inventory;

        createHotbarImage(gameData.selectedSlot);
    }

    public void createHotbarImage(int selectedSlot) {
        gameData.selectedSlot = selectedSlot;
        hotbar = new BufferedImage((rectSize * hotbarSize + (hotbarSize - 1) * 3), rectSize + 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = (Graphics2D) hotbar.getGraphics();
        g.setStroke(new BasicStroke(3));
        g.drawRect(rectSize, 0, rectSize, rectSize);
        for (int i = 0; i < hotbarSize; i++) {
            g.setColor(Color.black);
            g.drawRect(rectSize * i, 0, rectSize, rectSize);
            if (i == selectedSlot) {
                g.setColor(new Color(140, 140, 140, 200));
                g.fillRect(rectSize * i + 1, 1, rectSize - 1, rectSize - 1);
            } else {
                g.setColor(new Color(60, 60, 60, 200));
                g.fillRect(rectSize * i + 1, 1, rectSize - 1, rectSize - 1);
            }
            if (inventory.getInventory()[i] != null) {
                Item item = inventory.getInventory()[i];
                g.drawImage(item.getItemImage(), rectSize * i + 1, 1, null);
                g.setColor(Color.white);
                g.drawString(Integer.toString(item.getAmntOf()), rectSize * i + 4, 62);
            }
        }
        g.dispose();
    }

    public void render(Graphics2D g2) {
        g2.drawImage(hotbar, startX, startY, null);
    }
}
