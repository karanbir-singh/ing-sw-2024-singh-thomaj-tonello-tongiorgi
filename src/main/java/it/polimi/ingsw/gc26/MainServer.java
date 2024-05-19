package it.polimi.ingsw.gc26;

import java.io.*;

import it.polimi.ingsw.gc26.controller.MainController;
import it.polimi.ingsw.gc26.network.RMI.VirtualRMIMainController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.socket.server.SocketServer;

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
        System.out.println("Creating registry...");
        Registry registry = LocateRegistry.createRegistry(RMI_SERVER_PORT);

        // Bind main controller
        System.out.println("Binding RMI main controller to registry...");
        registry.rebind(serverName, virtualMainController);

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

        System.out.print(STR."Enter RMI server port (default: \{DEFAULT_RMI_SERVER_PORT}): ");
        String rmiServerPort = scanner.nextLine();

        if (rmiServerPort.isEmpty()) {
            RMI_SERVER_PORT = DEFAULT_RMI_SERVER_PORT;
        } else {
            RMI_SERVER_PORT = Integer.parseInt(rmiServerPort);
        }

        System.out.print(STR."Enter server socket port (default: \{DEFAULT_SOCKET_SERVER_PORT}): ");
        String serverSocketPort = scanner.nextLine();

        if (serverSocketPort.isEmpty()) {
            SERVER_SOCKET_PORT = DEFAULT_SOCKET_SERVER_PORT;
        } else {
            SERVER_SOCKET_PORT = Integer.parseInt(serverSocketPort);
        }

        System.out.println("Do you want to restore games from backup? (y/n)");
        String decision = scanner.nextLine().toLowerCase();
        if (decision.equals("y")) {
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream(MainController.MAIN_CONTROLLER_FILE_PATH);
            } catch (FileNotFoundException e) {
                System.out.println("File not found");
            }

            ObjectInputStream inputStream = null;
            try {
                inputStream = new ObjectInputStream(fileInputStream);
            } catch (IOException e) {
                System.out.println("Error opening input stream from file");
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
                System.out.println("Cannot read file or close input streams, new ");
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found");
            }
        }

        // Start RMI Server
        try {
            startRMIServer(mainController, RMI_SERVER_PORT);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        // Start Server Socket
        try {
            startSocketServer(mainController, SERVER_SOCKET_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
