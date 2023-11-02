import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class servidorRMI {
    public static void main(String[] args) {
        try {
            Registry rmi = LocateRegistry.createRegistry(42069);
            System.setProperty("java.rmi.server.hostname","192.168.232.12");
            rmi.rebind("Chat", (Remote) new implementacionChat());
            System.out.println("Servidor Activo");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}