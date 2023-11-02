package server;

import operaciones.Operations;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionHandler {
    private final DataOutputStream dos;
    private final Operations operations;
    ClientState clientState;
    ActionHandler(DataOutputStream dos, ClientState clientState){
        this.dos = dos;
        operations = new Operations();
        this.clientState= clientState;
    }
    public void handleFirstNumber(String payload) throws IOException {
        try {
            clientState.a = Double.parseDouble(payload);
            clientState.currentAction = UserActions.OPERATOR;
            dos.writeUTF("success");
        } catch (Exception ex) {
            dos.writeUTF("error");
            Logger.getLogger(ActionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleOperator(String payload) throws IOException {
        try {
            clientState.operator = payload;
            if(payload.equals("√")){
                dos.writeUTF(Math.sqrt(clientState.a)+"");
                clientState.currentAction = UserActions.FIRST_NUMBER;
                return;
            }
            dos.writeUTF("success");
            clientState.currentAction = UserActions.SECOND_NUMBER;
        } catch (Exception ex) {
            dos.writeUTF("error");
            Logger.getLogger(ActionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleSecondNumber(String payload) throws IOException {
        try {
            var b = Double.parseDouble(payload);
            var a = clientState.a;

            double res = 0;
            switch (clientState.operator) {
                case "+" -> res = operations.suma(a,b);
                case "-" -> res = operations.resta(a,b);
                case "*" -> res = operations.multiplicacion(a,b);
                case "/" -> res = operations.division(a,b);
                case "%"-> res = operations.modulo(a,b);
            }
            clientState.currentAction = UserActions.FIRST_NUMBER;
            dos.writeUTF(res+"");
        } catch (Exception ex) {
            dos.writeUTF("error");
            Logger.getLogger(ActionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
