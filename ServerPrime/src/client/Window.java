package client;

import types.ClientActions;
import types.IServerMethods;

import javax.swing.*;
import java.awt.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.rmi.RemoteException;

public class Window extends JFrame {
    private final JLabel label1;
    private final JLabel label3;
    private final JLabel labelBelowButton1;
    private final JLabel labelBelowButton2;
    private final JLabel labelBelowButton3;
    int len;

    public Window(IServerMethods server) {
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

        labelBelowButton1 = new JLabel("");
        labelBelowButton2 = new JLabel("");
        labelBelowButton3 = new JLabel("");

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
            try {
                server.makeAction(ClienteThread.id, ClientActions.SEQUENTIAL);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

        button2.addActionListener(e -> {
            try {
                server.makeAction(ClienteThread.id,ClientActions.FORK_JOIN);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

        button3.addActionListener(e -> {
            try {
                server.makeAction(ClienteThread.id,ClientActions.EXECUTOR_SERVICE);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

        Button openSearchButton = new Button("Encuentra números");
//        openSearchButton.addActionListener(e -> new client.Finder("Encuentra números"));
        add(openSearchButton, BorderLayout.NORTH);

        setLocationRelativeTo(null);
    }

    public void sequential(int elapsedTime){
        labelBelowButton1.setText( elapsedTime / 1e6 + " ms");
    }

    public void forkJoin(int elapsedTime){
        labelBelowButton2.setText( elapsedTime / 1e6 + " ms");
    }

    public void executorService(int elapsedTime){
        labelBelowButton3.setText( elapsedTime / 1e6 + " ms");
    }
}
