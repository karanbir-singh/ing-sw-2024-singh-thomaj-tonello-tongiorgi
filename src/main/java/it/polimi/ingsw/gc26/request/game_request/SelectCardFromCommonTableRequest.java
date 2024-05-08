package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;

import java.io.Serializable;

public class SelectCardFromCommonTableRequest implements GameRequest, Serializable {
    final private int selectedX;
    final private int selectedY;
    final private String playerID;

    public SelectCardFromCommonTableRequest(int selectedX, int selectedY, String playerID) {
        this.selectedX = selectedX;
        this.selectedY = selectedY;
        this.playerID = playerID;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.selectCardFromCommonTable(this.selectedX, this.selectedY, this.playerID);
    }
}
