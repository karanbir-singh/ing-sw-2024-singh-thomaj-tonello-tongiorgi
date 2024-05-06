package it.polimi.ingsw.gc26;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.network.RMI.VirtualRMIView;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.socket.client.SocketClient;
import it.polimi.ingsw.gc26.network.socket.server.SocketServer;
import javafx.util.Pair;

import java.io.*;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;
import java.util.Scanner;

public class MainClient {
    /**
     * RMI bound object name
     */
    private static final String remoteObjectName = "RMIMainController";

    /**
     * User interface types
     */
    private enum UserInterface {tui, gui}

    /**
     * Client view types
     */
    private enum ClientViewType {rmi, socket}

    /**
     * Game controller, it can be instanced as a SocketGameController or a RMIGameController
     */

    /**
     * Starts RMI Client view
     *
     * @param userInterface selected user interface type
     * @throws RemoteException
     * @throws NotBoundException
     */
    private static void startRMIClient(UserInterface userInterface) throws RemoteException, NotBoundException {
        // Finding the registry and getting the stub of virtualMainController in the registry
        Registry registry = LocateRegistry.getRegistry(1099);
        VirtualMainController virtualMainController = (VirtualMainController) registry.lookup(remoteObjectName);

        // Check chosen user interface
        if (userInterface == UserInterface.tui) {
            Pair<VirtualGameController, String> controllerPair = new VirtualRMIView(virtualMainController).runTUI();
            VirtualGameController gameController = controllerPair.getKey();
            String clientID = controllerPair.getValue();
            MainClient.runCommonTui(gameController, clientID);
        } else if (userInterface == UserInterface.gui) {
            new VirtualRMIView(virtualMainController).runGUI();
        }
    }

    /**
     * Starts socket client
     *
     * @param serverHostname IP address of the server
     * @param serverPort     port of the server
     * @param userInterface  selected user interface type
     * @throws IOException
     */
    private static void startSocketClient(String serverHostname, int serverPort, UserInterface userInterface) throws IOException {
        // Create connection with the server
        Socket serverSocket = new Socket(serverHostname, serverPort);

        // Get input and out stream from the server
        InputStreamReader socketRx = new InputStreamReader(serverSocket.getInputStream());
        OutputStreamWriter socketTx = new OutputStreamWriter(serverSocket.getOutputStream());

        // Check chosen user interface
        if (userInterface == UserInterface.tui) {
            Pair<VirtualGameController, String> controllerPair = new SocketClient(new BufferedReader(socketRx), new PrintWriter(socketTx)).runTUI();
            VirtualGameController gameController = controllerPair.getKey();
            String clientID = controllerPair.getValue();
            MainClient.runCommonTui(gameController, clientID);

        } else if (userInterface == UserInterface.gui) {
            new SocketClient(new BufferedReader(socketRx), new PrintWriter(socketTx)).runGUI();
        }
    }

    /**
     * Runs the TUI for socket and RMI connection, after game controller creation
     *
     * @param virtualGameController socket or rmi game controller
     * @param clientID              client's ID
     * @throws RemoteException
     */
    public static void runCommonTui(VirtualGameController virtualGameController, String clientID) {
        // Declare scanner
        System.out.println(clientID);
        Scanner scan = new Scanner(System.in);
        // Infinite loop
        while (true) {
            boolean chat = false;
            String line = scan.nextLine();
            String receiver = "";

            if (line.startsWith("/chat")) {
                chat = true;
                line = line.substring(line.indexOf(" ") + 1);
                if (line.startsWith("/")) {
                    receiver = line.substring(1, line.indexOf(" "));
                    line = line.substring(line.indexOf(" ") + 1);
                }
            }

            switch (line) {
                case "/1":
                    System.out.println("WHAT CARD DO YOU WANT TO SELECTED: 0/1/2");
                    String xPosition = scan.nextLine();
                    try {
                        virtualGameController.selectCardFromHand(Integer.parseInt(xPosition), clientID);
                    } catch (RemoteException e) {
                        System.out.println("LA RETET NON FUNZIONA, FORSE SERVER DOWN");

                        //prova
                        Registry registry = null;
                        try {
                            registry = LocateRegistry.getRegistry(1099);
                            System.out.println(virtualGameController);
                            VirtualMainController virtualMainController = (VirtualMainController) registry.lookup(remoteObjectName);
                            System.out.println("virtualMainController Preso");
                            virtualGameController = virtualMainController.getVirtualGameController();
                            System.out.println(virtualGameController);
                            //virtualGameController.selectCardFromHand(Integer.parseInt(xPosition), clientID)
                            System.out.println("virtualGameController Preso");
                        } catch (RemoteException ex) {
                            System.out.println("LA RETET NON FUNZIONA, FORSE SERVER DOWN");
                        } catch (NotBoundException ex) {
                            System.out.println("LA RETET NON FUNZIONA, FORSE SERVER DOWN");
                        }


                    }
                    break;
                case "/2":
                    try {
                        virtualGameController.turnSelectedCardSide(clientID);
                    } catch (RemoteException e) {
                        System.out.println("LA RETET NON FUNZIONA, FORSE SERVER DOWN");
                    }
                    break;
                case "/3":
                    try {
                        virtualGameController.playCardFromHand(clientID);
                    } catch (RemoteException e) {
                        System.out.println("LA RETET NON FUNZIONA, FORSE SERVER DOWN");
                    }
                    break;
                case "/4":
                    System.out.println("WHAT XPOSITION DO YOU WANT TO SELECT ON PERSONAL BOARD:");
                    String XPosition = scan.nextLine();
                    System.out.println("WHAT YPOSITION DO YOU WANT TO SELECT ON PERSONAL BOARD:");
                    String YPosition = scan.nextLine();
                    try {
                        virtualGameController.selectPositionOnBoard(Integer.parseInt(XPosition), Integer.parseInt(YPosition), clientID);
                    } catch (RemoteException e) {
                        System.out.println("LA RETET NON FUNZIONA, FORSE SERVER DOWN");
                    }
                    break;
                case "/5":
                    System.out.println("WHAT XPOSITION DO YOU WANT TO SELECT ON COMMON BOARD:");
                    XPosition = scan.nextLine();
                    System.out.println("WHAT YPOSITION DO YOU WANT TO SELECT ON COMMON BOARD:");
                    YPosition = scan.nextLine();
                    try {
                        virtualGameController.selectCardFromCommonTable(Integer.parseInt(XPosition), Integer.parseInt(YPosition), clientID);
                    } catch (RemoteException e) {
                        System.out.println("LA RETET NON FUNZIONA, FORSE SERVER DOWN");
                    }
                    break;
                case "/6":
                    try {
                        virtualGameController.drawSelectedCard(clientID);
                    } catch (RemoteException e) {
                        System.out.println("LA RETET NON FUNZIONA, FORSE SERVER DOWN");
                    }
                    break;
                case "/7":
                    try {
                        virtualGameController.choosePawnColor("red", clientID);
                    } catch (RemoteException e) {
                        System.out.println("LA RETET NON FUNZIONA, FORSE SERVER DOWN");
                    }
                    break;
                case "/8":
                    try {
                        virtualGameController.selectSecretMission(0, clientID);
                    } catch (RemoteException e) {
                        System.out.println("LA RETET NON FUNZIONA, FORSE SERVER DOWN");

                    }
                    break;
                case "/9":
                    try {
                        virtualGameController.setSecretMission(clientID);
                    } catch (RemoteException e) {
                        System.out.println("LA RETET NON FUNZIONA, FORSE SERVER DOWN");
                    }
                    break;
                case "/10":
                    try {
                        virtualGameController.printPersonalBoard("", clientID); // TODO get nickname as parameter
                    } catch (RemoteException e) {
                        System.out.println("LA RETET NON FUNZIONA, FORSE SERVER DOWN");
                    }
            }

            if (chat) {
                try {
                    virtualGameController.addMessage(line, receiver, clientID, LocalTime.now().toString());
                } catch (RemoteException e) {
                    System.out.println("LA RETET NON FUNZIONA, FORSE SERVER DOWN");
                }
            }

        }
    }

    public static void main(String args[]) {
        String serverHostname;
        int serverPort;

        // Default values
        ClientViewType clientViewType;

        // Check if the client passes server IP address and his port
        if (args.length == 2) {
            serverHostname = args[0];
            serverPort = Integer.parseInt(args[1]);
        } else {
            ObjectMapper JsonMapper = new ObjectMapper();
            JsonNode root = null;
            try {
                root = JsonMapper.readTree(new FileReader(SocketServer.filePath));
            } catch (IOException e) {
                e.printStackTrace();
            }
            serverHostname = root.get("server-hostname").asText();
            serverPort = root.get("port").asInt();
        }

        Scanner scanner = new Scanner(System.in);
        System.out.println("What technology do you want to connect with? (rmi/socket)");
        clientViewType = ClientViewType.valueOf(scanner.nextLine().toLowerCase());

        UserInterface userInterface;
        System.out.println("What type of user interface do you want to use? (tui/gui)");
        userInterface = UserInterface.valueOf(scanner.nextLine());
        try {
            if (clientViewType == ClientViewType.rmi) {
                // Start RMI Client
                startRMIClient(userInterface);
            } else if (clientViewType == ClientViewType.socket) {
                // Start Socket Client
                startSocketClient(serverHostname, serverPort, userInterface);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    }
}
