package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;

import java.io.Serializable;

public class SelectSecretMissionRequest implements GameRequest, Serializable {
    final private String playerID;
    final private int position;

    public SelectSecretMissionRequest(int position, String playerID) {
        this.playerID = playerID;
        this.position = position;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.selectSecretMission(this.position, this.playerID);
    }
}
