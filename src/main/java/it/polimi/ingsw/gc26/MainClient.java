package it.polimi.ingsw.gc26;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.controller.GameController;
import it.polimi.ingsw.gc26.controller.MainController;
import it.polimi.ingsw.gc26.network.RMI.VirtualRMIMainController;
import it.polimi.ingsw.gc26.network.RMI.VirtualRMIView;
import it.polimi.ingsw.gc26.network.VirtualGameController;
import it.polimi.ingsw.gc26.network.VirtualMainController;
import it.polimi.ingsw.gc26.network.VirtualView;
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

    private VirtualMainController virtualMainController;

    private VirtualGameController virtualGameController;

    private VirtualView virtualView;

    public MainClient(VirtualMainController mainController) {
        this.virtualMainController = mainController;
        this.lock = new Object();
        this.clientState = ClientState.CONNECTION;
        this.lockController = new Object();
    }

    private String clientID;
    private String nickname;
    private Object lock;
    private Object lockController;
    ClientState clientState;

    public void setClientID(String clientID) {
        synchronized (this.lockController) {
            this.clientID = clientID;
            lockController.notifyAll();
        }
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setClientState(ClientState clientState) {
        synchronized (lock) {
            this.clientState = clientState;
            this.lock.notifyAll();
        }
    }

    public void setVirtualGameController(VirtualGameController virtualGameController) {
        synchronized (this) {
            this.virtualGameController = virtualGameController;
            this.notifyAll();
        }
    }

    public ClientState getClientState() {
        synchronized (lock) {
            while(clientState == null) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return clientState;

        }
    }

    public String getClientID() {
        synchronized (this) {
            while(this.clientID == null){
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            return clientID;
        }
    }

    public String getNickname() {
        return nickname;
    }

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
        MainClient client = new MainClient((VirtualMainController) registry.lookup(remoteObjectName));

        // Check chosen user interface
        if (userInterface == UserInterface.tui) {
            client.virtualView = new VirtualRMIView(client.virtualMainController, client);
            client.connect();
            client.runCommonTui();
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
        SocketClient socketClient = new SocketClient(new BufferedReader(socketRx), new PrintWriter(socketTx));
        MainClient client = new MainClient(socketClient.getVirtualMainController());
        socketClient.setMainClient(client);

        // Check chosen user interface
        if (userInterface == UserInterface.tui) {
            client.virtualView = socketClient.serverHandler;
            client.connect();
            client.runCommonTui();
        } else if (userInterface == UserInterface.gui) {
            //new SocketClient(new BufferedReader(socketRx), new PrintWriter(socketTx)).runGUI();
        }
    }

    /**
     * Runs the TUI for socket and RMI connection, after game controller creation
     *
     * @throws RemoteException
     */
    public void runCommonTui() throws RemoteException {
        // Declare scanner
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
                    virtualGameController.selectCardFromHand(Integer.parseInt(xPosition), clientID);
                    break;
                case "/2":
                    virtualGameController.turnSelectedCardSide(clientID);
                    break;
                case "/3":
                    virtualGameController.playCardFromHand(clientID);
                    break;
                case "/4":
                    System.out.println("WHAT XPOSITION DO YOU WANT TO SELECT ON PERSONAL BOARD:");
                    String XPosition = scan.nextLine();
                    System.out.println("WHAT YPOSITION DO YOU WANT TO SELECT ON PERSONAL BOARD:");
                    String YPosition = scan.nextLine();
                    virtualGameController.selectPositionOnBoard(Integer.parseInt(XPosition), Integer.parseInt(YPosition), clientID);
                    break;
                case "/5":
                    System.out.println("WHAT XPOSITION DO YOU WANT TO SELECT ON COMMON BOARD:");
                    XPosition = scan.nextLine();
                    System.out.println("WHAT YPOSITION DO YOU WANT TO SELECT ON COMMON BOARD:");
                    YPosition = scan.nextLine();
                    virtualGameController.selectCardFromCommonTable(Integer.parseInt(XPosition), Integer.parseInt(YPosition), clientID);
                    break;
                case "/6":
                    virtualGameController.drawSelectedCard(clientID);
                    break;
                case "/7":
                    virtualGameController.choosePawnColor("red", clientID);
                    break;
                case "/8":
                    virtualGameController.selectSecretMission(0, clientID);
                    break;
                case "/9":
                    virtualGameController.setSecretMission(clientID);
                    break;
                case "/10":
                    virtualGameController.printPersonalBoard("gabi", clientID); // TODO get nickname as parameter
            }

            if (chat) {
                virtualGameController.addMessage(line, receiver, clientID, LocalTime.now().toString());
            }

        }
    }

    public void connect() throws RemoteException {
        // TODO gestire la Remote Exception
        //Initial state in CONNECTION
        Scanner scanner = new Scanner(System.in);
        System.out.println("Connected to the server successfully!");
        System.out.println("Insert your nickname: ");
        this.nickname = scanner.nextLine();
        this.virtualMainController.connect(this.virtualView, this.nickname, this.clientState);

        synchronized (this.lock) {
            while (this.clientState == ClientState.CONNECTION) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (this.clientState == ClientState.CREATOR) {
            System.out.println("You must initialize a new game \nInsert number of players: (2/3/4)");
            String decision = scanner.nextLine();
            this.virtualMainController.createWaitingList(this.virtualView, this.nickname, Integer.parseInt(decision));

            synchronized (this.lock) {
                while (this.clientState == ClientState.CREATOR) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            while (this.clientState == ClientState.INVALID_NUMBER_OF_PLAYER) {
                this.clientState = ClientState.CREATOR;
                System.err.println("Invalid number of players!"); System.err.flush();
                System.out.println("Insert number of players: (2/3/4)");
                decision = scanner.nextLine();
                this.virtualMainController.createWaitingList(this.virtualView, this.nickname, Integer.parseInt(decision));

                synchronized (this.lock) {
                    while (this.clientState == ClientState.CREATOR) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else if (clientState.equals(ClientState.INVALID_NICKNAME)) {
            while (clientState == ClientState.INVALID_NICKNAME) {
                System.err.println("Nickname not available!"); System.err.flush();
                System.out.println("Insert new nickname: ");
                this.nickname = scanner.nextLine();

                this.virtualMainController.connect(this.virtualView, this.nickname, this.clientState);

                synchronized (this.lock) {
                    while (this.clientState == ClientState.CONNECTION) {
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
        while (clientState == ClientState.WAITING) {
            System.out.flush();
        }

        synchronized (this.lockController) {
            this.virtualGameController = this.virtualMainController.getVirtualGameController();
            while(this.virtualGameController == null) {
                try {
                    this.lockController.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println("Game begin");
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
