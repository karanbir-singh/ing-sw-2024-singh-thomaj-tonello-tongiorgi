package it.polimi.ingsw.gc26.request.view_request;

import it.polimi.ingsw.gc26.view_model.SimplifiedPersonalBoard;
import it.polimi.ingsw.gc26.view_model.ViewController;

/**
 * This class manages the request to update the personal board of another player
 */
public class OtherPersonalBoardUpdateRequest implements ViewRequest {
    /**
     * Simplified version of personal board containing only relevant information for the client
     */
    private final SimplifiedPersonalBoard otherPersonalBoard;
    /**
     * Info message to be displayed with the update
     */
    private final String message;

    /**
     * Creates a new update request
     *
     * @param otherPersonalBoard Simplified version of personal board containing only relevant information for the client
     * @param message            Info message to be displayed with the update
     */
    public OtherPersonalBoardUpdateRequest(SimplifiedPersonalBoard otherPersonalBoard, String message) {
        this.otherPersonalBoard = otherPersonalBoard;
        this.message = message;
    }

    /**
     * Method to be executed
     *
     * @param viewController must be not null
     */
    @Override
    public void execute(ViewController viewController) {
        viewController.updateOtherPersonalBoard(this.otherPersonalBoard, this.message);
    }
}
