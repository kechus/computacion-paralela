import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class CarProducer extends Thread {
    private final BlockingQueue<Car> buffer;

    private final boolean goesRight;
    private final Random random = new Random();

    public CarProducer(BlockingQueue<Car> buffer, boolean goesRight) {
        this.buffer = buffer;
        this.goesRight = goesRight;
    }

    private boolean shouldProduceCar(int chance) {
        return random.nextInt(0, 100) > chance;
    }

    private void createAndAddCar() throws InterruptedException {
        System.out.println("Creating a new car to the " + (goesRight ? "right" : "left"));
        var offset = (buffer.size() +1) * 50;
        var initialX = goesRight ? 10 - offset : 600 + offset;
        var car = new Car(initialX, goesRight);
        buffer.put(car);
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (shouldProduceCar(65)) {
                    createAndAddCar();
                }
                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
