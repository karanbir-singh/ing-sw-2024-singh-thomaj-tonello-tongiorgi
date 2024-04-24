package it.polimi.ingsw.gc26.request;

import it.polimi.ingsw.gc26.controller.GameController;

public class TurnSelectedSideRequest implements Request{
    final private String playerID;

    public TurnSelectedSideRequest(String playerID){
        this.playerID = playerID;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.turnSelectedCardSide(this.playerID);
    }
}
