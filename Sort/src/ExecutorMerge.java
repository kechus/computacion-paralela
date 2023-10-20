import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ExecutorMerge implements Callable<int[]> {
    int[] arreglo;
    ExecutorService executor;
    int ciclo;

    ExecutorMerge(int[] arreglo, ExecutorService executor, int ciclo) {
        this.arreglo = arreglo;
        this.executor = executor;
        this.ciclo = ciclo;
    }


    @Override
    public int[] call() throws Exception {
        if (arreglo.length <= 1) return arreglo;
        int[] newDer = new int[0];
        int[] newIzq = new int[0];
        if (ciclo < 4) {
            ciclo++;
            int centro = (arreglo.length / 2);
            int[] izq = new int[centro];
            int[] der = new int[arreglo.length - centro];
            int izqIndex = 0;
            int derIndex = 0;
            for (int i = 0; i < arreglo.length; i++) {
                if (i < centro) {
                    izq[izqIndex] = arreglo[i];
                    izqIndex++;
                } else {
                    der[derIndex] = arreglo[i];
                    derIndex++;
                }
            }
            Future<int[]> derTask = executor.submit(new ExecutorMerge(der, executor, ciclo + 1));
            Future<int[]> izqTask = executor.submit(new ExecutorMerge(izq, executor, ciclo + 1));

            newDer = derTask.get();
            newIzq = izqTask.get();
            return Interfaz.merge(newIzq, newDer);
        }else{
            return Interfaz.mergeSort(arreglo);
        }
    }
}
