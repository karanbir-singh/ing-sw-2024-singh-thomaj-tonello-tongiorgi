package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;

import java.io.Serializable;

/**
 * This class manages the request to choose a pawn color.
 */
public class ChoosePawnColorRequest implements GameRequest, Serializable {
    /**
     * Pawn's color
     */
    final private String color;
    /**
     * ID player who has made the request
     */
    final private String playerID;

    /**
     * Creates the new pawn request
     *
     * @param color    Pawn's color
     * @param playerID ID player who has made the request
     */
    public ChoosePawnColorRequest(String color, String playerID) {
        this.color = color;
        this.playerID = playerID;
    }

    /**
     * Adds the request using the game controller.
     *
     * @param gameController game's controller, must be not equals to null
     */
    @Override
    public void execute(GameController gameController) {
        gameController.choosePawnColor(this.color, this.playerID);
    }
}
