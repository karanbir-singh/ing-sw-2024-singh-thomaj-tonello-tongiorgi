package it.polimi.ingsw.gc26.model.game;

import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.model.player.Player;
import it.polimi.ingsw.gc26.Parser.ParserCore;

import java.util.ArrayList;

public class Game {
    private static final int MAX_NUM_PLAYERS = 4;
    private int numberOfPlayers;
    private ArrayList<PersonalBoard> personalBoards;

    private GameState gameState;
    private Player currentPlayer;
    private ArrayList<Player> Players;
    private CommonTable commonTable;
    private Round round;


    public Game(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
        this.gameState = GameState.INITIAL_STAGE;
        this.Players = new ArrayList<>(this.numberOfPlayers);
        this.personalBoards = new ArrayList<>(this.numberOfPlayers);
        //this.commonTable = new CommonTable(ParserCore); // TODO

    }

    public GameState getState() {
        return this.gameState;

    }

    public void setGameState(GameState newGameState) {
        this.gameState = newGameState;
    }

    public void goToNextPlayer() {
        if (Players.indexOf(this.currentPlayer) +1 == this.numberOfPlayers) {
            this.currentPlayer = this.Players.getFirst();
        } else {
            this.currentPlayer = this.Players.get(this.Players.indexOf(this.currentPlayer) + 1);

        }
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Player getCurrentPlayer() {
        return this.currentPlayer;
    }

    public void addPlayer(Player newPlayer) {
        this.Players.add(newPlayer);
    }

    public CommonTable getCommonTable() { return this.commonTable;}

}
