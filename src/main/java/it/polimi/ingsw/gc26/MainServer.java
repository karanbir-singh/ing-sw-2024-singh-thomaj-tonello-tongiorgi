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
import java.util.Scanner;

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

        Scanner scanner = new Scanner(System.in);
        System.out.println("DO YOU WANT TO RESTORE EVERYTHING?");
        String decision = scanner.nextLine();
        if(decision.equals("yes")){
            //System.out.println("INSERT THE FILEPATH");
            //String path = scanner.nextLine();
            FileInputStream fileInputStream = null;
            try {
                fileInputStream = new FileInputStream("src/main/mainController.bin");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                System.out.println("FILE NOT FOUND, FAULT OF FileInputStream");
            }
            ObjectInputStream inputStream = null;
            try {
                inputStream = new ObjectInputStream(fileInputStream);
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("IO EXCEPTION, FAULT OF ObjectInputStream");
            }
            try {
                mainController = (MainController) inputStream.readObject();
                mainController.launchThreadExecutor();
                inputStream.close();
                fileInputStream.close();
                mainController.recreateGames();

            } catch (IOException e) {
                e.printStackTrace();
                System.out.println();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }else if(decision.equals("no")){
            System.out.println("everything new");
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
