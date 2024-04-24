package it.polimi.ingsw.gc26.request;

import it.polimi.ingsw.gc26.controller.GameController;

public class SetSecretMissionRequest implements Request{
    final private String playerID;
    public SetSecretMissionRequest(String playerID){
        this.playerID = playerID;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.setSecretMission(this.playerID);
    }
}
