package it.polimi.ingsw.gc26.controller;

import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.player.Player;
import it.polimi.ingsw.gc26.network.VirtualView;

import java.rmi.RemoteException;
import java.util.*;

public class MainController {
    /**
     * This attribute represents the list of clients who are waiting for a new game
     */
    private ArrayList<VirtualView> waitingClients;

    /**
     * This attribute represents the list of players who are waiting for a new game
     */
    private ArrayList<Player> waitingPlayers;

    /**
     * This attribute represents the list of game controllers of started games
     */
    private final ArrayList<GameController> gamesControllers;

    /**
     * This attribute represents the number of player that needs to wait until a game is created
     */
    private int maxNumWaitingClients;

    /**
     * Initializes waiting players' list and games controllers' list
     */
    public MainController() {
        this.waitingClients = new ArrayList<>();
        this.waitingPlayers = new ArrayList<>();
        this.gamesControllers = new ArrayList<>();
        maxNumWaitingClients = 0;
    }

    /**
     * Checks if the given nickname is alreadyUsed
     *
     * @param nickname
     * @return Returns true if waitingPlayer not contains other players with the given nickname, otherwise false
     */
    private boolean isNicknameValid(String nickname) {
        return !waitingPlayers.stream().anyMatch(p -> p.getNickname().equals(nickname));
    }

    public String connect(VirtualView client, String nickname) {
        // For now, ID it's random string
        String clientID = UUID.randomUUID().toString();

        // Check if there is a waiting game
        if (this.existsWaitingGame()) {
            // Check if there is another player with same nickname
            if (this.isNicknameValid(nickname)) {
                // Then join in waiting list
                this.joinWaitingList(client, clientID, nickname);
            } else {
                try {
                    client.updateState(ClientState.INVALID_NICKNAME);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        } else {
            try {
                client.updateState(ClientState.CREATOR);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return clientID;
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
     * @param clientID   ID of the player who is initializing the waiting list
     * @param nickname   nickname of the player who is initializing the waiting list
     */
    public synchronized void createWaitingList(VirtualView client, String clientID, String nickname, int numPlayers) {
        // Check if given number of players is correct
        if (numPlayers > 1 && numPlayers <= Game.MAX_NUM_PLAYERS) {
            // Add client in the waiting list
            this.waitingClients.add(client);
            this.waitingPlayers.add(new Player(clientID, nickname));

            // Update the max numbers of players for the game
            this.maxNumWaitingClients = numPlayers;

            try {
                client.updateState(ClientState.WAITING);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            try {
                client.updateState(ClientState.INVALID_NUMBER_OF_PLAYER);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Adds a player into the waiting list, if exists
     *
     * @param playerID       ID of the player who is joining the waiting list
     * @param playerNickname Nickname of the player who is joining the waiting list
     */
    private GameController joinWaitingList(VirtualView client, String playerID, String playerNickname) {
        GameController gameController = null;

        // Add client in waiting list
        this.waitingClients.add(client);
        this.waitingPlayers.add(new Player(playerID, playerNickname));

        // Check number of clients in waiting list
        if (waitingClients.size() >= maxNumWaitingClients) {
            // Then, create a new game controller
            gameController = new GameController(new Game(waitingPlayers));
            gamesControllers.add(gameController);

            // Update of the view
            for (VirtualView view : waitingClients) {
                try {
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

        return gameController;
    }

    /**
     * @return Returns the last created game controller
     */
    public GameController getGameController() {
        if (!gamesControllers.isEmpty())
            return gamesControllers.getLast();
        return null;
    }
}
