public class ElfProducer extends Thread{

    @Override
    public void run() {
        while(true){
            try {
                Main.elfsSemaphore.acquire();
                var elf = new Elf();
                Main.window.freeElfs.add(elf);
                elf.start();
                Main.elfsSemaphore.release();
                Thread.sleep(1500);
            }catch(Exception e){
                System.out.println("Error " + e.getMessage());
            }
        }
    }
}
