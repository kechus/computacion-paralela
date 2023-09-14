import javax.swing.*;

public class Clock implements Runnable {
    private JLabel label;
    private int speed;

    Clock(JLabel l, int s) {
        label = l;
        speed = s;
    }

    @Override
    public void run() {
        boolean moving = true;
        while(moving){
            var size = label.getPreferredSize();
            label.setBounds(label.getX() + speed, label.getY(), size.width, size.height);
            if(label.getX() >= 900){
                moving = false;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
