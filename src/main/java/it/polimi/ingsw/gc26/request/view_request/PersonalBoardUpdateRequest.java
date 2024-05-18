package it.polimi.ingsw.gc26.request.view_request;

import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.view_model.SimplifiedPersonalBoard;
import it.polimi.ingsw.gc26.view_model.ViewController;

public class PersonalBoardUpdateRequest implements ViewRequest {

    private final SimplifiedPersonalBoard personalBoard;
    private final String message;

    public PersonalBoardUpdateRequest(SimplifiedPersonalBoard personalBoard, String message) {
        this.personalBoard = personalBoard;
        this.message = message;
    }

    @Override
    public void execute(ViewController viewController) {
        viewController.updatePersonalBoard(this.personalBoard, this.message);
    }
}
