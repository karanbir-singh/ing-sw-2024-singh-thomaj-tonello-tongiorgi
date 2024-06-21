package it.polimi.ingsw.gc26.request.view_request;

import it.polimi.ingsw.gc26.view_model.SimplifiedPersonalBoard;
import it.polimi.ingsw.gc26.view_model.ViewController;

/**
 * This class manages the request to update the own personal board
 */
public class PersonalBoardUpdateRequest implements ViewRequest {

    /**
     * Simplified version of personal board containing only relevant information for the client
     */
    private final SimplifiedPersonalBoard personalBoard;
    /**
     * Info message to be displayed with the update
     */
    private final String message;

    /**
     * Creates a new update request
     *
     * @param personalBoard
     * @param message Info message to be displayed with the update
     */
    public PersonalBoardUpdateRequest(SimplifiedPersonalBoard personalBoard, String message) {
        this.personalBoard = personalBoard;
        this.message = message;
    }

    /**
     * Method to be executed
     *
     * @param viewController must be not null
     */
    @Override
    public void execute(ViewController viewController) {
        viewController.updatePersonalBoard(this.personalBoard, this.message);
    }
}
