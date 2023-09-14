import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Window extends JFrame {
    BufferedImage leftCarImage;
    BufferedImage rightCarImage;
   Window(){
       setSize(800, 600);
       setVisible(true);
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       try {
           rightCarImage = ImageIO.read(new File("src/images/car_right.png"));
           leftCarImage = ImageIO.read(new File("src/images/car_left.png"));
       } catch (IOException e) {
           e.printStackTrace();
       }

   }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        var g2d = (Graphics2D) g;
        g2d.setColor(Color.black);
        g2d.setStroke(new BasicStroke(10));
        g2d.drawRect(100,100,600,10);
        g2d.drawRect(100,500,600,10);


        g2d.drawString("Carros hacia derecha:"+Main.rightQueue.size(),50,50);
        for(var a : Main.rightQueue){
           g2d.drawImage(rightCarImage,a.pos,200,this);
            if(a.pos >= 700){
                try {
                    Main.rightQueue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        g2d.drawString("Carros hacia izquierda:"+Main.leftQueue.size(),300,50);
        for(var a : Main.leftQueue){
            g2d.drawImage(leftCarImage,a.pos,300,this);
            if(a.pos <= 100){
                try {
                    Main.leftQueue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
   }
}
