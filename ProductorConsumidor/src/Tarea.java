import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Tarea {
    Tarea() {
        var namer = new Namer();
        var random = new Random();
        var numConsumers = namer.namesList.size();
        var itemsToProduce = random.nextInt(10, 30);
        System.out.println("Going to produce " + itemsToProduce);
        BlockingQueue<Integer> buffer = new ArrayBlockingQueue<>(itemsToProduce);

        var producerThread = new Thread(new Producer(buffer, itemsToProduce));
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
