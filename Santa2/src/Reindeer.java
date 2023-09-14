import java.util.concurrent.Semaphore;

public class Reindeer extends Thread{
    Window window;
    Semaphore semaforoRenos;
    Semaphore semaforoInterfaz;
    int x;
    int y;
    int heigth;
    Reindeer(Window window, Semaphore semaforoRenos, Semaphore semaforoInterfaz, int x, int y){
        this.window = window;
        this.semaforoRenos = semaforoRenos;
        this.semaforoInterfaz = semaforoInterfaz;
        this.x = x;
        this.y = y;
        this.heigth = window.getHeight();
    }

    @Override
    public void run() {
        try {
            while (x >= 0) {
                x -= 20;
                semaforoInterfaz.acquire();
                window.repaint();
                semaforoInterfaz.release();
                Thread.sleep(300);
            }
            semaforoRenos.acquire();
            window.reindeers.remove(this);
            semaforoRenos.release();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
