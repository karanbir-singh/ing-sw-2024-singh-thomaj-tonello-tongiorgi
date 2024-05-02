package it.polimi.ingsw.gc26.network.socket.client;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;
import javafx.util.Pair;

import java.io.*;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 * This class represents the client's socket. It is used only during the creation of the game, initializing the TUI or GUI.
 */
public class SocketClient {
    /**
     * Main controller used before the game controller and the game are ready.
     */
    private final VirtualMainController virtualMainController;
    /**
     * Game controller used during the game.
     */
    private VirtualGameController virtualGameController;
    /**
     * BufferedWriter to send json to the server.
     */
    private final PrintWriter outputToServer;
    /**
     * Server handler to decode json from the server.
     */
    public final SocketServerHandler serverHandler;
    /**
     * Client nickname
     */
    private String nickname;
    /**
     * Client identifier
     */
    private String clientID;

    /**
     * State of the client
     */
    private ClientState clientState;

    /**
     * lock used to wait while the client state is changed
     */
    final Object lock;


    /**
     * Socket Client's constructor. Initializes the MainController.
     *
     * @param inputFromServer buffered reader from the server.
     * @param outputToServer  writer to the server.
     */
    public SocketClient(BufferedReader inputFromServer, PrintWriter outputToServer) {
        this.virtualMainController = new VirtualSocketMainController(outputToServer);
        this.virtualGameController = null;
        this.outputToServer = outputToServer;
        this.serverHandler = new SocketServerHandler(this, inputFromServer);
        this.clientID = null;
        clientState = ClientState.CONNECTION;
        this.lock = new Object();
        this.runServerListener();
    }

    /**
     * Create a thread to listen input from the server and handle its commands.
     */
    private void runServerListener() {
        new Thread(this.serverHandler).start();
    }

    /**
     * TUI VERSION
     * Asks user to set the parameters needed before the game starts, such as nickname and number of players.
     *
     * @return gameController, clientID and nickname set by the user
     * @throws RemoteException
     */
    public Pair<VirtualGameController, String> runTUI() throws RemoteException {
        // TODO gestire la Remote Exception
        //Initial state in CONNECTION
        System.out.println("YOU CONNECTED TO THE SERVER");
        Scanner scanner = new Scanner(System.in);

        System.out.print("INSERISCI IL NICKNAME\nNickname: ");
        this.nickname = scanner.nextLine();
        this.virtualMainController.connect(this.serverHandler, this.nickname);

        synchronized (this.lock) {
            while (this.clientState == ClientState.CONNECTION) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (this.clientState == ClientState.CREATOR) {
            System.out.print("THERE ARE NO GAME FREE, YOU MUST CREATE A NEW GAME:\nNumber of players (2/3/4): ");
            String decision = scanner.nextLine();
            this.virtualMainController.createWaitingList(this.serverHandler, this.nickname, Integer.parseInt(decision));

            synchronized (this.lock) {
                while (this.clientState == ClientState.CREATOR) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            while (this.clientState == ClientState.INVALID_NUMBER_OF_PLAYER) {
                this.clientState = ClientState.CREATOR;
                System.out.print("INVALID NUMBER OF PLAYERS\nNumber of players (2/3/4): ");
                decision = scanner.nextLine();
                this.virtualMainController.createWaitingList(this.serverHandler, this.nickname, Integer.parseInt(decision));

                synchronized (this.lock) {
                    while (this.clientState == ClientState.CREATOR) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else if (clientState.equals(ClientState.INVALID_NICKNAME)) {
            while (clientState == ClientState.INVALID_NICKNAME) {
                System.out.print("NICKNAME GIA' PRESO\nNickname: ");
                this.nickname = scanner.nextLine();

                this.virtualMainController.connect(this.serverHandler, this.nickname);

                synchronized (this.lock) {
                    while (this.clientState == ClientState.CONNECTION) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        System.out.println("WAITING ...");
        while (clientState == ClientState.WAITING) {
            System.out.flush();
        }

        synchronized (this) {
            this.virtualMainController.getVirtualGameController();
            while(this.virtualGameController == null) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println("GAME BEGIN");
        return new Pair<>(this.virtualGameController, this.clientID);

        //this.runServerListener();
//        // Start CLI
//        Scanner scan = new Scanner(System.in);
//
//        // TODO gestire la Remote Exception
//        // Initial state in CONNECTION
//        System.out.println("Connected to the server successfully!");
//        System.out.println("Insert your nickname: ");
//        this.nickname = scan.nextLine();
//        this.virtualMainController.connect(this.serverHandler, this.nickname);
//
//        // wait for the server to update the client's ID
//        synchronized (this) {
//            try {
//                this.wait();
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        // wait for the server to update the client's state
//
//        synchronized (this.lock) {
//            while(clientState == ClientState.INVALID_NICKNAME || clientState == ClientState.CONNECTION) {
//                System.out.println("Nickname not available \nInsert new nickname: ");
//                this.nickname = scan.nextLine();
//                this.virtualMainController.connect( this.serverHandler, this.nickname);
//                try {
//                    this.lock.wait();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//
//
//        // check whether there is a game in WAITING or a new game should be created
//        if (clientState == ClientState.CREATOR) {
//            System.out.println("You must initialize a new game \n Insert number of players: ");
//            Integer numberPlayers = Integer.parseInt(scan.nextLine());
//            this.virtualMainController.createWaitingList((VirtualView) this.serverHandler, this.nickname, numberPlayers);
//        }
//        System.out.println("Waiting for other players ...");
//
//        // wait for the server to update the client's state
//        // (the server notifies when the game has all its player so the game can start)
//        synchronized (this.lock) {
//            while(clientState != ClientState.BEGIN) {
//                try {
//                    this.lock.wait();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//
//        synchronized (this) {
//            this.virtualMainController.getVirtualGameController();
//            while(this.virtualGameController == null) {
//                try {
//                    this.wait();
//                } catch (InterruptedException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//
//
//        System.out.println("Game begin");
//        return new Pair<>(this.virtualGameController, this.clientID);

    }

    /**
     * GUI VERSION
     * Asks user to set the parameters needed before the game starts, such as nickname and number of players.
     */
    public void runGUI() {
        this.runServerListener();

        // TODO
    }

    /**
     * Method used by the server to set the client's ID as an answer to connect()
     *
     * @param clientID
     */
    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    /**
     * Method used by the server to set the game controller as an answer to the change of state
     */
    public void setVirtualGameController() {
        this.virtualGameController = new VirtualSocketGameController(this.outputToServer);
    }

    /**
     * Sets the state of the client
     *
     * @param clientState
     */
    public void setState(ClientState clientState) {
        synchronized (this.clientState) {
            this.clientState = clientState;
        }
    }

    public String getClientID() {
        return this.clientID;
    }

    public String getNickname() {
        return this.nickname;
    }

    public VirtualMainController getVirtualMainController() {
        return virtualMainController;
    }
}

