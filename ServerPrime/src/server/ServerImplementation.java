package server;

import types.IClientActions;
import types.IServerMethods;
import types.ClientActions;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

class ServerImplementation extends UnicastRemoteObject implements IServerMethods {
    static final HashMap<Integer,ClientActions> clientStates = new HashMap<>();
    public ServerImplementation() throws RemoteException {
        super();
    }

    @Override
    public void register(int id,IClientActions client) throws RemoteException {
        System.out.println("cliente conectado");
    }

    @Override
    public void makeAction(int id, ClientActions userAction) throws RemoteException {
//        var actionHandler = new ActionHandler(dos);
        switch (userAction) {
            case SEQUENTIAL:
                break;
            case FIND:
                break;
            case FORK_JOIN:
                break;
            case EXECUTOR_SERVICE:
                break;
        }
    }
}
