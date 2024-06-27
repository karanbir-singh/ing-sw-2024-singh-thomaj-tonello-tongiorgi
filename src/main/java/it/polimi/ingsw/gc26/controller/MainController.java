package it.polimi.ingsw.gc26.controller;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.player.Player;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.request.main_request.MainRequest;
import it.polimi.ingsw.gc26.utils.ConsoleColors;
import javafx.util.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.util.*;

/**
 * This class implements the methods to join automatically the client to the games.
 * It also destroys the games if a client is not longer reachable.
 */
public class MainController implements Serializable {
    /**
     * This attribute represents the file path for saving the main controller
     */
    public static final String MAIN_CONTROLLER_FILE_PATH = "../mainController.bin";

    /**
     * This attribute represents the list of clients who are waiting for a new game
     */
    private transient ArrayList<VirtualView> waitingClients;

    /**
     * This attribute represents the list of players who are waiting for a new game
     */
    private final ArrayList<Player> waitingPlayers;

    /**
     * This attribute represents the list of game controllers of started games and id of that game controller
     */
    private final Map<Integer, GameController> gamesControllers;

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
     * This attribute represents the executor current state
     */
    public boolean threadStarted = false;

    /**
     * This lock is used to synchronize the reading of threadStated
     */
    public Boolean lock = true;

    /**
     * Map of timers, having clients' IDs as key
     */
    transient private Map<String, Long> timers;

    /**
     * Max time the server has to wait to suppose that a client is down (10 seconds)
     */
    private static final int TIMEOUT = 10;

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

        timers = new HashMap<>();
        this.launchExecutor();
    }

    /**
     * Copy everything on the disk
     *
     * @throws IOException
     */
    private void backup() {
        FileOutputStream fileOutputStream;
        ObjectOutputStream outputStream;
        try {
            fileOutputStream = new FileOutputStream(MAIN_CONTROLLER_FILE_PATH);
            outputStream = new ObjectOutputStream(fileOutputStream);
            outputStream.writeObject(this);
            outputStream.close();
            fileOutputStream.close();
        } catch (IOException e) {
            ConsoleColors.printError("[ERROR]: error while saving main controller data on file");
        }
    }

    /**
     * Launch a thread for executing clients connection requests
     */
    public void launchExecutor() {
        this.mainRequests = new PriorityQueue<>((a, b) -> a.getPriority() > b.getPriority() ? -1 : 1);
        this.waitingClients = new ArrayList<>();

        new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    threadStarted = true;
                    lock.notifyAll();
                }
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
     * @param nickname nickname of the client
     * @return Returns true if waitingPlayer not contains other players with the given nickname, otherwise false
     */
    private boolean isNicknameValid(String nickname) {
        return !waitingPlayers.stream().anyMatch(player -> player.getNickname().equals(nickname));
    }

    /**
     * Joins the new client to an existing game if there's any game with available player,
     * otherwise it set the client's state to creator.
     *
     * @param client   Virtual view representing the client
     * @param nickname client's nickname
     */
    public void connect(VirtualView client, String nickname) {
        synchronized (mainRequests) {
            // Check if there is not a game waiting for players
            if (!this.existsWaitingGame()) {
                // Set new game on creation
                gameOnCreation = true;
                synchronized (mainRequests) {
                    this.mainRequests.notifyAll();
                }
                try {
                    client.updateClientState(ClientState.CREATOR);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                // Otherwise client joins into a game on creation
                this.joinWaitingList(client, nickname);
            }
        }

        // Copy on disk
        this.backup();
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
     * @param client     Virtual view representing the client
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
            synchronized (mainRequests) {
                this.mainRequests.notifyAll();
            }

            try {
                client.setClientID(clientID);
                client.updateClientState(ClientState.WAITING);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            // Otherwise the game is still on creation
            this.gameOnCreation = true;
            synchronized (mainRequests) {
                this.mainRequests.notifyAll();
            }
            try {
                client.updateClientState(ClientState.INVALID_NUMBER_OF_PLAYER);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        //copy on the disk
        this.backup();
    }

    /**
     * Adds a player into the waiting list, if exists
     *
     * @param client   view to call methods in the client side
     * @param nickname Nickname of the player who is joining the waiting list
     */
    public void joinWaitingList(VirtualView client, String nickname) {
        GameController gameController = null;
        Game game;

        // Check if the nickname it's not available
        if (!this.isNicknameValid(nickname)) {
            try {
                invalidNickname = true;
                synchronized (mainRequests) {
                    this.mainRequests.notifyAll();
                }
                client.updateClientState(ClientState.INVALID_NICKNAME);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            // For now, ID it's random string
            String clientID = UUID.randomUUID().toString();

            invalidNickname = false;

            synchronized (mainRequests) {
                this.mainRequests.notifyAll();
            }

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
                game = new Game(waitingPlayers, waitingClients);
                gameController = new GameController(game, this.numberOfTotalGames);

                // Add game controller to the map
                gamesControllers.put(numberOfTotalGames, gameController);

                // Update of the view
                waitingClients.forEach(view -> {
                    try {
                        view.updateIDGame(numberOfTotalGames);
                        view.updateClientState(ClientState.BEGIN);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });

                // Launch thread for pinging clients
                this.startClientsPing(gameController.getGame().getObservable().getClients(), this.numberOfTotalGames);

                // Start game
                gameController.prepareCommonTable();

                // Reset flag
                gameOnCreation = false;
                invalidNickname = false;

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
        this.backup();
    }

    /**
     * Returns the game controller if exists.
     *
     * @param gameControllerID unique identifier for the game
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
     * @throws IOException            if connection is not restored
     * @throws ClassNotFoundException if class is not found
     */
    public void recreateGames() throws IOException, ClassNotFoundException {
        for (Integer gameControllerID : gamesControllers.keySet()) {
            FileInputStream fileInputStream = new FileInputStream(GameController.GAME_CONTROLLER_FILE_PATH + gameControllerID + ".bin");
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

        //recreate timers
        this.timers = new HashMap<>();
        // Launch a thread for recreating ping
        this.createGeneratorPingThread();
        System.out.println("Games recreated");
    }


    /**
     * Thread useful after a server goes up from a crash: called in recreateGame()
     */
    private void createGeneratorPingThread() {
        for (Integer gameControllerID : gamesControllers.keySet()) {
            new Thread(() -> {
                Game game = gamesControllers.get(gameControllerID).getGame();
                int maxSecondsToWaitForClientReconnection = 30;
                long currentTime = System.currentTimeMillis();
                while (game.getNumberOfPlayers() != game.getObservable().getClients().size()) {
                    // wait here so that everything is reloading, because not necessary the virtual views are already there


                    //if a client isn't reconnecting for more than 30 seconds, the server closes the game
                    if ((System.currentTimeMillis() - currentTime) / 1000 >= maxSecondsToWaitForClientReconnection) {
                        this.destroyGame(gameControllerID);
                        return;
                    }
                    System.out.println("we are in the while, game " + gameControllerID);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

                // Launch thread for pinging clients
                this.startClientsPing(game.getObservable().getClients(), gameControllerID);
                System.out.println("thread creati, game " + gameControllerID);
            }).start();
        }
    }


    /**
     * Creates a thread for every client and pings each one. If a client goes down, it attempts to reconnect.
     * If the client doesn't go up, it destroys the game
     *
     * @param clients
     * @param gameControllerID
     */
    private void startClientsPing(ArrayList<Pair<VirtualView, String>> clients, int gameControllerID) {
        // Ping each client of the game
        for (Pair<VirtualView, String> client : clients) {
            new Thread(() -> {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    try {
                        client.getKey().ping();
                    } catch (RemoteException e) {
                        //System.out.println("Connection Problem, thread ping server to client");
                    }
                }
            }).start();

            new Thread(() -> {
                boolean isAlive = true;
                while (isAlive) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    long elapsed;
                    long currentTime = System.currentTimeMillis();
                    if (this.timers.get(client.getValue()) == null) { //not exist a timer, so the first ping is not already arrived
                        elapsed = 0;
                    } else {
                        elapsed = (currentTime - this.timers.get(client.getValue())) / 1000;
                    }

                    if (elapsed >= TIMEOUT) {
                        System.out.println("Client " + client.getValue() + " disconnected");
                        isAlive = false;
                        synchronized (this.timers) {
                            this.timers.remove(client.getValue());
                        }
                        this.destroyGame(gameControllerID);
                    }
                }
            }).start();
        }
    }

    /**
     * Destroy a game controller by its ID
     *
     * @param gameControllerID ID of the game controller that you want to destroy
     */
    public void destroyGame(int gameControllerID) {
        //the game is already destroyed by another player
        if (gamesControllers.get(gameControllerID) == null) {
            return;
        }
        // Notify game destruction
        gamesControllers.get(gameControllerID).getGame().getObservable().notifyGameClosed();
        // Remove game from the map
        gamesControllers.remove(gameControllerID);

        // Copy to disk again
        this.backup();

        // Delete game controller file
        Path fileToDeletePath = Paths.get(GameController.GAME_CONTROLLER_FILE_PATH + gameControllerID + ".bin");
        try {
            Files.delete(fileToDeletePath);
        } catch (IOException e) {
            System.out.println("There is no file on the disk, it is already been deleted");
            return;
        }
        System.out.println("Game " + gameControllerID + " destroyed");
    }

    /**
     * Resets server timer for the client caller
     *
     * @param clientID ID of the client
     */
    public void resetServerTimer(String clientID) {
        //is the first ping so we need to create the timer
        if (this.timers.get(clientID) == null) {
            synchronized (this.timers) {
                this.timers.put(clientID, System.currentTimeMillis());
            }
        } else { //exist already a timer so it s not the first ping that comes from that specific client
            synchronized (this.timers) {
                this.timers.put(clientID, System.currentTimeMillis());
            }
        }
    }

    /**
     * Returns client's timers saved on server
     *
     * @return
     */
    public Map<String, Long> getTimers() {
        return timers;
    }

    /**
     * Returns the queue of requests
     *
     * @return mainRequests
     */
    public PriorityQueue<MainRequest> getMainRequests() {
        return mainRequests;
    }

    /**
     * Returns the clients that are waiting to be inserted in a game
     *
     * @return an arrayList representing all the waiting clients
     */
    public ArrayList<VirtualView> getWaitingClients() {
        return waitingClients;
    }
}
