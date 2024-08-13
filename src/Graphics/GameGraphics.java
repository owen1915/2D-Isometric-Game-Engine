package Graphics;

import Graphics.Panels.GamePanel;
import Listeners.MouseListener;
import MainConfig.GameData;
import MainConfig.ImageLoader;
import World.WorldTile;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameGraphics {

    private IsoCordTool isoCordTool;
    private GameData gameData;
    private ImageLoader imageLoader;
    private BufferedImage bufferedImage;
    private Graphics2D bufferedGraphics;
    private Chunk[] chunks;

    public GameGraphics(GameData gameData) {
        this.gameData = gameData;
        imageLoader = gameData.imageLoader;
        isoCordTool = gameData.gamePanel.getIsoCordTool();

        bufferedImage = new BufferedImage(gameData.screenWidth, gameData.screenHeight, BufferedImage.TYPE_INT_ARGB);
        bufferedGraphics = (Graphics2D) bufferedImage.getGraphics();

        createChunks();
    }

    public void createChunks() {
        int amntOfChunks = (gameData.world.getWorldXSize() * gameData.world.getWorldYSize())/gameData.chunkSize;
        chunks = new Chunk[amntOfChunks];
        System.out.println("Creating " + amntOfChunks + " chunks");
        int i = 0;
        for (int x = 0; x < gameData.world.getWorldXSize(); x += gameData.chunkSize/2) {
            for (int y = 0; y < gameData.world.getWorldYSize(); y += gameData.chunkSize/2) {
                chunks[i++] = new Chunk(x, y, gameData);
                System.out.println("Creating chunk " + i + " of " + amntOfChunks);
            }
        }
    }

    public void render(Graphics2D g2) {
        bufferedGraphics.clearRect(0, 0, gameData.screenWidth, gameData.screenHeight);
        //.bufferedGraphics.drawImage(imageLoader.getBackground()[0], 0, 0, gameData.screenWidth, gameData.screenHeight, null);

        // iterate over the (current) chunks that holds the tiles
        /*for (int i = 0; i < chunks.length; i++) {
            bufferedGraphics.drawImage(chunks[i].getChunkImage(), i * (gameData.chunkSize/2 * gameData.tileSize/2), i * (gameData.chunkSize/2 * gameData.tileSize/4), null);
        }*/

        int baseX = gameData.camera.getxOffset();
        int baseY = gameData.camera.getyOffset();

        bufferedGraphics.drawImage(chunks[0].getChunkImage(), baseX, baseY, null);
        bufferedGraphics.drawImage(chunks[1].getChunkImage(), baseX - 64, baseY + 32, null);
        bufferedGraphics.drawImage(chunks[2].getChunkImage(), baseX + 64, baseY + 32, null);
        bufferedGraphics.drawImage(chunks[3].getChunkImage(), baseX, baseY + 64, null);

        //Get the mouse world cords from mouse motion listener
        int[] mouseWorldCor = gameData.gamePanel.getMouseMotionListener().getMouseWorldCords();

        //Draw the selection tile
        if (mouseWorldCor[0] - 1 < 0 || mouseWorldCor[1] - 1  < 0 || mouseWorldCor[0] - 1 > gameData.world.getWorldXSize()
                || mouseWorldCor[1] - 1  > gameData.world.getWorldYSize()) {
            bufferedGraphics.drawImage(imageLoader.getTextures()[WorldTile.Tile.RedSelection.ordinal()], isoCordTool.getXIso(mouseWorldCor[0] - 1, mouseWorldCor[1] - 1), isoCordTool.getYIso(mouseWorldCor[0] - 1, mouseWorldCor[1] - 1), null);
        } else {
            bufferedGraphics.drawImage(imageLoader.getTextures()[WorldTile.Tile.Selection.ordinal()], isoCordTool.getXIso(mouseWorldCor[0] - 1, mouseWorldCor[1] - 1), isoCordTool.getYIso(mouseWorldCor[0] - 1, mouseWorldCor[1] - 1), null);
        }

        //draw to panel
        g2.drawImage(bufferedImage, 0, 0, null);
    }

    private boolean withinBounds(int x, int y) {
        // Convert isometric coordinates to screen coordinates
        int screenX = isoCordTool.getXIso(x, y);
        int screenY = isoCordTool.getYIso(x, y);

        return screenX <= gameData.screenWidth + gameData.tileSize && screenX >= -gameData.tileSize && screenY <= gameData.screenHeight + gameData.tileSize && screenY >= -gameData.tileSize;
    }
}
