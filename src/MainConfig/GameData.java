package MainConfig;

public class GameData {

    // fps
    public static int FPS = 60;

    // when creating debug options like printing fps, always check if debug is true
    public static boolean debug = true;

    // width and height
    public static int width = 1280;
    public static int height = 720;

    /**
     * Game state indicates what we are drawing.
     * When GAMESTATE is 1 it will render the Menu, when GAMESTATE is 2 it can render the game, etc...
     */
    public static int GAMESTATE = 1;
}
