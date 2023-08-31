import javax.swing.*;

public class Circulo implements Runnable{
    JFrame frame;
    int pos;
    Circulo(JFrame pane,int pos){
       this.frame = pane;
       this.pos = pos;
    }
    @Override
    public void run() {
        try {
            while (true){
                Window.circles[pos] += 10;
                frame.repaint();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
