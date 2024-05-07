package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;

public class SelectCardFromHandRequest implements GameRequest {
    final private int cardIndex;
    final private String playerID;

    public SelectCardFromHandRequest(int cardIndex, String playerId) {
        this.cardIndex = cardIndex;
        this.playerID = playerId;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.selectCardFromHand(this.cardIndex, playerID);
    }
}
