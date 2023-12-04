package client;

import types.IServerMethods;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Random;

public class ClienteThread {
    static final String IP = "192.168.100.6";
    static int id;
    public static void main(String[] args) throws RemoteException, NotBoundException {
        id = new Random().nextInt(1,100);
        var rmi = LocateRegistry.getRegistry( IP,42069);
        var server = (IServerMethods) rmi.lookup("PrimeServer");
        new Thread(new PrimeClient(server)).start();
    }
}
