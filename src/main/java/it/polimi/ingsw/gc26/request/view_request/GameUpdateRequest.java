package it.polimi.ingsw.gc26.request.view_request;

import it.polimi.ingsw.gc26.view_model.SimplifiedGame;
import it.polimi.ingsw.gc26.view_model.ViewController;

/**
 * This class manages the request to update the game
 */
public class GameUpdateRequest implements ViewRequest {
    /**
     * Simplified version of game containing only relevant information for the client
     */
    private final SimplifiedGame simplifiedGame;
    /**
     * Info message to be displayed with the update
     */
    private final String message;

    /**
     * Creates a new update request
     *
     * @param simplifiedGame Simplified version of game containing only relevant information for the client
     * @param message Info message to be displayed with the update
     */
    public GameUpdateRequest(SimplifiedGame simplifiedGame, String message) {
        this.simplifiedGame = simplifiedGame;
        this.message = message;
    }

    /**
     * Method to be executed
     *
     * @param viewController must be not null
     */
    @Override
    public void execute(ViewController viewController) {
        viewController.updateGame(this.simplifiedGame, this.message);
    }
}