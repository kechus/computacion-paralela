import java.util.concurrent.RecursiveAction;

public class ForkJoin extends RecursiveAction {
    public int[] A;
    int izq, der;
    public ForkJoin(int[] A, int izq, int der) {
        this.A = A;
        this.izq = izq;
        this.der = der;
    }
    @Override
    protected void compute() {
        if (izq < der){
            int m=(izq+der)/2;

            ForkJoin izquierda = new ForkJoin(A, izq, m);
            ForkJoin derecha = new ForkJoin(A, m + 1, der);
            invokeAll(izquierda, derecha);
            merge(A,izq,m,der);
        }
    }
    private void merge(int[] A, int izq, int m, int der) {
        int n1 = m - izq + 1;
        int n2 = der - m;

        // Create temporary arrays to hold the left and right subarrays
        int[] leftArray = new int[n1];
        int[] rightArray = new int[n2];

        // Copy data to temporary arrays
        for (int i = 0; i < n1; i++) {
            leftArray[i] = A[izq + i];
        }
        for (int j = 0; j < n2; j++) {
            rightArray[j] = A[m + 1 + j];
        }

        // Merge the temporary arrays back into A

        int i = 0; // Initial index of the left subarray
        int j = 0; // Initial index of the right subarray
        int k = izq; // Initial index of the merged subarray

        while (i < n1 && j < n2) {
            if (leftArray[i] <= rightArray[j]) {
                A[k] = leftArray[i];
                i++;
            } else {
                A[k] = rightArray[j];
                j++;
            }
            k++;
        }

        // Copy the remaining elements of leftArray, if any
        while (i < n1) {
            A[k] = leftArray[i];
            i++;
            k++;
        }

        // Copy the remaining elements of rightArray, if any
        while (j < n2) {
            A[k] = rightArray[j];
            j++;
            k++;
        }
    }
}
