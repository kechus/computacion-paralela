package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorThread {
    static final int PUERTO = 42069;
    static final HashMap<Integer,ClientState> clientStates = new HashMap<>();

    public static void main(String[] args) {
        System.out.print("Inicializando servidor... ");
        try {
            ServerSocket ss;
            ss = new ServerSocket(PUERTO);
            System.out.println("\t[OK]");
            int idSession = 0;
            while (true) {
                var socket = ss.accept();
                System.out.println("Nueva conexién entrante: " + socket);
                clientStates.put(idSession,new ClientState());
                (new ServidorMultiParlante(socket, idSession)).start();
                idSession++;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorThread.class.getName () ).log(Level.SEVERE, null, ex);
        }
    }
}
