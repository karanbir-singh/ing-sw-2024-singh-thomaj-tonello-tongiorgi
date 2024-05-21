package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.hand.Hand;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.model.player.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class SimplifiedGame {
    /**
     * This attribute represents the current game's state
     */
    private GameState gameState;
    /**
     * This attribute represents the current player's nickname
     */
    private String currentPlayer; //TODO capire se basta il nickname o se serve il player

    /**
     * This attribute represents the players' score
     */
    private final HashMap<String, Integer> scores;
    /**
     * This attribute represents the winners of the game
     */
    private ArrayList<String> winners;
    /**
     * This attribute represents the available pawns in the game
     */
    private final ArrayList<Pawn> availablePawns;

    public SimplifiedGame(GameState gameState, String currentPlayer, HashMap<String, Integer> scores, ArrayList<String> winners, ArrayList<Pawn> availablePawns){
        this.gameState = gameState;
        this.currentPlayer = currentPlayer;
        this.scores = scores;
        this.winners = winners;
        this.availablePawns = availablePawns;
    }

    public GameState getGameState() {
        return gameState;
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }

    public HashMap<String, Integer> getScores() {
        return scores;
    }

    public ArrayList<String> getWinners() {
        return winners;
    }

    public ArrayList<Pawn> getAvailablePawns() {
        return availablePawns;
    }
}
