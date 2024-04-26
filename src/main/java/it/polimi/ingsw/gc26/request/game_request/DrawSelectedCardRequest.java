package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;

public class DrawSelectedCardRequest implements GameRequest {
    final private String playerID;

    public DrawSelectedCardRequest(String playerID) {
        this.playerID = playerID;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.drawSelectedCard(this.playerID);
    }
}
