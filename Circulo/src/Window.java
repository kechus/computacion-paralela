import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Window extends JFrame {
    JPanel panel;
    public static int[] circles = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
    public static int[] squares = {200,300};
    ArrayList<Thread> threads = new ArrayList<>();

    Window() {
        panel = new JPanel();
        add(panel);
        panel.setPreferredSize(new Dimension(800, 600));

        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        for (int i = 0; i < circles.length; i++) {
            var th = new Thread(new Circulo(this, i));
            threads.add(th);
            th.start();
        }
        for(int i = 0; i < squares.length; i++){
            var th = new Thread(new Cuadrado(this,i));
            threads.add(th);
            th.start();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        var g2d = (Graphics2D) g;
        g2d.setColor(Color.RED);
        for (int x : circles) {
            g2d.drawOval(x, 200, 100, 100);
        }
        for(int x : squares){
            g2d.drawRect(x,new Random().nextInt(100,200),350,60);
        }
    }
}
