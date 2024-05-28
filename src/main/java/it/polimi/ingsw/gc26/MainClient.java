package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.network.RMI.RMIServerPing;
import it.polimi.ingsw.gc26.network.RMI.VirtualRMIView;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.network.socket.SocketServerPing;
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
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class MainClient {
    /**
     * ID of the client
     */
    private String clientID;

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

    /**
     * Server RMI registry
     */
    private Registry registry;

    public MainClient(UpdateInterface view) {
        this.clientID = null;
        this.clientState = ClientState.CONNECTION;
        this.lock = new Object();
        this.viewController = new ViewController(this, view);

    }

    public void setClientState(ClientState clientState) {
        synchronized (this.lock) {
            this.clientState = clientState;
            this.lock.notifyAll();
        }
    }

    public void setClientID(String clientID) {
        synchronized (this.lock) {
            this.clientID = clientID;
            this.lock.notifyAll();
        }
    }

    public String getClientID() {
        return clientID;
    }

    public Object getLock() {
        return this.lock;
    }

    public ClientState getClientState() {
        return this.clientState;
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

    public Registry getRegistry() {
        return registry;
    }

    /**
     * Set's the client's flag to true to kill all threads
     */
    public void killProcesses() {
        System.out.println("Game ended because another player disconnected itself!");
        System.exit(0);
    }

    public static void main(String[] args) {
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
        MainClient mainClient = null;

        // Set view
        if (graphicType.equals(GraphicType.tui)) {
            mainClient = new MainClient(new TUIUpdate());
        } else if (graphicType.equals(GraphicType.gui)) {
            mainClient = new MainClient(new GUIUpdate());
        }

        try {
            mainClient.registry = LocateRegistry.getRegistry(SERVER_IP, RMI_SERVER_PORT);
        } catch (RemoteException e) {
            throw new RemoteException("[ERROR]: unable to find RMI registry");
        }

        // Get remote object
        Remote remoteObject = null;
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

        // Launch thread for pinging RMI server
        new Thread(new RMIServerPing(mainClient)).start();

        return mainClient;
    }

    public static MainClient startSocketClient(GraphicType graphicType) throws IOException {
        // Create connection with the server
        Socket serverSocket = null;
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
        MainClient mainClient = null;

        // Set view
        if (graphicType.equals(GraphicType.tui)) {
            mainClient = new MainClient(new TUIUpdate());
        } else if (graphicType.equals(GraphicType.gui)) {
            mainClient = new MainClient(new GUIUpdate());
        }

        mainClient.setVirtualMainController(new VirtualSocketMainController(socketOut));
        mainClient.setVirtualView(new VirtualSocketView(null));

        // Launch a thread for managing server requests
        new SocketServerHandler(mainClient.getViewController(), socketIn, socketOut);

        // Launch thread for pinging RMI server
        new Thread(new SocketServerPing(mainClient)).start();

        return mainClient;
    }

}
