package client;

import types.ClientActions;
import types.IServerMethods;
import types.PrimeResults;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.Arrays;

public class Window extends JFrame {
//    private final JLabel label1;
//    private final JLabel label3;
    private final JLabel labelBelowButton1;
    private final JLabel labelBelowButton2;
    private final JLabel labelBelowButton3;
    int len;
    int id;

    public Window(IServerMethods server, int id) {
        super("Programa");
        this.id = id;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 250);
        setLayout(new BorderLayout());

//        label1 = new JLabel("Datos: ");
//        label3 = new JLabel("");

        JPanel arrayPanel = new JPanel();
        arrayPanel.setLayout(new FlowLayout());
//        arrayPanel.setLayout(new BoxLayout(arrayPanel, BoxLayout.Y_AXIS));
//        arrayPanel.add(label1);
//        arrayPanel.add(label3);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JTextField textField = new JTextField(15);
        JButton inputButton = new JButton("Aceptar longitud");
        JButton resetButton = new JButton("Reiniciar");

        arrayPanel.add(textField);
        arrayPanel.add(inputButton);
        arrayPanel.add(resetButton);

        // Agregar JTextAreas
        var textArea1 = new JLabel();
        var textArea2 = new JLabel();

        // Crear un JScrollPane para cada JTextArea
        JScrollPane scrollPane1 = new JScrollPane(textArea1);
        JScrollPane scrollPane2 = new JScrollPane(textArea2);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new GridLayout(1, 2));

        middlePanel.add(scrollPane1);
        middlePanel.add(scrollPane2);

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
        add(middlePanel, BorderLayout.CENTER); // Agregado el nuevo panel con JTextAreas
        add(bottomPanel, BorderLayout.SOUTH);

        resetButton.addActionListener(e -> {
            inputButton.setEnabled(true);
            textField.setEnabled(true);
//            label3.setText("");
        });
        inputButton.addActionListener(e -> {
            inputButton.setEnabled(false);
            textField.setEnabled(false);
            len = Integer.parseInt(textField.getText());
//            label1.setText("Longitud: " + len);
            try {
                server.setLenght(id,len*100);
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            }
        });

        button1.addActionListener(e -> {
            try {
                PrimeResults a;
                do {
                    Thread.sleep(1000);
                    a = server.calculate(id, ClientActions.SEQUENTIAL);
                }while (null == a);
                setText(a.time,labelBelowButton1);
                System.out.println(Arrays.toString(a.ownResults));
                System.out.println(Arrays.toString(a.remoteResults));
                textArea1.setText(Arrays.toString(a.ownResults));
                textArea2.setText(Arrays.toString(a.remoteResults));
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });

        button2.addActionListener(e -> {
            try {
                PrimeResults a;
                do {
                    Thread.sleep(1000);
                    a = server.calculate(id, ClientActions.FORK_JOIN);

                }while (null == a);
                setText(a.time,labelBelowButton2);
                textArea1.setText(Arrays.toString(a.ownResults));
                textArea2.setText(Arrays.toString(a.remoteResults));
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }

        });

        button3.addActionListener(e -> {
            try {
                PrimeResults a;
                do {
                    Thread.sleep(1000);
                    a = server.calculate(id, ClientActions.EXECUTOR_SERVICE);
                }while (null == a);
                setText(a.time,labelBelowButton3);
                textArea1.setText(Arrays.toString(a.ownResults));
                textArea2.setText(Arrays.toString(a.remoteResults));
            } catch (RemoteException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        });


        setLocationRelativeTo(null);
    }

    public void setText(long elapsedTime, JLabel label){
        label.setText( elapsedTime / 1e6 + " ms");
    }
}
