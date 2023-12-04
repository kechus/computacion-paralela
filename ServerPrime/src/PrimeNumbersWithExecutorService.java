import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.List;
import java.util.ArrayList;

public class PrimeNumbersWithExecutorService {
    static final int THREAD_POOL_SIZE = Runtime.getRuntime().availableProcessors()-1;

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
                    if (PrimeNumbers.isPrime(i)) {
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

    public static long main(int n) {
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


        for (Future<List<Integer>> future : futures) {
            try {
                future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        executorService.shutdown();
        return  elapsedTime;
    }
}
