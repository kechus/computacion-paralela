import javax.swing.*;
import java.util.concurrent.BlockingQueue;

public class Circulo implements Runnable{
    int pos;
    boolean goesRight;

    Circulo(int pos,boolean goesRight){
       this.pos = pos;
       this.goesRight = goesRight;
    }
    @Override
    public void run() {
        var speed = 10;
        var rate = 100;
        try {
            while (true){
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
