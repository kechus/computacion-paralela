import java.util.concurrent.Semaphore;

public class ElfsProducer extends Thread{
    Window window;
    Semaphore semaforoElfos;
    Semaphore semaforoInterfaz;
    Semaphore semaforoAgrupacionElfos;
    ElfsProducer(Window window, Semaphore semaforoElfos, Semaphore semaforoInterfaz, Semaphore semaforoAgrupacionElfos){
        this.window = window;
        this.semaforoElfos = semaforoElfos;
        this.semaforoInterfaz  = semaforoInterfaz;
        this.semaforoAgrupacionElfos = semaforoAgrupacionElfos;
    }

    @Override
    public void run() {
        while(true){
            try {
                semaforoElfos.acquire();
                Elf nuevoElfo = new Elf(semaforoElfos, semaforoInterfaz, window, semaforoAgrupacionElfos);
                window.freeElfs.add(nuevoElfo);
                nuevoElfo.start();
                semaforoElfos.release();
                Thread.sleep(1500);
            }catch(Exception e){
                System.out.println("Error " + e.getMessage());
            }
        }
    }
}
