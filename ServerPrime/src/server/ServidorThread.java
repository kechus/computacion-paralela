package server;
import types.IServerMethods;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorThread {
    static final int PORT = 42069;

    public static void main(String[] args){
        try{
            System.setProperty("java.rmi.server.hostname","192.168.100.5");
        var server = new ServerImplementation();
        var casted =(IServerMethods) UnicastRemoteObject.exportObject(server,0);
        var rmi = LocateRegistry.createRegistry(PORT);
        rmi.rebind("server", casted);
        System.out.println("Servidor Activo");
        } catch (IOException ex) {
            Logger.getLogger(ServidorThread.class.getName () ).log(Level.SEVERE, null, ex);
        }
    }
}
