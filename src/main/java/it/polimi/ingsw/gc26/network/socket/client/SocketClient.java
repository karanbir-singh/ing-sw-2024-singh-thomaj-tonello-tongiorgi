package it.polimi.ingsw.gc26.network.socket.client;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;

import java.io.*;
import java.rmi.RemoteException;
import java.util.Scanner;

public class SocketClient {
    private final VirtualMainController virtualMainController;
    private final VirtualGameController virtualGameController;
    private final SocketServerHandler handler;
    private String nickname = null;
    private String clientID = null;

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
            // TODO gestire la Remote Exception
            //Initial state in CONNECTION
            System.out.println("Connected to the server successfully!");
            System.out.println("Insert your nickname: ");
            this.nickname = scan.nextLine();
            this.virtualMainController.connect(this.handler, this.nickname);

            while (handler.clientState == ClientState.INVALID_NICKNAME || handler.clientState == ClientState.CONNECTION) {
                System.out.println("Nickname not available \nInsert new nickname: ");
                this.nickname = scan.nextLine();
                this.virtualMainController.connect(this.handler, this.nickname);
            }

            if (handler.clientState == ClientState.CREATOR) {
                System.out.println("You must initialize a new game \n Insert number of players: ");
                Integer numberPlayers = Integer.parseInt(scan.nextLine());
                this.virtualMainController.createWaitingList(this.handler, this.clientID, this.nickname, numberPlayers);
            }
            System.out.println("Waiting for other players ...");
            while (handler.clientState == ClientState.WAITING){
                System.out.flush();
            }

            this.virtualMainController.getVirtualGameController();
            System.out.println("Game begin");

            //game started
            boolean chat = false;
            String line = scan.nextLine();
            if (this.nickname == null) {
                this.nickname = line;
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
                    this.virtualGameController.selectCardFromHand(0, this.nickname);
                    break;
                case "/2":
                    this.virtualGameController.turnSelectedCardSide(this.nickname);
                    break;
                case "/3":
                    this.virtualGameController.selectPositionOnBoard(0, 0, this.nickname);
                    break;
                case "/4":
                    this.virtualGameController.playCardFromHand(this.nickname);
                    break;
                case "/5":
                    this.virtualGameController.selectCardFromCommonTable(0, 0, this.nickname);
                    break;
                case "/6":
                    this.virtualGameController.drawSelectedCard(this.nickname);
                    break;
            }

            if (chat) {
                this.virtualGameController.addMessage(line, receiver, this.nickname, "");
            } else {
                //this.virtualGameController.sendText(line);
                this.virtualMainController.connect(this.handler , this.nickname);
            }
        }
    }

    public void runGUI(){
        this.runServerListener();

        // TODO
    }


}

