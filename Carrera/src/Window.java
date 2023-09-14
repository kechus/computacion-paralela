import javax.swing.*;

public class Window extends JFrame {
    Window() {
        setSize(1000, 800);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        var basePath = "src/images/";
        var harePath = basePath+"hare.png";
        var turtlePath = basePath+"turtle.png";
        var squirrelPath = basePath+"squirrel.png";
        addAnimal(harePath,50,100,10);
        addAnimal(turtlePath,50,300,5);
        addAnimal(squirrelPath,50,600,7);


        var finishLinePath = basePath+"finish.png";
        var label = new JLabel(new ImageIcon(finishLinePath));
        var size = label.getPreferredSize();
        label.setBounds(900, 100, size.width, size.height);
        add(label);

        setVisible(true);
    }

    public void addAnimal(String path, int x, int y, int speed){
        var label = new JLabel(new ImageIcon(path));
        var size = label.getPreferredSize();
        label.setBounds(x, y, size.width, size.height);
        add(label);
        var thread = new Thread(new Clock(label, speed));
        thread.start();
    }
}
