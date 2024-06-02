package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.hand.Hand;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.model.player.Player;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SimplifiedGame implements Serializable {
    /**
     * This attribute represents the current game's state
     */
    private GameState gameState;
    /**
     * This attribute represents the current player's nickname
     */
    private String currentPlayer;

    /**
     * This attribute represents the players' score
     */
    private final HashMap<String, Integer> scores;

    private final HashMap<String, Pawn> pawnsSelected;
    /**
     * This attribute represents the winners of the game
     */
    private final ArrayList<String> winners;
    /**
     * This attribute represents the available pawns in the game
     */
    private final ArrayList<Pawn> availablePawns;

    public SimplifiedGame(GameState gameState, String currentPlayer, HashMap<String, Integer> scores, ArrayList<String> winners, ArrayList<Pawn> availablePawns, HashMap<String, Pawn> pawnsSelected){
        this.gameState = gameState;
        this.currentPlayer = currentPlayer;
        this.scores = scores;
        this.winners = winners;
        this.availablePawns = availablePawns;
        this.pawnsSelected = pawnsSelected;
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

    public ArrayList<String> getPlayersNicknames() {
        ArrayList<String> playersNicknames = new ArrayList<>();
        for (Map.Entry<String, Integer> player : scores.entrySet()) {
            playersNicknames.add(player.getKey());
        }
        return playersNicknames;
    }

    public HashMap<String, Pawn> getPawnsSelected() {
        return pawnsSelected;
    }
}
