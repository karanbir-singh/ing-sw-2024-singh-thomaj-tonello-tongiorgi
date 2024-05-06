package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.network.RMI.VirtualRMIView;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.network.ClientController;
import it.polimi.ingsw.gc26.network.socket.client.SocketServerHandler;
import it.polimi.ingsw.gc26.network.socket.client.VirtualSocketMainController;
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
    private static final int DEFAULT_SOCKET_SERVER_PORT = 3060;
    private static final int DEFAULT_RMI_SERVER_PORT = 1099;
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
    private ClientController clientController;

    /**
     * Attribute used for synchronize actions between server and client
     */
    private final Object lock;

    public MainClient() {
        this.lock = new Object();
        this.clientController = new ClientController(this, null, null, ClientState.CONNECTION, lock);
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
        this.virtualGameController = virtualGameController;
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
     * Starts RMI Client view
     *
     * @param userInterface selected user interface type
     * @throws RemoteException
     * @throws NotBoundException
     */
    private static void startRMIClient(String RMIServerAddress, int RMIServerPort, UserInterface userInterface) throws RemoteException, NotBoundException {
        // Finding the registry and getting the stub of virtualMainController in the registry
        Registry registry = LocateRegistry.getRegistry(RMIServerAddress, RMIServerPort);

        // Create RMI Client
        MainClient mainClient = new MainClient();
        mainClient.setVirtualMainController((VirtualMainController) registry.lookup(remoteObjectName));
        mainClient.setVirtualView(new VirtualRMIView(mainClient.clientController));

        // Check chosen user interface
        if (userInterface == UserInterface.tui) {
            mainClient.runConnectionTUI();
            mainClient.runGameTUI();
        } else if (userInterface == UserInterface.gui) {
            //new VirtualRMIView(virtualMainController).runGUI();
        }
    }

    /**
     * Starts socket client
     *
     * @param socketServerIPAddress IP address of the server
     * @param socketServerPort      port of the server
     * @param userInterface         selected user interface type
     * @throws IOException
     */
    private static void startSocketClient(String socketServerIPAddress, int socketServerPort, UserInterface userInterface) throws IOException {
        // Create connection with the server
        Socket serverSocket = new Socket(socketServerIPAddress, socketServerPort);

        // Get input and out stream from the server
        InputStreamReader socketRx = new InputStreamReader(serverSocket.getInputStream());
        OutputStreamWriter socketTx = new OutputStreamWriter(serverSocket.getOutputStream());

        // Reader
        BufferedReader socketIn = new BufferedReader(socketRx);

        // Writer
        PrintWriter socketOut = new PrintWriter(socketTx);

        // Create socket client
        MainClient mainClient = new MainClient();
        mainClient.setVirtualMainController(new VirtualSocketMainController(socketOut));
        mainClient.setVirtualView(new VirtualSocketView(null));
        // Launch a thread for managing server requests
        new SocketServerHandler(mainClient.clientController, socketIn, socketOut);

        // Check chosen user interface
        if (userInterface == UserInterface.tui) {
            mainClient.runConnectionTUI();
            mainClient.runGameTUI();
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
        String socketServerAddress = "127.0.0.1";
        int socketServerPort = DEFAULT_SOCKET_SERVER_PORT;

        // Default values
        ClientViewType clientViewType;

        // Check if the client passes server IP address and his port
        if (args.length == 2) {
            socketServerAddress = args[0];
            socketServerPort = Integer.parseInt(args[1]);
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
                String RMIServerAddress = socketServerAddress;
                startRMIClient(RMIServerAddress, DEFAULT_RMI_SERVER_PORT, userInterface);
            } else if (clientViewType == ClientViewType.socket) {
                // Start Socket Client
                startSocketClient(socketServerAddress, socketServerPort, userInterface);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        }
    }
}
