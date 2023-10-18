import java.util.concurrent.*;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MergeSortWithExecutorService {

    public static void main(String[] args) {
        int[] arr = {12, 11, 13, 5, 6, 7};
        int[] sortedArray = mergeSort(arr);
        System.out.println(Arrays.toString(sortedArray));
    }

    public static int[] mergeSort(int[] arr) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        int[] sortedArray = mergeSort(arr, 0, arr.length - 1, executorService);
        executorService.shutdown();
        return sortedArray;
    }

    public static int[] mergeSort(int[] arr, int left, int right, ExecutorService executorService) {
        if (left < right) {
            int mid = (left + right) / 2;

            Future<int[]> leftTask = executorService.submit(() -> mergeSort(arr, left, mid, executorService));
            Future<int[]> rightTask = executorService.submit(() -> mergeSort(arr, mid + 1, right, executorService));

            int[] leftArray;
            int[] rightArray;
            try {
                leftArray = leftTask.get();
                rightArray = rightTask.get();
            } catch (Exception e) {
                e.printStackTrace();
                return new int[0];
            }

            return merge(leftArray, rightArray);
        } else {
            return Arrays.copyOfRange(arr, left, left + 1);
        }
    }

    public static int[] merge(int[] leftArr, int[] rightArr) {
        int n1 = leftArr.length;
        int n2 = rightArr.length;
        int[] mergedArray = new int[n1 + n2];

        int i = 0, j = 0, k = 0;

        while (i < n1 && j < n2) {
            if (leftArr[i] <= rightArr[j]) {
                mergedArray[k] = leftArr[i];
                i++;
            } else {
                mergedArray[k] = rightArr[j];
                j++;
            }
            k++;
        }

        while (i < n1) {
            mergedArray[k] = leftArr[i];
            i++;
            k++;
        }

        while (j < n2) {
            mergedArray[k] = rightArr[j];
            j++;
            k++;
        }

        return mergedArray;
    }
}
