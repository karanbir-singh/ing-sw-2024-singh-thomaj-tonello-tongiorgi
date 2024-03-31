package it.polimi.ingsw.gc26.socket;

import it.polimi.ingsw.gc26.GameController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import it.polimi.ingsw.gc26.model.Message;

/**
 * logica con cui vado a leggere i messaggi
 */
public class SocketClientHandler implements VirtualView {
    final GameController controller;
    final SocketServer server;
    final BufferedReader input;
    final PrintWriter output;

    public SocketClientHandler(GameController controller, SocketServer server, BufferedReader input, PrintWriter output) {
        this.controller = controller;
        this.server = server;
        this.input = input;
        this.output = output;
    }

    public void runVirtualView() throws IOException {
        String line;
        while ((line = input.readLine()) != null) {
            switch(line) {
                //logica
            }
        }
    }

    @Override
    public void showMessage(Message message) {
        synchronized (this.output) {
            this.output.println(message);
            this.output.flush();
        }
    }

    @Override
    public void reportError(String errorMessage) {
        synchronized (this.output) {
            this.output.println(errorMessage);
        }
    }
 }
