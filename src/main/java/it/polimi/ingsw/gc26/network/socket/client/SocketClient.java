package it.polimi.ingsw.gc26.network.socket.client;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;

import java.io.*;
import java.rmi.RemoteException;
import java.util.Scanner;

public class SocketClient {
    private final VirtualMainController virtualMainController;
    private VirtualGameController virtualGameController;
    private final BufferedWriter output;
    private final SocketServerHandler handler;
    protected String nickname = null;
    protected String clientID = null;

    public SocketClient(BufferedReader input, BufferedWriter output) {
        this.virtualMainController = new VirtualSocketMainController(output);
        this.handler = new SocketServerHandler(this, input);
        this.output = output;
        this.clientID = "";

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

    public VirtualGameController runTUI() throws RemoteException {
        this.runServerListener();
        // Start CLI
        Scanner scan  = new Scanner(System.in);

        // TODO gestire la Remote Exception
        //Initial state in CONNECTION
        System.out.println("Connected to the server successfully!");
        System.out.println("Insert your nickname: ");
        this.nickname = scan.nextLine();
        this.virtualMainController.connect(this.handler, this.nickname);

        // wait to give the server the time to update the state
        while(true) {
            synchronized (this.clientID) {
                if (this.clientID != "") {break;}
            }
        };
        while(true) {
            synchronized (this.handler.clientState) {
                if (this.handler.changeState == true) {
                    this.handler.changeState = false;
                    if (handler.clientState == ClientState.INVALID_NICKNAME || handler.clientState == ClientState.CONNECTION) {
                        System.out.println("Nickname not available \nInsert new nickname: ");
                        this.nickname = scan.nextLine();
                        this.virtualMainController.connect(this.handler, this.nickname);
                    } else {
                        break;
                    }
                }
            }
        }

        if (handler.clientState == ClientState.CREATOR) {
            System.out.println("You must initialize a new game \n Insert number of players: ");
            Integer numberPlayers = Integer.parseInt(scan.nextLine());
            this.virtualMainController.createWaitingList(this.handler, this.clientID, this.nickname, numberPlayers);
        }
        System.out.println("Waiting for other players ...");

        while (true) {
            synchronized (this.handler.clientState) {
                if (handler.clientState == ClientState.BEGIN){
                    break;
                }
            }
        }

        this.virtualMainController.getVirtualGameController();
        System.out.println("Game begin");
        return this;

        while (true) {
            //game started
            boolean chat = false;
            String line = scan.nextLine();
            String receiver = "";
            if (line.startsWith("/chat")) {
                chat = true;
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
                this.virtualGameController.addMessage(line, receiver, this.clientID, "");
            }
        }
    }

    public void runGUI(){
        this.runServerListener();

        // TODO
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public void setVirtualGameController() {
        this.virtualGameController = new VirtualSocketGameController(this.output);
    }


}

