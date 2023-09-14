import java.util.Objects;
import java.util.concurrent.Semaphore;

public class ReindeersProducer extends Thread {
    Window window;
    Semaphore semaforoRenos;
    Semaphore semaforoInterfaz;
    int currentReno = 0;
    Santa santa;
    Semaphore semaforoSanta;

    ReindeersProducer(Window window, Semaphore semaforoRenos, Semaphore semaforoInterfaz, Santa santa, Semaphore semaforoSanta) {
        this.window = window;
        this.semaforoRenos = semaforoRenos;
        this.semaforoInterfaz = semaforoInterfaz;
        this.santa = santa;
        this.semaforoSanta = semaforoSanta;
    }

    @Override
    public void run() {
        while (true) {
            try {
                semaforoRenos.acquire();
                Reindeer nuevoReindeer = new Reindeer(window, semaforoRenos, semaforoInterfaz, window.getWidth() - 20 - window.reindeerImg.getWidth(), 50 + currentReno * (window.reindeerImg.getHeight() + 10));
                window.reindeers.add(nuevoReindeer);
                currentReno++;
                semaforoRenos.release();
                semaforoInterfaz.acquire();
                window.repaint();
                semaforoInterfaz.release();
                Thread.sleep(2500);
                if(currentReno == 9) {
                    String estatus;
                    do {
                        semaforoSanta.acquire();
                        estatus = santa.status;
                        semaforoSanta.release();
                        Thread.sleep(1000);
                    } while (Objects.equals(estatus, "Renos"));
                    currentReno = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
