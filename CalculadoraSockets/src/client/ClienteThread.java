package client;

import java.io.IOException;
import java.util.ArrayList;

public class ClienteThread {
    static final int MAX_HILOS = 1;
    public static void main(String[] args) {
        var clients = new ArrayList<Thread>();
        for (int i = 0; i < MAX_HILOS; i++) {
            clients.add(new HiloClienteParlante());
        }
        for (Thread thread : clients) {
            thread.start();
        }
    }
}
