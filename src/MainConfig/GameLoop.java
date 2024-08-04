package MainConfig;

public class GameLoop implements Runnable {

    public GameLoop() {
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
            if (System.currentTimeMillis() - lastTime > 1000) {
                System.out.println("FPS: " + frameCounter);
                frameCounter = 0;
                lastTime = System.currentTimeMillis();
            }
        }
    }
}
