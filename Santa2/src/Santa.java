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

    private void handleSleepingState() throws InterruptedException {
        boolean changed = false;

        Main.reindeersSemaphore.acquire();
        if (Main.window.reindeers.size() == 9) {
            status = SantaStatus.REINDEERS;
            changed = true;
//            reindeersStart = true;
        }
        Main.reindeersSemaphore.release();

        Main.elfsGroupingSemaphore.acquire();
        if (!Main.window.groupedElfs.isEmpty() && !changed) {
            elfGrouping = Main.window.groupedElfs.get(0);
            status = SantaStatus.ELFS;
        }
        Main.elfsGroupingSemaphore.release();
    }

    private void handleElfsState() throws InterruptedException {
        boolean changed = false;

        Main.reindeersSemaphore.acquire();
        if (Main.window.reindeers.size() == 9) {
            status = SantaStatus.REINDEERS;
            changed = true;
        }
        Main.reindeersSemaphore.release();

        if (elfGrouping.stage < 3 && !changed) {
            elfGrouping.stage++;
        }

        if (elfGrouping.stage == 3 && !changed) {
            Main.elfsGroupingSemaphore.acquire();
            Main.window.groupedElfs.remove(elfGrouping);
            status = (!Main.window.groupedElfs.isEmpty()) ? SantaStatus.ELFS : SantaStatus.SLEEPING;
            elfGrouping = (status == SantaStatus.ELFS) ? Main.window.groupedElfs.get(0) : null;
            Main.elfsGroupingSemaphore.release();
        }
    }

    private void handleReindeersState() throws InterruptedException {
        Main.reindeersSemaphore.acquire();
        ArrayList<Reindeer> reindeers = Main.window.reindeers;
        Main.reindeersSemaphore.release();

        if (reindeers.size() == 9) {
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

    private void updateWindow() throws InterruptedException {
        Main.drawSemaphore.acquire();
        Main.window.repaint();
        Main.drawSemaphore.release();
    }

    @Override
    public void run() {
        while (true) {
            try {
                Main.santaSemaphore.acquire();

                if (status == SantaStatus.SLEEPING) {
                    handleSleepingState();
                } else if (status == SantaStatus.ELFS) {
                    handleElfsState();
                } else if (status == SantaStatus.REINDEERS) {
                    handleReindeersState();
                }

                updateWindow();

                Main.santaSemaphore.release();
                Thread.sleep(750);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
