package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;

import java.io.Serializable;

public class SetSecretMissionRequest implements GameRequest, Serializable {
    final private String playerID;

    public SetSecretMissionRequest(String playerID) {
        this.playerID = playerID;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.setSecretMission(this.playerID);
    }
}
