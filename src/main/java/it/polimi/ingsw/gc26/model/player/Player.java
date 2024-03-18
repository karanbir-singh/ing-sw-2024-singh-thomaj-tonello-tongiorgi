package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.model.hand.Hand;

public class Player {
    private int ID;
    private String nickname;
    private Pawn pawnColor;
    private Hand hand;
    private PersonalBoard personalBoard;
    private int turn;

    /* ID is set automatically when a player connects to the game
        the nickname is asked in order to connect */
    /* the pawn, the hand and the personalBoard are set during the initial stage of the game */

    public Player(int id, String name) {
        this.ID = id;
        this.nickname = name;
        this.pawnColor = null;
        this.hand = null;
        this.personalBoard = null;
        this.turn = 0;
    }

    public int getID() {
        return this.ID;
    }

    public void setNickname(String name) {
        this.nickname = name;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setPawn(Pawn chosenColor) {
        this.pawnColor = chosenColor;
    }

    public Pawn getPawnColor() {
        return this.pawnColor;
    }

    public void setHand() {
        this.hand = new Hand();
    }

    public Hand getHand() {
        return new Hand(this.hand.getCards());
    }

    public void setPersonalBoard() {
        this.personalBoard = new PersonalBoard(); // da cambiare parametri nel costruttore di Personal Board
    }

    public int getScore() {
        return this.personalBoard.getScore();
    }

    public int getTurn() {
        return turn;
    }

    public void increaseNumber() {
        turn++;
    }
}
