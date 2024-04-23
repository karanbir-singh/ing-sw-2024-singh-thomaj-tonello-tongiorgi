package it.polimi.ingsw.gc26.request;

import it.polimi.ingsw.gc26.controller.GameController;

public class PlayCardFromHandRequest implements Request{
    final private String playerID;
    public PlayCardFromHandRequest(String playerID){
        this.playerID = playerID;
    }
    public void execute(GameController gameController){
        gameController.playCardFromHand(this.playerID);
    }
}
