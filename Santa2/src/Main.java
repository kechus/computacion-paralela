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
        ElfProducer elfProducer = new ElfProducer();
        ReindeerProducer reindeerProducer = new ReindeerProducer();

        window.santa = santa;
        window.elfProducer = elfProducer;
        window.reindeerProducer = reindeerProducer;
        window.init();
    }
}