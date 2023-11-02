package Multithread;

import java.io.IOException;
import java.util.ArrayList;

public class ClienteThread {
    static final int MAX_HILOS = 10;
    public static final String HOST = "localhost";

    public static void main(String[] args) throws IOException, InterruptedException {
        ArrayList<Thread> clients = new ArrayList<Thread>();
        for (int i = 0; i < 5; i++) {
            clients.add(new HiloClienteParlante(i));
        }
        for (Thread thread : clients) {
            thread.start();
        }
    }
}
