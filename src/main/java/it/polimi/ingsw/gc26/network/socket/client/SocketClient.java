package it.polimi.ingsw.gc26.network.socket.client;

import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;

import java.io.*;
import java.rmi.RemoteException;
import java.util.Scanner;

public class SocketClient {
    private final VirtualMainController virtualMainController;
    private final VirtualGameController virtualGameController;
    private final SocketServerHandler handler;
    private final String username;

    public SocketClient(BufferedReader input, BufferedWriter output) {
        this.virtualMainController = new VirtualSocketMainController(output);
        // TODO create virtual game controller only when it's available
        this.virtualGameController = new VirtualSocketGameController(output);
        this.handler = new SocketServerHandler(input);

    }

    private void runServerListener() {
        // Create a thread for listening server
        new Thread(() -> {
            try {
                this.handler.onServerListening();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void runTUI() throws RemoteException {
        this.runServerListener();
        // Start CLI
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

    public void runGUI(){
        this.runServerListener();

        // TODO
    }


}

