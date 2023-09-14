import javax.swing.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class Main {

    public static Semaphore elfSemaphore = new Semaphore(1, true);
    public static Semaphore reindeersSemaphore = new Semaphore(1, true);
    public static Semaphore paintSemaphore = new Semaphore(1, true);
    public static ArrayBlockingQueue<Elf> elfs;
    public static ArrayBlockingQueue<Reindeer> reindeers;
    public static JFrame window;

    public static void main(String[] args) {
        elfs = new ArrayBlockingQueue<>(10);
        reindeers = new ArrayBlockingQueue<>(10);
        window = new Window();
        var reindeersProducer = new ReindeersProducer();
        reindeersProducer.start();
        window.repaint();
    }
}