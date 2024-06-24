package it.polimi.ingsw.gc26.request.view_request;

import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import it.polimi.ingsw.gc26.view_model.ViewController;

/**
 * This class manages the request to update the secret hand
 */
public class SecretHandUpdateRequest implements ViewRequest {

    /**
     * Simplified version of secret hand containing only relevant information for the client
     */
    private final SimplifiedHand simplifiedSecretHand;
    /**
     * Info message to be displayed with the update
     */
    private final String message;

    /**
     * Creates a new update request
     *
     * @param simplifiedSecretHand Simplified version of secret hand containing only relevant information for the client
     * @param message Info message to be displayed with the update
     */
    public SecretHandUpdateRequest(SimplifiedHand simplifiedSecretHand, String message) {
        this.simplifiedSecretHand = simplifiedSecretHand;
        this.message = message;
    }

    /**
     * Method to be executed
     *
     * @param viewController must be not null
     */
    @Override
    public void execute(ViewController viewController) {
        viewController.updateSecretHand(this.simplifiedSecretHand, this.message);
    }
}
