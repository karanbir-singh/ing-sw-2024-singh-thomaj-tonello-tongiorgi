package it.polimi.ingsw.gc26.socket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.model.player.Player;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.function.Function;


public class SocketClient implements VirtualView {
    private final static String filePath = "src/main/resources/envClient.json";
    private final BufferedReader inputFromServer;
    private final VirtualSocketServer server;
    private final Player user;

    protected SocketClient(BufferedReader input, BufferedWriter output, String username) {
        this.inputFromServer = input;
        this.server = new VirtualSocketServer(output);
        this.user = new Player("0", username);
    }

    private void run() throws RemoteException {

        new Thread(() -> {
            try {
                this.runVirtualServer();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }).start();
        this.runCLI();
    }

    private void runVirtualServer() throws IOException {
        String line;
        while((line = inputFromServer.readLine()) != null) {
            //switch per capire il comando
            this.showMessage(new Message(line));
        }
    }

    private void runCLI() throws RemoteException {
        Scanner scan  = new Scanner(System.in);
        while (true) {
            //System.out.println("> ");
            String line = scan.nextLine();
            String receiver = "";
            if (line.startsWith("/")) {
                receiver = line.substring(1, line.indexOf(" "));
                line = line.substring(line.indexOf(" ")+1);
            }
            this.server.addMessage(new Message(line, new Player("0",receiver), this.user, null));
        }
    }

    public void showMessage(Message message) {
        System.out.println(message);
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
        String username = simpleLogin(); System.out.println("\n");
        new SocketClient(new BufferedReader(socketRx), new BufferedWriter(socketTx), username).run();
    }

    private static String simpleLogin() {
        Scanner scan  = new Scanner(System.in);
        System.out.println("Insert your name: ");
        return scan.nextLine();
    }

    private void wrap_message(Callable<Object> function, String values) {

    }
}
