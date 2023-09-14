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
    BufferedImage groupdedElfsImg;
    ArrayList<Elf> freeElfs;
    ArrayList<ElfGrouping> groupedElfs;
    ElfsProducer elfsProducer;
    ReindeersProducer reindeersProducer;
    Santa santa;

    ArrayList<Reindeer> reindeers;

    Window() {
        var baseUrl = "src/images/";
        try {
            this.reindeers = new ArrayList<>();
            this.santaImg = ImageIO.read(new File(baseUrl + "santa.png"));
            this.elfImg = ImageIO.read(new File(baseUrl + "elf.png"));
            this.reindeerImg = ImageIO.read(new File(baseUrl + "reindeer.png"));
            this.groupdedElfsImg = ImageIO.read(new File(baseUrl+"agrupacionElfos.png"));
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
            elfsY += elfImg.getHeight() + 10;
            if (elfsY >= this.getHeight()) {
                elfsX += elfImg.getWidth() + 10;
                elfsY = 50;
            }
        }

        for (int i = 0; i < groupedElfs.size(); i++) {
            g.drawImage(groupdedElfsImg, elfImg.getWidth() + 40, 50 + i * (groupdedElfsImg.getHeight() + 10), this);
            if (Objects.equals(santa.status, "Ayudando") && santa.elfGrouping == groupedElfs.get(i)) {
                g.drawImage(santaImg, elfImg.getWidth() + groupdedElfsImg.getWidth() + 60, 50 + i * (groupdedElfsImg.getHeight() + 10), this);
            }
        }

        if (Objects.equals(santa.status, "Durmiendo")) {
            g.drawImage(santaImg, (getWidth() - santaImg.getWidth()) / 2, getHeight() - santaImg.getHeight() - 50, this);
        }
        if (Objects.equals(santa.status, "Renos")) {
            g.drawImage(santaImg, getWidth() - 40 - reindeerImg.getWidth() - santaImg.getWidth(), 50, this);
        }

        for (var reindeer : reindeers) {
            g.drawImage(reindeerImg, reindeer.x, reindeer.y, this);
        }
    }

    void init() {
        santa.start();
        elfsProducer.start();
        reindeersProducer.start();
    }
}
