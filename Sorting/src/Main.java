import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static int[] copyArray(int[] original) {
        int[] copy = new int[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
    }

   public static void printArray(int[] numbers){
       for (int val : numbers) {
           System.out.print(val + " ");
       }
       System.out.println("=========");
   }

   static long startTime;

    public static void getTime(){
        var endTime = System.currentTimeMillis();
        NumberFormat formatter = new DecimalFormat("#0.00000");
        System.out.println("Execution time is " + formatter.format((endTime - startTime) / 1000d) + " seconds");
    }

    public static void main(String[] args) {
        var len = 1_000;
        int[] arr = new int[len];
        var random = new Random();
        for(int i = 0; i < len; i ++){
            arr[i] = random.nextInt(1,len);
        }


        startTime = System.currentTimeMillis();
        var sort = new MergeSort();
        int[] merged = sort.mergeSort(copyArray(arr), 0, arr.length - 1);
        getTime();
//        printArray(merged);

        startTime = System.currentTimeMillis();
        var tp = new TP();
        int[] tpMerged = tp.mergeSort(copyArray(arr));
        getTime();
//        printArray(tpMerged);

        startTime = System.currentTimeMillis();
        var fj = new FJ();
        int[] fjMerged = fj.mergeSort(copyArray(arr));
        getTime();
//        printArray(fjMerged);
    }
}