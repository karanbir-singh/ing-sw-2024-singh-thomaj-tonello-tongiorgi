package it.polimi.ingsw.gc26.client;

import it.polimi.ingsw.gc26.network.RMI.RMIServerPing;
import it.polimi.ingsw.gc26.network.RMI.VirtualRMIView;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.socket.SocketServerPing;
import it.polimi.ingsw.gc26.network.socket.client.SocketServerHandler;
import it.polimi.ingsw.gc26.network.socket.client.VirtualSocketMainController;
import it.polimi.ingsw.gc26.network.socket.server.VirtualSocketView;

import java.io.*;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class TUIApplication {
    MainClient mainClient;

    public void init(MainClient.NetworkType networkType , String ip, int socketPort, int rmiPort, String remoteObjectName) throws NotBoundException, IOException {
        if(networkType.equals(MainClient.NetworkType.rmi)){
            this.startRMIClient(ip,rmiPort, remoteObjectName);
            new Thread(new RMIServerPing(this.mainClient)).start();
        }else if(networkType.equals(MainClient.NetworkType.socket)){
            this.startSocketClient(ip,socketPort);
            new Thread(new SocketServerPing(this.mainClient)).start();
        }
        this.runConnectionTUI();
        this.runGameTUI();


    }


    public void runConnectionTUI() throws RemoteException {
        //Initial state in CONNECTION
        Scanner scanner = new Scanner(System.in);

        System.out.println("Insert your nickname: ");
        String nickname = scanner.nextLine();
        this.mainClient.getVirtualMainController().connect(this.mainClient.getVirtualView(), nickname, this.mainClient.getClientState());

        synchronized (this.mainClient.getLock()) {
            while (this.mainClient.getClientState() == ClientState.CONNECTION) {
                try {
                    mainClient.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (this.mainClient.getClientState() == ClientState.CREATOR) {
            System.out.println("You must initialize a new game \nInsert number of players: (2/3/4)");
            String input = scanner.nextLine();
            this.mainClient.getVirtualMainController().createWaitingList(this.mainClient.getVirtualView(), nickname, Integer.parseInt(input));

            synchronized (this.mainClient.getLock()) {
                while (this.mainClient.getClientState() == ClientState.CREATOR) {
                    try {
                        mainClient.getLock().wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            while (this.mainClient.getClientState() == ClientState.INVALID_NUMBER_OF_PLAYER) {
                this.mainClient.setClientState(ClientState.CREATOR);
                System.err.println("Invalid number of players!");
                System.err.flush();
                System.out.println("Insert number of players: (2/3/4)");
                input = scanner.nextLine();
                this.mainClient.getVirtualMainController().createWaitingList(this.mainClient.getVirtualView(), nickname, Integer.parseInt(input));

                synchronized (this.mainClient.getLock()) {
                    while (this.mainClient.getClientState() == ClientState.CREATOR) {
                        try {
                            mainClient.getLock().wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else if (this.mainClient.getClientState() == ClientState.INVALID_NICKNAME) {
            while (this.mainClient.getClientState() == ClientState.INVALID_NICKNAME) {
                System.err.println("Nickname not available!");
                System.err.flush();
                System.out.println("Insert new nickname: ");
                nickname = scanner.nextLine();

                this.mainClient.getVirtualMainController().connect(this.mainClient.getVirtualView(), nickname, this.mainClient.getClientState());

                synchronized (this.mainClient.getLock()) {
                    while (this.mainClient.getClientState() == ClientState.CONNECTION) {
                        try {
                            mainClient.getLock().wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        System.out.println("Waiting for other players ...");
        synchronized (this.mainClient.getLock()) {
            while (this.mainClient.getClientState() == ClientState.WAITING) {
                System.out.flush();
                try {
                    this.mainClient.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        synchronized (this.mainClient.getLock()) {
            this.mainClient.setVirtualGameController(this.mainClient.getVirtualMainController().getVirtualGameController(this.mainClient.getViewController().getGameID()));
            while (this.mainClient.getVirtualGameController()== null) {
                try {
                    this.mainClient.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Game begin");
    }


    private int printOptions() {
        System.out.println("Select your option:");
        System.out.println("" +
                "1) Select a card.\n" +
                "2) Turn selected card side.\n" +
                "3) Play card from hand.\n" +
                "4) Select position on board.\n" +
                "5) Select card from common table.\n" +
                "6) Draw selected card.\n" +
                "7) Choose pawn color.\n" +
                "8) Select secret mission.\n" +
                "9) Set secret mission.\n" +
                "10) Print player's personal board.\n" +
                "11) Open chat.\n" +
                "12) Exit game\n");

        Scanner scan = new Scanner(System.in);
        int response = scan.nextInt();
        return response;
    }


    public void runGameTUI() throws RemoteException {
        // Declare scanner
        Scanner scan = new Scanner(System.in);
        // Infinite loop
        while (true) {
            int cardIndex;
            Integer option = this.printOptions();
            switch (option) {
                case 1:
                    System.out.println("Select the card position: (0/1/2)");
                    String xPosition = scan.nextLine();
                    mainClient.getVirtualGameController().selectCardFromHand(Integer.parseInt(xPosition),this.mainClient.getClientID());
                    break;
                case 2:
                    mainClient.getVirtualGameController().turnSelectedCardSide(this.mainClient.getClientID());
                    break;
                case 3:
                    mainClient.getVirtualGameController().playCardFromHand(this.mainClient.getClientID());
                    break;
                case 4:
                    System.out.println("Insert the X coordinate: ");
                    String XPosition = scan.nextLine();
                    System.out.println("Insert the Y coordinate: ");
                    String YPosition = scan.nextLine();
                    mainClient.getVirtualGameController().selectPositionOnBoard(Integer.parseInt(XPosition), Integer.parseInt(YPosition), this.mainClient.getClientID());
                    break;
                case 5:
                    //TODO use only one number
                    System.out.println("Insert the card index (0/1/2/3/4/5): ");
                    cardIndex = Integer.parseInt(scan.nextLine());
                    mainClient.getVirtualGameController().selectCardFromCommonTable(cardIndex, this.mainClient.getClientID());
                    break;
                case 6:
                    mainClient.getVirtualGameController().drawSelectedCard(this.mainClient.getClientID());
                    break;
                case 7:
                    System.out.println("Insert the pawn color: ");
                    String color = scan.nextLine();
                    mainClient.getVirtualGameController().choosePawnColor(color, this.mainClient.getClientID());
                    break;
                case 8:
                    System.out.println("Insert the card index: (0/1) ");
                    cardIndex = Integer.parseInt(scan.nextLine());
                    mainClient.getVirtualGameController().selectSecretMission(cardIndex, this.mainClient.getClientID());
                    break;
                case 9:
                    mainClient.getVirtualGameController().setSecretMission(this.mainClient.getClientID());
                    break;
                case 10:
                    System.out.println("Insert the player's nickname owner of the board: ");
                    String playerNickname = scan.nextLine();
                    mainClient.getVirtualGameController().printPersonalBoard(playerNickname,this.mainClient.getClientID() );
                    break;
                case 11:
                    System.out.println("Insert the receiver's nickname: (Press enter for a broadcast message)");
                    String receiverNickname = scan.nextLine();
                    String message;
                    do {
                        System.out.println("Insert message: ");
                        message = scan.nextLine();
                    } while (message == "");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    mainClient.getVirtualGameController().addMessage(message, receiverNickname, this.mainClient.getClientID(), LocalTime.now().toString().formatted(formatter));
                    break;
                case 12:
                    System.exit(0);
                    break;
            }
        }
    }



    private void startSocketClient(String socketServerAddress, int socketServerPort) throws IOException {
        // Create connection with the server
        Socket serverSocket = new Socket(socketServerAddress, socketServerPort);

        // Get input and out stream from the server
        InputStreamReader socketRx = new InputStreamReader(serverSocket.getInputStream());
        OutputStreamWriter socketTx = new OutputStreamWriter(serverSocket.getOutputStream());

        // Reader
        BufferedReader socketIn = new BufferedReader(socketRx);

        // Writer
        BufferedWriter socketOut = new BufferedWriter(socketTx);

        // Create socket client
        this.mainClient = new MainClient(MainClient.GraphicType.tui);
        this.mainClient.setVirtualMainController(new VirtualSocketMainController(socketOut));
        this.mainClient.setVirtualView(new VirtualSocketView(null));

        // Launch a thread for managing server requests
        new SocketServerHandler(mainClient.getViewController(), socketIn, socketOut);

    }

    private void startRMIClient(String RMIServerAddress, int RMIServerPort, String remoteObjectName) throws RemoteException, NotBoundException {
        // Finding the registry and getting the stub of virtualMainController in the registry
        Registry registry = LocateRegistry.getRegistry(RMIServerAddress, RMIServerPort);

        // Create RMI Client
        this.mainClient = new MainClient(MainClient.GraphicType.tui);
        Remote remoteObject = (Remote) registry.lookup(remoteObjectName);
        this.mainClient.setVirtualMainController((VirtualMainController) remoteObject);
        this.mainClient.setVirtualView(new VirtualRMIView(mainClient.getViewController()));
    }
}
