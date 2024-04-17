package it.polimi.ingsw.gc26.controller;

import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.player.Player;

import java.rmi.RemoteException;
import java.util.*;

public class MainController {
    /**
     * This attribute represents the list of players who are waiting for a new game
     */
    private ArrayList<Player> waitingPlayer;

    /**
     * This attribute represents the list of game controllers of started games
     */
    private final ArrayList<GameController> gamesControllers;

    /**
     * This attribute represents the number of player that needs to wait until a game is created
     */
    private int maxNumWaitingPlayers;

    /**
     * Initializes waiting players' list and games controllers' list
     */
    public MainController() {
        this.waitingPlayer = new ArrayList<>();
        this.gamesControllers = new ArrayList<>();
        maxNumWaitingPlayers = 0;
    }

    /**
     * Check if there are players waiting
     *
     * @return true if there are players waiting, false otherwise
     */
    public boolean existsWaitingGame() {
        return !waitingPlayer.isEmpty();
    }

    /**
     * Initializes the waiting list of players and updating max numbers of players for the next game
     *
     * @param numPlayers     number of players of the next game
     * @param playerID       ID of the player who is initializing the waiting list
     * @param playerNickname nickname of the player who is initializing the waiting list
     */
    public void createWaitingList(int numPlayers, String playerID, String playerNickname) {
        // Check if given number of players is correct
        if (numPlayers > 1 && numPlayers <= Game.MAX_NUM_PLAYERS) {
            // Re-initialize waiting player with the new dimension of the list
            this.waitingPlayer = new ArrayList<>(numPlayers);

            // Add in the list the player
            this.waitingPlayer.add(new Player(playerID, playerNickname));

            // Update the max numbers of players for the game
            this.maxNumWaitingPlayers = numPlayers;
        } else {
            // TODO gestire cosa fare in cui il numero di giocatori è negativo o maggiori di Game.MAX_NUM_PLAYERS
        }
    }

    /**
     * Adds a player into the waiting list, if exists
     *
     * @param playerID       ID of the player who is joining the waiting list
     * @param playerNickname Nickname of the player who is joining the waiting list
     */
    public GameController joinWaitingList(String playerID, String playerNickname) {
        Player newPlayer = new Player(playerID, playerNickname);
        GameController gameController = null;

        // Add player to the waiting list
        this.waitingPlayer.add(newPlayer);

        // Check if waiting list is full
        if (waitingPlayer.size() >= maxNumWaitingPlayers) {
            // Then, create a new game controller and add to the list
            try {
                gameController = new GameController(new Game(waitingPlayer));
                gamesControllers.add(gameController);
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            // Clear waiting list
            this.waitingPlayer.clear();
            this.waitingPlayer = null;

        }
        return gameController;
    }

    public ArrayList<Player> getWaitingPlayer() {
        return waitingPlayer;
    }
}
