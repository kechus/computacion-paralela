import javax.swing.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Semaphore;

public class Main {
    public static ArrayBlockingQueue<Car> rightQueue;
    public static ArrayBlockingQueue<Car> leftQueue;
    public static Semaphore drawSemaphore;
    public static Semaphore laneSemaphore;
    public static JFrame window;

    public static void main(String[] args) {
        rightQueue = new ArrayBlockingQueue<>(10);
        leftQueue = new ArrayBlockingQueue<>(10);
        drawSemaphore = new Semaphore(1, true);
        laneSemaphore = new Semaphore(1);
        var lane = new Thread( new Lane(Main.rightQueue,true) );
        var lane2 = new Thread( new Lane(Main.leftQueue,false) );
        lane.start();
        lane2.start();
        window = new Window();
    }
}