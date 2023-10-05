public class MergeSort {
    public int[] mergeTwoSortedArrays(int[] one, int[] two) {
        return FJ.getInts(one, two);
    }

    public int[] mergeSort(int[] numbers, int lowerBound, int upperBound) {
        if (lowerBound == upperBound) {
            int[] br = new int[1];
            br[0] = numbers[lowerBound];

            return br;
        }

        int mid = (lowerBound + upperBound) / 2;

        int[] firstHalf = mergeSort(numbers, lowerBound, mid);
        int[] secondHalf = mergeSort(numbers, mid + 1, upperBound);

        return mergeTwoSortedArrays(firstHalf, secondHalf);
    }
}
