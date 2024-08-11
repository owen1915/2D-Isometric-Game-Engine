package MainConfig;

import World.WorldTile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageLoader {

    private Image[] sprites;
    private Image[] textures;
    private Image[] background;
    private GameData gameData;

    public ImageLoader(GameData gameData) {
        this.gameData = gameData;
        loadImages();
    }

    /**
     * TEXTURES FOR BLOCKS ARE ONLY THE FIRST 5 COLS AND ALL ROWS UNDER THOSE FOR NOW IN SPRITESHEET
     */
    public void loadImages() {
        try (InputStream spriteStream = getClass().getResourceAsStream("/SpriteSheet.png")) {
            if (spriteStream == null) {
                throw new IOException("Resource not found: /SpriteSheet.png");
            }
            BufferedImage spriteSheet = ImageIO.read(spriteStream);
            BufferedImage bufferedSpriteSheet = new BufferedImage(spriteSheet.getWidth(), spriteSheet.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = bufferedSpriteSheet.getGraphics();
            g.drawImage(spriteSheet, 0, 0, null);
            g.dispose();

            int spriteWidth = 64;
            int spriteHeight = 64;
            int spritesCols = 11;
            int spritesRows = bufferedSpriteSheet.getHeight() / spriteHeight;

            sprites = new Image[spritesCols * spritesRows];
            background = new Image[5];

            int index = 0;
            for (int y = 0; y < spritesRows; y++) {
                for (int x = 0; x < spritesCols; x++) {
                    sprites[index] = bufferedSpriteSheet.getSubimage(x * spriteWidth, y * spriteHeight, spriteWidth, spriteHeight);
                    index++;
                }
            }

            index = 0;
            for (int y = 0; y < bufferedSpriteSheet.getHeight(); y += spriteHeight * 3) {
                background[index] = bufferedSpriteSheet.getSubimage(spriteWidth * 11, y, spriteWidth * 4, spriteHeight * 3);
                index++;
            }

            createTextures();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createTextures() {
        textures = new Image[WorldTile.Tile.values().length];
        textures[WorldTile.Tile.Grass.ordinal()] = sprites[0].getScaledInstance(gameData.tileSize, gameData.tileSize, Image.SCALE_SMOOTH);
        textures[WorldTile.Tile.Wall.ordinal()] = sprites[1].getScaledInstance(gameData.tileSize, gameData.tileSize, Image.SCALE_SMOOTH);
        textures[WorldTile.Tile.FurnaceOff.ordinal()] = sprites[2].getScaledInstance(gameData.tileSize, gameData.tileSize, Image.SCALE_SMOOTH);
        textures[WorldTile.Tile.FurnaceOn.ordinal()] = sprites[3].getScaledInstance(gameData.tileSize, gameData.tileSize, Image.SCALE_SMOOTH);
        textures[WorldTile.Tile.Dirt.ordinal()] = sprites[11].getScaledInstance(gameData.tileSize, gameData.tileSize, Image.SCALE_SMOOTH);
        textures[WorldTile.Tile.Selection.ordinal()] = sprites[9].getScaledInstance(gameData.tileSize, gameData.tileSize, Image.SCALE_SMOOTH);
    }

    public Image[] getTextures() {
        return textures;
    }

    public Image[] getBackground() {
        return background;
    }
}
