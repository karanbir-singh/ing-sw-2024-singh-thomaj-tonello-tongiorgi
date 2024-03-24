package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.model.card_side.Side;
import it.polimi.ingsw.gc26.model.hand.Hand;

import java.util.ArrayList;

public class Player {
    private final int ID;
    private String nickname;
    private Pawn pawnColor;
    private boolean amIFirstPlayer;
    private Hand hand;
    private PersonalBoard personalBoard;
    private int turn;

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

    public void setFirstPlayer() {
        this.amIFirstPlayer = true;
    }

    public boolean isFirstPlayer() {
        return this.amIFirstPlayer;
    }

    public void createHand() {
        this.hand = new Hand(new ArrayList<>());
    }

    public Hand getHand() {
        return this.hand;
    }

    public void createPersonalBoard(Side initialSide) {
        this.personalBoard = new PersonalBoard(initialSide);
    }

    public int getTurn() {
        return turn;
    }

    public void increaseNumber() {
        turn++;
    }

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }
}
