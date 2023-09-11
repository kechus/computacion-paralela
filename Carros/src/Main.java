import javax.swing.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class Main {
    public static ArrayBlockingQueue<Circulo> buffer;
    public static ArrayBlockingQueue<Circulo> leftBuffer;
    public static Semaphore drawSemaphore;
    public static Semaphore laneSemaphore;
    public static JFrame window;
    public static boolean estaPrendido = false;
    public static boolean vaIzq = false;

    public static void main(String[] args) {
        buffer = new ArrayBlockingQueue<>(10);
        leftBuffer = new ArrayBlockingQueue<>(10);
        drawSemaphore = new Semaphore(1, true);
        laneSemaphore = new Semaphore(1);
        window = new Window();

        var th = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1500);
                    if (Main.buffer.isEmpty() && Main.leftBuffer.isEmpty() && Main.laneSemaphore.availablePermits() == 0) {
                        System.out.println("vacio");
                        Main.estaPrendido = false;
                        Main.laneSemaphore.release();
                    }
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        th.start();
    }
}