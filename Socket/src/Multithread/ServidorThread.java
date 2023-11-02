package Multithread;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorThread {
    static final int PUERTO = 42069;

    public static void main(String[] args) throws IOException {
        ServerSocket ss;
        System.out.print("Inicializando servidor... ");
        try {
            ss = new ServerSocket(PUERTO);
            System.out.println("\t[OK]");
            int idSession = 0;
            while (true) {
                Socket socket;
                socket = ss.accept();
                System.out.println("Nueva conexién entrante: " + socket);
                ((ServidorMultiParlante) new ServidorMultiParlante(socket, idSession)).start();
                idSession++;
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorThread.class.getName () ).log(Level.SEVERE, null, ex);
        }
    }


}
