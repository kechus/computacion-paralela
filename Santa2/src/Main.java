import java.util.concurrent.Semaphore;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        Semaphore semaforoElfos = new Semaphore(1,true);
        Semaphore semaforoRepaint = new Semaphore(1,true);
        Semaphore semaforoRenos = new Semaphore(1,true);
        Semaphore semaforoAgrupacionElfos = new Semaphore(1,true);
        Semaphore semaforoSanta = new Semaphore(1,true);


        Window window = new Window();

        ElfsProducer elfsProducer = new ElfsProducer(window, semaforoElfos, semaforoRepaint, semaforoAgrupacionElfos);

        Santa santa = new Santa(window, semaforoRepaint, semaforoAgrupacionElfos, semaforoSanta, semaforoRenos);

        ReindeersProducer reindeersProducer = new ReindeersProducer(window,semaforoRenos,semaforoRepaint,santa,semaforoSanta);

        window.santa = santa;
        window.elfsProducer = elfsProducer;
        window.reindeersProducer = reindeersProducer;

        window.init();

    }
}