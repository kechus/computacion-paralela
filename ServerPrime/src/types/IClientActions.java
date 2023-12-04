package types;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IClientActions extends Remote {
    void sequential(int n) throws RemoteException;
    void forkJoin(int n) throws RemoteException;
    void executorService(int n) throws RemoteException;
    void find(int n) throws RemoteException;
}
