package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;

/**
 * This interface contains the method to be executed with the command design pattern.
 * This pattern manages requests from the client to be executed async in the game controller.
 */
public interface GameRequest {
    /**
     * Method to be executed
     * @param gameController game's controller, must be not equals to null
     */
    void execute(GameController gameController);
}
