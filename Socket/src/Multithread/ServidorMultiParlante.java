package Multithread;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

class ServidorMultiParlante extends Thread {
    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    private int idSessio;

    public ServidorMultiParlante(Socket socket, int id) {
        this.socket = socket;
        this.idSessio = id;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {

        }
    }

    public void desconectar() {
        try {
            socket.close();
        } catch (IOException ex) {
        }
    }

    @Override
    public void run() {
        String accion = "";
        try {
            accion = dis.readUTF();
            if (accion.equals("Hola")) {
                System.out.println("El cliente con idSesion " + this.idSessio + " saluda");
                dos.writeUTF("adios");
            }
        } catch (IOException ex) {
            Logger.getLogger(ServidorMultiParlante.class.getName()).log(Level.SEVERE,null,ex);
        }
        desconectar();
    }
}
