package client;

import types.IClientActions;
import types.IServerMethods;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrimeClient extends UnicastRemoteObject implements IClientActions ,Runnable{
    private final Window window;
    protected PrimeClient(IServerMethods server) throws RemoteException {
        server.register(ClienteThread.id, this);
        window = new Window(server);
        window.setVisible(true);
        System.out.println("Conectado");
    }

    @Override
    public void sequential(int n) throws RemoteException {
        window.sequential(n);
    }

    @Override
    public void forkJoin(int n) throws RemoteException {
    }

    @Override
    public void executorService(int n) throws RemoteException {
    }

    @Override
    public void find(int n) throws RemoteException {
    }

    @Override
    public void run() {
        while(true){}
    }
}
