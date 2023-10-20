import java.util.ArrayList;
import java.util.Random;

public class Main {
    public int[] numbers;
    public static ArrayList<Integer> numbersList;
    public static void main(String[] args) {
        var random = new Random();
        var length = 10;
        Main.numbersList = new ArrayList<>(length);
        for(int i = 0; i < 10; i++){
            numbersList.add(random.nextInt(1,100));
        }
    }
}