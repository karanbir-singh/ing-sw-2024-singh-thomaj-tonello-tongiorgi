package it.polimi.ingsw.gc26.network.socket.client;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.controller.GameController;
import it.polimi.ingsw.gc26.controller.MainController;
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
    private final BufferedWriter output;
    /**
     * Server handler to decode json from the server.
     */
    private final SocketServerHandler handler;
    /**
     * Client nickname
     */
    protected String nickname;
    /**
     * Client identifier
     */
    protected String clientID;


    /**
     * Socket Client's constructor. Initializes the MainController.
     * @param input buffered reader from the server.
     * @param output buffered writer to the server.
     */
    public SocketClient(BufferedReader input, BufferedWriter output) {
        this.virtualMainController = new VirtualSocketMainController(output);
        this.handler = new SocketServerHandler(this, input);
        this.output = output;
        this.clientID = "";
        this.virtualGameController = new VirtualSocketGameController(this.output); //TODO should be done in a setter method, it doesn't work :/

    }

    /**
     * Create a thread to listen input from the server and handle its commands.
     */
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

    /**
     * TUI VERSION
     * Asks user to set the parameters needed before the game starts, such as nickname and number of players.
     * @return gameController, clientID and nickname set by the user
     * @throws RemoteException
     */
    public Pair<VirtualGameController, String> runTUI() throws RemoteException {
        this.runServerListener();
        // Start CLI
        Scanner scan  = new Scanner(System.in);

        // TODO gestire la Remote Exception
        // Initial state in CONNECTION
        System.out.println("Connected to the server successfully!");
        System.out.println("Insert your nickname: ");
        this.nickname = scan.nextLine();
        this.virtualMainController.connect(this.handler, this.nickname);

        // wait for the server to update the client's ID
        while(true) {
            synchronized (this.clientID) {
                if (this.clientID != "") {break;}
            }
        };

        // wait for the server to update the client's state
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

        // check whether there is a game in WAITING or a new game should be created
        if (handler.clientState == ClientState.CREATOR) {
            System.out.println("You must initialize a new game \n Insert number of players: ");
            Integer numberPlayers = Integer.parseInt(scan.nextLine());
            this.virtualMainController.createWaitingList(this.handler, this.clientID, this.nickname, numberPlayers);
        }
        System.out.println("Waiting for other players ...");

        // wait for the server to update the client's state
        // (the server notifies when the game has all its player so the game can start)
        while (true) {
            synchronized (this.handler.clientState) {
                if (handler.clientState == ClientState.BEGIN){
                    break;
                }
            }
        }

        this.virtualMainController.getVirtualGameController();
        System.out.println("Game begin");
        return new Pair<>(this.virtualGameController, this.clientID);

    }

    /**
     * GUI VERSION
     * Asks user to set the parameters needed before the game starts, such as nickname and number of players.
     */
    public void runGUI(){
        this.runServerListener();

        // TODO
    }

    /**
     * Method used by the server to set the client's ID as an answer to connect()
     * @param clientID
     */
    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    /**
     * Method used by the server to set the game controller as an answer to the change of state
     */
    public void setVirtualGameController() {
        this.virtualGameController = new VirtualSocketGameController(this.output);
    }


}

