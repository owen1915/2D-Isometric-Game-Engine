package MainConfig;

import World.World;

public class GameData {

    // fps
    public static int FPS = 60;

    // when creating debug options like printing fps, always check if debug is true
    public static boolean debug = true;

    // screenWidth and screenHeight
    public static int screenWidth = 1280;
    public static int screenHeight = 720;

    // default tile screenHeight and screenWidth
    public static int tileHeight = 64;
    public static int tileWidth = 64;

    // max rows and cols
    public static World world = new World(15, 15);

    /**
     * Game state indicates what we are drawing.
     * When GAMESTATE is 1 it will render the Menu, when GAMESTATE is 2 it can render the game, etc...
     */
    public static int GAMESTATE = 1;


}
