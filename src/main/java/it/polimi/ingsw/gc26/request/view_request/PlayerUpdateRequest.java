package it.polimi.ingsw.gc26.request.view_request;

import it.polimi.ingsw.gc26.view_model.SimplifiedPlayer;
import it.polimi.ingsw.gc26.view_model.ViewController;

/**
 * This class manages the request to update the player
 */
public class PlayerUpdateRequest implements ViewRequest {
    /**
     * Simplified version of player containing only relevant information for the client
     */
    private final SimplifiedPlayer simplifiedPlayer;
    /**
     * Info message to be displayed with the update
     */
    private final String message;

    /**
     * Creates a new update request
     *
     * @param simplifiedPlayer Simplified version of player containing only relevant information for the client
     * @param message Info message to be displayed with the update
     */
    public PlayerUpdateRequest(SimplifiedPlayer simplifiedPlayer, String message) {
        this.simplifiedPlayer = simplifiedPlayer;
        this.message = message;
    }

    /**
     * Method to be executed
     *
     * @param viewController must be not null
     */
    @Override
    public void execute(ViewController viewController) {
        viewController.updatePlayer(this.simplifiedPlayer, this.message);
    }
}
