package it.polimi.ingsw.gc26.ui.tui;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.MainClient;
import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.game.Message;
import it.polimi.ingsw.gc26.network.ClientResetTimerToServer;
import it.polimi.ingsw.gc26.ui.UIInterface;
import it.polimi.ingsw.gc26.ui.gui.sceneControllers.SceneController;
import it.polimi.ingsw.gc26.utils.ConsoleColors;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.rmi.RemoteException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The TUIApplication class implements the UIInterface to provide a text-based user interface for the application.
 * It interacts with the MainClient to handle the logic for initializing and running the application.
 */
public class TUIApplication implements UIInterface {
    /**
     * The main client instance that this application interacts with.
     */
    MainClient mainClient;

    /**
     * Initializes the application with the specified network type.
     *
     * @param networkType The type of network to initialize.
     */
    @Override
    public void init(MainClient.NetworkType networkType) {
        this.start(networkType.toString());
    }

    /**
     * Sets network parameters and starts pinging the server
     *
     * @param args array with custom parameters
     */
    public void start(String... args) {
        String networkType = args[0];

        if (MainClient.NetworkType.valueOf(networkType) == MainClient.NetworkType.rmi) {
            try {
                this.mainClient = MainClient.startRMIClient(MainClient.GraphicType.tui);
            } catch (RemoteException e) {
                ConsoleColors.printError(e.getMessage());
                System.exit(-1);
            }
        } else {
            try {
                this.mainClient = MainClient.startSocketClient(MainClient.GraphicType.tui);
            } catch (IOException e) {
                ConsoleColors.printError(e.getMessage());
                System.exit(-1);
            }
        }

        this.mainClient
                .getViewController()
                .getSimplifiedModel()
                .setViewUpdater(
                        new TUIUpdate(this.mainClient.getViewController().getSimplifiedModel()));
        try {
            this.runConnection();

            // Launch thread for managing server ping
            new Thread(this.mainClient.getPingManager()).start();
            new Thread(new ClientResetTimerToServer(this.mainClient)).start();

        } catch (RemoteException e) {
            ConsoleColors.printError("[ERROR]: unable to communicate with the server");
            System.exit(-1);
        }

        try {
            this.runGame();
        } catch (RemoteException ignored) {

        }
    }

    /**
     * Connects the client to the server and sets game controller
     *
     * @throws RemoteException if the network is now working
     */
    @Override
    public void runConnection() throws RemoteException {
        //Initial state in CONNECTION
        Scanner scanner = new Scanner(System.in);

        System.out.println("Insert your nickname: ");
        String nickname = scanner.nextLine();
        this.mainClient.getVirtualMainController().connect(this.mainClient.getVirtualView(), nickname, this.mainClient.getClientState());

        synchronized (this.mainClient.getLock()) {
            while (this.mainClient.getClientState() == ClientState.CONNECTION) {
                try {
                    mainClient.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        if (this.mainClient.getClientState() == ClientState.CREATOR) {
            System.out.println("You must initialize a new game \nInsert number of players: (2/3/4)");
            String input = scanner.nextLine();
            this.mainClient.getVirtualMainController().createWaitingList(this.mainClient.getVirtualView(), nickname, Integer.parseInt(input));

            synchronized (this.mainClient.getLock()) {
                while (this.mainClient.getClientState() == ClientState.CREATOR) {
                    try {
                        mainClient.getLock().wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            while (this.mainClient.getClientState() == ClientState.INVALID_NUMBER_OF_PLAYER) {
                this.mainClient.setClientState(ClientState.CREATOR);
                System.err.println("Invalid number of players!");
                System.err.flush();
                System.out.println("Insert number of players: (2/3/4)");
                input = scanner.nextLine();
                this.mainClient.getVirtualMainController().createWaitingList(this.mainClient.getVirtualView(), nickname, Integer.parseInt(input));

                synchronized (this.mainClient.getLock()) {
                    while (this.mainClient.getClientState() == ClientState.CREATOR) {
                        try {
                            mainClient.getLock().wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else if (this.mainClient.getClientState() == ClientState.INVALID_NICKNAME) {
            while (this.mainClient.getClientState() == ClientState.INVALID_NICKNAME) {
                System.err.println("Nickname not available!");
                System.err.flush();
                System.out.println("Insert new nickname: ");
                nickname = scanner.nextLine();
                this.mainClient.setNickname(nickname);

                this.mainClient.getVirtualMainController().connect(this.mainClient.getVirtualView(), nickname, this.mainClient.getClientState());

                synchronized (this.mainClient.getLock()) {
                    while (this.mainClient.getClientState() == ClientState.CONNECTION) {
                        try {
                            mainClient.getLock().wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        System.out.println("Waiting for other players ...");
        synchronized (this.mainClient.getLock()) {
            while (this.mainClient.getClientState() == ClientState.WAITING) {
                System.out.flush();
                try {
                    this.mainClient.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        synchronized (this.mainClient.getLock()) {
            this.mainClient.setVirtualGameController(this.mainClient.getVirtualMainController().getVirtualGameController(this.mainClient.getViewController().getGameID()));
            while (this.mainClient.getVirtualGameController() == null) {
                try {
                    this.mainClient.getLock().wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Game begin");
    }

    /**
     * Start with game interface
     */
    @Override
    public void runGame() throws RemoteException {
        // Declare scanner
        Scanner scan = new Scanner(System.in);
        // Infinite loop
        while (true) {
            int cardIndex;
            String option = scan.nextLine();

            GameState gameState;
            try {
                gameState = this.mainClient.getViewController().getSimplifiedModel().getSimplifiedGame().getGameState();
            } catch (NullPointerException e) {
                gameState = GameState.WAITING_STARTER_CARD_PLACEMENT;
            }
            switch (gameState) {
                case WAITING_STARTER_CARD_PLACEMENT:
                    switch (option) {
                        case "1":
                            mainClient.getVirtualGameController().turnSelectedCardSide(this.mainClient.getClientID());
                            break;
                        case "2":
                            mainClient.getVirtualGameController().playCardFromHand(this.mainClient.getClientID());
                            break;
                        case "3":
                            openChat(gameState);
                            break;
                        case "4":
                            System.exit(0);
                            break;
                        case "5":
                            openRulebook();
                            TUIUpdate.printOptions(gameState);
                            break;
                        case null, default:
                            System.out.println("Invalid option");
                            break;
                    }
                    break;
                case WAITING_PAWNS_SELECTION:
                    switch (option) {
                        case "1":
                            System.out.println("Insert the pawn color: ");
                            String color = new Scanner(System.in).nextLine();
                            mainClient.getVirtualGameController().choosePawnColor(color, this.mainClient.getClientID());
                            break;
                        case "2":
                            openChat(gameState);
                            break;
                        case "3":
                            System.exit(0);
                            break;
                        case "4":
                            openRulebook();
                            TUIUpdate.printOptions(gameState);
                            break;
                        case null, default:
                            System.out.println("Invalid option");
                            break;
                    }
                    break;
                case WAITING_SECRET_MISSION_CHOICE:
                    switch (option) {
                        case "1":
                            do {
                                System.out.println("Insert the card index: (0/1) ");
                                cardIndex = Integer.parseInt(new Scanner(System.in).nextLine());
                            } while (cardIndex != 0 && cardIndex != 1);
                            mainClient.getVirtualGameController().selectSecretMission(cardIndex, this.mainClient.getClientID());
                            break;
                        case "2":
                            mainClient.getVirtualGameController().setSecretMission(this.mainClient.getClientID());
                            break;
                        case "3":
                            openChat(gameState);
                            break;
                        case "4":
                            System.exit(0);
                            break;
                        case "5":
                            openRulebook();
                            TUIUpdate.printOptions(gameState);
                            break;
                        case null, default:
                            System.out.println("Invalid option");
                            break;
                    }
                    break;
                case GAME_STARTED:
                    switch (option) {
                        case "1":
                            String xPosition;
                            do {
                                System.out.println("Select the card position: (0/1/2)");
                                xPosition = new Scanner(System.in).nextLine();
                            } while (Integer.parseInt(xPosition) < 0 || Integer.parseInt(xPosition) > 2);
                            mainClient.getVirtualGameController().selectCardFromHand(Integer.parseInt(xPosition), this.mainClient.getClientID());
                            break;
                        case "2":
                            mainClient.getVirtualGameController().turnSelectedCardSide(this.mainClient.getClientID());
                            break;
                        case "3":
                            mainClient.getVirtualGameController().playCardFromHand(this.mainClient.getClientID());
                            break;
                        case "4":
                            System.out.println("Insert the X coordinate: ");
                            String XPosition = new Scanner(System.in).nextLine();
                            System.out.println("Insert the Y coordinate: ");
                            String YPosition = new Scanner(System.in).nextLine();
                            mainClient.getVirtualGameController().selectPositionOnBoard(Integer.parseInt(XPosition), Integer.parseInt(YPosition), this.mainClient.getClientID());
                            break;
                        case "5":
                            do {
                                System.out.println("Insert the card index (0/1/2/3/4/5): ");
                                cardIndex = Integer.parseInt(new Scanner(System.in).nextLine());
                            } while (cardIndex < 0 || cardIndex > 5);
                            mainClient.getVirtualGameController().selectCardFromCommonTable(cardIndex, this.mainClient.getClientID());
                            break;
                        case "6":
                            mainClient.getVirtualGameController().drawSelectedCard(this.mainClient.getClientID());
                            break;
                        case "7":
                            System.out.println("Insert the player's nickname owner of the board: ");
                            String playerNickname = new Scanner(System.in).nextLine();
                            if (mainClient.getViewController().getSimplifiedModel().getOthersPersonalBoards().containsKey(playerNickname)) {
                                mainClient.getViewController().getSimplifiedModel().getView().updateViewOtherPersonalBoard(mainClient.getViewController().getSimplifiedModel().getOthersPersonalBoards().get(playerNickname));
                                break;
                            }
                            System.out.println(playerNickname + "'s Personal Board not found!");
                            break;
                        case "8":
                            openChat(gameState);
                            break;
                        case "9":
                            System.exit(0);
                            break;
                        case "10":
                            if (Desktop.isDesktopSupported()) {
                                try {
                                    File myFile = new File("src/main/resources/Rulebook/CODEX_Rulebook_EN.pdf");
                                    Desktop.getDesktop().open(myFile);
                                } catch (IOException ex) {
                                    System.out.println("Np application found to open the rulebook!");
                                }
                            }
                            TUIUpdate.printOptions(gameState);
                            break;
                        case null, default:
                            System.out.println("Invalid option");
                            break;
                    }
                    break;
                case END_STAGE:
                    switch (option) {
                        case "1":
                            openChat(gameState);
                            break;
                        case "2":
                            System.exit(0);
                            break;
                        case "3":
                            openRulebook();
                            break;
                        case null, default:
                            System.out.println("Invalid option");
                            break;
                    }
                    break;
                case null, default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

    /**
     * Opens with the predetermine application a pdf containing the game rules
     */
    private void openRulebook() {
        InputStream inputStream = SceneController.class.getResourceAsStream("CODEX_Rulebook_EN.pdf");
        try {
            if (inputStream != null) {
                File tempFile = File.createTempFile("CODEX_Rulebook_EN", ".pdf");
                tempFile.deleteOnExit();

                // Copy the input stream to a temporary file
                Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                // Open the temporary file
                Desktop.getDesktop().open(tempFile);
            } else {
                System.err.println("Resource not found: ");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Manages the chat action that can be performed by the user, such as send a new message or see previous messages
     *
     * @param gameState current game's state
     * @throws RemoteException if the connection has gone down
     */
    private void openChat(GameState gameState) throws RemoteException {
        int function = 0;
        do {
            System.out.println("1) Open Chat.\n" +
                    "2) Send new message.");
            try {
                function = Integer.parseInt(new Scanner(System.in).nextLine());
            } catch (Exception ex) {
                // pass
            }
        } while (function != 1 && function != 2);

        if (function == 1) {
            System.out.println("Insert the player's nickname to filter the messages: (Press enter for a all messages)");
            String playerNickname = new Scanner(System.in).nextLine();
            ArrayList<String> playersNicknames = this.mainClient.getViewController().getSimplifiedModel().getSimplifiedGame().getPlayersNicknames();
            if (mainClient.getViewController().getSimplifiedModel().getSimplifiedChat() == null) {
                System.out.println("No messages!");
                return;
            }
            for (Message message : mainClient.getViewController().getSimplifiedModel().getSimplifiedChat().filterMessagesByPlayer(playerNickname, playersNicknames)) {
                System.out.println(message);
            }
            TUIUpdate.printOptions(gameState);
        } else {
            System.out.println("Insert the receiver's nickname: (Press enter for a broadcast message)");
            String receiverNickname = new Scanner(System.in).nextLine();
            String message;
            do {
                System.out.println("Insert message: ");
                message = new Scanner(System.in).nextLine();
            } while (message.isEmpty());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            mainClient.getVirtualGameController().addMessage(message, receiverNickname, this.mainClient.getClientID(), LocalTime.now().toString().formatted(formatter));
        }
    }
}
