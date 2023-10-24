import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.List;
import java.util.ArrayList;

public class PrimeNumbersWithExecutorServiceAndFileOutput {
    static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors()-1;

    public static boolean isPrime(int number) {
        if (number <= 1) {
            return false;
        }

        if (number <= 3) {
            return true;
        }

        if (number % 2 == 0 || number % 3 == 0) {
            return false;
        }

        for (int i = 5; i * i <= number; i += 6) {
            if (number % i == 0 || number % (i + 2) == 0) {
                return false;
            }
        }

        return true;
    }

    public static class PrimeCalculator implements Callable<List<Integer>> {
        int start, end;

        public PrimeCalculator(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public List<Integer> call() {
            List<Integer> primeNumbers = new ArrayList<>();
            try (FileWriter fileWriter = new FileWriter("prime_numbers_exec.txt", true)) {
                for (int i = start; i <= end; i++) {
                    if (isPrime(i)) {
                        primeNumbers.add(i);
                        fileWriter.write(i + " ");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return primeNumbers;
        }
    }

    public static void main(String[] args) {
        int n = 50_000;
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        long startTime = System.nanoTime();
        List<Future<List<Integer>>> futures = new ArrayList<>();

        int segmentSize = n / THREAD_POOL_SIZE;
        int start = 2;
        int end = start + segmentSize - 1;

        for (int i = 0; i < THREAD_POOL_SIZE; i++) {
            Callable<List<Integer>> task = new PrimeCalculator(start, end);
            Future<List<Integer>> future = executorService.submit(task);
            futures.add(future);
            start = end + 1;
            end = start + segmentSize - 1;
        }

        List<Integer> primeNumbers = new ArrayList<>();

        for (Future<List<Integer>> future : futures) {
            try {
                primeNumbers.addAll(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Time taken by ExecutorService: " + elapsedTime / 1e6 + " milliseconds");

        executorService.shutdown();
    }
}
