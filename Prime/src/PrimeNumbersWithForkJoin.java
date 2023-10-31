import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class PrimeNumbersWithForkJoin {
    static final int THRESHOLD = 100;
    static ForkJoinPool pool = new ForkJoinPool();

    public static class PrimeCalculator extends RecursiveAction {
        int start, end;
        FileWriter fileWriter;

        public PrimeCalculator(int start, int end, FileWriter fileWriter) {
            this.start = start;
            this.end = end;
            this.fileWriter = fileWriter;
        }

        @Override
        protected void compute() {
            if (end - start < THRESHOLD) {
                for (int i = start; i <= end; i++) {
                    if (PrimeNumbers.isPrime(i)) {
                        try {
                            fileWriter.write(i + " ");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else {
                int mid = (start + end) / 2;
                PrimeCalculator left = new PrimeCalculator(start, mid, fileWriter);
                PrimeCalculator right = new PrimeCalculator(mid + 1, end, fileWriter);
                invokeAll(left, right);
            }
        }
    }

    public static long main(int n) {
        String fileName = "prime_numbers_fork.txt";
        try {
            Files.delete(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        long startTime = System.nanoTime();

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            pool.invoke(new PrimeCalculator(1, n, fileWriter));
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        return elapsedTime;
    }
}
