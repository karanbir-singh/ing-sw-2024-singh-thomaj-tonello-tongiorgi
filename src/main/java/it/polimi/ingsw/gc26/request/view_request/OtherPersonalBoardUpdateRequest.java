package it.polimi.ingsw.gc26.request.view_request;

import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.view_model.SimplifiedPersonalBoard;
import it.polimi.ingsw.gc26.view_model.ViewController;

public class OtherPersonalBoardUpdateRequest implements ViewRequest {
    private final SimplifiedPersonalBoard otherPersonalBoard;
    private final String message;

    public OtherPersonalBoardUpdateRequest(SimplifiedPersonalBoard otherPersonalBoard, String message) {
        this.otherPersonalBoard = otherPersonalBoard;
        this.message = message;
    }

    @Override
    public void execute(ViewController viewController) {
        viewController.updateOtherPersonalBoard(this.otherPersonalBoard, this.message);
    }
}
