package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.network.RMI.VirtualRMIView;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.network.ViewController;
import it.polimi.ingsw.gc26.network.socket.client.SocketServerHandler;
import it.polimi.ingsw.gc26.network.socket.client.VirtualSocketGameController;
import it.polimi.ingsw.gc26.network.socket.client.VirtualSocketMainController;
import it.polimi.ingsw.gc26.network.socket.server.VirtualSocketView;

import java.io.*;
import java.net.Socket;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class MainClient {
    /**
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
    private enum UserInterface {tui, gui}

    /**
     * Client view types
     */
    private enum NetworkType {rmi, socket}

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
    private ViewController viewController;
    /**
     * Attribute used for synchronize actions between server and client
     */
    private final Object lock;

    /**
     * Attribute used for saving if server is on
     */
    boolean isServerUp;

    public MainClient() {
        this.lock = new Object();
        this.viewController = new ViewController(this, null, null, ClientState.CONNECTION, lock);
        this.isServerUp = true;
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
     * Set's the client's flag to true to kill all threads
     */
    public void killProcesses() {
        System.out.println("Game ended because another player disconnected itself!");
        System.exit(0);
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
        Remote remoteObject = (Remote) registry.lookup(remoteObjectName);
        mainClient.setVirtualMainController((VirtualMainController) remoteObject);
        mainClient.setVirtualView(new VirtualRMIView(mainClient.viewController));

        // Check chosen user interface
        if (userInterface == UserInterface.tui) {
            mainClient.runConnectionTUI();
            new Thread(() -> mainClient.RMIPingServer()).start();
            mainClient.runGameTUI();
        } else if (userInterface == UserInterface.gui) {
            //new VirtualRMIView(virtualMainController).runGUI();
        }
        registry.unbind(remoteObjectName);
    }

    /**
     * Starts socket client
     *
     * @param socketServerAddress IP address of the server
     * @param socketServerPort    port of the server
     * @param userInterface       selected user interface type
     * @throws IOException
     */
    private static void startSocketClient(String socketServerAddress, int socketServerPort, UserInterface userInterface) throws IOException {
        // Create connection with the server
        Socket serverSocket = new Socket(socketServerAddress, socketServerPort);

        // Get input and out stream from the server
        InputStreamReader socketRx = new InputStreamReader(serverSocket.getInputStream());
        OutputStreamWriter socketTx = new OutputStreamWriter(serverSocket.getOutputStream());

        // Reader
        BufferedReader socketIn = new BufferedReader(socketRx);

        // Writer
        BufferedWriter socketOut = new BufferedWriter(socketTx);

        // Create socket client
        MainClient mainClient = new MainClient();
        mainClient.setVirtualMainController(new VirtualSocketMainController(socketOut));
        mainClient.setVirtualView(new VirtualSocketView(null));

        // Launch a thread for managing server requests
        new SocketServerHandler(mainClient.viewController, socketIn, socketOut);

        // Check chosen user interface
        if (userInterface == UserInterface.tui) {
            new Thread(() -> mainClient.SocketPingServer()).start();
            mainClient.runConnectionTUI();
            mainClient.runGameTUI();
        } else if (userInterface == UserInterface.gui) {
            //new SocketClient(new BufferedReader(socketRx), new PrintWriter(socketTx)).runGUI();
        }
        serverSocket.close();
    }

    public void runConnectionTUI() throws RemoteException {
        //Initial state in CONNECTION
        Scanner scanner = new Scanner(System.in);

        System.out.println("Insert your nickname: ");
        String input = scanner.nextLine();
        this.viewController.setNickname(input);

        this.virtualMainController.connect(this.virtualView, viewController.getNickname(), viewController.getClientState());
        synchronized (this.lock) {
            while (viewController.getClientState() == ClientState.CONNECTION) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (viewController.getClientState() == ClientState.CREATOR) {
            System.out.println("You must initialize a new game \nInsert number of players: (2/3/4)");
            input = scanner.nextLine();
            this.virtualMainController.createWaitingList(this.virtualView, viewController.getNickname(), Integer.parseInt(input));

            synchronized (this.lock) {
                while (viewController.getClientState() == ClientState.CREATOR) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            while (viewController.getClientState() == ClientState.INVALID_NUMBER_OF_PLAYER) {
                viewController.updateClientState(ClientState.CREATOR);
                System.err.println("Invalid number of players!");
                System.err.flush();
                System.out.println("Insert number of players: (2/3/4)");
                input = scanner.nextLine();
                this.virtualMainController.createWaitingList(this.virtualView, viewController.getNickname(), Integer.parseInt(input));

                synchronized (this.lock) {
                    while (viewController.getClientState() == ClientState.CREATOR) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else if (viewController.getClientState() == ClientState.INVALID_NICKNAME) {
            while (viewController.getClientState() == ClientState.INVALID_NICKNAME) {
                System.err.println("Nickname not available!");
                System.err.flush();
                System.out.println("Insert new nickname: ");
                input = scanner.nextLine();
                viewController.setNickname(input);

                this.virtualMainController.connect(this.virtualView, viewController.getNickname(), viewController.getClientState());

                synchronized (this.lock) {
                    while (viewController.getClientState() == ClientState.CONNECTION) {
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
        while (viewController.getClientState() == ClientState.WAITING) {
            System.out.flush();
        }

        synchronized (this.lock) {
            this.virtualGameController = this.virtualMainController.getVirtualGameController(viewController.getGameID());
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
            Integer option = printOptions();
            switch (option) {
                case 1:
                    System.out.println("Select the card position: (0/1/2)");
                    String xPosition = scan.nextLine();
                    try {
                        virtualGameController.selectCardFromHand(Integer.parseInt(xPosition), viewController.getClientID());
                    } catch (RemoteException e) {
                        //TODO DA METTERE IN TUTTI I METODI IL CATCH
                    }
                    break;
                case 2:
                    virtualGameController.turnSelectedCardSide(viewController.getClientID());
                    break;
                case 3:
                    virtualGameController.playCardFromHand(viewController.getClientID());
                    break;
                case 4:
                    System.out.println("Insert the X coordinate: ");
                    String XPosition = scan.nextLine();
                    System.out.println("Insert the Y coordinate: ");
                    String YPosition = scan.nextLine();
                    virtualGameController.selectPositionOnBoard(Integer.parseInt(XPosition), Integer.parseInt(YPosition), viewController.getClientID());
                    break;
                case 5:
                    //TODO use only one number
                    System.out.println("Insert the X coordinate: ");
                    XPosition = scan.nextLine();
                    System.out.println("Insert the X coordinate: ");
                    YPosition = scan.nextLine();
                    virtualGameController.selectCardFromCommonTable(Integer.parseInt(XPosition), Integer.parseInt(YPosition), viewController.getClientID());
                    break;
                case 6:
                    virtualGameController.drawSelectedCard(viewController.getClientID());
                    break;
                case 7:
                    System.out.println("Insert the pawn color: ");
                    String color = scan.nextLine();
                    virtualGameController.choosePawnColor(color, viewController.getClientID());
                    break;
                case 8:
                    System.out.println("Insert the card index: (0/1) ");
                    int cardIndex = Integer.parseInt(scan.nextLine());
                    virtualGameController.selectSecretMission(cardIndex, viewController.getClientID());
                    break;
                case 9:
                    virtualGameController.setSecretMission(viewController.getClientID());
                    break;
                case 10:
                    System.out.println("Insert the player's nickname owner of the board: ");
                    String playerNickname = scan.nextLine();
                    virtualGameController.printPersonalBoard(playerNickname, viewController.getClientID());
                    break;
                case 11:
                    System.out.println("Insert the receiver's nickname: (Press enter for a broadcast message)");
                    String receiverNickname = scan.nextLine();
                    String message;
                    do {
                        System.out.println("Insert message: ");
                        message = scan.nextLine();
                    } while (message == "");
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    virtualGameController.addMessage(message, receiverNickname, viewController.getClientID(), LocalTime.now().toString().formatted(formatter));
                    break;
                case 12:
                    System.exit(0);
                    break;
            }
        }
    }

    private int printOptions() {
        System.out.println("Select your option:");
        System.out.println("" +
                "1) Select a card.\n" +
                "2) Turn selected card side.\n" +
                "3) Play card from hand.\n" +
                "4) Select position on board.\n" +
                "5) Select card from common table.\n" +
                "6) Draw selected card.\n" +
                "7) Choose pawn color.\n" +
                "8) Select secret mission.\n" +
                "9) Set secret mission.\n" +
                "10) Print player's personal board.\n" +
                "11) Open chat.\n" +
                "12) Exit game\n");


        Scanner scan = new Scanner(System.in);
        int response = scan.nextInt();
        return response;
    }

    public static void main(String args[]) {
        // Get server IP and port
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter server IP address (default: 127.0.0.1): ");
        String serverIP = scanner.nextLine();
        if (!serverIP.isEmpty()) {
            SERVER_IP = serverIP;
        }

        System.out.print("Enter server RMI port (default 1099): ");
        String rmiServerPort = scanner.nextLine();
        if (!rmiServerPort.isEmpty()) {
            RMI_SERVER_PORT = Integer.parseInt(rmiServerPort);
        }

        System.out.print("Enter server socket port (default 3060): ");
        String socketServerPort = scanner.nextLine();
        if (!socketServerPort.isEmpty()) {
            SERVER_SOCKET_PORT = Integer.parseInt(socketServerPort);
        }

        // Get type of communication from user
        NetworkType networkType = null;
        do {
            System.out.println("What type of communication do you want to use? (rmi/socket)");
            try {
                networkType = NetworkType.valueOf(scanner.nextLine().toLowerCase());

            } catch (IllegalArgumentException e) {
                System.err.println("[ERROR]: Invalid input");
            }
        } while (networkType == null);

        // Get user interface from user
        UserInterface userInterface = null;
        do {
            System.out.println("What type of user interface do you want to use? (tui/gui)");
            try {
                userInterface = UserInterface.valueOf(scanner.nextLine().toLowerCase());
            } catch (IllegalArgumentException e) {
                System.err.println("[ERROR]: Invalid input");
            }
        } while (userInterface == null);

        try {
            if (networkType == NetworkType.rmi) {
                // Start RMI Client
                startRMIClient(SERVER_IP, RMI_SERVER_PORT, userInterface);
            } else if (networkType == NetworkType.socket) {
                // Start Socket Client
                startSocketClient(SERVER_IP, SERVER_SOCKET_PORT, userInterface);
            }
        } catch (IOException | NotBoundException e) {
            System.err.println("[ERROR]: Something went wrong with client creation");
        }

    }


    private void RMIPingServer() {
        while (true) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("Thread didn't sleep");
            }

            int numWait = 3;
            while (numWait > 0) {
                try {
                    // Ping server
                    virtualMainController.amAlive();
                    numWait = 0;
                } catch (RemoteException e) {
                    System.out.println("SOMETHING WRONG, TRY " + numWait);
                    numWait--;
                    if (numWait == 0) {
                        System.out.println("SERVER DOWN, WAIT a BIT");
                        isServerUp = false;
                        while (!isServerUp) {
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                            Registry registry = null;
                            try {
                                registry = LocateRegistry.getRegistry("172.20.10.11", MainServer.DEFAULT_RMI_SERVER_PORT);

                                this.setVirtualMainController((VirtualMainController) registry.lookup(MainClient.remoteObjectName));
                                this.setVirtualGameController(virtualMainController.getVirtualGameController(viewController.getGameID()));

                                virtualGameController.reAddView(virtualView, viewController.getClientID());

                                isServerUp = true;
                            } catch (RemoteException ex) {
                                //TODO FORSE METTERE QUALCOSA QUA
                            } catch (NotBoundException ep) {
                                ep.printStackTrace();
                            }
                        }
                        System.out.println("NOW SERVER UP, NOW YOU CAN PLAY");
                    }


                }
            }

        }
    }


    //TODO FARLO ANCHE QUA
    private void SocketPingServer() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("LA SLEEP non è andata a buon fine");
            }
            int numWait = 3;
            while (numWait > 0) {
                try {
                    virtualMainController.amAlive();
                    numWait = 0;
                } catch (IOException e) {
                    System.out.println("SOMETHING WRONG, TRY " + numWait);
                    numWait--;
                    if (numWait == 0) {
                        System.out.println("SERVER DOWN, WAIT a BIT");
                        isServerUp = false;
                        while (!isServerUp) {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }

                            try {
                                Socket serverSocket = new Socket("172.20.10.11", MainServer.DEFAULT_SOCKET_SERVER_PORT);
                                InputStreamReader socketRx = new InputStreamReader(serverSocket.getInputStream());
                                OutputStreamWriter socketTx = new OutputStreamWriter(serverSocket.getOutputStream());

                                // Reader
                                BufferedReader socketIn = new BufferedReader(socketRx);

                                // Writer
                                BufferedWriter socketOut = new BufferedWriter(socketTx);

                                // Create socket client

                                this.setVirtualMainController(new VirtualSocketMainController(socketOut));
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException ex) {
                                    throw new RuntimeException(ex);
                                }
                                this.virtualGameController = this.virtualMainController.getVirtualGameController(viewController.getGameID());
                                this.setVirtualGameController(new VirtualSocketGameController(socketOut));
                                new SocketServerHandler(this.viewController, socketIn, socketOut);

                                virtualGameController.reAddView(virtualView, viewController.getClientID());
                                isServerUp = true;
                            } catch (IOException ex) {
                                System.out.println("SERVER AGAIN DOWN");
                            }
                        }
                        System.out.println("NOW SERVER UP, NOW YOU CAN PLAY");
                    }
                }

            }

        }
    }
}
