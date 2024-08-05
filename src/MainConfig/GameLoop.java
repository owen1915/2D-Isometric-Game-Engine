package MainConfig;

import Graphics.Panels.GamePanel;

public class GameLoop implements Runnable {

    private GamePanel gamePanel;

    public GameLoop(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        new Thread(this).start();
    }

    @Override
    public void run() {
        int runTime = 1000 / GameData.FPS;
        int frameCounter = 0;
        long lastTime = System.currentTimeMillis();

        while (true) {
            long startTime = System.currentTimeMillis();

            //render and update game here
            gamePanel.update();


            long elaspedTime = System.currentTimeMillis() - startTime;
            long sleep = runTime - elaspedTime;


            if (sleep > 0) {
                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            frameCounter++;
            if (GameData.debug && System.currentTimeMillis() - lastTime > 1000) {
                // debug for printing fps
                System.out.println("FPS: " + frameCounter);
                frameCounter = 0;
                lastTime = System.currentTimeMillis();
            }
        }
    }
}
