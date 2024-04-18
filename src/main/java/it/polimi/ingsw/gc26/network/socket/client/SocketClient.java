package it.polimi.ingsw.gc26.network.socket.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.network.VirtualView;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Scanner;


public class SocketClient implements VirtualView {
    private final static String filePath = "src/main/resources/envClient.json";
    private final BufferedReader inputFromServer;
    private final VirtualSocketMainController virtualMainController;
    private final VirtualSocketGameController virtualGameController;
    private String username;

    protected SocketClient(BufferedReader input, BufferedWriter output, String username) {
        this.inputFromServer = input;
        this.virtualMainController = new VirtualSocketMainController(output);
        this.username = username;
        this.virtualGameController = new VirtualSocketGameController(output);
    }

    private void run() throws RemoteException {

        new Thread(() -> {
            try {
                this.runVirtualServer();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        this.runCLI();
    }

    private void runVirtualServer() throws IOException {
        String line;
        while((line = inputFromServer.readLine()) != null) {
            JsonNode root = null;
            try {
                ObjectMapper JsonMapper = new ObjectMapper();
                root = JsonMapper.readTree(line);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            }

            switch (root.get("function").asText()) {
                case "showMessage":
                    this.showMessage(root.get("value").asText());
                    break;
                case "reportMessage":
                    this.reportMessage(root.get("value").asText());
                    break;
                case "reportError":
                    this.reportError(root.get("value").asText());
                    break;
            }
        }
    }

    private void runCLI() throws RemoteException {
        Scanner scan  = new Scanner(System.in);
        while (true) {
            //System.out.println("> ");
            boolean chat = false;
            String line = scan.nextLine();
            if (this.username.equals("")) {
                this.username = line;
            }

            String receiver = "";
            if (line.startsWith("/chat")) {
                chat = true;
                receiver = line.substring(1, line.indexOf(" "));
                line = line.substring(line.indexOf(" ")+1);
                if (line.startsWith("/")) {
                    receiver = line.substring(1, line.indexOf(" "));
                    line = line.substring(line.indexOf(" ")+1);
                }
            }

            switch (line) {
                case "/1":
                    this.virtualGameController.selectCardFromHand(0, this.username);
                    break;
                case "/2":
                    this.virtualGameController.turnSelectedCardSide(this.username);
                    break;
                case "/3":
                    this.virtualGameController.selectPositionOnBoard(0, 0, this.username);
                    break;
                case "/4":
                    this.virtualGameController.playCardFromHand(this.username);
                    break;
                case "/5":
                    this.virtualGameController.selectCardFromCommonTable(0, 0, this.username);
                    break;
                case "/6":
                    this.virtualGameController.drawSelectedCard(this.username);
                    break;
            }

            if (chat) {
                this.virtualGameController.addMessage(line, receiver, this.username, "");
            } else {
                //this.virtualGameController.sendText(line);
                this.virtualMainController.connect(this, this.username);
            }
        }
    }

    public void showMessage(String line) {
        JsonNode message = null;
        try {
            ObjectMapper JsonMapper = new ObjectMapper();
            message = JsonMapper.readTree(line);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("[" + message.get("sender").asText() + "]: " + message.get("text").asText());
    }

    @Override
    public void reportMessage(String message) {
        System.out.println("[SERVER]: " + message);
    }

    public void reportError(String errorMessage) {
        System.out.println("[ERROR]: " + errorMessage);
    }
    public static void main(String[] args) throws IOException {
        String hostName;
        int portNumber;
        if (args.length == 2) {
            hostName = args[0];
            portNumber = Integer.parseInt(args[1]);
        } else {
            ObjectMapper JsonMapper = new ObjectMapper();
            JsonNode root = JsonMapper.readTree(new FileReader(SocketClient.filePath));
            hostName = root.get("hostName").asText();
            portNumber = root.get("portNumber").asInt();
        }

        Socket serverSocket = new Socket(hostName, portNumber);
        InputStreamReader socketRx = new InputStreamReader(serverSocket.getInputStream());
        OutputStreamWriter socketTx = new OutputStreamWriter(serverSocket.getOutputStream());
        new SocketClient(new BufferedReader(socketRx), new BufferedWriter(socketTx), "").run();
    }

    private static String simpleLogin() {
        Scanner scan  = new Scanner(System.in);
        System.out.println("Insert your name: ");
        return scan.nextLine();
    }

}


