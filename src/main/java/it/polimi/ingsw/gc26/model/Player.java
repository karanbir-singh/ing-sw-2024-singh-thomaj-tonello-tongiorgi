package it.polimi.ingsw.gc26.model;

public class Player {
    private int ID;
    private String nickname;
    private Pawn pawnColor;
    private Hand hand;
    private PersonalBoard personalBoard;
    private Turn turn;

    /* ID is set automatically when a player connects to the game
        the nickname is asked in order to connect */
    /* the pawn, the hand and the personalBoard are set during the initial stage of the game */

    public Player(int id, String name) {
        this.ID = id;
        this.nickname = name;
        this.pawnColor = null;
        this.hand = null;
        this.personalBoard = null;
        this.turn = new Turn();
    }

    public int getID(){
        return this.ID;
    }

    public void setNickname(String name){
        this.nickname = name;
    }
    public String getNickname(){
        return this.nickname;
    }

    public void setPawn(Pawn chosenColor){
        this.pawnColor = chosenColor;
    }
    public Pawn getPawnColor(){
        return this.pawnColor;
    }
    public void setHand(){
        this.hand = new Hand();
    }
    public Hand getHand(){
        return this.hand;
    }
    public void setPersonalBoard(){
        this.personalBoard = new PersonalBoard();
    }

    public PersonalBoard getPersonalBoard() {
        return this.personalBoard;
    }

    public int getScore() {
        return this.personalBoard.getScore;
    }
}
