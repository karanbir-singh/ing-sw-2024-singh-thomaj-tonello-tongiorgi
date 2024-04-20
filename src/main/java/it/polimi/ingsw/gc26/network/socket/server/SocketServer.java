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
     * File's path with the server configuration
     */
    public final static String filePath = "src/main/resources/envServer.json";
    /**
     * This attribute represents all the client handlers connected to the server
     */
    private final ArrayList<SocketClientHandler> clients = new ArrayList<>();
    /**
     * This attribute represents the Server socket
     */
    private final ServerSocket listenSocket;
    /**
     * This attribute represents the main controller to be given in to the handlers
     */
    private final MainController controller;

    /**
     * Socket server constructor. It initializes the listener socket and the controller
     * @param listenSocket
     * @param controller
     */
    public SocketServer(ServerSocket listenSocket, MainController controller) {
        this.listenSocket = listenSocket;
        this.controller = controller;
    }

    /**
     * Starts an infinite loop listening to accept clients and creates its handlers
     * @throws IOException
     */
    public void runServer() throws  IOException {
        Socket clientSocket = null;
        System.out.println(STR."Starting Socket Server... \nListening in port: \{this.listenSocket.getLocalPort()}");
        while ((clientSocket = this.listenSocket.accept()) != null) {
            InputStreamReader socketRx = new InputStreamReader(clientSocket.getInputStream());
            OutputStreamWriter socketTx = new OutputStreamWriter(clientSocket.getOutputStream());

            SocketClientHandler handler = new SocketClientHandler(this.controller, new BufferedReader(socketRx), new PrintWriter(socketTx));
            synchronized (this.clients) { this.clients.add(handler);}
            new Thread(() -> {
                try {
                    handler.runClientHandler();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

}
