package server;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class PrimeNumbersWithForkJoin {
    static final int THRESHOLD = 100;
    static ForkJoinPool pool = new ForkJoinPool();

    public static ArrayList<Integer> numbers = new ArrayList<>();

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
                        numbers.add(i);
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

    public static ArrayList<Integer> main(int n) {
        String fileName = "prime_numbers_fork.txt";
        try {
            Files.delete(Path.of(fileName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try (FileWriter fileWriter = new FileWriter(fileName)) {
            var calculator = new PrimeCalculator(1, n, fileWriter);
            pool.invoke(calculator);
            return numbers;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
