import java.util.Objects;

public class ReindeersProducer extends Thread {
    int currentReindeer = 0;

    @Override
    public void run() {
        while (true) {
            try {
                Main.reindeersSemaphore.acquire();
                var y = Main.window.getWidth() - 20 - Main.window.reindeerImg.getWidth();
                var x = 50 + currentReindeer * (Main.window.reindeerImg.getHeight() - 50);
                var createdReindeer = new Reindeer(Main.window, Main.reindeersSemaphore, Main.drawSemaphore, x, y);
                Main.window.reindeers.add(createdReindeer);
                currentReindeer++;
                Main.reindeersSemaphore.release();

                Main.drawSemaphore.acquire();
                Main.window.repaint();
                Main.drawSemaphore.release();
                Thread.sleep(2500);
                if (currentReindeer == 9) {
                    String estatus;
                    do {
                        Main.santaSemaphore.acquire();
                        estatus = Main.santa.status;
                        Main.santaSemaphore.release();
                        Thread.sleep(1000);
                    } while (Objects.equals(estatus, "Renos"));
                    currentReindeer = 0;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
