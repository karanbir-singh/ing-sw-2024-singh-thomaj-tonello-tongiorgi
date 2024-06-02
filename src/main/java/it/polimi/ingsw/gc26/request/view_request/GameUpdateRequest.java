package it.polimi.ingsw.gc26.request.view_request;

import it.polimi.ingsw.gc26.view_model.SimplifiedGame;
import it.polimi.ingsw.gc26.view_model.ViewController;

public class GameUpdateRequest implements ViewRequest {
    private final SimplifiedGame simplifiedGame;

    private final String message;

    public GameUpdateRequest(SimplifiedGame simplifiedGame, String message) {
        this.simplifiedGame = simplifiedGame;
        this.message = message;
    }

    @Override
    public void execute(ViewController viewController) {
        viewController.updateGame(this.simplifiedGame, this.message);
    }
}