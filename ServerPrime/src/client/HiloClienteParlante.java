package client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloClienteParlante extends Thread {
    protected Socket sk;
    protected DataOutputStream dos;
    protected DataInputStream dis;
    public HiloClienteParlante() {
    }

    @Override
    public void run() {
        try {
            sk = new Socket("localhost",42069);
            dos = new DataOutputStream(sk.getOutputStream());
            dis = new DataInputStream(sk.getInputStream());
            new Window(dos,dis).setVisible(true);
            while (true){}
        } catch (IOException e) {
            Logger.getLogger(HiloClienteParlante.class.getName()).log(Level.SEVERE,null,e);
        }
    }
}
