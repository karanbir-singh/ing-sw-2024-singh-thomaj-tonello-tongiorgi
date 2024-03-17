package it.polimi.ingsw.gc26.model.hand;

public class HandNotPlayable extends HandState{

    public void transition(){
        hand.setState(new HandPlayable());
    }

    public Side playCard(Side side, Card card){
        /*exception*/
    }
}
