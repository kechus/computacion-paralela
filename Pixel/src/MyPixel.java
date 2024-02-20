import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Pixel extends JFrame {
    private BufferedImage buffer;

    Pixel() {
        setTitle("Pixel");
        setSize(500, 500);
        setLayout(null);
        buffer = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
    }

    public void putPixel(int x, int y, Color c) {
        buffer.setRGB(9, 9, c.getRGB());
        this.getGraphics().drawImage(buffer, x, y, this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        putPixel(250, 250, Color.BLUE);
    }
}
