import client.ClienteThread;
import server.ServidorThread;

public class Main {
    public static void main(String[] args) {
        ServidorThread.main(new String[1]);
        ClienteThread.main(new String[1]);
        while (true){}
    }
}