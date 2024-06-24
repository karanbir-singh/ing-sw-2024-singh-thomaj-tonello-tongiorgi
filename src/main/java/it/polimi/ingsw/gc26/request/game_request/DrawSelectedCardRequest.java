package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;

import java.io.Serializable;

/**
 * This class manages the request to draw a selected card.
 */
public class DrawSelectedCardRequest implements GameRequest, Serializable {
    /**
     * ID client that has created the request
     */
    final private String playerID;

    /**
     * Crates the new request
     *
     * @param playerID ID client that has created the request
     */
    public DrawSelectedCardRequest(String playerID) {
        this.playerID = playerID;
    }

    /**
     * Adds the request using the game controller.
     *
     * @param gameController game's controller, must be not equals to null
     */
    @Override
    public void execute(GameController gameController) {
        gameController.drawSelectedCard(this.playerID);
    }
}
