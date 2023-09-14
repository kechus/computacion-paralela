import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Lane implements Runnable {
    private final BlockingQueue<Car> buffer;

    private final boolean goesRight;
    private final Random random = new Random();

    public Lane(BlockingQueue<Car> buffer, boolean goesRight) {
        this.buffer = buffer;
        this.goesRight = goesRight;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Main.laneSemaphore.acquire();
                while (!buffer.isEmpty()) {
                    for (var car : buffer) {
                        var th = new Thread(car);
                        th.start();
                    }
                    Thread.sleep(500);
                }
                Main.laneSemaphore.release();
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
