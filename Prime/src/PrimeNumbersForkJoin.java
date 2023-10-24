import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class PrimeNumbersForkJoin {
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

    public static class PrimeCalculator extends RecursiveTask<List<Integer>> {
        int start, end;

        public PrimeCalculator(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        protected List<Integer> compute() {
            List<Integer> primeNumbers = new ArrayList<>();

            if (end - start < THRESHOLD) {
                for (int i = start; i <= end; i++) {
                    if (isPrime(i)) {
                        primeNumbers.add(i);
                    }
                }
            } else {
                int mid = (start + end) / 2;
                PrimeCalculator left = new PrimeCalculator(start, mid);
                PrimeCalculator right = new PrimeCalculator(mid + 1, end);
                invokeAll(left, right);

                primeNumbers.addAll(left.join());
                primeNumbers.addAll(right.join());
            }

            return primeNumbers;
        }
    }

    public static void main(String[] args) {
        int n = 50_000;
        String fileName = "prime_numbers.json";

        long startTime = System.nanoTime();

        List<Integer> primeNumbers = pool.invoke(new PrimeCalculator(2, n));
        JsonObject jsonOutput = new JsonObject();
        jsonOutput.addProperty("total", primeNumbers.size());
        JsonArray primeArray = new JsonArray();
        for (int prime : primeNumbers) {
            primeArray.add(prime);
        }
        jsonOutput.add("primes", primeArray);

        try (FileWriter fileWriter = new FileWriter(fileName)) {
            System.out.println("Prime numbers from 1 to " + n + " written to " + fileName + ":");
            Gson gson = new Gson();
            String json = gson.toJson(jsonOutput);
            fileWriter.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        System.out.println("Time taken by seq: " + elapsedTime / 1e6 + " milliseconds");
    }
}
