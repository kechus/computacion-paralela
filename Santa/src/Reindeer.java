public class Reindeer extends Thread{
    int x;
    int y;
    Reindeer(int x, int y){
        this.x = x;
        this.y = y;
    }

    private void repaint() throws InterruptedException {
        Main.paintSemaphore.acquire();
        Main.window.repaint();
        Main.paintSemaphore.release();
    }
    @Override
    public void run() {
        try {
            while (true) {
                while (y >= 0) {
                    y -= 20;
                    Main.paintSemaphore.acquire();
                    Main.window.repaint();
                    Main.paintSemaphore.release();
                    Thread.sleep(300);
                }
                Main.reindeersSemaphore.acquire();
                Main.reindeers.take();
                System.out.println("taking");
                Main.reindeersSemaphore.release();
                repaint();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
