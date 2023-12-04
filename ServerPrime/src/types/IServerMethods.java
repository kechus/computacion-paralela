package types;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServerMethods extends Remote {
    void register(int id,IClientActions client) throws RemoteException;

    void makeAction(int id, ClientActions action) throws RemoteException;
}
