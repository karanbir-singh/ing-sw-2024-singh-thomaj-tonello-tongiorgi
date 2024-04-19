package it.polimi.ingsw.gc26.network.socket.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.controller.GameController;
import it.polimi.ingsw.gc26.controller.MainController;
import it.polimi.ingsw.gc26.model.game.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServer {
    public final static String filePath = "src/main/resources/envServer.json";
    private final ArrayList<SocketClientHandler> clients = new ArrayList<>();
    private final ServerSocket listenSocket;
    private final MainController controller;

    public SocketServer(ServerSocket listenSocket, MainController controller) {
        this.listenSocket = listenSocket;
        this.controller = controller;
    }

    public void runServer() throws  IOException {
        Socket clientSocket = null;
        System.out.println(STR."Starting Socket Server... \nListening in port: \{this.listenSocket.getLocalPort()}");
        while ((clientSocket = this.listenSocket.accept()) != null) {
            InputStreamReader socketRx = new InputStreamReader(clientSocket.getInputStream());
            OutputStreamWriter socketTx = new OutputStreamWriter(clientSocket.getOutputStream());

            SocketClientHandler handler = new SocketClientHandler(this.controller, this, new BufferedReader(socketRx), new PrintWriter(socketTx));
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
