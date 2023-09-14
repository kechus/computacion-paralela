import java.util.ArrayList;
import java.util.Objects;

enum SantaStatus {
    SLEEPING,
    REINDEERS,
    ELFS,
}

public class Santa extends Thread {
    SantaStatus status = SantaStatus.SLEEPING;
    ElfGrouping elfGrouping;

    @Override
    public void run() {
        while (true) {
            try {
                boolean renosStart = false;
                Main.santaSemaphore.acquire();
                if (Objects.equals(status, SantaStatus.SLEEPING)) {
                    boolean changed = false;
                    Main.reindeersSemaphore.acquire();
                    if (Main.window.reindeers.size() == 9) {
                        status = SantaStatus.REINDEERS;
                        changed = true;
                        renosStart = true;
                    }
                    Main.reindeersSemaphore.release();
                    Main.elfsGroupingSemaphore.acquire();
                    if (!Main.window.groupedElfs.isEmpty() && !changed) {
                        elfGrouping = Main.window.groupedElfs.get(0);
                        status = SantaStatus.ELFS;
                    }
                    Main.elfsGroupingSemaphore.release();
                }
                if (Objects.equals(status, SantaStatus.ELFS)) {
                    boolean changed = false;
                    Main.reindeersSemaphore.acquire();
                    if (Main.window.reindeers.size() == 9) {
                        status = SantaStatus.REINDEERS;
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
                        status = (!Main.window.groupedElfs.isEmpty()) ? SantaStatus.ELFS : SantaStatus.SLEEPING;
                        elfGrouping = (status.equals(SantaStatus.ELFS)) ? Main.window.groupedElfs.get(0) : null;
                        Main.elfsGroupingSemaphore.release();
                    }
                }
                if (Objects.equals(status, SantaStatus.REINDEERS)) {
                    Main.reindeersSemaphore.acquire();
                    ArrayList<Reindeer> reindeers = Main.window.reindeers;
                    Main.reindeersSemaphore.release();
                    if (renosStart) {
                        for (var r : reindeers) {
                            r.start();
                        }
                    }
                    if (reindeers.isEmpty()) {
                        Main.elfsGroupingSemaphore.acquire();
                        status = (!Main.window.groupedElfs.isEmpty()) ? SantaStatus.ELFS : SantaStatus.SLEEPING;
                        if (elfGrouping == null) elfGrouping = Main.window.groupedElfs.get(0);
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
