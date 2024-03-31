package it.polimi.ingsw.gc26.socket;

import java.io.BufferedWriter;
import java.io.PrintWriter;

public class VirtualSocketServer implements VirtualServer {
    private final PrintWriter output;

    public VirtualSocketServer(BufferedWriter output) {
        this.output = new PrintWriter(output);
    }

    //TODO mappare funzioni controller
}
