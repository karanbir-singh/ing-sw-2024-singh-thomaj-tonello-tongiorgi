package it.polimi.ingsw.gc26.model.game;

import it.polimi.ingsw.gc26.model.deck.Deck;
import it.polimi.ingsw.gc26.model.player.Player;
import it.polimi.ingsw.gc26.Parser.ParserCore;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * This class represents an entire Game play. It has a minimum number of player of two and a maximum number of player of four.
 * To play more that one game, more instances of game have to be created
 */
public class Game {
    /**
     * This attribute represents the maximum number of players per game
     */
    public static final int MAX_NUM_PLAYERS = 4;
    /**
     * This attribute represents the actual number of players
     */
    private final int numberOfPlayers;
    /**
     * This attribute represents the current game's state
     */
    private GameState gameState;
    /**
     * This attribute represents the current player
     */
    private Player currentPlayer;
    /**
     * THis attribute represents all the players in the game
     */
    private final ArrayList<Player> players;
    /**
     * This attribute represents the common table to all the players
     */
    private final CommonTable commonTable;
    /**
     * This attribute represents how many rounds have been played
     */
    private int round;

    /**
     * This attribute represents the final round of the game
     */
    private int finalRound;
    /**
     * This attribute represents the winners of the game
     */
    private ArrayList<Player> winners;

    /**
     * Initializes the game, creates the decks and sets the common table
     *
     * @param players list of players of the game
     */
    public Game(ArrayList<Player> players) {
        this.numberOfPlayers = players.size();

        this.players = new ArrayList<>();
        this.players.addAll(players);
        this.winners = null;

        ParserCore p = new ParserCore("src/main/resources/Data/CodexNaturalisCards.json");
        Deck goldCardDeck = p.getGoldCards();
        Deck resourceCardDeck = p.getResourceCards();
        Deck missionDeck = p.getMissionCards();
        Deck starterDeck = p.getStarterCards();

        this.commonTable = new CommonTable(resourceCardDeck, goldCardDeck, starterDeck, missionDeck);
        this.round = 0;
        this.finalRound = -1;
    }

    /**
     * Adds a player in the game
     *
     * @param newPlayer new player to be added in the game
     */
    public void addPlayer(Player newPlayer) throws Exception {
        if (gameState == GameState.WAITING && this.players.size() < numberOfPlayers) {
            this.players.add(newPlayer);
        }
        if (this.players.size() == numberOfPlayers) {
            gameState = GameState.INITIAL_STAGE;
        }
    }

    /**
     * Sets the currentPlayer to the next one in an infinite cycle
     */
    public void goToNextPlayer() {
        // Check if game is on END_STAGE state, if the round it's the final round and if the next player is the first
        if (this.gameState.equals(GameState.END_STAGE) && this.round == this.finalRound &&
                players.indexOf(this.currentPlayer) + 1 == this.numberOfPlayers) {

            // Call end game to update score (valuate missions)
            players.forEach(player -> player.getPersonalBoard().endGame(commonTable.getCommonMissions()));

            // Calculate the max score
            int maxScore = players.stream()
                    .mapToInt(player -> player.getPersonalBoard().getScore())
                    .max()
                    .orElse(-1);

            // Get winners of the game
            winners = players.stream()
                    .filter(player -> player.getPersonalBoard().getScore() == maxScore)
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        // Check if the next player is the first
        if (players.indexOf(this.currentPlayer) + 1 == this.numberOfPlayers) {
            this.currentPlayer = this.players.getFirst();
            this.round++;
        } else {
            this.currentPlayer = this.players.get(this.players.indexOf(this.currentPlayer) + 1);
        }
    }

    /**
     * Sets the current Player to the parameter given
     *
     * @param currentPlayer new current player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Returns the current player
     *
     * @return current player
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Return the common table for every player
     *
     * @return common table
     */
    public CommonTable getCommonTable() {
        return this.commonTable;
    }

    /**
     * Increases number of rounds
     */
    public void increaseRound() {
        this.round += 1;
    }

    /**
     * Return the current round
     *
     * @return round
     */
    public int getRound() {
        return this.round;
    }

    /**
     * Returns the number of player for the game
     *
     * @return number of player
     */
    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }

    /**
     * Return the game state
     *
     * @return game state
     */
    public GameState getState() {
        return this.gameState;
    }

    /**
     * Sets the game state to the parameter given
     *
     * @param newGameState new game state
     */
    public void setGameState(GameState newGameState) {
        this.gameState = newGameState;
    }

    /**
     * Returns an array of players
     *
     * @return players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int getFinalRound() {
        return finalRound;
    }


    public void setRound(int round) {
        this.round = round;
    }

    public void setFinalRound(int finalRound) {
        this.finalRound = finalRound;
    }
}
