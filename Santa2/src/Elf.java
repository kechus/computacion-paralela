import java.util.ArrayList;

public class Elf extends Thread{
    boolean stopped = false;

    @Override
    public void run() {
        while(true){
            if(stopped) break;
            try{
                Main.elfsSemaphore.acquire();
                if(Main.window.freeElfs.size() >= ElfGrouping.size){
                    ArrayList<Elf> alreadyReleased = new ArrayList<>();
                    int aboutToFree = 0;
                    while(alreadyReleased.size() < (ElfGrouping.size - 1)){
                        var release = Main.window.freeElfs.get(aboutToFree);
                        aboutToFree++;
                        if(release != this) {
                            alreadyReleased.add(release);
                            Main.window.freeElfs.remove(release);
                            release.stopped = true;
                            aboutToFree--;
                        }
                    }
                    Main.window.freeElfs.remove(this);
                    this.stopped = true;
                    Main.elfsGroupingSemaphore.acquire();
                    Main.window.groupedElfs.add(new ElfGrouping());
                    Main.elfsGroupingSemaphore.release();
                }
                Main.elfsSemaphore.release();
                Main.drawSemaphore.acquire();
                Main.window.repaint();
                Main.drawSemaphore.release();
                Thread.sleep(1000);
            }catch(Exception e){
                e.printStackTrace();
            }

        }
    }
}
