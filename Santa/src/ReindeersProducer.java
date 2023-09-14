public class ReindeersProducer extends Thread {
    private void repaint() throws InterruptedException {
        Main.paintSemaphore.acquire();
        Main.window.repaint();
        Main.paintSemaphore.release();
    }

    @Override
    public void run() {
        try {
            while (true) {
                Main.reindeersSemaphore.acquire();
                var length = Main.reindeers.size() + 1;
                if (length == 10) {
                    repaint();
                } else {
                    var reindeer = new Reindeer(500, 50 * length);
                    reindeer.start();
                    Main.reindeers.add(reindeer);
                    Main.reindeersSemaphore.release();
                    repaint();
                }
                Thread.sleep(500);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
