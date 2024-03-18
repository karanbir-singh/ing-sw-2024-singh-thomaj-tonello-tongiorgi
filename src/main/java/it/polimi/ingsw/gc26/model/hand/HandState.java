package it.polimi.ingsw.gc26.model.hand;

import it.polimi.ingsw.gc26.model.card_side.Side;
import it.polimi.ingsw.gc26.model.card.Card;

abstract class HandState {

    protected Hand hand;

    public abstract Side playCard(Side side, Card card);
    public void transition(HandState state){}

}
