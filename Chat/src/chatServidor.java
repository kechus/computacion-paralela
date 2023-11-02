import java.rmi.Remote;
import java.rmi.RemoteException;

public interface chatServidor extends Remote {
    void registro(chatCliente cliente) throws RemoteException;

    default void mensaje(String mensaje) throws RemoteException {

    }

}