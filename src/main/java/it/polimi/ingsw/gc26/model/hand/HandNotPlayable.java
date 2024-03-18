package it.polimi.ingsw.gc26.model.hand;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Side;

public class HandNotPlayable extends HandState{

    public void transition(){
        hand.setState(new HandPlayable());
    }

    public Side playCard(Side side, Card card){
        return null;
        /* devo gestire una exception */
    }
}
