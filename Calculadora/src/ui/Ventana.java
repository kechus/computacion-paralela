package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

import operaciones.*;

public class Ventana extends JFrame implements ActionListener {
    private String value = "";
    private final JLabel label;
    private double a;
    private final Operacion operacion;
    private String simboloDeOperacion;
    private final ArrayList<JButton> botonesDeOperacion = new ArrayList<>();

    public Ventana() {
        setTitle("Calculadora");
        setSize(500,500);

        createButtons();
        label = new JLabel(value);
        add(label);

        setLayout(new GridLayout(3,3));
        setVisible(true);

        operacion = new Operador();
    }

    private void createButtons(){
        for(int i = 0; i < 10; i++){
            var btn = new JButton(String.valueOf(i));
            add(btn);
            btn.addActionListener(e-> appendToValue(btn.getText()));
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

        for(var btn : botonesDeOperacion){
            add(btn);
           btn.addActionListener(this);
        }
   }

   void setFirstValue(String operation){
        this.simboloDeOperacion = operation;
        this.a = Double.parseDouble(value);
        value = "";
        label.setText("");
   }

   void makeOperation(){
        var b = Double.parseDouble(value);
        double res = 0;

       switch (simboloDeOperacion) {
           case "+" -> res = operacion.suma(a,b);
           case "-" -> res = operacion.resta(a,b);
           case "*" -> res = operacion.multiplicacion(a,b);
           case "/" -> res = operacion.division(a,b);
           case "%"-> res = operacion.modulo(a,b);
       }
       label.setText(String.valueOf(res));
    }

   void appendToValue(String a){
       value = value+a;
       label.setText(value);
   }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(var btn : botonesDeOperacion){
            if(e.getSource()==btn&& Objects.equals(btn.getText(), "=")){
                makeOperation();
               return;
            }

            if(e.getSource() == btn && Objects.equals(btn.getText(),"√")){
                var res = operacion.raizCuadrada(Double.parseDouble(value));
                value="";
                label.setText(String.valueOf(res));
                return;
            }

            if(e.getSource() == btn && Objects.equals(btn.getText(),"C")){
                value = "";
                simboloDeOperacion = "";
                label.setText(value);
                return;
            }

            if(e.getSource() == btn && Objects.equals(btn.getText(),"⌫")){
               if(value.isEmpty()) return;

               value = value.substring(0,value.length()-1);
               label.setText(value);
               return;
            }

            if(e.getSource() == btn){
                setFirstValue(btn.getText());
                return;
            }
        }
    }
}
