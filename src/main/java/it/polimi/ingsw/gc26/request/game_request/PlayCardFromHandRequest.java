package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;

import java.io.Serializable;

/**
 * This class manages the request to play a card from the hand.
 */
public class PlayCardFromHandRequest implements GameRequest, Serializable {
    /**
     * ID client that has created the request
     */
    final private String playerID;

    /**
     * Crates the new request
     *
     * @param playerID ID client that has created the request
     */
    public PlayCardFromHandRequest(String playerID) {
        this.playerID = playerID;
    }

    /**
     * Adds the request using the game controller.
     *
     * @param gameController game's controller, must be not equals to null
     */
    @Override
    public void execute(GameController gameController) {
        gameController.playCardFromHand(this.playerID);
    }
}
