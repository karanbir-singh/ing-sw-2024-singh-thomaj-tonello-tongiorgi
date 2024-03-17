package it.polimi.ingsw.gc26.model;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;
    private HandState state;
    private Card selectedCard;
    private Side selectedSide;

    public void setState(HandState state){
        this.state = state;
    }

    public Card getSelectedCard() {
        return this.selectedCard;
    }
    /* setSelectedCard might be called by the controller */
    public void setSelectedCard(Card selected){
        this.selectedCard = selected;
        if(selected == null){
            this.selectedSide = null;
            return;
        }
        this.selectedSide = /* selected.getFront() */;
    }
    public Side getSelectedSide(){
        return this.selectedSide;
    }

    public void turnSide(Side selectedSide){
        /* mi servono i metodi di card */
    }

    public void removeCard(Card card){
        cards.remove(card);
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public Side playCard(Side selectedSide, Card selectedCard){
        return state.playCard(selectedSide, selectedCard);
    }

}
