package MainConfig;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageLoader {

    private Image[][] sprites;

    public ImageLoader() {
        loadImages();
    }

    public void loadImages() {
        try (InputStream spriteStream = getClass().getResourceAsStream("/SpriteSheet.png")) {
            if (spriteStream == null) {
                throw new IOException("Resource not found: /res/SpriteSheet.png");
            }
            BufferedImage spriteSheet = ImageIO.read(spriteStream);
            BufferedImage bufferedSpriteSheet = new BufferedImage(spriteSheet.getWidth(), spriteSheet.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics g = bufferedSpriteSheet.getGraphics();
            g.drawImage(spriteSheet, 0, 0, null);
            g.dispose();

            int spriteWidth = 64;
            int spriteHeight = 64;
            int spritesCols = bufferedSpriteSheet.getWidth() / spriteWidth;
            int spritesRows = bufferedSpriteSheet.getHeight() / spriteHeight;

            sprites = new Image[spritesCols][spritesRows];

            for (int y = 0; y < spritesRows; y++) {
                for (int x = 0; x < spritesCols; x++) {
                    sprites[y][x] = bufferedSpriteSheet.getSubimage(x * spriteWidth, y * spriteHeight, spriteWidth, spriteHeight);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Image[][] getSprites() {
        return sprites;
    }
}
