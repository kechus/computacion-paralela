import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class PrimeNumbers {
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

    public static long main(int n) {
        String fileName = "prime_numbers_sec.txt";
        try {
            Files.delete(Path.of(fileName));
        } catch (IOException e) {
//            throw new RuntimeException(e);
        }

        long startTime = System.nanoTime();

        for (int i = 2; i <= n; i++) {
            if (isPrime(i)) {
                try (var fileWriter = new FileWriter(fileName, true)) {
                    fileWriter.write(i + " ");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        long endTime = System.nanoTime();
        long elapsedTime = endTime - startTime;
        return elapsedTime;
    }
}

