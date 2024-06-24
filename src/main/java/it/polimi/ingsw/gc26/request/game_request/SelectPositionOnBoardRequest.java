package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;

import java.io.Serializable;

/**
 * This class manages the request to select a position on the score board.
 */
public class SelectPositionOnBoardRequest implements GameRequest, Serializable {
    /**
     * X coordinate on the personal board. Real number.
     */
    final private int selectedX;
    /**
     * Y coordinate on the personal board.Real number.
     */
    final private int selectedY;
    /**
     * ID client that has created the request
     */
    final private String playerID;

    /**
     * Crates the new request
     *
     * @param selectedX X coordinate on the personal board. Real number.
     * @param selectedY Y coordinate on the personal board.Real number.
     * @param playerID  ID client that has created the request
     */
    public SelectPositionOnBoardRequest(int selectedX, int selectedY, String playerID) {
        this.selectedX = selectedX;
        this.selectedY = selectedY;
        this.playerID = playerID;
    }

    /**
     * Adds the request using the game controller.
     *
     * @param gameController game's controller, must be not equals to null
     */
    @Override
    public void execute(GameController gameController) {
        gameController.selectPositionOnBoard(selectedX, selectedY, playerID);
    }
}
