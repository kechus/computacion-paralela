import types.ClientActions;
import types.IClientActions;
import types.IServerMethods;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class PrimeClient extends UnicastRemoteObject implements IClientActions ,Runnable{
    protected PrimeClient(IServerMethods server) throws RemoteException {
        server.register(0, this);
    }

    @Override
    public void sequential(int n) throws RemoteException {

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

    }
}
