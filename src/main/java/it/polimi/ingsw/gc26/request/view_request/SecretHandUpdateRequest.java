package it.polimi.ingsw.gc26.request.view_request;

import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import it.polimi.ingsw.gc26.view_model.ViewController;

public class SecretHandUpdateRequest implements ViewRequest {

    private final SimplifiedHand simplifiedSecretHand;
    private final String message;

    public SecretHandUpdateRequest(SimplifiedHand simplifiedSecretHand, String message) {
        this.simplifiedSecretHand = simplifiedSecretHand;
        this.message = message;
    }

    @Override
    public void execute(ViewController viewController) {
        viewController.updateSecretHand(this.simplifiedSecretHand, this.message);
    }
}
