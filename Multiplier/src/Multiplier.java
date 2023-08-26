import java.util.Arrays;

public class Multiplier implements Runnable{
    private final int[] numbers;
    private final int index;

    public Multiplier(int[] numbers, int index){
        this.numbers = numbers;
        this.index = index;
    }

    @Override
    public void run() {
        Main.results[index] = Arrays.stream(numbers).map(n->n*2).toArray();
    }
}
