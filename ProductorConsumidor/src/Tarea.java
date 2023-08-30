import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Tarea {
    public static int itemsToProduce = new Random().nextInt(10,30);
    Tarea() {
        var namer = new Namer();
        var numConsumers = namer.namesList.size();
        BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(itemsToProduce);
        var producerThread = new Thread(new Producer(buffer));

        var consumers = new ArrayList<Thread>();
        for (int i = 0; i < numConsumers; i++) {
            var consumerThread = new Thread(new Consumer(buffer));
            consumerThread.setName(namer.getRandomName());
            consumers.add(consumerThread);
        }

        producerThread.start();
        for (var consumer : consumers) {
            consumer.start();
        }
    }
}
