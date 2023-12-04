import types.IServerMethods;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

public class Main {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        var rmi = LocateRegistry.getRegistry("localhost",42069);
        var server = (IServerMethods) rmi.lookup("PrimeServer");
        new Thread(new PrimeClient(server)).start();
    }
}