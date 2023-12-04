package server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorThread {
    static final int PORT = 42069;
    static final HashMap<Integer,Integer> clientStates = new HashMap<>();

    public static void main(String[] args){
        try{
        Registry rmi = LocateRegistry.createRegistry(PORT);
        System.setProperty("java.rmi.server.hostname","192.168.100.6");
        rmi.rebind("PrimeServer", new ServerImplementation());
        System.out.println("Servidor Activo");
        } catch (IOException ex) {
            Logger.getLogger(ServidorThread.class.getName () ).log(Level.SEVERE, null, ex);
        }
    }
}
