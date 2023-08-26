import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static int[][] results = new int[2][];
    private static final int len = 9;

    public static void main(String[] args) {
        var initial = new int[len];
        for (int i = 0; i < len; i++) {
            initial[i] = ThreadLocalRandom.current().nextInt(1, 101);
        }
        printArrayAsMatrix(initial);

        var mid = initial.length / 2;
        var split = new int[2][];
        split[0] = Arrays.copyOfRange(initial, 0, mid);
        split[1] = Arrays.copyOfRange(initial, mid, initial.length);

        var multiplierThreads = new ArrayList<Thread>();
        for (int i = 0; i < split.length; i++) {
            var multiplier = new Multiplier(split[i], i);
            multiplierThreads.add(new Thread(multiplier));
        }

        try {
            for (Thread multiplierThread : multiplierThreads) {
                multiplierThread.start();
            }

            for (Thread multiplierThread : multiplierThreads) {
                multiplierThread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        var concatenated = Arrays.copyOf(results[0], len);
        System.arraycopy(results[1], 0, concatenated, results[0].length, results[1].length);
        printArrayAsMatrix(concatenated);
    }

    public static void printArrayAsMatrix(int[] array) {
        for (int i = 0; i < len; i = i + 3) {
            var row = Arrays.copyOfRange(array, i, i + 3);
            System.out.println(Arrays.toString(row));
        }
        System.out.println("========");
    }

}