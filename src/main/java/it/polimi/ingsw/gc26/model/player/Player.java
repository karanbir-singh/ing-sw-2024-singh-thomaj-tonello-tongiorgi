package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.model.card_side.Side;
import it.polimi.ingsw.gc26.model.hand.Hand;

import java.util.ArrayList;

/**
 * This class represents the player in the game
 */
public class Player {
    /**
     * This attributes represents the player with an unique id
     */
    private final int ID;
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
     * This attribute represents the player's hand
     */
    private Hand hand;
    /**
     * This attribute represents then player's personal board
     */
    private PersonalBoard personalBoard;
    /**
     * This attribute represents how many turns the player has played
     */
    private int turn;

    /**
     * Initializes the player with an id and a name
     * @param id unique number that represent the player
     * @param name player's nickname
     */
    public Player(int id, String name) {
        this.ID = id;
        this.nickname = name;
        this.pawnColor = null;
        this.hand = null;
        this.personalBoard = null;
        this.turn = 0;
    }

    /**
     * Returns the player's id
     * @return player's id
     */
    public int getID() {
        return this.ID;
    }

    /**
     * Sets the player nickname
     * @param name new nickname
     */
    public void setNickname(String name) {
        this.nickname = name;
    }

    /**
     * Returns the player's nickname
     * @return nickname
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Sets the pawn color to the chosen one
     * @param chosenColor new pawn color
     */
    public void setPawn(Pawn chosenColor) {
        this.pawnColor = chosenColor;
    }

    /**
     * Return the pawn color
     * @return pawn color
     */
    public Pawn getPawnColor() {
        return this.pawnColor;
    }

    /**
     * Sets boolean that indicates the player is the first one to true
     */
    public void setFirstPlayer() {
        this.amIFirstPlayer = true;
    }

    /**
     * Returns a boolean that equals true if the player is the first one to play in the round
     * @return amIFirstPlayer
     */
    public boolean isFirstPlayer() {
        return this.amIFirstPlayer;
    }

    /**
     * Creates an empty hand
     */
    public void createHand() {
        this.hand = new Hand(new ArrayList<>());
    }

    /**
     * Return the player's hand
     * @return hand
     */
    public Hand getHand() {
        return this.hand;
    }

    /**
     * Creates the player's personal board
     * @param initialSide initial card side
     */
    public void createPersonalBoard(Side initialSide) {
        this.personalBoard = new PersonalBoard(initialSide);
    }

    /**
     * Returns the current player's turn
     * @return turn
     */
    public int getTurn() {
        return turn;
    }

    /**
     * Increases the player's number of turn played
     */
    public void increaseNumber() {
        turn++;
    }

    /**
     * Returns the player's personal board
     * @return personal board
     */
    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }
}
