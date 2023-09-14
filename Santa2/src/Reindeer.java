import java.util.concurrent.Semaphore;

public class Reindeer extends Thread{
    int x;
    int y;
    int heigth;
    Reindeer(int x, int y){
        this.x = x;
        this.y = y;
        this.heigth = Main.window.getHeight();
    }

    @Override
    public void run() {
        try {
            while (x >= 0) {
                x -= 20;
                Main.drawSemaphore.acquire();
                Main.window.repaint();
                Main.drawSemaphore.release();
                Thread.sleep(220);
            }
            Main.reindeersSemaphore.acquire();
            Main.window.reindeers.remove(this);
            Main.reindeersSemaphore.release();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
