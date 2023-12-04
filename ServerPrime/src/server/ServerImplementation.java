package server;

import types.IClientActions;
import types.IServerMethods;
import types.ClientActions;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;

class ServerImplementation extends UnicastRemoteObject implements IServerMethods {
    static final HashMap<Integer,IClientActions> clientStates = new HashMap<>();
    public ServerImplementation() throws RemoteException {
        super();
    }

    @Override
    public void register(int id,IClientActions client) throws RemoteException {
        System.out.println("cliente conectado");
        clientStates.put(id,client);
    }

    @Override
    public void makeAction(int id, ClientActions userAction, int n) throws RemoteException {
        try {
            var client = clientStates.get(id);
            var actionHandler = new ActionHandler(client);
            switch (userAction) {
                case SEQUENTIAL:
                    actionHandler.handleSequential(n);
                    break;
                case FIND:
                    break;
                case FORK_JOIN:
                    actionHandler.handleForkJoin(n);
                    break;
                case EXECUTOR_SERVICE:
                    actionHandler.handleExecutorService(n);
                    break;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
