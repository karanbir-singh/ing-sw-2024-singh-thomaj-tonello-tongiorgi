package it.polimi.ingsw.gc26.model;

import it.polimi.ingsw.gc26.model.Pawn;

public class Player {
    private int ID;
    private String nickname;
    private Pawn pawnColor;
    private int score = 0;
    private Hand hand;
    private PersonalBoard personalBoard;
    private Turn turn;


    public int getID(){
        return this.ID;
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
}
