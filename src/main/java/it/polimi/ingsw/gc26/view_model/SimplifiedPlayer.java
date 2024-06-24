package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.model.player.PlayerState;

import java.io.Serializable;

/**
 * Represents a simplified version of a player in the game.
 */
public class SimplifiedPlayer implements Serializable {

    /**
     * Unique identifier of the player.
     */
    private final String ID;

    /**
     * Name of the player that is shown to other players.
     */
    private final String nickname;

    /**
     * Pawn color representing the player on the game board.
     */
    private final Pawn pawnColor;

    /**
     * Indicates whether the player is the first player in the game.
     */
    private final boolean amIFirstPlayer;

    /**
     * State of the player in the game.
     */
    private final PlayerState state;

    /**
     * Constructs a simplified player with the specified attributes.
     *
     * @param ID             Unique identifier of the player.
     * @param nickname       Name of the player shown to others.
     * @param pawnColor      Pawn color representing the player on the board.
     * @param amIFirstPlayer Indicates if the player is the first player.
     * @param state          State of the player.
     */
    public SimplifiedPlayer(String ID, String nickname, Pawn pawnColor, boolean amIFirstPlayer, PlayerState state) {
        this.ID = ID;
        this.nickname = nickname;
        this.pawnColor = pawnColor;
        this.amIFirstPlayer = amIFirstPlayer;
        this.state = state;
    }

    /**
     * Returns the unique identifier of the player.
     *
     * @return Player's ID.
     */
    public String getID() {
        return ID;
    }

    /**
     * Returns the name of the player shown to others.
     *
     * @return Player's nickname.
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * Returns the pawn color representing the player on the board.
     *
     * @return Pawn color.
     */
    public Pawn getPawnColor() {
        return pawnColor;
    }

    /**
     * Checks if the player is the first player in the game.
     *
     * @return True if the player is the first player, false otherwise.
     */
    public boolean isAmIFirstPlayer() {
        return amIFirstPlayer;
    }

    /**
     * Returns the current state of the player in the game.
     *
     * @return Player's state.
     */
    public PlayerState getState() {
        return state;
    }
}
