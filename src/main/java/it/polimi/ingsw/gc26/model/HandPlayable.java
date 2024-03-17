package it.polimi.ingsw.gc26.model;

public class HandPlayable extends HandState{

    public Card getSelectedCard() {
        return this.selectedCard;
    }
    /* setSelectedCard might be called by the controller */
    public void setSelectedCard(Card selected){
        this.selectedCard = selected;
    }

    public Side getSelectedSide(){
        return this.selectedSide;
    }
    public void turnSide(Side selectedSide){
    /* mi servono i metodi di card */
    }
    public void removeCard(Card toBeRemoved){
        cards.remove(Card toBeRemoved);
    }

    public void addCard(Card drawnCard){
        cards.add(drawnCard);
    }

    public void transition(){
        hand.setState(new HandNotPlayable);
    }

    public Side playCard(){
        playSide = this.selectedSide;
        removeCard(selectedCard);
        selectedCard = null;
        selectedSide = null;
        this.transition();
        return playSide;
    }
}
