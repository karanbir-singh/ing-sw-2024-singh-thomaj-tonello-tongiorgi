package it.polimi.ingsw.gc26.socket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.model.Message;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.Scanner;

public class SocketClient implements VirtualView {
    private final static String filePath = "src/main/java/it.polimi.ingsw.gc26/socket/envClient.json";
    private final BufferedReader input;
    private final VirtualSocketServer server;

    protected SocketClient(BufferedReader input, BufferedWriter output) {
        this.input = input;
        this.server = new VirtualSocketServer(output);
    }

    private void run() throws RemoteException {
        new Thread(() -> {
            try {
                this.runVirtualServer();
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }).start();
        this.runCLi();
    }

    private void runVirtualServer() throws IOException {
        String line;
        while((line = input.readLine()) != null) {
            switch (line) {
                //TODO
            }
        }
    }

    private void runCLi() throws RemoteException {
        Scanner scan  = new Scanner(System.in);
        while (true) {
            System.out.println("> ");
        }
    }

    public void showMessage(Message message) {
        System.out.println("\n = " + message.getText());
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

        new SocketClient(new BufferedReader(socketRx), new BufferedWriter(socketTx)).run();
    }
}
