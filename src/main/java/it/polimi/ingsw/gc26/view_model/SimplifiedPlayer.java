package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.model.player.Pawn;
import it.polimi.ingsw.gc26.model.player.PlayerState;

import java.io.Serializable;

public class SimplifiedPlayer implements Serializable {
    /**
     * This attributes represents the player with a unique id
     */
    private String ID;
    /**
     * This attribute represents the player's name and will be shown to the other players
     */
    private String nickname;
    /**
     * This attribute represents the pawn, which represent the player in the board
     */
    private Pawn pawnColor;
    /**
     * This attribute represent whether the player is the first one
     */
    private boolean amIFirstPlayer;
    /**
     * This attribute represents the player's state
     */
    private PlayerState state;

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
