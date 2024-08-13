package MainConfig;

import Graphics.Camera.Camera;
import Graphics.IsoCordTool;
import Graphics.Panels.GamePanel;
import MainConfig.TextureSplicer.GridManager;
import MainConfig.TextureSplicer.TextureManager;
import World.World;
import World.WorldTile.Tile;
import org.w3c.dom.Text;

import java.util.Random;

public class GameData {

    // fps
    public int FPS = 60;

    // when creating debug options like printing fps, always check if debug is true
    public boolean debug = true;

    // screenWidth and screenHeight
    public int screenWidth = 1200;
    public int screenHeight = 800;

    // fullscreen
    public boolean fullscreen = false;

    public int chunkSize = 4;

    // random
    public Random random = new Random();

    // default tile screenHeight and screenWidth
    public int tileSize = 64;

    public final int maxTileSize = 512;

    public final int minTileSize = 16;

    // max rows and cols
    public World world = new World(4, this);

    public Camera camera = new Camera(this);

    public ImageLoader imageLoader = new ImageLoader(this);
    public TextureManager textureManager = new TextureManager(this);

    public int frameCounter = 0;

    /**
     * Game state indicates what we are drawing.
     * When GAMESTATE is 1 it will render the Menu, when GAMESTATE is 2 it can render the game, etc...
     */
    public int GAMESTATE = 1;

    public int selectedTile = 0;


    // game panel instance
    public GamePanel gamePanel;


    public GameData(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

}
