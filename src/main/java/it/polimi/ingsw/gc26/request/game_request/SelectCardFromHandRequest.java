package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;

import java.io.Serializable;

/**
 * This class manages the request to select a card from the hand.
 */
public class SelectCardFromHandRequest implements GameRequest, Serializable {
    /**
     * Card's position on hand array. Number between zero and three
     */
    final private int cardIndex;
    /**
     * ID client that has created the request
     */
    final private String playerID;

    /**
     * Crates the new request
     *
     * @param cardIndex Card's position on hand array. Number between zero and three
     * @param playerID  ID client that has created the request
     */
    public SelectCardFromHandRequest(int cardIndex, String playerID) {
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
        gameController.selectCardFromHand(this.cardIndex, playerID);
    }
}
