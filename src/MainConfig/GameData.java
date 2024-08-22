package MainConfig;

import Graphics.Camera.Camera;
import Graphics.GameGraphics;
import Graphics.Inventory.Inventory;
import Graphics.Panels.GamePanel;
import World.BlockManipulator;
import World.World;

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
    public int chunkSize = 16;
    //random
    public Random random = new Random();
    //default worldsize
    public int worldSize = 64;
    // default tilesize
    public int tileSize = 64;
    //camera and world
    public Camera camera;
    public World world;
    //block manipulator
    public BlockManipulator blockManipulator = new BlockManipulator(this);
    //imageloader
    public ImageLoader imageLoader = new ImageLoader(this);
    //framecounter
    public int frameCounter = 0;
    //inventory things
    public int hotbarSize = 9;
    public int selectedSlot = -1;
    public Inventory inventory;
    /**
     * Game state indicates what we are drawing.
     * When GAMESTATE is 1 it will render the Menu, when GAMESTATE is 2 it can render the game, etc...
     */
    public int GAMESTATE = 1;
    // game panel instance
    public GamePanel gamePanel;
    // game graphics
    public GameGraphics gameGraphics;
    //gamepanel created
    public GameData(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    //whenever a newgame is made
    public void newGame() {
        camera = new Camera(this);
        world = new World(worldSize, this);
        inventory = new Inventory(this);
        gameGraphics = new GameGraphics(this);
        gamePanel.setGameGraphics(gameGraphics);
    }
    //whenever loading a game
    public void loadGame() {
        
    }
}
