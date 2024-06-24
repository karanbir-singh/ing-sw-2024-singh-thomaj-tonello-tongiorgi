package it.polimi.ingsw.gc26.request.view_request;

import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.ViewController;

/**
 * This class manages the request to update the common table
 */
public class CommonTableUpdateRequest implements ViewRequest {

    /**
     * Simplified version of common table containing only relevant information for the client
     */
    private final SimplifiedCommonTable simplifiedCommonTable;
    /**
     * Info message to be displayed with the update
     */
    private final String message;

    /**
     * Crates a new update request
     *
     * @param simplifiedCommonTable Simplified version of common table containing only relevant information for the client
     * @param message               Info message to be displayed with the update
     */
    public CommonTableUpdateRequest(SimplifiedCommonTable simplifiedCommonTable, String message) {
        this.simplifiedCommonTable = simplifiedCommonTable;
        this.message = message;
    }

    /**
     * Method to be executed
     *
     * @param viewController must be not null
     */
    @Override
    public void execute(ViewController viewController) {
        viewController.updateCommonTable(this.simplifiedCommonTable, this.message);
    }
}
