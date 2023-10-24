import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

public class Window extends JFrame {
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;

    static long startTime;

    int[] arr;
    Random random = new Random();

    public Window() {
        super("Programa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLayout(new BorderLayout());

        label1 = new JLabel("Datos: ");
        label2 = new JLabel("");
        label3 = new JLabel("");
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
        JButton inputButton = new JButton("Aceptar longitud");
        JButton resetButton = new JButton("Reiniciar");

        topPanel.add(textField);
        topPanel.add(inputButton);
        topPanel.add(resetButton);

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

        resetButton.addActionListener(e -> {
            inputButton.setEnabled(true);
            textField.setEnabled(true);
            label2.setText("");
            label3.setText("");
//            arr = new int[0];
        });
        inputButton.addActionListener(e -> {
            inputButton.setEnabled(false);
            textField.setEnabled(false);
            var len = Integer.parseInt(textField.getText());
            arr = new int[len];
            for (int i = 0; i < len; i++) {
                arr[i] = random.nextInt(1, Integer.MAX_VALUE);
            }
            label1.setText("Longitud: " + len);
            String[] list = Arrays.stream(arr)
                    .mapToObj(String::valueOf)
                    .toArray(String[]::new);
            label2.setText(String.join(" ", list));
        });

        button1.addActionListener(e -> {
            startTime = System.currentTimeMillis();
            var sort = new MergeSort();
            var merged = sort.mergeSort(copyArray(arr), 0, arr.length - 1);
            var time = getTime();
            labelBelowButton1.setText(time+ " MS");
            String[] list = Arrays.stream(merged)
                    .mapToObj(String::valueOf)
                    .toArray(String[]::new);
            label3.setText(String.join(" ", list));
        });

        button2.addActionListener(e -> {
            startTime = System.currentTimeMillis();

            var task = new FJ.MergeTask(copyArray(arr), 0, arr.length - 1);
            var pool = new ForkJoinPool();
            Future<int[]> result = pool.submit(task);
            int[] merged;
            try {
                merged = result.get();
            } catch (InterruptedException | ExecutionException ex) {
                throw new RuntimeException(ex);
            }

           var time = getTime();

            String[] list = Arrays.stream(merged)
                    .mapToObj(String::valueOf)
                    .toArray(String[]::new);
            label3.setText(String.join(" ", list));
            labelBelowButton2.setText(time+" MS");
        });

        button3.addActionListener(e -> {
            startTime = System.currentTimeMillis();

            int[] merged;
            String time;
            try {
                ExecutorService executor = Executors.newCachedThreadPool();
                Future<int[]> future = executor.submit(new ExecutorMerge(copyArray(arr), executor, 0));
                merged = future.get();
                time = getTime();
                executor.shutdown();
            } catch (InterruptedException | ExecutionException ex) {
                throw new RuntimeException(ex);
            }

            String[] list = Arrays.stream(merged)
                    .mapToObj(String::valueOf)
                    .toArray(String[]::new);
            label3.setText(String.join(" ", list));
            labelBelowButton3.setText(time+" MS");
        });

        setLocationRelativeTo(null);
    }


    public static int[] copyArray(int[] original) {
        int[] copy = new int[original.length];
        System.arraycopy(original, 0, copy, 0, original.length);
        return copy;
    }

    public static String getTime() {
        var endTime = System.currentTimeMillis();
        NumberFormat formatter = new DecimalFormat("#0.00000");
        return formatter.format((endTime - startTime) / 1000d);
    }
}
