import java.util.concurrent.*;

public class TP {
    private static class MergeTask implements Runnable {
        private final int[] array;
        private final int lowerBound;
        private final int upperBound;

        public MergeTask(int[] array, int lowerBound, int upperBound) {
            this.array = array;
            this.lowerBound = lowerBound;
            this.upperBound = upperBound;
        }

        private Future<?> future;

        public void setFuture(Future<?> future) {
            this.future = future;
        }

        @Override
        public void run() {
            if (lowerBound < upperBound) {
                int mid = (lowerBound + upperBound) / 2;
                MergeTask leftTask = new MergeTask(array, lowerBound, mid);
                MergeTask rightTask = new MergeTask(array, mid + 1, upperBound);

                ExecutorService threadPool = Executors.newFixedThreadPool(2);
                threadPool.execute(leftTask);
                threadPool.execute(rightTask);
                threadPool.shutdown();

                try {
                    threadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                merge(array, lowerBound, mid, upperBound);
            }
        }
        public void merge(int[] array, int lowerBound, int mid, int upperBound) {
            int[] temp = new int[array.length];
            int i = lowerBound;
            int j = mid + 1;
            int k = lowerBound;

            while (i <= mid && j <= upperBound) {
                if (array[i] < array[j]) {
                    temp[k++] = array[i++];
                } else {
                    temp[k++] = array[j++];
                }
            }

            while (i <= mid) {
                temp[k++] = array[i++];
            }

            while (j <= upperBound) {
                temp[k++] = array[j++];
            }

            for (i = lowerBound; i <= upperBound; i++) {
                array[i] = temp[i];
            }
        }
    }

    public int[] mergeSort(int[] numbers) {
        var mergeTask = new MergeTask(numbers, 0, numbers.length - 1);
        var threadPool = Executors.newSingleThreadExecutor();
        Future<?> future = threadPool.submit(mergeTask);
        threadPool.shutdown();

        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return numbers;
    }
}
