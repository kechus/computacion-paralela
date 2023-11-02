import java.io.*;
import java.net.Socket;

public class Cliente {
    public static final int PUERTO = 8080;
    public static final String HOST = "localhost";

    public static void main(String[] args) throws IOException {
        System.out.println("addr = " + HOST);
        Socket socket = new Socket(HOST, PUERTO);
        try {
            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter salida = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            salida.println("Usuario: " + "Hola mundo jiji");
            String str = entrada.readLine();
            System.out.println(str);
            salida.println("FIN");
        } finally {
            System.out.println("cerrando ... ");
            socket.close();
        }
    }
}
