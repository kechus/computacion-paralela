import javax.swing.*;
import java.awt.*;
public class Window extends JFrame {
   Window(){
       setSize(800, 600);
       setVisible(true);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       var lane = new Thread( new Lane(Main.buffer,true) );
       var lane2 = new Thread( new Lane(Main.leftBuffer,false) );
       lane.start();
       try {
           Main.laneSemaphore.acquire();
       } catch (InterruptedException e) {
           throw new RuntimeException(e);
       }
       lane2.start();
   }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        var g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(10));
        g2d.drawRect(100,100,600,10);
        g2d.drawRect(100,500,600,10);

        g2d.setColor(Color.RED);
        for(var a : Main.buffer){
            g2d.drawOval(a.pos, 200, 100, 100);
            if(a.pos >= 700){
                try {
                    Main.buffer.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        g2d.setColor(Color.BLUE);
        for(var a : Main.leftBuffer){
            g2d.drawOval(a.pos, 200, 100, 100);
            if(a.pos <= 100){
                try {
                    Main.leftBuffer.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

   }
}
