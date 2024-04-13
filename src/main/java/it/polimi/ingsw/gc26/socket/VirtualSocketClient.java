package it.polimi.ingsw.gc26.socket;

import it.polimi.ingsw.gc26.model.game.Message;

import java.io.PrintWriter;

public class VirtualSocketClient implements  VirtualView {
    final PrintWriter outputToServer;

    public VirtualSocketClient(PrintWriter output) {
        this.outputToServer = output;
    }

    @Override
    public void showMessage(Message message) {
        this.outputToServer.println(message.toString());
    }

    @Override
    public void reportError(String errorMessage) {
        this.outputToServer.println(errorMessage);

    }
}
