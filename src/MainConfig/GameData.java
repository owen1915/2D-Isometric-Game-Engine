package MainConfig;

import Graphics.Camera.Camera;
import Graphics.Panels.GamePanel;
import World.World;

public class GameData {

    // fps
    public int FPS = 60;

    // when creating debug options like printing fps, always check if debug is true
    public boolean debug = true;

    // screenWidth and screenHeight
    public int screenWidth = 1280;
    public int screenHeight = 720;

    // default tile screenHeight and screenWidth
    public int tileHeight = 64;
    public int tileWidth = 64;;

    // max rows and cols
    public World world = new World(100, 100);

    public Camera camera = new Camera(this);

    public ImageLoader imageLoader = new ImageLoader();

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
