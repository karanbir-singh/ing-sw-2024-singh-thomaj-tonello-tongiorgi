package it.polimi.ingsw.gc26.model;

import java.util.*;

abstract class HandState {

    protected Hand hand;

    public Side playCard(Side side, Card card){}
    public void transition(HandState state){}

}
