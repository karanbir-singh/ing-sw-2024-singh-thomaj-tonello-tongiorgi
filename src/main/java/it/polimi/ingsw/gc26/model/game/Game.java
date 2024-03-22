package it.polimi.ingsw.gc26.model.game;

import it.polimi.ingsw.gc26.model.deck.Deck;
import it.polimi.ingsw.gc26.model.player.Player;
import it.polimi.ingsw.gc26.Parser.ParserCore;

import java.util.ArrayList;

public class Game {
    public static final int MAX_NUM_PLAYERS = 4;
    private final int numberOfPlayers;

    private GameState gameState;
    private Player currentPlayer;
    private ArrayList<Player> players;
    private CommonTable commonTable;
    private int round;


    public Game(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        this.gameState = GameState.INITIAL_STAGE;
        this.players = new ArrayList<>();
        ParserCore p = new ParserCore("src/main/resources/Data/CodexNaturalisCards.json");
        Deck goldCardDeck = p.getGoldCards();
        Deck resourceCardDeck = p.getResourceCards();
        Deck missionDeck = p.getMissionCards();
        Deck starterDeck = p.getStarterCards();
        this.commonTable = new CommonTable(resourceCardDeck, goldCardDeck, starterDeck, missionDeck);
        this.round = 1;
    }

    public void addPlayer(Player newPlayer) throws Exception {
        if (this.players.size() < numberOfPlayers) {
            this.players.add(newPlayer);
        } else {
            throw new Exception();
        }
    }

    public void goToNextPlayer() {
        if (players.indexOf(this.currentPlayer) +1 == this.numberOfPlayers) {
            this.currentPlayer = this.players.getFirst();
        } else {
            this.currentPlayer = this.players.get(this.players.indexOf(this.currentPlayer) + 1);
        }
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public CommonTable getCommonTable() { return this.commonTable;}

    private void increaseRound() {
        this.round += 1;
    }

    private int getRound() {
        return this.round;
    }

    public int getNumberOfPlayers(){
        return this.numberOfPlayers;
    }
    public GameState getState() {
        return this.gameState;
    }

    public void setGameState(GameState newGameState) {
        this.gameState = newGameState;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }
}
