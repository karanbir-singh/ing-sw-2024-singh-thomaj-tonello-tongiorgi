package it.polimi.ingsw.gc26.request.view_request;

import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import it.polimi.ingsw.gc26.view_model.ViewController;

public class HandUpdateRequest implements ViewRequest {
    private final SimplifiedHand simplifiedHand;

    private final String message;

    public HandUpdateRequest(SimplifiedHand simplifiedHand, String message) {
        this.simplifiedHand = simplifiedHand;
        this.message = message;
    }

    @Override
    public void execute(ViewController viewController) {
        viewController.updateHand(this.simplifiedHand, this.message);
    }
}
