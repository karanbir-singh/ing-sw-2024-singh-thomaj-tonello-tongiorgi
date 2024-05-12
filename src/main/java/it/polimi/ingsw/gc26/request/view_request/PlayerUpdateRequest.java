package it.polimi.ingsw.gc26.request.view_request;

import it.polimi.ingsw.gc26.view_model.SimplifiedPlayer;
import it.polimi.ingsw.gc26.view_model.ViewController;

public class PlayerUpdateRequest implements ViewRequest {
    private final SimplifiedPlayer simplifiedPlayer;
    private final String message;

    public PlayerUpdateRequest(SimplifiedPlayer simplifiedPlayer, String message) {
        this.simplifiedPlayer = simplifiedPlayer;
        this.message = message;
    }

    @Override
    public void execute(ViewController viewController) {
        viewController.updatePlayer(this.simplifiedPlayer, this.message);
    }
}
