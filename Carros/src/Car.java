public class Car implements Runnable{
    int pos;
    boolean goesRight;

    Car(int pos, boolean goesRight){
       this.pos = pos;
       this.goesRight = goesRight;
    }
    @Override
    public void run() {
        var speed = 10;
        var rate = 200;
        try {
            while (goesRight ? pos < 700 : pos > 100){
                if(goesRight){
                    pos += speed;
                }else{
                    pos -= speed;
                }
                Main.drawSemaphore.acquire();
                Main.window.repaint();
                Main.drawSemaphore.release();
                Thread.sleep(rate);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
