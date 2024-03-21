package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Side;
import it.polimi.ingsw.gc26.model.hand.Hand;

import java.util.ArrayList;

public class Player {
    private int ID;
    private String nickname;
    private Pawn pawnColor;
    private boolean amIFirstPlayer;
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
    public void setFirstPlayer(){this.amIFirstPlayer = true;}
    public boolean isFirstPlayer(){return this.amIFirstPlayer;}

    public void setHand() {
        this.hand = new Hand(new ArrayList<>());
    }

    public Hand getHand() {
        return new Hand(this.hand.getCards());
    }

    public void setPersonalBoard(Side initialSide, Card secretMission, Card firstCommonMission, Card secondCommonMission) {
        this.personalBoard = new PersonalBoard(initialSide, secretMission, firstCommonMission, secondCommonMission); // TODO da cambiare parametri nel costruttore di Personal Board
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

    public PersonalBoard getPersonalBoard() {
        return personalBoard;
    }
}
