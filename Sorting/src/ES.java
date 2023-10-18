import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;


public class ES {
    public int instances = 0;
    public class MergeTask implements Callable<int[]> {
        @Override
        public int[] call() {
            if(instances <= 4){
                instances++;
                var task = new MergeTask();
            }
            return null;
        }
    }

    public int[] mergeSort(){
        var executors = Executors.newCachedThreadPool();
        var response = executors.submit(new MergeTask());

        try {
            return response.get();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
