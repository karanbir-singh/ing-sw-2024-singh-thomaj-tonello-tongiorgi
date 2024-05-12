package it.polimi.ingsw.gc26.request.view_request;

import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.view_model.ViewController;

public class PersonalBoardUpdateRequest implements ViewRequest {

    private final PersonalBoard personalBoard;
    private final String message;

    public PersonalBoardUpdateRequest(PersonalBoard personalBoard, String message) {
        this.personalBoard = personalBoard;
        this.message = message;
    }

    @Override
    public void execute(ViewController viewController) {
        viewController.updatePersonalBoard(this.personalBoard, this.message);
    }
}
