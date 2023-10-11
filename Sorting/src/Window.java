import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Random;

public class Window extends JFrame {
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;

    static long startTime;

    int[] arr;
    Random random = new Random();

    public Window() {
        super("Swing Window Example");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 250);
        setLayout(new BorderLayout());

        label1 = new JLabel("Array Data: ");
        label2 = new JLabel("abd");
        label3 = new JLabel("def");
//        label2.setHorizontalTextPosition(SwingConstants.LEADING);

//        JScrollPane scrollPane = new JScrollPane(label2);
//        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        JPanel arrayPanel = new JPanel();
        arrayPanel.setLayout(new BoxLayout(arrayPanel, BoxLayout.Y_AXIS));
        arrayPanel.add(label1);
        arrayPanel.add(label2);
        arrayPanel.add(label3);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JTextField textField = new JTextField(15);
        JButton inputButton = new JButton("Input Button");

        topPanel.add(textField);
        topPanel.add(inputButton);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 3));

        JButton button1 = new JButton("Secuencial");
        JButton button2 = new JButton("ForkJoin");
        JButton button3 = new JButton("Executor Service");

        JLabel labelBelowButton1 = new JLabel("");
        JLabel labelBelowButton2 = new JLabel("");
        JLabel labelBelowButton3 = new JLabel("");

        bottomPanel.add(button1);
        bottomPanel.add(button2);
        bottomPanel.add(button3);
        bottomPanel.add(labelBelowButton1);
        bottomPanel.add(labelBelowButton2);
        bottomPanel.add(labelBelowButton3);

        add(arrayPanel, BorderLayout.NORTH);
        add(topPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        inputButton.addActionListener(e -> {
            inputButton.setEnabled(false);
            textField.setEnabled(false);
            var len = Integer.parseInt(textField.getText());
            arr = new int[len];
            for(int i = 0; i < len; i ++){
                arr[i] = random.nextInt(1,100);
            }
           label1.setText("Largo "+len);
            String[] list = Arrays.stream(arr)
                    .mapToObj(String::valueOf)
                    .toArray(String[]::new);
            label2.setText(String.join(" ",list));
        });

        button1.addActionListener(e->{
            startTime = System.currentTimeMillis();
            var sort = new MergeSort();
            var merged = sort.mergeSort(copyArray(arr), 0, arr.length - 1);
            var time = getTime();
            labelBelowButton1.setText(time);
            String[] list = Arrays.stream(merged)
                    .mapToObj(String::valueOf)
                    .toArray(String[]::new);
            label3.setText(String.join(" ",list));
        });

        button2.addActionListener(e -> {
            startTime = System.currentTimeMillis();

            var fj = new FJ();
            var merged = fj.mergeSort(copyArray(arr));
            var time = getTime();

            String[] list = Arrays.stream(merged)
                    .mapToObj(String::valueOf)
                    .toArray(String[]::new);
            label3.setText(String.join(" ",list));
            labelBelowButton2.setText(time);
        });

        setLocationRelativeTo(null);
    }


    public static int[] copyArray(int[] original) {
        int[] copy = new int[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
    }

    public static String getTime(){
        var endTime = System.currentTimeMillis();
        NumberFormat formatter = new DecimalFormat("#0.00000");
        return formatter.format((endTime - startTime) / 1000d);
    }
}