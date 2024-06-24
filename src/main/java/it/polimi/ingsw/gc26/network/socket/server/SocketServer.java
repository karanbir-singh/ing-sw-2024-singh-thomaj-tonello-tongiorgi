package it.polimi.ingsw.gc26.network.socket.server;

import it.polimi.ingsw.gc26.controller.MainController;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * This class represents the server for the socket implementation.
 * It creates a new client handler for each new socket client
 */
public class SocketServer {

    /**
     * This attribute represents all the client handlers connected to the server
     */
    private final ArrayList<SocketClientHandler> clients;
    /**
     * This attribute represents the Server socket
     */
    private final ServerSocket listenSocket;
    /**
     * This attribute represents the main controller to be given in to the handlers
     */
    private final MainController mainController;

    /**
     * Socket server constructor. It initializes the listener socket and the controller
     *
     * @param listenSocket
     * @param mainController
     */
    public SocketServer(ServerSocket listenSocket, MainController mainController) {
        this.listenSocket = listenSocket;
        this.mainController = mainController;
        this.clients = new ArrayList<>();
    }

    /**
     * Starts an infinite loop listening to accept clients and creates its handlers
     *
     * @throws IOException
     */
    public void runServer() throws IOException {
        Socket clientSocket;
        System.out.println("\nStarting Socket Server... \nListening in port: " + this.listenSocket.getLocalPort());

        // Keep server on listening for connection
        while ((clientSocket = this.listenSocket.accept()) != null) {
            System.out.println("Client " + clientSocket.getRemoteSocketAddress() + " connected");

            // Get input and out stream from the client
            InputStreamReader socketRx = new InputStreamReader(clientSocket.getInputStream());
            OutputStreamWriter socketTx = new OutputStreamWriter(clientSocket.getOutputStream());

            // Create client handler
            SocketClientHandler handler = new SocketClientHandler(this.mainController, new BufferedReader(socketRx), new BufferedWriter(socketTx));

            // Add to clients list
            synchronized (this.clients) {
                this.clients.add(handler);
            }

            // Launch a thread for listening clients requests
            new Thread(handler).start();
        }
    }

}
