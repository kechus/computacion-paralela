import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Lane implements Runnable {
    private final BlockingQueue<Circulo> buffer;

    private final boolean goesRight;

    public Lane(BlockingQueue<Circulo> buffer, boolean goesRight) {
        this.buffer = buffer;
        this.goesRight = goesRight;
    }

    @Override
    public void run() {
        try {
            while (true) {
                var produced = new Random().nextInt(0, 100) > 60;
//                System.out.println("hola desde " + (goesRight ? "derecha" : "izquierda"));

                if (produced) {
                    Main.laneSemaphore.acquire();
                    System.out.println("creando primer circulo a la " + (goesRight ? "derecha" : "izquierda"));
                    var circl = new Circulo((goesRight) ? 10 : 600, goesRight);
                    var t = new Thread(circl);
                    t.start();
                    buffer.put(circl);

                    while (!buffer.isEmpty()) {
                        System.out.println("novacio");
                        var prod = new Random().nextInt(0, 100) > 80;
                        if (prod) {
                            System.out.println("creando nuevo circulo a la " + (goesRight ? "derecha" : "izquierda"));
                            var circle = new Circulo((goesRight) ? 10 : 600, goesRight);
                            var th = new Thread(circle);
                            th.start();
                            buffer.put(circle);
                        }
                        Thread.sleep(1000);
                    }
                }

                Thread.sleep(2000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
