package Multithread;

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
    private int id;
    public HiloClienteParlante (int id) {
            this.id = id;
    }

    @Override
    public void run() {
        try {
            sk = new Socket("localhost",42069);
            dos = new DataOutputStream(sk.getOutputStream());
            dis = new DataInputStream(sk.getInputStream());
            System.out.println(id+"Envia saludos");
            dos.writeUTF("Hola");
            String respuesta = "";
            respuesta = dis.readUTF();
            System.out.println(id+" servidor responde " +respuesta);
            dis.close();
            dos.close();
            sk.close();
        } catch (IOException e) {
            Logger.getLogger(HiloClienteParlante.class.getName()).log(Level.SEVERE,null,e);
        }
    }
}
