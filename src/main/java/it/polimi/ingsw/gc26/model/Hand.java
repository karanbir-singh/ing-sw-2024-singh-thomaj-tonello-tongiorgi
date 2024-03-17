package it.polimi.ingsw.gc26.model;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;
    private HandState state;
    private Side playSide;


    public void setState(HandState state){
        this.state = state;
    }
}
