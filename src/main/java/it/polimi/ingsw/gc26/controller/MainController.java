package it.polimi.ingsw.gc26.controller;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.player.Player;
import it.polimi.ingsw.gc26.network.VirtualView;
import it.polimi.ingsw.gc26.request.main_request.MainRequest;

import java.io.*;
import java.rmi.RemoteException;
import java.util.*;

public class MainController implements Serializable {
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
    private Map<Integer,GameController> gamesControllers;

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

    private int numberOfTotalGames;


    //here we put an arrayList of virtualViewIP
    //private ArrayList<String> allClientsIP; //capire come gestire le VirtualView, in quanto
                                            //va gestito anche l observable

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
        this.launchExecutor();
        numberOfTotalGames = 0;
        //allClients = new ArrayList<>();
    }


    private void copyToDisk() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("src/main/mainController.bin");
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

    public void connect(VirtualView client, String nickname){
        // Check if there is not a game waiting for players
        if (!this.existsWaitingGame()) {
            // Set new game on creation
            gameOnCreation = true;
            this.mainRequests.notifyAll();
            try {
                client.updateState(ClientState.CREATOR);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            // Otherwise client joins into a game on creation
            this.joinWaitingList(client, nickname);
        }

        //copy on the disk
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
                client.updateState(ClientState.WAITING);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            // Otherwise the game is still on creation
            this.gameOnCreation = true;
            this.mainRequests.notifyAll();
            try {
                client.updateState(ClientState.INVALID_NUMBER_OF_PLAYER);
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
    private void joinWaitingList(VirtualView client, String nickname){
        GameController gameController = null;

        // Check if the nickname it's not available
        if (!this.isNicknameValid(nickname)) {
            try {
                invalidNickname = true;
                this.mainRequests.notifyAll();
                client.updateState(ClientState.INVALID_NICKNAME);
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
                // Then, create a new game controller
                try {
                    numberOfTotalGames = numberOfTotalGames + 1;
                    gameController = new GameController(new Game(waitingPlayers, waitingClients),"src/main/resources/gameControllerText" +
                                                                                             numberOfTotalGames + ".bin");

                } catch (IOException e) {
                    System.out.println("COLPA DELLA CREAZIONE GAME CONTROLLER");
                    e.printStackTrace();
                }
                gamesControllers.put(numberOfTotalGames,gameController);

                // Update of the view
                for (VirtualView view : waitingClients) {
                    try {
                        view.updateIDGame(numberOfTotalGames);
                        view.updateState(ClientState.BEGIN);
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
                    client.updateState(ClientState.WAITING);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }

        //copy on the disk
        try {
            this.copyToDisk();
        } catch (IOException e) {
            System.out.println("COPIA COPY TO DISK DI JOINWAITINGLIST");
            e.printStackTrace();
        }
    }

    /**
     * @return Returns the last created game controller
     */
    public GameController getGameController(int id) {
        if (!gamesControllers.isEmpty()){
            return gamesControllers.get(id);
        }
        return null;
    }

    public void recreateGames() throws IOException, ClassNotFoundException {
        for(Integer id : gamesControllers.keySet()){
            GameController gameController = new GameController(null,null);
            FileInputStream fileInputStream = new FileInputStream("src/main/resources/gameControllerText" + id + ".bin");
            ObjectInputStream inputStream = new ObjectInputStream(fileInputStream);
            gameController = (GameController) inputStream.readObject();
            //gamesControllers.remove(id);
            gamesControllers.put(id,gameController);
            gamesControllers.get(id).launchExecutor();
            inputStream.close();
            fileInputStream.close();
        }
        //System.out.println(gamesControllers.get(1).getGame().getPlayers().getFirst().getID() + " " + gamesControllers.get(1).getGame().getPlayers().getFirst().getNickname());
        //System.out.println(gamesControllers.get(1).getGame().getPlayers().getLast().getID() + " " + gamesControllers.get(1).getGame().getPlayers().getLast().getNickname());

        //System.out.println(gamesControllers.get(2).getGame().getPlayers().getFirst().getID() + " " + gamesControllers.get(2).getGame().getPlayers().getFirst().getNickname());
        //System.out.println(gamesControllers.get(2).getGame().getPlayers().getLast().getID() + " " + gamesControllers.get(2).getGame().getPlayers().getLast().getNickname());

        System.out.println("GAME CREATI");
    }


    public void amAlive() {
    }
}
