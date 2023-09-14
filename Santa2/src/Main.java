import java.util.concurrent.Semaphore;

public class Main {
    public static Semaphore elfsSemaphore = new Semaphore(1, true);
    public static Semaphore drawSemaphore = new Semaphore(1, true);
    public static Semaphore reindeersSemaphore = new Semaphore(1, true);
    public static Semaphore elfsGroupingSemaphore = new Semaphore(1, true);
    public static Semaphore santaSemaphore = new Semaphore(1, true);
    public static Window window = new Window();
    public static Santa santa = new Santa();

    public static void main(String[] args) {
        ElfsProducer elfsProducer = new ElfsProducer();
        ReindeersProducer reindeersProducer = new ReindeersProducer();

        window.santa = santa;
        window.elfsProducer = elfsProducer;
        window.reindeersProducer = reindeersProducer;
        window.init();
    }
}