package it.polimi.ingsw.gc26.controller;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.player.Player;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.request.main_request.MainRequest;
import javafx.util.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.*;

public class MainController implements Serializable {
    /**
     * This constant represents the max numbers of reconnection attempts
     */
    private static final int NUM_RECONNECTION_ATTEMPTS = 3;
    /**
     * This attribute represents the file path for saving the main controller
     */
    public static final String MAIN_CONTROLLER_FILE_PATH = "src/main/mainController.bin";

    /**
     * This attribute represents the list of clients who are waiting for a new game
     */
    private transient ArrayList<VirtualView> waitingClients;

    /**
     * This attribute represents the list of players who are waiting for a new game
     */
    private ArrayList<Player> waitingPlayers;

    /**
     * This attribute represents the list of game controllers of started games and id of that game controller
     */
    private Map<Integer, GameController> gamesControllers;

    /**
     * This attribute represents a priority queue of main requests
     */
    private transient PriorityQueue<MainRequest> mainRequests;

    /**
     * This attribute represents a game that is being created
     */
    private boolean gameOnCreation;

    /**
     * This attribute represents a boolean for checking if a client sent an invalid nickname
     */
    private boolean invalidNickname;

    /**
     * This attribute represents the number of player that needs to wait until a game is created
     */
    private int maxNumWaitingClients;

    /**
     * This attribute represents the number of games
     */
    private int numberOfTotalGames;


    /**
     * Initializes waiting players' list and games controllers' list
     */
    public MainController() {
        this.waitingClients = new ArrayList<>();
        this.waitingPlayers = new ArrayList<>();
        this.gamesControllers = new HashMap<>();
        this.mainRequests = new PriorityQueue<>((a, b) -> a.getPriority() > b.getPriority() ? -1 : 1);
        maxNumWaitingClients = 0;
        gameOnCreation = false;
        invalidNickname = false;
        numberOfTotalGames = 0;

        this.launchExecutor();
    }

    /**
     * Copy everything on the disk
     *
     * @throws IOException
     */
    private void copyToDisk() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(MAIN_CONTROLLER_FILE_PATH);
        ObjectOutputStream outputStream = new ObjectOutputStream(fileOutputStream);
        outputStream.writeObject(this);
        outputStream.close();
        fileOutputStream.close();
    }

    /**
     * Launch a thread for executing clients connection requests
     */
    public void launchExecutor() {
        this.mainRequests = new PriorityQueue<>((a, b) -> a.getPriority() > b.getPriority() ? -1 : 1);
        this.waitingClients = new ArrayList<>();
        //le due righe di prima servono solo perchè quando il server da down va in up esse diventano null
        new Thread(() -> {
            while (true) {
                synchronized (mainRequests) {
                    while (mainRequests.isEmpty() || gameOnCreation || invalidNickname) {
                        try {
                            mainRequests.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    mainRequests.remove().execute(this);
                }
            }
        }).start();
    }

    /**
     * Adds a new request from the client
     *
     * @param mainRequest request from the client
     */
    public void addRequest(MainRequest mainRequest) {
        synchronized (mainRequests) {
            mainRequests.add(mainRequest);
            if (mainRequest.getPriority() == 1) {
                gameOnCreation = false;
            } else if (mainRequest.getPriority() == 2) {
                invalidNickname = false;
            }
            mainRequests.notifyAll();
        }
    }

    /**
     * Checks if the given nickname is alreadyUsed
     *
     * @param nickname
     * @return Returns true if waitingPlayer not contains other players with the given nickname, otherwise false
     */
    private boolean isNicknameValid(String nickname) {
        return !waitingPlayers.stream().anyMatch(player -> player.getNickname().equals(nickname));
    }

    public void connect(VirtualView client, String nickname) {
        // Check if there is not a game waiting for players
        if (!this.existsWaitingGame()) {
            // Set new game on creation
            gameOnCreation = true;
            this.mainRequests.notifyAll();
            try {
                client.updateClientState(ClientState.CREATOR);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            // Otherwise client joins into a game on creation
            this.joinWaitingList(client, nickname);
        }

        // Copy on disk
        try {
            this.copyToDisk();
        } catch (IOException e) {
            System.out.println("COLPA DI COPYTODISK CONNECT");
            e.printStackTrace();
        }
    }

    /**
     * Check if there are players waiting
     *
     * @return true if there are players waiting, false otherwise
     */
    private boolean existsWaitingGame() {
        return !waitingClients.isEmpty();
    }

    /**
     * Initializes the waiting list of players and updating max numbers of players for the next game
     *
     * @param numPlayers number of players of the next game
     * @param nickname   nickname of the player who is initializing the waiting list
     */
    public void createWaitingList(VirtualView client, String nickname, int numPlayers) {
        // For now, ID it's random string
        String clientID = UUID.randomUUID().toString();

        // Check if given number of players is correct
        if (numPlayers > 1 && numPlayers <= Game.MAX_NUM_PLAYERS) {
            // Add client in the waiting list
            this.waitingClients.add(client);
            this.waitingPlayers.add(new Player(clientID, nickname));

            // Update the max numbers of players for the game
            this.maxNumWaitingClients = numPlayers;

            // The game is "created" but waiting for players
            this.gameOnCreation = false;
            this.mainRequests.notifyAll();

            try {
                client.setClientID(clientID);
                client.updateClientState(ClientState.WAITING);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            // Otherwise the game is still on creation
            this.gameOnCreation = true;
            this.mainRequests.notifyAll();
            try {
                client.updateClientState(ClientState.INVALID_NUMBER_OF_PLAYER);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        //copy on the disk
        try {
            this.copyToDisk();
        } catch (IOException e) {
            System.out.println("COLPA DI COPYTODISK DI CREATEWAITING LIST");
            e.printStackTrace();
        }
    }

    /**
     * Adds a player into the waiting list, if exists
     *
     * @param nickname Nickname of the player who is joining the waiting list
     */
    private void joinWaitingList(VirtualView client, String nickname) {
        GameController gameController = null;
        Game game;

        // Check if the nickname it's not available
        if (!this.isNicknameValid(nickname)) {
            try {
                invalidNickname = true;
                this.mainRequests.notifyAll();
                client.updateClientState(ClientState.INVALID_NICKNAME);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            // For now, ID it's random string
            String clientID = UUID.randomUUID().toString();

            invalidNickname = false;
            this.mainRequests.notifyAll();

            // Otherwise, add client in waiting list
            this.waitingClients.add(client);
            this.waitingPlayers.add(new Player(clientID, nickname));

            try {
                client.setClientID(clientID);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            // Check number of clients in waiting list
            if (waitingClients.size() >= maxNumWaitingClients) {
                // Update number of games
                this.numberOfTotalGames = this.numberOfTotalGames + 1;

                // Then, create a new game controller
                try {
                    game = new Game(waitingPlayers, waitingClients);
                    gameController = new GameController(game, this.numberOfTotalGames);
                } catch (IOException e) {
                    System.out.println("COLPA DELLA CREAZIONE GAME CONTROLLER");
                    e.printStackTrace();
                }

                // Launch thread for pinging clients
                this.createSingleGamePingThread(gameController.getGame().getObservable().getClients(), this.numberOfTotalGames);

                // Add game controller to the map
                gamesControllers.put(numberOfTotalGames, gameController);

                // Update of the view
                for (VirtualView view : waitingClients) {
                    try {
                        view.updateIDGame(numberOfTotalGames);
                        view.updateClientState(ClientState.BEGIN);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

                // Start game
                gameController.prepareCommonTable();

                // Clear waiting lists
                waitingClients.clear();
                waitingPlayers.clear();
            } else {
                // Otherwise client state is on WAITING_GAME
                try {
                    client.updateClientState(ClientState.WAITING);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        //copy on the disk
        try {
            this.copyToDisk();
        } catch (IOException e) {
            System.out.println("COLPA COPY TO DISK DI JOINWAITINGLIST");
            e.printStackTrace();
        }
    }

    /**
     * @return Returns the right game controller based on the id
     */
    public GameController getGameController(int gameControllerID) {
        if (!gamesControllers.isEmpty()) {
            return gamesControllers.get(gameControllerID);
        }
        return null;
    }

    /**
     * Reconstruct every game controller from the disk
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void recreateGames() throws IOException, ClassNotFoundException {
        for (Integer gameControllerID : gamesControllers.keySet()) {
            FileInputStream fileInputStream = new FileInputStream(STR."\{GameController.GAME_CONTROLLER_FILE_PATH}\{gameControllerID}.bin");
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);

            // Retrieve game controller
            GameController gameController = (GameController) inputStream.readObject();
            gamesControllers.put(gameControllerID, gameController);

            // Restart game controller request executor thread
            gamesControllers.get(gameControllerID).launchExecutor();

            // Close streams
            inputStream.close();
            fileInputStream.close();
        }

        // Launch a thread for recreating al games
        this.createGeneratorPingThread();
        System.out.println("Games recreated");
    }

    /**
     * Useful for pinging from the client to the server
     */
    public void amAlive() {
        //TODO MAYBE FOR SOCKET IS BETTER IF THIS RETURN A STRING
    }

    /**
     * Thread useful after a server goes up from a crash: called in recreateGame()
     */
    private void createGeneratorPingThread() {
        new Thread(() -> {
            for (Integer gameControllerID : gamesControllers.keySet()) {
                Game game = gamesControllers.get(gameControllerID).getGame();
                while (game.getNumberOfPlayers() != game.getObservable().getClients().size()) {
                    // wait here so that everything is reloading, because not necessary the virtual views are already there
                }

                // Launch thread for pinging clients
                this.createSingleGamePingThread(game.getObservable().getClients(), gameControllerID);
            }
        }).start();
    }

    /**
     * Useful to verify if the client is online or not
     *
     * @param clients          Array of VirtualView and id of that particular Game
     * @param gameControllerID id of the game
     */
    private void createSingleGamePingThread(ArrayList<Pair<VirtualView, String>> clients, int gameControllerID) {
        new Thread(() -> {
            // Each client is alive
            boolean allClientAlive = true;

            // While
            while (allClientAlive) {

                // Ping each client of the game
                for (Pair client : clients) {

                    // Use a counter for managing
                    int numAttempt = 0;
                    while (numAttempt < NUM_RECONNECTION_ATTEMPTS) {
                        try {
                            // Ping client
                            ((VirtualView) client.getKey()).isClientAlive();

                            // Reset num of attempt for this client
                            numAttempt = NUM_RECONNECTION_ATTEMPTS;
                        } catch (RemoteException e) {
                            System.out.println(STR."Trying to reconnecting with a client \{numAttempt}");

                            // Increase attempt num
                            numAttempt++;

                            // Check if attempt num reached max after error
                            if (numAttempt == NUM_RECONNECTION_ATTEMPTS) {
                                System.out.println(STR."A client is disconnected \{client.getValue()}");

                                // Client is not alive
                                allClientAlive = false;

                                // Destry game
                                this.destroyGame(gameControllerID);
                            }
                        }
                    }
                    // Check if client is alive
                    if (!allClientAlive) break;
                }

                // Sleep thread
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    System.out.println("Thread interrupted");
                }
            }
        }).start();
    }


    /**
     * @param gameControllerID ID of the game controller that you want to destroy
     */
    private void destroyGame(int gameControllerID) {
        // Notify game destruction
        gamesControllers.get(gameControllerID).getGame().getObservable().notifyGameClosed();

        // Remove game from the map
        gamesControllers.remove(gameControllerID);

        // Copy to disk again
        try {
            this.copyToDisk();
        } catch (IOException e) {
            System.out.println("COLPA DEL COPY TO DISK in DESTROY GAME");
        }

        // Delete game controller file
        Path fileToDeletePath = Paths.get(STR."\{GameController.GAME_CONTROLLER_FILE_PATH}\{gameControllerID}.bin");
        try {
            Files.delete(fileToDeletePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(STR."Game \{gameControllerID} destroyed");
    }
}
