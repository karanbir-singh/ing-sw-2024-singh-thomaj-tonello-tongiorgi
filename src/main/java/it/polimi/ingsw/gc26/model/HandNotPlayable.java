package it.polimi.ingsw.gc26.model;

public class HandNotPlayable {

    public void transition(){
        hand.setState(new HandPlayable);
    }

    public Side playCard(Side side, Card card){
        /*exception*/
    }
}
