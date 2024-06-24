package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.player.Pawn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a simplified version of a game.
 * It provides methods to access the attributes.
 */
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

    /**
     * Pawn already selected by players
     */
    private final HashMap<String, Pawn> pawnsSelected;
    /**
     * This attribute represents the winners of the game
     */
    private final ArrayList<String> winners;
    /**
     * This attribute represents the available pawns in the game
     */
    private final ArrayList<Pawn> availablePawns;

    /**
     * Constructs a SimplifiedGame object with the specified parameters.
     *
     * @param gameState      the game state of the simplified game.
     * @param currentPlayer  the nickname of the current player.
     * @param scores         scores mapping from player nickname to their score.
     * @param winners        list of winners' nicknames.
     * @param availablePawns list of available pawns in the game.
     * @param pawnsSelected  mapping from player nickname to their selected pawn.
     */
    public SimplifiedGame(GameState gameState, String currentPlayer, HashMap<String, Integer> scores,
                          ArrayList<String> winners, ArrayList<Pawn> availablePawns,
                          HashMap<String, Pawn> pawnsSelected) {
        this.gameState = gameState;
        this.currentPlayer = currentPlayer;
        this.scores = scores;
        this.winners = winners;
        this.availablePawns = availablePawns;
        this.pawnsSelected = pawnsSelected;
    }

    /**
     * Returns the game state of the simplified game.
     *
     * @return the game state.
     */
    public GameState getGameState() {
        return gameState;
    }

    /**
     * Returns the nickname of the current player.
     *
     * @return the current player's nickname.
     */
    public String getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Returns the scores mapping from player nickname to their score.
     *
     * @return the scores mapping.
     */
    public HashMap<String, Integer> getScores() {
        return scores;
    }

    /**
     * Returns the list of winners' nicknames.
     *
     * @return the list of winners.
     */
    public ArrayList<String> getWinners() {
        return winners;
    }

    /**
     * Returns the list of available pawns in the game.
     *
     * @return the list of available pawns.
     */
    public ArrayList<Pawn> getAvailablePawns() {
        return availablePawns;
    }

    /**
     * Returns the list of player nicknames in the game.
     *
     * @return the list of player nicknames.
     */
    public ArrayList<String> getPlayersNicknames() {
        ArrayList<String> playersNicknames = new ArrayList<>();
        for (Map.Entry<String, Integer> player : scores.entrySet()) {
            playersNicknames.add(player.getKey());
        }
        return playersNicknames;
    }

    /**
     * Returns the mapping from player nickname to their selected pawn.
     *
     * @return the mapping of selected pawns.
     */
    public HashMap<String, Pawn> getPawnsSelected() {
        return pawnsSelected;
    }
}
