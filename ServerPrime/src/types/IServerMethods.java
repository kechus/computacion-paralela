package types;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerMethods extends Remote {
    int register() throws RemoteException;
    PrimeResults calculate(int id, ClientActions action) throws RemoteException;
    void setLenght(int id,int n)throws RemoteException ;
}
