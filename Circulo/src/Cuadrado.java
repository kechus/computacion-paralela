import javax.swing.*;

public class Cuadrado implements Runnable{
    JFrame frame;
    int pos;
    Cuadrado(JFrame pane, int pos){
       this.frame = pane;
       this.pos = pos;
    }
    @Override
    public void run() {
        try {
            while (true){
                Window.cuadrados[pos] += 20;
                frame.repaint();
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
