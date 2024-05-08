package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;

import java.io.Serializable;

public class TurnSelectedSideRequest implements GameRequest, Serializable {
    final private String playerID;

    public TurnSelectedSideRequest(String playerID) {
        this.playerID = playerID;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.turnSelectedCardSide(this.playerID);
    }
}
