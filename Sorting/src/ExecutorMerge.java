import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
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
        int[] newDer;
        int[] newIzq;
        if (ciclo < 11) {
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
            return merge(newIzq, newDer);
        } else {
            return mergeSort(arreglo);
        }
    }

    public static int[] mergeSort(int[] arreglo) {
        if (arreglo.length == 1) return arreglo;
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
        izq = mergeSort(izq);
        der = mergeSort(der);

        return merge(izq, der);
    }

    public static int[] merge(int[] izq, int[] der) {
        int[] res = new int[izq.length + der.length];
        int resIndex = 0;
        int izqIndex = 0;
        int derIndex = 0;
        while (izqIndex < izq.length && derIndex < der.length) {
            if (izq[izqIndex] <= der[derIndex]) {
                res[resIndex] = izq[izqIndex];
                izqIndex++;
            } else {
                res[resIndex] = der[derIndex];
                derIndex++;
            }
            resIndex++;
        }
        while (izqIndex < izq.length) {
            res[resIndex] = izq[izqIndex];
            izqIndex++;
            resIndex++;
        }
        while (derIndex < der.length) {
            res[resIndex] = der[derIndex];
            derIndex++;
            resIndex++;
        }
        return res;
    }
}
