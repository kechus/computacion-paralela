import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.Semaphore;

public class Santa extends Thread {
    String status = "Durmiendo";
    Window window;
    AgrupacionElfos agrupacionElfos;
    Semaphore semaforoInterfaz;
    Semaphore semaforoAgrupacionElfos;
    Semaphore semaforoSanta;
    Semaphore semaforoRenos;

    Santa(Window window, Semaphore semaforoInterfaz, Semaphore semaforoAgrupacionElfos, Semaphore semaforoSanta, Semaphore semaforoRenos) {
        this.window = window;
        this.semaforoInterfaz = semaforoInterfaz;
        this.semaforoAgrupacionElfos = semaforoAgrupacionElfos;
        this.semaforoSanta = semaforoSanta;
        this.semaforoRenos = semaforoRenos;
    }

    @Override
    public void run() {
        while (true) {
            try {
                boolean renosStart = false;
                semaforoSanta.acquire();
                if (Objects.equals(status, "Durmiendo")) {
                    boolean changed = false;
                    semaforoRenos.acquire();
                    if(window.reindeers.size() == 9){
                        status = "Renos";
                        changed = true;
                        renosStart = true;
                    }
                    semaforoRenos.release();
                    semaforoAgrupacionElfos.acquire();
                    if (!window.gropuedElfs.isEmpty() && !changed) {
                        agrupacionElfos = window.gropuedElfs.get(0);
                        status = "Ayudando";
                    }
                    semaforoAgrupacionElfos.release();
                }
                if (Objects.equals(status, "Ayudando")) {
                    boolean changed = false;
                    semaforoRenos.acquire();
                    if(window.reindeers.size() == 9){
                        status = "Renos";
                        changed = true;
                        renosStart = true;
                    }
                    semaforoRenos.release();
                    if (agrupacionElfos.stage < 3 && !changed) {
                        agrupacionElfos.stage++;
                    }
                    if (agrupacionElfos.stage == 3 && !changed) {
                        semaforoAgrupacionElfos.acquire();
                        window.gropuedElfs.remove(agrupacionElfos);
                        status = (!window.gropuedElfs.isEmpty()) ? "Ayudando" : "Durmiendo";
                        agrupacionElfos = (status.equals("Ayudando")) ? window.gropuedElfs.get(0) : null;
                        semaforoAgrupacionElfos.release();
                    }
                }
                if(Objects.equals(status, "Renos")){
                    semaforoRenos.acquire();
                    ArrayList<Reindeer> reindeers = window.reindeers;
                    semaforoRenos.release();
                    if(renosStart){
                        for(var r : reindeers){
                            r.start();
                        }
                    }
                    if(reindeers.isEmpty()){
                        semaforoAgrupacionElfos.acquire();
                        status = (!window.gropuedElfs.isEmpty()) ? "Ayudando" : "Durmiendo";
                        if(agrupacionElfos == null) agrupacionElfos = window.gropuedElfs.get(0);
                        semaforoAgrupacionElfos.release();
                    }
                }
                semaforoInterfaz.acquire();
                window.repaint();
                semaforoInterfaz.release();
                semaforoSanta.release();
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
