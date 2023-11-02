package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Window extends JFrame implements ActionListener {
    private String value = "";
    private final JLabel label;
    private final ArrayList<JButton> botonesDeOperacion = new ArrayList<>();

    protected DataOutputStream dos;
    protected DataInputStream dis;

    public Window(DataOutputStream dos, DataInputStream dis) {
        this.dos = dos;
        this.dis = dis;

        setTitle("Calculadora");
        setSize(500, 500);

        createButtons();
        label = new JLabel(value);
        add(label);

        setLayout(new GridLayout(3, 3));
        setVisible(true);

    }

    private void createButtons() {
        for (int i = 0; i < 10; i++) {
            var btn = new JButton(String.valueOf(i));
            add(btn);
            btn.addActionListener(e -> appendToValue(btn.getText()));
        }

        var suma = new JButton("+");
        botonesDeOperacion.add(suma);
        var resta = new JButton("-");
        botonesDeOperacion.add(resta);
        var multiplicacion = new JButton("*");
        botonesDeOperacion.add(multiplicacion);
        var division = new JButton("/");
        botonesDeOperacion.add(division);
        var modulo = new JButton("%");
        botonesDeOperacion.add(modulo);
        var sqrt = new JButton("√");
        botonesDeOperacion.add(sqrt);

        var remove = new JButton("⌫");
        botonesDeOperacion.add(remove);
        var igual = new JButton("=");
        botonesDeOperacion.add(igual);
        var c = new JButton("C");
        botonesDeOperacion.add(c);

        for (var btn : botonesDeOperacion) {
            add(btn);
            btn.addActionListener(this);
        }
    }

    void setFirstValue(String operation) {
        try {
            dos.writeUTF(value);
            System.out.println(dis.readUTF());
            dos.writeUTF(operation);
            System.out.println(dis.readUTF());
        } catch (Exception ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        value = "";
        label.setText("");
    }

    void makeOperation() {
        try {
            dos.writeUTF(value);
            label.setText(dis.readUTF());
        } catch (Exception ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void appendToValue(String a) {
        value = value + a;
        label.setText(value);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for (var btn : botonesDeOperacion) {
            if (e.getSource() == btn && Objects.equals(btn.getText(), "=")) {
                makeOperation();
                return;
            }

            if (e.getSource() == btn && Objects.equals(btn.getText(), "√")) {
                try {
                    dos.writeUTF(value);
                    System.out.println(dis.readUTF());
                    dos.writeUTF("√");
                    var res = dis.readUTF();
                    System.out.println(res);
                    value = "";
                    label.setText((res));
                } catch (Exception ex) {

                }
                return;
            }

            if (e.getSource() == btn && Objects.equals(btn.getText(), "C")) {
                value = "";
                label.setText(value);
                return;
            }

            if (e.getSource() == btn && Objects.equals(btn.getText(), "⌫")) {
                if (value.isEmpty()) return;

                value = value.substring(0, value.length() - 1);
                label.setText(value);
                return;
            }

            if (e.getSource() == btn) {
                setFirstValue(btn.getText());
                return;
            }
        }
    }
}
