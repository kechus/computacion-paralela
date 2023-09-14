import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Objects;

public class Window extends JFrame {
    BufferedImage santaImg;
    BufferedImage elfImg;
    BufferedImage reindeerImg;
    BufferedImage groupedElfsImg;
    ArrayList<Elf> freeElfs;
    ArrayList<ElfGrouping> groupedElfs;
    ElfProducer elfProducer;
    ReindeerProducer reindeerProducer;
    Santa santa;
    ArrayList<Reindeer> reindeers;

    Window() {
        var baseUrl = "src/images/";
        try {
            this.reindeers = new ArrayList<>();
            this.santaImg = ImageIO.read(new File(baseUrl + "santa.png"));
            this.elfImg = ImageIO.read(new File(baseUrl + "elf.png"));
            this.reindeerImg = ImageIO.read(new File(baseUrl + "reindeer.png"));
            this.groupedElfsImg = ImageIO.read(new File(baseUrl+"agrupacionElfos.png"));
            this.groupedElfs = new ArrayList<>();
            this.freeElfs = new ArrayList<>();
            setSize(800, 800);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        int elfsX = 20;
        int elfsY = 50;
        for (int i = 0; i < freeElfs.size(); i++) {
            g.drawImage(elfImg, elfsX, elfsY, this);
            elfsX += elfImg.getHeight() + 10;
            if (elfsX >= this.getHeight()) {
                elfsY += elfImg.getWidth() + 10;
                elfsX = 50;
            }
        }

        for (int i = 0; i < groupedElfs.size(); i++) {
            var x =50 + i * (groupedElfsImg.getHeight() + 10);
            var y = elfImg.getWidth() + 40;
            g.drawImage(groupedElfsImg, x,y , this);
            if (Objects.equals(santa.status, SantaStatus.ELFS) && santa.elfGrouping == groupedElfs.get(i)) {
                var santaX = 50 + i * (groupedElfsImg.getHeight() + 10);
                var santaY  = elfImg.getWidth() + groupedElfsImg.getWidth() + 60;
                g.drawImage(santaImg, santaX, santaY, this);
            }
        }

        if (Objects.equals(santa.status, SantaStatus.SLEEPING)) {
            var x = getHeight() - santaImg.getHeight() - 50;
            var y = (getWidth() - santaImg.getWidth()) / 2;
            g.drawImage(santaImg, x, y, this);
        }
        if (Objects.equals(santa.status, SantaStatus.REINDEERS)) {
            var x = 50;
            var y = getWidth() - 40 - reindeerImg.getWidth() - santaImg.getWidth();
            g.drawImage(santaImg, x, y, this);
        }

        for (var reindeer : reindeers) {
            g.drawImage(reindeerImg, reindeer.x, reindeer.y, this);
        }
    }

    void init() {
        santa.start();
        elfProducer.start();
        reindeerProducer.start();
    }
}
