import java.util.ArrayList;
import java.util.Objects;

public class Santa extends Thread {
    String status = "Durmiendo";
    ElfGrouping elfGrouping;

    @Override
    public void run() {
        while (true) {
            try {
                boolean renosStart = false;
               Main.santaSemaphore.acquire();
                if (Objects.equals(status, "Durmiendo")) {
                    boolean changed = false;
                    Main.reindeersSemaphore.acquire();
                    if(Main.window.reindeers.size() == 9){
                        status = "Renos";
                        changed = true;
                        renosStart = true;
                    }
                    Main.reindeersSemaphore.release();
                    Main.elfsGroupingSemaphore.acquire();
                    if (!Main.window.groupedElfs.isEmpty() && !changed) {
                        elfGrouping = Main.window.groupedElfs.get(0);
                        status = "Ayudando";
                    }
                    Main.elfsGroupingSemaphore.release();
                }
                if (Objects.equals(status, "Ayudando")) {
                    boolean changed = false;
                    Main.reindeersSemaphore.acquire();
                    if(Main.window.reindeers.size() == 9){
                        status = "Renos";
                        changed = true;
                        renosStart = true;
                    }
                    Main.reindeersSemaphore.release();
                    if (elfGrouping.stage < 3 && !changed) {
                        elfGrouping.stage++;
                    }
                    if (elfGrouping.stage == 3 && !changed) {
                        Main.elfsGroupingSemaphore.acquire();
                        Main.window.groupedElfs.remove(elfGrouping);
                        status = (!Main.window.groupedElfs.isEmpty()) ? "Ayudando" : "Durmiendo";
                        elfGrouping = (status.equals("Ayudando")) ? Main.window.groupedElfs.get(0) : null;
                        Main.elfsGroupingSemaphore.release();
                    }
                }
                if(Objects.equals(status, "Renos")){
                    Main.reindeersSemaphore.acquire();
                    ArrayList<Reindeer> reindeers = Main.window.reindeers;
                    Main.reindeersSemaphore.release();
                    if(renosStart){
                        for(var r : reindeers){
                            r.start();
                        }
                    }
                    if(reindeers.isEmpty()){
                        Main.elfsGroupingSemaphore.acquire();
                        status = (!Main.window.groupedElfs.isEmpty()) ? "Ayudando" : "Durmiendo";
                        if(elfGrouping == null) elfGrouping = Main.window.groupedElfs.get(0);
                        Main.elfsGroupingSemaphore.release();
                    }
                }
                Main.drawSemaphore.acquire();
                Main.window.repaint();
                Main.drawSemaphore.release();
                Main.santaSemaphore.release();
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
