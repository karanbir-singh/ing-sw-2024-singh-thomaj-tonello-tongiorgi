package it.polimi.ingsw.gc26.model.hand;

import it.polimi.ingsw.gc26.model.card_side.Side;
import it.polimi.ingsw.gc26.model.card.Card;

abstract class HandState {

    protected Hand hand;

    public Side playCard(Side side, Card card){
        // To finish
    }
    public void transition(HandState state){}

}
