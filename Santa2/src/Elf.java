import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class Elf extends Thread{
    Semaphore semaforoElfos;
    Semaphore semaforoInterfaz;
    Semaphore semaforoAgrupacionElfos;
    Window window;
    boolean stopped = false;

    Elf(Semaphore semaforoElfos, Semaphore semaforoInterfaz, Window window, Semaphore semaforoAgrupacionElfos){
        this.semaforoElfos = semaforoElfos;
        this.semaforoInterfaz = semaforoInterfaz;
        this.window = window;
        this.semaforoAgrupacionElfos = semaforoAgrupacionElfos;
    }
    @Override
    public void run() {
        while(true){
            if(stopped) break;
            try{
                semaforoElfos.acquire();
                if(window.freeElfs.size() >= AgrupacionElfos.size){
                    ArrayList<Elf> sacados = new ArrayList<>();
                    int indexPorSacar = 0;
                    while(sacados.size() < (AgrupacionElfos.size - 1)){
                        Elf sacado = window.freeElfs.get(indexPorSacar);
                        indexPorSacar++;
                        if(sacado != this) {
                            sacados.add(sacado);
                            window.freeElfs.remove(sacado);
                            sacado.stopped = true;
                            indexPorSacar--;
                        }
                    }
                    window.freeElfs.remove(this);
                    this.stopped = true;
                    semaforoAgrupacionElfos.acquire();
                    window.gropuedElfs.add(new AgrupacionElfos());
                    semaforoAgrupacionElfos.release();
                }
                semaforoElfos.release();
                semaforoInterfaz.acquire();
                window.repaint();
                semaforoInterfaz.release();
                Thread.sleep(1000);
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }
}
