package it.polimi.ingsw.gc26.request;

import it.polimi.ingsw.gc26.controller.GameController;

public class SelectSecretMissionRequest implements Request {
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
