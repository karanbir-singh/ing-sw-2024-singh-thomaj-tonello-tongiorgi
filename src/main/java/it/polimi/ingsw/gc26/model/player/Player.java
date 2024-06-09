package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.model.hand.Hand;
import it.polimi.ingsw.gc26.model.utils.TextStyle;
import it.polimi.ingsw.gc26.view_model.SimplifiedGame;
import it.polimi.ingsw.gc26.network.ModelObservable;
import it.polimi.ingsw.gc26.view_model.SimplifiedPlayer;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * This class represents the player in the game
 */
public class Player implements Serializable {
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
    private Pawn pawnColor;
    /**
     * This attribute represent whether the player is the first one
     */
    private boolean amIFirstPlayer;
    /**
     * This attribute represents the player's hand
     */
    private Hand hand;
    /**
     * This attribute represents the player's secret mission hand (used only in the initial game state)
     */
    private Hand secretMissionHand;
    /**
     * This attribute represents the player's personal board
     */
    private PersonalBoard personalBoard;

    /**
     * This attribute represents the player's state
     */
    private PlayerState state;

    /**
     * Observable to notify client
     */
    private ModelObservable observable;

    /**
     * Initializes the player with an id and a name
     *
     * @param id   unique number that represent the player
     * @param name player's nickname
     */
    public Player(String id, String name) {
        this.ID = id;
        this.nickname = name;
        this.pawnColor = null;
        this.hand = null;
        this.personalBoard = null;
        this.state = PlayerState.PLAYING;
    }

    /**
     * Returns the player's id
     *
     * @return player's id
     */
    public String getID() {
        return this.ID;
    }

    /**
     * This method set the observable
     *
     * @param observable contains a list of the observers
     */
    public void setObservable(ModelObservable observable) {
        this.observable = observable;
    }

    /**
     * Returns the player's nickname
     *
     * @return nickname
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Sets the pawn color to the chosen one
     *
     * @param pawn player's pawn
     */
    public void setPawn(Pawn pawn, String clientID) {
        this.pawnColor = pawn;

        // Notify client
        this.observable.notifyUpdatePlayer(
                new SimplifiedPlayer(
                        ID,
                        nickname,
                        pawnColor,
                        amIFirstPlayer,
                        state
                ),
                "Color " + pawn.toString() + " set",
                clientID);
    }

    /**
     * Return the pawn color
     *
     * @return pawn color
     */
    public Pawn getPawnColor() {
        return this.pawnColor;
    }

    /**
     * Sets boolean that indicates the player is the first one to true
     */
    public void setFirstPlayer(String clientID) {
        this.amIFirstPlayer = true;

        this.observable.notifyUpdatePlayer(
                new SimplifiedPlayer(clientID, nickname, pawnColor, true, state),
                "You are the first player!",
                clientID);
    }

    /**
     * Returns a boolean that equals true if the player is the first one to play in the round
     *
     * @return amIFirstPlayer
     */
    public boolean isFirstPlayer() {
        return this.amIFirstPlayer;
    }

    /**
     * Creates an empty hand
     */
    public void createHand() {
        this.hand = new Hand(new ArrayList<>(), this.observable);
    }

    /**
     * Creates an empty secret mission hand
     */
    public void createSecretMissionHand() {
        this.secretMissionHand = new Hand(new ArrayList<>(), this.observable);
    }

    /**
     * Returns the player's secret mission hand
     *
     * @return secretMissionHand
     */
    public Hand getSecretMissionHand() {
        return secretMissionHand;
    }

    /**
     * Returns the player's hand
     *
     * @return hand
     */
    public Hand getHand() {
        return this.hand;
    }

    /**
     * Creates the player's personal board
     */
    public void createPersonalBoard() {
        this.personalBoard = new PersonalBoard(this.observable, this.nickname);
    }

    /**
     * Returns the player's personal board
     *
     * @return personal board
     */
    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    /**
     * Returns player's state
     *
     * @return state
     */
    public PlayerState getState() {
        return state;
    }

    /**
     * Sets player's state
     *
     * @param state new state
     */
    public void setState(PlayerState state, String clientID) {
        this.state = state;

        this.observable.notifyUpdatePlayer(
                new SimplifiedPlayer(clientID, nickname, pawnColor, amIFirstPlayer, state),
                "Your new state is " + this.state.toString(), clientID);
    }
}

