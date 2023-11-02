import java.rmi.Remote;
import java.rmi.RemoteException;

public interface chatCliente extends Remote {
    void mensajeCliente(String mensaje) throws RemoteException;
}