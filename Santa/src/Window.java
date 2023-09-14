import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Window extends JFrame {
    BufferedImage elfImage;
    BufferedImage reindeerImage;
    BufferedImage santaImage;
    Window(){
        setSize(800, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        try {
            reindeerImage = ImageIO.read(new File("src/images/reindeer.png"));
            elfImage = ImageIO.read(new File("src/images/elf.png"));
            santaImage = ImageIO.read(new File("src/images/santa.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        var g2d = (Graphics2D) g;
//        g2d.drawImage(elfImage,100,50,this);
//        g2d.drawImage(santaImage,100,200,this);
//        g2d.drawImage(reindeerImage,100,350,this);
        for(var reindeer : Main.reindeers){
            g2d.drawImage(reindeerImage,reindeer.x,reindeer.y,this);
        }
//        for(var a : Main.rightQueue){
//            g2d.drawImage(rightCarImage,a.pos,300,this);
//            if(a.pos >= 700){
//                try {
//                    Main.rightQueue.take();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//
//        for(var a : Main.leftQueue){
//            g2d.drawImage(leftCarImage,a.pos,300,this);
//            if(a.pos <= 100){
//                try {
//                    Main.leftQueue.take();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
    }
}
