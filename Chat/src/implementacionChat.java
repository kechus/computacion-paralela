import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class implementacionChat extends UnicastRemoteObject implements chatServidor {
    public ArrayList<chatCliente> clientes;

    public implementacionChat() throws RemoteException {
        super();
        clientes = new ArrayList<chatCliente>();
    }

    @Override
    public void mensaje(String mensaje) throws RemoteException {
        for(var cliente : clientes){
            cliente.mensajeCliente(mensaje);
        }
    }

    @Override
    public void registro(chatCliente cliente) throws RemoteException {
        System.out.println("cliente conectado");
        this.clientes.add(cliente);
    }
}