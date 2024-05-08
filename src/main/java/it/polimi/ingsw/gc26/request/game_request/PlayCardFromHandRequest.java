package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;

import java.io.Serializable;

public class PlayCardFromHandRequest implements GameRequest, Serializable {
    final private String playerID;

    public PlayCardFromHandRequest(String playerID) {
        this.playerID = playerID;
    }

    public void execute(GameController gameController) {
        gameController.playCardFromHand(this.playerID);
    }
}
