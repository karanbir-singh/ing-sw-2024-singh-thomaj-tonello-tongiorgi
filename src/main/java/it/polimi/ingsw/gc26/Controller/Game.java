package it.polimi.ingsw.gc26.Controller;

import java.time.Period;
import java.util.ArrayList;
import java.util.Arrays;

public class Game {
    private static final int MAX_NUM_PLAYERS = 4;
    private int numberOfPlayers;
    private ArrayList<PersonalBoard> personalBoards;

    private GameState gameState;
    private Player currentPlayer ;
    private ArrayList<Plater> Players;
    private CommonTable commonTable;
    private Round round;


    public Game(int numberOfPlayers){
        this.numberOfPlayers = numberOfPlayers;
        this.gameState = GameState.INITIAL_STAGE;
        this.Players = new ArrayList<Players>(this.numberOfPlayers);
        this.personalBoards = new ArrayList< PersonalBoard> (this.numberOfPlayers);
        this.commonTable = new CommonTable(Parser.getDecks()); // TODO

    }

    public GameState getState() {
        return this.gameState;

    }

    public void setGameState(GameState newGameState) {
        this.gameState = newGameState;
    }

    public void goToNextPlayer() {
        this.currentPlayer = Players.indexOf(this.currentPlayer) + 1 // TODO da sistemare, propongo cycled linked list
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


}
