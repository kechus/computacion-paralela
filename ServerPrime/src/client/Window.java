package client;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;

public class Window extends JFrame {
    private JLabel label1;
    private JLabel label3;

    int len;
    public Window(DataOutputStream dos, DataInputStream dis) {
        super("Programa");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 250);
        setLayout(new BorderLayout());

        label1 = new JLabel("Datos: ");
        label3 = new JLabel("");

        JPanel arrayPanel = new JPanel();
        arrayPanel.setLayout(new BoxLayout(arrayPanel, BoxLayout.Y_AXIS));
        arrayPanel.add(label1);
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
            label3.setText("");
        });
        inputButton.addActionListener(e -> {
            inputButton.setEnabled(false);
            textField.setEnabled(false);
            len = Integer.parseInt(textField.getText());
            label1.setText("Longitud: " + len);
        });

        button1.addActionListener(e -> {
//            var elapsedTime = PrimeNumbers.main(len);
//            labelBelowButton1.setText( elapsedTime / 1e6 + " ms");
        });

        button2.addActionListener(e -> {
//            var elapsedTime = PrimeNumbersWithForkJoin.main(len);
//            labelBelowButton2.setText( elapsedTime / 1e6 + " ms");
        });

        button3.addActionListener(e -> {
//            var elapsedTime = PrimeNumbersWithExecutorService.main(len);
//            labelBelowButton3.setText( elapsedTime / 1e6 + " ms");
        });

        Button openSearchButton = new Button("Encuentra números");
//        openSearchButton.addActionListener(e -> new Finder("Encuentra números"));
        add(openSearchButton, BorderLayout.NORTH);

        setLocationRelativeTo(null);
    }
}
