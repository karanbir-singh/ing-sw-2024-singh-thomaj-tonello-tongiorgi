package it.polimi.ingsw.gc26.socket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.GameController;
import it.polimi.ingsw.gc26.MainController;
import it.polimi.ingsw.gc26.model.game.Message;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServer {
    private final static String filePath = "src/main/resources/envServer.json";
    private final ArrayList<SocketClientHandler> clients = new ArrayList<>();
    private final ServerSocket listenSocket;
    private final MainController controller;
    public SocketServer(ServerSocket listenSocket, MainController controller) {
        this.listenSocket = listenSocket;
        this.controller = controller;
    }

    private void runServer() throws  IOException {
        Socket clientSocket = null;
        System.out.println("Listening in port:" + this.listenSocket.getLocalPort());
        while ((clientSocket = this.listenSocket.accept()) != null) {
            InputStreamReader socketRx = new InputStreamReader(clientSocket.getInputStream());
            OutputStreamWriter socketTx = new OutputStreamWriter(clientSocket.getOutputStream());

            SocketClientHandler handler = new SocketClientHandler(this.controller, this, new BufferedReader(socketRx), new PrintWriter(socketTx));
            synchronized (this.clients) { this.clients.add(handler);}
            new Thread(() -> {
                try {
                    handler.runVirtualView();
                } catch (IOException e) {
                    throw new RuntimeException();
                }
            }).start();
        }
    }

    public void broadCastUpdate(Message message, SocketClientHandler sender) {
        synchronized (this.clients) {
            for (var client : this.clients) {
                if (!client.equals(sender)) {
                    client.showMessage(message);
                }
            }
        }

    }
    public static void main(String[] args) throws IOException {
        String hostName;
        int portNumber;
        if (args.length == 2) {
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
        } else {
            ObjectMapper JsonMapper = new ObjectMapper();
            JsonNode root = JsonMapper.readTree(new FileReader(SocketServer.filePath));
            hostName = root.get("hostName").asText();
            portNumber = root.get("portNumber").asInt();
        }
        try {
            ServerSocket listenSocket = new ServerSocket(portNumber);
            new SocketServer(listenSocket, new MainController()).runServer(); //TODO qui ci va il main controller
        } catch (Exception e) {
            //TODO handle exception
        }

    }

}
