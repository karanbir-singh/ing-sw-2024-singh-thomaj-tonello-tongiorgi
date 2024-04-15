package it.polimi.ingsw.gc26.socket;

import it.polimi.ingsw.gc26.model.game.Message;

import java.io.PrintWriter;

public class VirtualSocketClient implements  VirtualView {
    final PrintWriter outputToServer;

    public VirtualSocketClient(PrintWriter output) {
        this.outputToServer = output;
    }

    @Override
    public void showMessage(String message) {
        this.outputToServer.println(message);
        this.outputToServer.flush();
    }

    @Override
    public void reportMessage(String message) {
        String msg = STR."{\"function\": \"reportMessage\", \"value\" : \" \{message} \"}";
        this.outputToServer.println(msg);
        this.outputToServer.flush();
    }

    @Override
    public void reportError(String errorMessage) {
        String msg = STR."{\"function\": \"reportError\", \"value\" : \" \{errorMessage} \"}";
        this.outputToServer.println(msg);
        this.outputToServer.flush();

    }
}
