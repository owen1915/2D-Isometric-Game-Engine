package MainConfig;

import Graphics.Camera.Camera;
import Graphics.Inventory.Inventory;
import Graphics.IsoCordTool;
import Graphics.Panels.GamePanel;
import World.BlockManipulator;
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
    public int screenWidth = 1920;
    public int screenHeight = 1080;

    // fullscreen
    public boolean fullscreen = false;

    //chunk size
    public int chunkSize = 4;

    // random
    public Random random = new Random();

    public int worldSize = 32;

    // default tile screenHeight and screenWidth
    public int tileSize = 64;
    //public IsoCordTool isoCordTool = new IsoCordTool(this);
    public Camera camera = new Camera(this);

    public final int maxTileSize = 512;

    public final int minTileSize = 16;

    // max rows and cols
    public World world = new World(worldSize, this);

    // block manipulator
    public BlockManipulator blockManipulator = new BlockManipulator(this);

    public ImageLoader imageLoader = new ImageLoader(this);
    public TextureManager textureManager = new TextureManager(imageLoader);

    public int frameCounter = 0;

    public int hotbarSize = 9;
    public int selectedSlot = -1;
    public Inventory inventory = new Inventory(this);

    /**
     * Game state indicates what we are drawing.
     * When GAMESTATE is 1 it will render the Menu, when GAMESTATE is 2 it can render the game, etc...
     */
    public int GAMESTATE = 1;


    // game panel instance
    public GamePanel gamePanel;


    public GameData(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

}
