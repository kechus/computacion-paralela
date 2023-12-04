package server;

import types.IClientActions;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ActionHandler {
    private final IClientActions client;
    ActionHandler(IClientActions client){
        this.client = client;
    }
public void handleSequential(int payload) throws IOException {
        try {
            client.sequential(1000);
        } catch (Exception ex) {
            Logger.getLogger(ActionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleForkJoin(int payload) throws IOException {
        try {
        } catch (Exception ex) {
            Logger.getLogger(ActionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleExecutorService(int payload) throws IOException {
        try {
        } catch (Exception ex) {
            Logger.getLogger(ActionHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
