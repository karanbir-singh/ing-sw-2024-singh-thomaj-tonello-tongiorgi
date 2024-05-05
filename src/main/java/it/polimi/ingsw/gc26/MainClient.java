package it.polimi.ingsw.gc26;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.network.RMI.VirtualRMIView;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.network.socket.client.ClientController;
import it.polimi.ingsw.gc26.network.socket.client.SocketServerHandler;
import it.polimi.ingsw.gc26.network.socket.client.VirtualSocketMainController;
import it.polimi.ingsw.gc26.network.socket.server.SocketServer;
import it.polimi.ingsw.gc26.network.socket.server.VirtualSocketView;

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
     * ID of the client
     */
//    private String clientID;

    /**
     * Nickname of the client
     */
//    private String nickname;
    /**
     * RMI bound object name
     */
    private static final String remoteObjectName = "RMIMainController";

    /**
     * Client's state
     */
//    private ClientState clientState;

    /**
     * User interface types
     */
    private enum UserInterface {tui, gui}

    /**
     * Client view types
     */
    private enum ClientViewType {rmi, socket}

    private VirtualMainController virtualMainController;

    private VirtualGameController virtualGameController;

    private VirtualView virtualView;

    private ClientController clientController;

    private final Object lock;

    public MainClient() {
        this.lock = new Object();
        this.clientController = new ClientController(this, null, null, ClientState.CONNECTION, lock);
    }

    public void setVirtualMainController(VirtualMainController virtualMainController) {
        this.virtualMainController = virtualMainController;
    }

    public void setVirtualGameController(VirtualGameController virtualGameController) {
        this.virtualGameController = virtualGameController;
    }

    public void setVirtualView(VirtualView virtualView) {
        this.virtualView = virtualView;
    }

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
        //new VirtualRMIView(/*client.virtualMainController,*/ client);

        MainClient mainClient = new MainClient();
        mainClient.setVirtualMainController((VirtualMainController) registry.lookup(remoteObjectName));
        mainClient.setVirtualView(new VirtualRMIView(mainClient.clientController));

        // Check chosen user interface
        if (userInterface == UserInterface.tui) {
            mainClient.runConnectionTUI();
            mainClient.runGameTUI();
            //client.virtualView = new VirtualRMIView(/*client.virtualMainController,*/ client);
            // client.connect();
            // client.runCommonTui();
        } else if (userInterface == UserInterface.gui) {
            //new VirtualRMIView(virtualMainController).runGUI();
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

        // Reader
        BufferedReader socketIn = new BufferedReader(socketRx);

        // Writer
        PrintWriter socketOut = new PrintWriter(socketTx);

        MainClient mainClient = new MainClient();
        mainClient.setVirtualMainController(new VirtualSocketMainController(socketOut));
        mainClient.setVirtualView(new VirtualSocketView(null));
        new SocketServerHandler(mainClient.clientController, socketIn, socketOut);

        // Check chosen user interface
        if (userInterface == UserInterface.tui) {
            mainClient.runConnectionTUI();
            mainClient.runGameTUI();
//            client.virtualView = socketClient.serverHandler;
            // client.connect();
            // client.runCommonTui();
        } else if (userInterface == UserInterface.gui) {
            //new SocketClient(new BufferedReader(socketRx), new PrintWriter(socketTx)).runGUI();
        }
    }

    public void runConnectionTUI() throws RemoteException {
        // TODO gestire la Remote Exception
        //Initial state in CONNECTION
        Scanner scanner = new Scanner(System.in);
        System.out.println("Connected to the server successfully!");
        System.out.println("Insert your nickname: ");
        String input = scanner.nextLine();
        clientController.setNickname(input);
        this.virtualMainController.connect(this.virtualView, clientController.getNickname(), clientController.getClientState());

        synchronized (this.lock) {
            while (clientController.getClientState() == ClientState.CONNECTION) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (clientController.getClientState() == ClientState.CREATOR) {
            System.out.println("You must initialize a new game \nInsert number of players: (2/3/4)");
            input = scanner.nextLine();
            this.virtualMainController.createWaitingList(this.virtualView, clientController.getNickname(), Integer.parseInt(input));

            synchronized (this.lock) {
                while (clientController.getClientState() == ClientState.CREATOR) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            while (clientController.getClientState() == ClientState.INVALID_NUMBER_OF_PLAYER) {
                clientController.updateClientState(ClientState.CREATOR);
                System.err.println("Invalid number of players!");
                System.err.flush();
                System.out.println("Insert number of players: (2/3/4)");
                input = scanner.nextLine();
                this.virtualMainController.createWaitingList(this.virtualView, clientController.getNickname(), Integer.parseInt(input));

                synchronized (this.lock) {
                    while (clientController.getClientState() == ClientState.CREATOR) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else if (clientController.getClientState() == ClientState.INVALID_NICKNAME) {
            while (clientController.getClientState() == ClientState.INVALID_NICKNAME) {
                System.err.println("Nickname not available!");
                System.err.flush();
                System.out.println("Insert new nickname: ");
                input = scanner.nextLine();
                clientController.setNickname(input);

                this.virtualMainController.connect(this.virtualView, clientController.getNickname(), clientController.getClientState());

                synchronized (this.lock) {
                    while (clientController.getClientState() == ClientState.CONNECTION) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        System.out.println("Waiting for other players ...");
        while (clientController.getClientState() == ClientState.WAITING) {
            System.out.flush();
        }

        synchronized (this.lock) {
            this.virtualGameController = this.virtualMainController.getVirtualGameController();
            while (this.virtualGameController == null) {
                try {
                    this.lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Game begin");
    }

    public void runGameTUI() throws RemoteException {
        // Declare scanner
        Scanner scan = new Scanner(System.in);

        // Infinite loop
        while (true) {
            boolean chat = false;
            String input = scan.nextLine();
            String receiver = "";

            if (input.startsWith("/chat")) {
                chat = true;
                input = input.substring(input.indexOf(" ") + 1);
                if (input.startsWith("/")) {
                    receiver = input.substring(1, input.indexOf(" "));
                    input = input.substring(input.indexOf(" ") + 1);
                }
            }

            switch (input) {
                case "/1":
                    System.out.println("WHAT CARD DO YOU WANT TO SELECTED: 0/1/2");
                    String xPosition = scan.nextLine();
                    virtualGameController.selectCardFromHand(Integer.parseInt(xPosition), clientController.getClientID());
                    break;
                case "/2":
                    virtualGameController.turnSelectedCardSide(clientController.getClientID());
                    break;
                case "/3":
                    virtualGameController.playCardFromHand(clientController.getClientID());
                    break;
                case "/4":
                    System.out.println("WHAT XPOSITION DO YOU WANT TO SELECT ON PERSONAL BOARD:");
                    String XPosition = scan.nextLine();
                    System.out.println("WHAT YPOSITION DO YOU WANT TO SELECT ON PERSONAL BOARD:");
                    String YPosition = scan.nextLine();
                    virtualGameController.selectPositionOnBoard(Integer.parseInt(XPosition), Integer.parseInt(YPosition), clientController.getClientID());
                    break;
                case "/5":
                    System.out.println("WHAT XPOSITION DO YOU WANT TO SELECT ON COMMON BOARD:");
                    XPosition = scan.nextLine();
                    System.out.println("WHAT YPOSITION DO YOU WANT TO SELECT ON COMMON BOARD:");
                    YPosition = scan.nextLine();
                    virtualGameController.selectCardFromCommonTable(Integer.parseInt(XPosition), Integer.parseInt(YPosition), clientController.getClientID());
                    break;
                case "/6":
                    virtualGameController.drawSelectedCard(clientController.getClientID());
                    break;
                case "/7":
                    String color = scan.nextLine();
                    virtualGameController.choosePawnColor(color, clientController.getClientID());
                    break;
                case "/8":
                    int secretMissionIndex = Integer.parseInt(scan.nextLine());
                    virtualGameController.selectSecretMission(secretMissionIndex, clientController.getClientID());
                    break;
                case "/9":
                    virtualGameController.setSecretMission(clientController.getClientID());
                    break;
                case "/10":
                    virtualGameController.printPersonalBoard("gabi", clientController.getClientID()); // TODO get nickname as parameter
            }

            if (chat) {
                virtualGameController.addMessage(input, receiver, clientController.getClientID(), LocalTime.now().toString());
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
            e.printStackTrace();
        }
    }
}
