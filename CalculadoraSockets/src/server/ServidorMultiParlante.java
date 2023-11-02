package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class ServidorMultiParlante extends Thread {
    private final Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private final int idSession;

    public ServidorMultiParlante(Socket socket, int id) {
        this.socket = socket;
        this.idSession = id;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(ServidorMultiParlante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(ServidorMultiParlante.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                var payload = dis.readUTF();
                var clientState = ServidorThread.clientStates.get(idSession);
                System.out.println("Client "+ idSession + " in state: "+ clientState.currentAction);
                var actionhandler = new ActionHandler(dos,clientState);

                switch (clientState.currentAction) {
                    case OPERATOR ->
                        actionhandler.handleOperator(payload);
                    case FIRST_NUMBER ->
                        actionhandler.handleFirstNumber(payload);
                    case SECOND_NUMBER ->
                        actionhandler.handleSecondNumber(payload);
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorMultiParlante.class.getName()).log(Level.SEVERE, null, ex);
        }
        disconnect();
    }
}
