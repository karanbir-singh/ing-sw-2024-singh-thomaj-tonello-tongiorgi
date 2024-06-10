package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.network.PingManager;
import it.polimi.ingsw.gc26.network.RMI.RMIPingManager;
import it.polimi.ingsw.gc26.network.RMI.VirtualRMIView;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.network.socket.SocketPingManager;
import it.polimi.ingsw.gc26.network.socket.client.SocketServerHandler;
import it.polimi.ingsw.gc26.network.socket.client.VirtualSocketMainController;
import it.polimi.ingsw.gc26.network.socket.server.VirtualSocketView;
import it.polimi.ingsw.gc26.ui.UpdateInterface;
import it.polimi.ingsw.gc26.ui.gui.GUIApplication;
import it.polimi.ingsw.gc26.ui.gui.GUIUpdate;
import it.polimi.ingsw.gc26.ui.tui.TUIApplication;
import it.polimi.ingsw.gc26.ui.tui.TUIUpdate;
import it.polimi.ingsw.gc26.utils.ConsoleColors;
import it.polimi.ingsw.gc26.view_model.ViewController;

import java.io.*;
import java.net.Socket;
import java.rmi.ConnectException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class MainClient {
    /**
     * ID of the client
     */
    private String clientID;

    /**
     * Client's nickname
     */
    private String nickname;

    /**
     * State of the client
     */
    private ClientState clientState;
    /**
     * Default port of server socket
     * Server IP
     */
    public static String SERVER_IP = "127.0.0.1";
    /**
     * Port of Socket server
     */
    public static int SERVER_SOCKET_PORT = MainServer.DEFAULT_SOCKET_SERVER_PORT;

    /**
     * Port of RMI server
     */
    public static int RMI_SERVER_PORT = MainServer.DEFAULT_RMI_SERVER_PORT;

    /**
     * RMI bound object name
     */
    public static final String remoteObjectName = "RMIMainController";

    /**
     * User interface types
     */
    public enum GraphicType {tui, gui}

    /**
     * Client view types
     */
    public enum NetworkType {rmi, socket}

    /**
     * Remote interface of the main controller
     */
    private VirtualMainController virtualMainController;

    /**
     * Remote interface of the game controller
     */
    private VirtualGameController virtualGameController;

    /**
     * Remote interface of the view
     */
    private VirtualView virtualView;

    /**
     * Client controller
     */
    private final ViewController viewController;

    /**
     * Attribute used for synchronize actions between server and client
     */
    public final Object lock;

    public static String asciiCodexNaturalis = "\n" +
            "░█████╗░░█████╗░██████╗░███████╗██╗░░██╗  ███╗░░██╗░█████╗░████████╗██╗░░░██╗██████╗░░█████╗░██╗░░░░░██╗░██████╗\n" +
            "██╔══██╗██╔══██╗██╔══██╗██╔════╝╚██╗██╔╝  ████╗░██║██╔══██╗╚══██╔══╝██║░░░██║██╔══██╗██╔══██╗██║░░░░░██║██╔════╝\n" +
            "██║░░╚═╝██║░░██║██║░░██║█████╗░░░╚███╔╝░  ██╔██╗██║███████║░░░██║░░░██║░░░██║██████╔╝███████║██║░░░░░██║╚█████╗░\n" +
            "██║░░██╗██║░░██║██║░░██║██╔══╝░░░██╔██╗░  ██║╚████║██╔══██║░░░██║░░░██║░░░██║██╔══██╗██╔══██║██║░░░░░██║░╚═══██╗\n" +
            "╚█████╔╝╚█████╔╝██████╔╝███████╗██╔╝╚██╗  ██║░╚███║██║░░██║░░░██║░░░╚██████╔╝██║░░██║██║░░██║███████╗██║██████╔╝\n" +
            "░╚════╝░░╚════╝░╚═════╝░╚══════╝╚═╝░░╚═╝  ╚═╝░░╚══╝╚═╝░░╚═╝░░░╚═╝░░░░╚═════╝░╚═╝░░╚═╝╚═╝░░╚═╝╚══════╝╚═╝╚═════╝░";
    /**
     * Server RMI registry
     */
    private Registry registry;

    /**
     * Runnable of the thread that manage the server ping
     */
    private PingManager pingManager;

    public MainClient() {
        this.clientID = null;
        this.clientState = ClientState.CONNECTION;
        this.lock = new Object();
        this.viewController = new ViewController(this);

    }

    /**
     * Set client's state
     *
     * @param clientState new client state
     */
    public void setClientState(ClientState clientState) {
        synchronized (this.lock) {
            this.clientState = clientState;
            this.lock.notifyAll();
        }
    }

    /**
     * @return client's state
     */
    public ClientState getClientState() {
        return this.clientState;
    }

    /**
     * Sets client's ID
     *
     * @param clientID ID
     */
    public void setClientID(String clientID) {
        synchronized (this.lock) {
            this.clientID = clientID;
            this.lock.notifyAll();
        }
    }

    /**
     * @return client's ID
     */
    public String getClientID() {
        return clientID;
    }

    /**
     * @return main client's lock
     */
    public Object getLock() {
        return this.lock;
    }

    /**
     * Sets main client's server ping manager
     *
     * @param pingManager the server ping manager
     */
    public void setPingManager(PingManager pingManager) {
        this.pingManager = pingManager;
    }

    /**
     * @return main client's server ping manager
     */
    public PingManager getPingManager() {
        return pingManager;
    }

    /**
     * Sets virtual main controller passed by parameter
     *
     * @param virtualMainController virtual main controller to set
     */
    public void setVirtualMainController(VirtualMainController virtualMainController) {
        this.virtualMainController = virtualMainController;
    }

    /**
     * Sets virtual game controller passed by parameter
     *
     * @param virtualGameController virtual game controller to set
     */
    public void setVirtualGameController(VirtualGameController virtualGameController) {
        synchronized (this.lock) {
            this.virtualGameController = virtualGameController;
            this.lock.notifyAll();
        }
    }

    /**
     * @return virtual main controller
     */
    public VirtualMainController getVirtualMainController() {
        return virtualMainController;
    }

    /**
     * @return virtual game controller
     */
    public VirtualGameController getVirtualGameController() {
        return virtualGameController;
    }


    /**
     * Sets virtual view passed by parameter
     *
     * @param virtualView virtual view to set
     */
    public void setVirtualView(VirtualView virtualView) {
        this.virtualView = virtualView;
    }

    /**
     * @return virtual view
     */
    public VirtualView getVirtualView() {
        return virtualView;
    }

    /**
     * @return view controller
     */
    public ViewController getViewController() {
        return viewController;
    }

    /**
     * Set's the client's flag to true to kill all threads
     */
    public void killProcesses() {
        System.out.println("Game ended because another player disconnected itself!");
        System.exit(0);
    }

    /**
     * Return the client's nickname
     * @return
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Set's the client's nickname
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public static void main(String args[]) {
        // print logo
        System.out.println(asciiCodexNaturalis + "\n");

        // Get server IP and port
        Scanner scanner = new Scanner(System.in);

        // Insert server IP
        System.out.print("Enter server IP address (default: 127.0.0.1): ");
        String serverIP = scanner.nextLine();
        if (serverIP.matches(".*[a-zA-Z]+.*")) {
            ConsoleColors.printError("[ERROR]: Invalid input -> Set default server IP address");
        } else if (!serverIP.isEmpty()) {
            SERVER_IP = serverIP;
        }

        // Insert RMI Server port
        System.out.print("Enter server RMI port (default 1099): ");
        String rmiServerPort = scanner.nextLine();
        if (rmiServerPort.matches(".*[a-zA-Z]+.*")) {
            ConsoleColors.printError("[ERROR]: Invalid input -> Set default RMI Server port");
        } else if (!rmiServerPort.isEmpty()) {
            RMI_SERVER_PORT = Integer.parseInt(rmiServerPort);
        }

        // Insert server socket port
        System.out.print("Enter server socket port (default 3060): ");
        String socketServerPort = scanner.nextLine();
        if (socketServerPort.matches(".*[a-zA-Z]+.*")) {
            ConsoleColors.printError("[ERROR]: Invalid input -> Set default Server socket port");
        } else if (!socketServerPort.isEmpty()) {
            SERVER_SOCKET_PORT = Integer.parseInt(socketServerPort);
        }

        // Get type of communication from user
        NetworkType networkType = null;
        do {
            System.out.println("What type of communication do you want to use? (rmi/socket)");
            try {
                networkType = NetworkType.valueOf(scanner.nextLine().toLowerCase());
            } catch (IllegalArgumentException e) {
                ConsoleColors.printError("[ERROR]: Invalid input");
            }
        } while (networkType == null);

        // Get user interface from user
        GraphicType graphicType = null;
        do {
            System.out.println("What type of user interface do you want to use? (tui/gui)");
            try {
                graphicType = GraphicType.valueOf(scanner.nextLine().toLowerCase());
            } catch (IllegalArgumentException e) {
                ConsoleColors.printError("[ERROR]: Invalid input");
            }
        } while (graphicType == null);

        // Launch the chosen user interface
        if (graphicType.equals(GraphicType.tui)) {
            new TUIApplication().init(networkType);
        } else {
            new GUIApplication().init(networkType);
        }
    }

    public static MainClient startRMIClient(GraphicType graphicType) throws RemoteException {
        // Create RMI Client
        MainClient mainClient = new MainClient();

        try {
            mainClient.registry = LocateRegistry.getRegistry(SERVER_IP, RMI_SERVER_PORT);
        } catch (RemoteException e) {
            throw new RemoteException("[ERROR]: unable to find RMI registry");
        }

        // Get remote object
        Remote remoteObject;
        try {
            remoteObject = mainClient.registry.lookup(remoteObjectName);
        } catch (RemoteException | NotBoundException e) {
            throw new RemoteException("[ERROR]: unable to lookup remote object");
        }

        mainClient.setVirtualMainController((VirtualMainController) remoteObject);
        try {
            mainClient.setVirtualView(new VirtualRMIView(mainClient.getViewController()));
        } catch (RemoteException e) {
            throw new RemoteException("[ERROR]: unable to create virtual RMI view");
        }

        // Set RMI server ping manager
        mainClient.setPingManager(new RMIPingManager(mainClient));

        return mainClient;
    }

    public static MainClient startSocketClient(GraphicType graphicType) throws IOException {
        // Create connection with the server
        Socket serverSocket;
        try {
            serverSocket = new Socket(SERVER_IP, SERVER_SOCKET_PORT);
        } catch (IOException e) {
            throw new IOException("[ERROR]: unable to locate server");
        }

        // Get input and out stream from the server
        InputStreamReader socketRx;
        OutputStreamWriter socketTx;
        try {
            socketRx = new InputStreamReader(serverSocket.getInputStream());
            socketTx = new OutputStreamWriter(serverSocket.getOutputStream());
        } catch (IOException e) {
            throw new IOException("[ERROR]: unable to get server input and output server");
        }

        // Reader
        BufferedReader socketIn = new BufferedReader(socketRx);

        // Writer
        BufferedWriter socketOut = new BufferedWriter(socketTx);

        // Create socket client
        MainClient mainClient = new MainClient();

        mainClient.setVirtualMainController(new VirtualSocketMainController(socketOut));
        mainClient.setVirtualView(new VirtualSocketView(null));

        // Launch a thread for managing server requests
        new SocketServerHandler(mainClient.getViewController(), socketIn, socketOut);

        // Set Socket server ping manager
        mainClient.setPingManager(new SocketPingManager(mainClient));

        return mainClient;
    }

}
