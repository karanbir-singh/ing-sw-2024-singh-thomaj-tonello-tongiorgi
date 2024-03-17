package it.polimi.ingsw.gc26.model;

public class HandPlayable extends HandState{

    public void transition(){
        hand.setState(new HandNotPlayable);
    }

    public Side playCard(Side side, Card card){
        Side playSide;
        playSide = side;
        hand.removeCard(card);
        hand.setSelectedCard = null;
        this.transition();
        return playSide;
    }
}
