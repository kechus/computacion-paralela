package server;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionHandler {
    private final DataOutputStream dos;
    ActionHandler(DataOutputStream dos){
        this.dos = dos;
    }
public void handleSequential(String payload) throws IOException {
        try {
            var a = Double.parseDouble(payload);
            dos.writeUTF("success");
        } catch (Exception ex) {
            dos.writeUTF("error"); Logger.getLogger(ActionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleForkJoin(String payload) throws IOException {
        try {
            dos.writeUTF("success");
        } catch (Exception ex) {
            dos.writeUTF("error");
            Logger.getLogger(ActionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleExecutorService(String payload) throws IOException {
        try {
            dos.writeUTF(payload+"");
        } catch (Exception ex) {
            dos.writeUTF("error");
            Logger.getLogger(ActionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
