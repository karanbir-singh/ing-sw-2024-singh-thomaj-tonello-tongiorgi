package it.polimi.ingsw.gc26.model.game;

import it.polimi.ingsw.gc26.model.deck.Deck;
import it.polimi.ingsw.gc26.model.player.Player;
import it.polimi.ingsw.gc26.Parser.ParserCore;

import java.util.ArrayList;

/**
 * This class represents an entire Game play. It has a minimum number of player of two and a maximum number of player of four.
 * To play more that one game, more instances of game have to be created
 */
public class Game {
    /**
    This attribute represents the maximum number of players per game
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
     * This attribute represents the chat. It stores all the messages.
     */
    private final Chat chat;


    /**
     * Initializes the game, creates the decks and sets the common table
     * @param numberOfPlayers number of players in this game, min: 2 - max: 4
     * @throws Exception number of players invalid
     */
    public Game(int numberOfPlayers) throws Exception {
        if (numberOfPlayers < 2 || numberOfPlayers > 4) { throw new Exception("Number of players invalid!");}
        this.numberOfPlayers = numberOfPlayers;
        this.players = new ArrayList<>();
        this.gameState = GameState.INITIAL_STAGE;

        ParserCore p = new ParserCore("src/main/resources/Data/CodexNaturalisCards.json");
        Deck goldCardDeck = p.getGoldCards();
        Deck resourceCardDeck = p.getResourceCards();
        Deck missionDeck = p.getMissionCards();
        Deck starterDeck = p.getStarterCards();

        this.commonTable = new CommonTable(resourceCardDeck, goldCardDeck, starterDeck, missionDeck);
        this.round = 0;
        this.chat = new Chat();
    }

    /**
     * Adds a player in the game
     * @param newPlayer new player to be added in the game
     * @throws Exception number of players already maximum
     */
    public void addPlayer(Player newPlayer) throws Exception {
        if (this.players.size() < numberOfPlayers) {
            this.players.add(newPlayer);
        } else {
            throw new Exception();
        }
    }

    /**
     * Sets the currentPlayer to the next one in an infinite cycle
     */
    public void goToNextPlayer() {
        if (players.indexOf(this.currentPlayer) + 1 == this.numberOfPlayers) {
            this.currentPlayer = this.players.getFirst();
        } else {
            this.currentPlayer = this.players.get(this.players.indexOf(this.currentPlayer) + 1);
        }
    }

    /**
     * Sets the current Player to the parameter given
     * @param currentPlayer new current player
     */
    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    /**
     * Returns the current player
     * @return current player
     */
    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    /**
     * Return the common table for every player
     * @return common table
     */
    public CommonTable getCommonTable() {
        return this.commonTable;
    }

    /**
     * Increases number of rounds
     */
    private void increaseRound() {
        this.round += 1;
    }

    /**
     * Return the current round
     * @return round
     */
    private int getRound() {
        return this.round;
    }

    /**
     * Returns the number of player for the game
     * @return number of player
     */
    public int getNumberOfPlayers() {
        return this.numberOfPlayers;
    }

    /**
     * Return the game state
     * @return game state
     */
    public GameState getState() {
        return this.gameState;
    }

    /**
     * Sets the game state to the parameter given
     * @param newGameState new game state
     */
    public void setGameState(GameState newGameState) {
        this.gameState = newGameState;
    }

    /**
     * Returns an array of players
     * @return players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the group's chat
     * @return chat
     */
    public Chat getChat() {
        return this.chat;
    }
}
