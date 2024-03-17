package it.polimi.ingsw.gc26.model.hand;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Side;

public class HandPlayable extends HandState{

    public void transition(){
        hand.setState(new HandNotPlayable());
    }

    public Side playCard(Side side, Card card){
        Side playSide;
        playSide = side;
        hand.removeCard(card);
        hand.setSelectedCard(java.util.Optional.empty());
        this.transition();
        return playSide;
    }
}
