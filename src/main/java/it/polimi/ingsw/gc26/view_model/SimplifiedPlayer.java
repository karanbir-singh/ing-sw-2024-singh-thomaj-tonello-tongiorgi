package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.Printer;
import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.model.player.PlayerState;
import it.polimi.ingsw.gc26.model.utils.TextStyle;

import java.io.Serializable;

public class SimplifiedPlayer implements Serializable {
    /**
     * This attributes represents the player with a unique id
     */
    private final String ID;
    /**
     * This attribute represents the player's name and will be shown to the other players
     */
    private final String nickname;
    /**
     * This attribute represents the pawn, which represent the player in the board
     */
    private final Pawn pawnColor;
    /**
     * This attribute represent whether the player is the first one
     */
    private final boolean amIFirstPlayer;
    /**
     * This attribute represents the player's state
     */
    private final PlayerState state;

    public SimplifiedPlayer(String ID, String nickname, Pawn pawnColor, boolean amIFirstPlayer, PlayerState state) {
        this.ID = ID;
        this.nickname = nickname;
        this.pawnColor = pawnColor;
        this.amIFirstPlayer = amIFirstPlayer;
        this.state = state;
    }

    public String getID() {
        return ID;
    }

    public String getNickname() {
        return nickname;
    }

    public Pawn getPawnColor() {
        return pawnColor;
    }

    public boolean isAmIFirstPlayer() {
        return amIFirstPlayer;
    }

    public PlayerState getState() {
        return state;
    }
}
