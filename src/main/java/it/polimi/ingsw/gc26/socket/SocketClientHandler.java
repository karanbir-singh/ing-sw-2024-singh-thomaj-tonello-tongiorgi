package it.polimi.ingsw.gc26.socket;

import it.polimi.ingsw.gc26.GameController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.model.player.Player;

/**
 * logica con cui vado a leggere i messaggi, Fa parte del server
 */
public class SocketClientHandler implements VirtualView {
    final GameController controller;
    final SocketServer server;
    final BufferedReader inputFromClient;
    final PrintWriter outputToClient;

    public SocketClientHandler(GameController controller, SocketServer server, BufferedReader input, PrintWriter output) {
        this.controller = controller;
        this.server = server;
        this.inputFromClient = input;
        this.outputToClient = output;
        System.out.println("New client!");
    }

    public void runVirtualView() throws IOException {
        String line;
        while ((line = inputFromClient.readLine()) != null) {
            Message message = new Message(line);
            System.out.println(message);
            this.controller.addMessage( message);
            this.server.broadCastUpdate(message, this);
        }
    }

    @Override
    public void showMessage(Message message) {
        synchronized (this.outputToClient) {
            this.outputToClient.println(message.toJson());
            this.outputToClient.flush();
        }
    }

    @Override
    public void reportError(String errorMessage) {
        synchronized (this.outputToClient) {
            this.outputToClient.println(errorMessage);
        }
    }
 }
