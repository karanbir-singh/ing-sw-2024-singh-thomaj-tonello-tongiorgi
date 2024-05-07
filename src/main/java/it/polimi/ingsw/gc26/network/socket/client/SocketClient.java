package it.polimi.ingsw.gc26.network.socket.client;

import com.sun.tools.javac.Main;
import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;
import javafx.util.Pair;

import java.io.*;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * This class represents the client's socket. It is used only during the creation of the game, initializing the TUI or GUI.
 */
public class SocketClient {
    /**
     * Main controller used before the game controller and the game are ready.
     */
    private final VirtualMainController virtualMainController;
    /**
     * Game controller used during the game.
     */
    private VirtualGameController virtualGameController;
    /**
     * BufferedWriter to send json to the server.
     */
    private final PrintWriter outputToServer;
    /**
     * Server handler to decode json from the server.
     */
    public final SocketServerHandler serverHandler;

    public MainClient mainClient;


    /**
     * Socket Client's constructor. Initializes the MainController.
     *
     * @param inputFromServer buffered reader from the server.
     * @param outputToServer  writer to the server.
     */
    public SocketClient(BufferedReader inputFromServer, PrintWriter outputToServer) {
        this.virtualMainController = new VirtualSocketMainController(outputToServer);
        this.virtualGameController = null;
        this.outputToServer = outputToServer;
        this.serverHandler = new SocketServerHandler(this, inputFromServer);
        this.runServerListener();
    }

    public void setMainClient(MainClient mainClient) {
        this.mainClient = mainClient;
    }

    /**
     * Create a thread to listen input from the server and handle its commands.
     */
    private void runServerListener() {
        new Thread(this.serverHandler).start();
    }


    /**
     * GUI VERSION
     * Asks user to set the parameters needed before the game starts, such as nickname and number of players.
     */
    public void runGUI() {
        this.runServerListener();

        // TODO
    }

    /**
     * Method used by the server to set the client's ID as an answer to connect()
     *
     * @param clientID
     */
    public void setClientID(String clientID) {
        this.mainClient.setClientID(clientID);
    }

    /**
     * Method used by the server to set the game controller as an answer to the change of state
     */
    public void setVirtualGameController() {
        this.virtualGameController = new VirtualSocketGameController(this.outputToServer);
        this.mainClient.setVirtualGameController(this.virtualGameController);
    }

    /**
     * Sets the state of the client
     *
     * @param clientState
     */
    public void setState(ClientState clientState) {
        this.mainClient.setClientState(clientState);
    }

    public String getClientID() {
        return this.mainClient.getClientID();
    }

    public String getNickname() {
        return this.mainClient.getNickname();
    }

    public VirtualMainController getVirtualMainController() {
        return this.virtualMainController;
    }
}

