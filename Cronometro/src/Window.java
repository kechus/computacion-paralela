import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    private final JLabel label;

    Window() {
        setSize(100,100);
        setLayout(new GridLayout(1,1));
        label = new JLabel("",SwingConstants.CENTER);
        add(label);
        setVisible(true);
    }
    void updateTimeString(String timeString) {
        label.setText(timeString);
    }
}
