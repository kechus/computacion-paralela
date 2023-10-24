import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class PrimeNumbersWithForkJoinAndFileOutput {
    static final int THRESHOLD = 100;
    static ForkJoinPool pool = new ForkJoinPool();

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
                    if (isPrime(i)) {
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

    public static void main(String[] args) {
        int n = 50_000;
        String fileName = "prime_numbers.txt";

        long startTime = System.nanoTime();

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            System.out.println("Prime numbers from 1 to " + n + " written to " + fileName + ":");
            pool.invoke(new PrimeCalculator(2, n, fileWriter));
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Time taken by seq: " + elapsedTime / 1e6 + " milliseconds");
    }
}
