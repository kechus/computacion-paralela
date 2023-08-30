import java.util.Random;
import java.util.concurrent.BlockingQueue;

class Producer implements Runnable {
    private final BlockingQueue<Integer> buffer;
    private final Random random;

    public Producer(BlockingQueue<Integer> buffer) {
        this.buffer = buffer;
        random = new Random();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Tarea.itemsToProduce = random.nextInt(10, 30);
                System.out.println("\n Going to produce "+Tarea.itemsToProduce);
                for (int i = 0; i < Tarea.itemsToProduce; i++) {
                    buffer.put(i);
                    System.out.println("Produced: " + i);
                }
                Thread.sleep(5000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}