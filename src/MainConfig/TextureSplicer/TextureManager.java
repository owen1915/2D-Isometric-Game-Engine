package MainConfig.TextureSplicer;

import MainConfig.GameData;
import MainConfig.ImageLoader;
import World.WorldTile;
import org.w3c.dom.Text;

import javax.lang.model.type.NullType;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class TextureManager {
    private Image spriteSheet;
    private GridManager gridManager;
    private BufferedImage masks;
    private GameData gameData;
    private Image[][] faceTextures;
    private ImageLoader imageLoader;
    private Image[][] fullFaceTextures;

    public TextureManager(GameData gameData){
        //Load all the required sprite sheets
        this.imageLoader = gameData.imageLoader;

        this.gameData = gameData;
        this.gridManager = new GridManager();

        //Get the masking sprite
        Image loadedMaskingTile = imageLoader.getTextures()[WorldTile.Tile.MaskingTile.ordinal()];
        // Copy image to buffered image
        this.masks = toBufferedImage(loadedMaskingTile);

        //Get the sprites cut from the spriteSheet
        Image[] tileTextures = imageLoader.getTextures();

        //Create face textures array based of number of textures and faces
        faceTextures = new Image[tileTextures.length][Texture.values().length];

        //Loop through all tiles
        for (WorldTile.Tile tile : WorldTile.Tile.values()) {
            faceTextures[tile.ordinal()] = spliceTileIntoFaces(toBufferedImage(tileTextures[tile.ordinal()]));
        }
    }

    public BufferedImage toBufferedImage(Image img) {
        if (img == null){
            return null;
        }

        if (img instanceof BufferedImage) {
            return (BufferedImage) img;
        }

        // Create a buffered image with transparency
        BufferedImage bufferedImage = new BufferedImage(
                img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

        // Draw the image on to the buffered image
        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(img, 0, 0, null);
        bGr.dispose();

        // Return the buffered image
        return bufferedImage;
    }


    Image[] spliceTileIntoFaces(BufferedImage tileSprite){

        if (tileSprite == null){
            return null;
        }

        Image[] splicedTiles = new Image[Texture.values().length];
        Texture[] textures = Texture.values();
        for (int i = 0; i < Texture.values().length; i++){

            BufferedImage image = new BufferedImage(tileSprite.getWidth(), tileSprite.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
            for (int y = 0; y < tileSprite.getHeight(); y++) {
                for (int x = 0; x < tileSprite.getWidth(); x++) {
                    if ((masks.getRGB(x, y) == textures[i].maskRGB)) {
                        image.setRGB(x, y, tileSprite.getRGB(x, y));
                    }

                }
            }

            splicedTiles[i] = image;
        }
        return splicedTiles;
    }

    public Image[][] getFaceTextures() {
        return faceTextures;
    }
}
