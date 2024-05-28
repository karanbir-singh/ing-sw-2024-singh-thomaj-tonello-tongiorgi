package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.controller.MainController;
import it.polimi.ingsw.gc26.network.RMI.VirtualRMIMainController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.socket.server.SocketServer;
import it.polimi.ingsw.gc26.utils.ConsoleColors;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class MainServer {
    /**
     * Default port of Socket server
     */
    public static final int DEFAULT_SOCKET_SERVER_PORT = 3060;
    /**
     * Default port of RMI server
     */
    public static final int DEFAULT_RMI_SERVER_PORT = 1099;
    /**
     * Port of Socket server
     */
    public static int SERVER_SOCKET_PORT;
    /**
     * Port of RMI server
     */
    public static int RMI_SERVER_PORT;

    /**
     * Starts RMI Server, binding the main controller on the registry
     */
    private static void startRMIServer(MainController mainController, int RMI_SERVER_PORT) throws RemoteException {
        // Start RMI Server
        String serverName = "RMIMainController";

        // Create virtual main controller, the remote object
        System.out.println("Constructing RMI Server...");
        VirtualMainController virtualMainController = new VirtualRMIMainController(mainController);

        // Create registry
        Registry registry = null;
        try {
            System.out.println("Getting registry...");
            registry = LocateRegistry.getRegistry(RMI_SERVER_PORT);
        } catch (RemoteException e) {
            throw new RemoteException("[ERROR]: Cannot find RMI registry");
        }

        // Bind main controller
        try {
            System.out.println("Binding Main Controller to RMI registry...");
            registry.rebind(serverName, virtualMainController);
        } catch (RemoteException e) {
            throw new RemoteException("[ERROR]: Main Controller binding on RMI registry failed");
        }

        System.out.println("Server RMI on listening...");
    }

    /**
     * Starts server socket
     *
     * @param mainController main controller of the system
     * @throws IOException
     */
    private static void startSocketServer(MainController mainController, int SOCKET_SERVER_PORT) throws IOException {
        // Get server socket and launch it
        ServerSocket listenSocket = new ServerSocket(SOCKET_SERVER_PORT);
        new SocketServer(listenSocket, mainController).runServer();
    }

    public static void main(String[] args) {
        // Create main controller
        MainController mainController = new MainController();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the game server!");

        System.out.println("---------------------------");

        // Insert RMI server port
        System.out.print(STR."Enter RMI server port (default: \{DEFAULT_RMI_SERVER_PORT}): ");
        String rmiServerPort = scanner.nextLine();
        if(rmiServerPort.matches(".*[a-zA-Z]+.*")){
            ConsoleColors.printError("[ERROR]: Invalid input -> Set default RMI server port");
            RMI_SERVER_PORT = DEFAULT_RMI_SERVER_PORT;
        }
        else if (rmiServerPort.isEmpty()) {
            RMI_SERVER_PORT = DEFAULT_RMI_SERVER_PORT;
        } else {
            RMI_SERVER_PORT = Integer.parseInt(rmiServerPort);
        }

        // Insert Server socket port
        System.out.print(STR."Enter server socket port (default: \{DEFAULT_SOCKET_SERVER_PORT}): ");
        String serverSocketPort = scanner.nextLine();
        if(serverSocketPort.matches(".*[a-zA-Z]+.*")){
            ConsoleColors.printError("[ERROR]: Invalid input -> Set default Server socket port");
            SERVER_SOCKET_PORT = DEFAULT_SOCKET_SERVER_PORT;
        }
        else if (serverSocketPort.isEmpty()) {
            SERVER_SOCKET_PORT = DEFAULT_SOCKET_SERVER_PORT;
        } else {
            SERVER_SOCKET_PORT = Integer.parseInt(serverSocketPort);
        }

        // Insert decision to restore from backup
        String decision = null;
        do {
            System.out.println("Do you want to restore games from backup? (yes/no)");
            decision = scanner.nextLine().toLowerCase();
            if (!decision.equals("yes") && !decision.equals("no")) {
                ConsoleColors.printError("[ERROR]: invalid input");
            }
        } while (!decision.equals("yes") && !decision.equals("no"));

        // Restore from backup if 'yes'
        if (decision.equals("yes")) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(MainController.MAIN_CONTROLLER_FILE_PATH);
            } catch (FileNotFoundException e) {
                ConsoleColors.printError("[ERROR]: File not found");
            }

            ObjectInputStream inputStream = null;
            try {
                inputStream = new ObjectInputStream(fileInputStream);
            } catch (IOException e) {
                ConsoleColors.printError("[ERROR]: Error opening input stream from file");
            }
            try {
                // Read from file
                mainController = (MainController) inputStream.readObject();

                // Relaunch requests executor thread
                mainController.launchExecutor();

                // Recreate games
                mainController.recreateGames();

                // Close input streams
                inputStream.close();
                fileInputStream.close();
            } catch (IOException e) {
                ConsoleColors.printError("[ERROR]: Cannot read file or close input streams");
            } catch (ClassNotFoundException e) {
                ConsoleColors.printError("[ERROR]: Class not found");
            }
        }

        // Start RMI Server
        try {
            startRMIServer(mainController, RMI_SERVER_PORT);
        } catch (RemoteException e) {
            ConsoleColors.printError(e.getMessage());
            System.exit(-1);
        }

        // Start Server Socket
        try {
            startSocketServer(mainController, SERVER_SOCKET_PORT);
        } catch (IOException e) {
            ConsoleColors.printError("[ERROR]: Server socket not starting");
            System.exit(-1);
        }
    }

}
