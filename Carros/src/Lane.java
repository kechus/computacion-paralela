import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Lane implements Runnable {
    private final BlockingQueue<Car> buffer;

    private final boolean goesRight;

    public Lane(BlockingQueue<Car> buffer, boolean goesRight) {
        this.buffer = buffer;
        this.goesRight = goesRight;
    }
    private boolean shouldProduceCar(int chance) {
        return new Random().nextInt(0, 100) > chance;
    }

    private void createAndAddCar() throws InterruptedException {
        System.out.println("Creating a new car to the " + (goesRight ? "right" : "left"));
        int initialX = goesRight ? 10 : 600;
        var car = new Car(initialX, goesRight);
        var thread = new Thread(car);
        thread.start();
        buffer.put(car);
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (shouldProduceCar(60)) {
                    createAndAddCar();
                    Main.laneSemaphore.acquire();
                    while (!buffer.isEmpty()) {
                        if (shouldProduceCar(80)) {
                            createAndAddCar();
                        }
                        Thread.sleep(1000);
                    }
                    Main.laneSemaphore.release();
                }
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
