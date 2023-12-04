package client;

import types.IServerMethods;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Random;

public class ClienteThread {
    static final String IP = "192.168.100.5";
    public static void main(String[] args) throws RemoteException, NotBoundException {
        var rmi = LocateRegistry.getRegistry( IP,42069);
        var server = (IServerMethods) rmi.lookup("server");
        new Thread(new PrimeClient(server)).start();
    }
}
