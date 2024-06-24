package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;

/**
 * This class manages the request to select a card from the common table.
 */
public class SelectCardFromCommonTableRequest implements GameRequest {
    /**
     * Index in common table array. Between zero and six
     */
    final private int cardIndex;
    /**
     * ID client that has created the request
     */
    final private String playerID;

    /**
     * Crates the new request
     *
     * @param cardIndex index in common table array. Between zero and six
     * @param playerID ID client that has created the request
     */
    public SelectCardFromCommonTableRequest(int cardIndex, String playerID) {
        this.cardIndex = cardIndex;
        this.playerID = playerID;
    }

    /**
     * Adds the request using the game controller.
     *
     * @param gameController game's controller, must be not equals to null
     */
    @Override
    public void execute(GameController gameController) {
        gameController.selectCardFromCommonTable(this.cardIndex, this.playerID);
    }
}
