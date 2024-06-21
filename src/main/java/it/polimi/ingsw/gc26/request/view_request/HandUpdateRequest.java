package it.polimi.ingsw.gc26.request.view_request;

import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import it.polimi.ingsw.gc26.view_model.ViewController;

/**
 * This class manages the request to update the hand
 */
public class HandUpdateRequest implements ViewRequest {
    /**
     * Simplified version of hand containing only relevant information for the client
     */
    private final SimplifiedHand simplifiedHand;
    /**
     * Info message to be displayed with the update
     */
    private final String message;

    /**
     * Creates a new update request
     *
     * @param simplifiedHand Simplified version of hand containing only relevant information for the client
     * @param message        Info message to be displayed with the update
     */
    public HandUpdateRequest(SimplifiedHand simplifiedHand, String message) {
        this.simplifiedHand = simplifiedHand;
        this.message = message;
    }

    /**
     * Method to be executed
     *
     * @param viewController must be not null
     */
    @Override
    public void execute(ViewController viewController) {
        viewController.updateHand(this.simplifiedHand, this.message);
    }
}
