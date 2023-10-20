import java.util.concurrent.RecursiveTask;

public class ForkJoinMerge extends RecursiveTask<int[]> {
    int[] arreglo;
    ForkJoinMerge(int[] arreglo){
        this.arreglo = arreglo;
    }
    @Override
    protected int[] compute() {
        if(arreglo.length == 1) return arreglo;
        int centro = (arreglo.length / 2);
        int[] izq = new int[centro];
        int[] der = new int[arreglo.length - centro];
        int izqIndex = 0;
        int derIndex = 0;
        for(int i = 0; i < arreglo.length; i++){
            if(i < centro){
                izq[izqIndex] = arreglo[i];
                izqIndex++;
            }else{
                der[derIndex] = arreglo[i];
                derIndex++;
            }
        }
        ForkJoinMerge taskIzq = new ForkJoinMerge(izq);
        ForkJoinMerge taskDer = new ForkJoinMerge(der);

        taskDer.fork();
        taskIzq.fork();

        int[] newDer = taskDer.join();
        int[] newIzq = taskIzq.join();


        return Interfaz.merge(newIzq, newDer);
    }
}
