package it.polimi.ingsw.gc26;

import java.io.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.javac.Main;
import it.polimi.ingsw.gc26.controller.GameController;
import it.polimi.ingsw.gc26.controller.MainController;
import it.polimi.ingsw.gc26.network.RMI.VirtualRMIMainController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.socket.server.SocketServer;

import java.net.ServerSocket;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MainServer {
    /**
     * Starts RMI Server, binding the main controller on the registry
     */
    private static void startRMIServer(MainController mainController) throws RemoteException {
        // Start RMI Server
        String serverName = "RMIMainController";

        // Create virtual main controller, the remote object
        System.out.println("Constructing RMI Server...");
        VirtualMainController virtualMainController = new VirtualRMIMainController(mainController);

        // Create registry
        System.out.println("Creating registry...");
        Registry registry = LocateRegistry.createRegistry(1099);

        // Bind main controller
        System.out.println("Binding RMI main controller to registry...");
        registry.rebind(serverName, virtualMainController);

        System.out.println("Server RMI on listening...");
    }

    /**
     * Starts server socket, with port given with args on execution time
     *
     * @param port           server socket port
     * @param mainController main controller of the game
     * @throws IOException
     */
    private static void startSocketServer(int port, MainController mainController) throws IOException {
        ServerSocket listenSocket = new ServerSocket(port);
        new SocketServer(listenSocket, mainController).runServer();
    }

    /**
     * Starts server socket, with hostname and port on execution time
     *
     * @param mainController main controller of the game
     * @throws IOException
     */
    private static void startSocketServer(MainController mainController) throws IOException {
        int port;

        // Get hostname and port from file
        ObjectMapper JsonMapper = new ObjectMapper();
        JsonNode root = JsonMapper.readTree(new FileReader(SocketServer.filePath));
        port = root.get("port").asInt();

        ServerSocket listenSocket = new ServerSocket(port);
        new SocketServer(listenSocket, mainController).runServer();
    }

    public static void main(String[] args) {
        // Create main controller


        MainController mainController = new MainController();
        try{
            FileInputStream fileInputStream = new FileInputStream("mainController");
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            mainController = (MainController) inputStream.readObject();
            inputStream.close();
            fileInputStream.close();
            mainController.recreateGames();
        }catch (Exception i){
            System.out.println("file not found");
        }
        //Deserialization before everything, find if there was something in the disk


        System.out.println("Server is UP");

        // Start RMI Server
        try {
            startRMIServer(mainController);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        // Start Server Socket
        try {
            // Check if the server use a specific port passed on execution start
            if (args.length == 1) {
                startSocketServer(Integer.parseInt(args[0]), mainController);
            } else {
                startSocketServer(mainController);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
