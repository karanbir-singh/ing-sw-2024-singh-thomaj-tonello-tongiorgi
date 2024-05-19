package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;
import java.io.Serializable;

public class SelectCardFromCommonTableRequest implements GameRequest {
    final private int cardIndex;
    final private String playerID;

    public SelectCardFromCommonTableRequest(int cardIndex, String playerID) {
        this.cardIndex = cardIndex;
        this.playerID = playerID;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.selectCardFromCommonTable(this.cardIndex, this.playerID);
    }
}
