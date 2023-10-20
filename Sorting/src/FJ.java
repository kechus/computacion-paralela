import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

public class FJ {
    public static class MergeTask extends RecursiveTask<int[]> {
        private final int[] array;
        private final int lowerBound;
        private final int upperBound;

        public MergeTask(int[] array, int lowerBound, int upperBound) {
            this.array = array;
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }

        @Override
        protected int[] compute() {
            if (lowerBound == upperBound) {
                int[] result = new int[1];
                result[0] = array[lowerBound];
                return result;
            }

            int mid = (lowerBound + upperBound) / 2;

            MergeTask leftTask = new MergeTask(array, lowerBound, mid);
            MergeTask rightTask = new MergeTask(array, mid + 1, upperBound);

            invokeAll(leftTask, rightTask);

            int[] leftResult = leftTask.join();
            int[] rightResult = rightTask.join();

            return mergeTwoSortedArrays(leftResult, rightResult);
        }
    }

    public static int[] mergeTwoSortedArrays(int[] one, int[] two) {
        return getInts(one, two);
    }

    static int[] getInts(int[] one, int[] two) {
        int[] sorted = new int[one.length + two.length];

        int i = 0;
        int j = 0;
        int k = 0;

        while (i < one.length && j < two.length) {
            if (one[i] < two[j]) {
                sorted[k] = one[i];
                k++;
                i++;
            } else {
                sorted[k] = two[j];
                k++;
                j++;
            }
        }

        if (i == one.length) {

            while (j < two.length) {
                sorted[k] = two[j];
                k++;
                j++;
            }
        }

        if (j == two.length) {

            while (i < one.length) {
                sorted[k] = one[i];
                k++;
                i++;
            }
        }

        return sorted;
    }
}
