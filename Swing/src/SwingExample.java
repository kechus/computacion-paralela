import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingExample extends JFrame {
     SwingExample(){
        JFrame frame = new JFrame("Swing Components Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());

        JTextArea textArea = new JTextArea(5, 20);
        panel.add(textArea);

        JLabel label = new JLabel("Label");
        panel.add(label);

        Canvas canvas = new Canvas(){
           @Override
           public void paint(Graphics g) {
              super.paint(g);
              // Draw a line from (50, 50) to (200, 200)
              g.drawLine(50, 50, 200, 200);
           }
        };;
        canvas.setPreferredSize(new Dimension(100, 100));
        panel.add(canvas);

        JTextField textField = new JTextField(15);
        panel.add(textField);

        JButton button = new JButton("Button");
        panel.add(button);

        JCheckBox checkBox = new JCheckBox("Checkbox");
        panel.add(checkBox);

        String[] listData = {"Item 1", "Item 2", "Item 3"};
        JList<String> list = new JList<>(listData);
        panel.add(list);

        JRadioButton radioButton1 = new JRadioButton("Option 1");
        JRadioButton radioButton2 = new JRadioButton("Option 2");
        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(radioButton1);
        radioGroup.add(radioButton2);
        panel.add(radioButton1);
        panel.add(radioButton2);

        String[] comboBoxData = {"Option A", "Option B", "Option C"};
        JComboBox<String> comboBox = new JComboBox<>(comboBoxData);
        panel.add(comboBox);

        JScrollBar scrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 1, 0, 100);
        panel.add(scrollBar);

        String[][] tableData = {
                {"1", "Jesus", "Antonio"},
                {"2", "Fulanito", "De tal"}
        };
        String[] columnNames = {"ID", "First Name", "Last Name"};
        JTable table = new JTable(tableData, columnNames);
        panel.add(new JScrollPane(table));

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem menuItem = new JMenuItem("Open");
        menu.add(menuItem);
        menuBar.add(menu);
        frame.setJMenuBar(menuBar);

        JButton optionPaneButton = new JButton("Show OptionPane");
        optionPaneButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "This is a JOptionPane message.");
            }
        });
        panel.add(optionPaneButton);

        frame.add(panel);
        frame.setVisible(true);
    }
}
