package it.polimi.ingsw.gc26.request.view_request;

import it.polimi.ingsw.gc26.view_model.ViewController;

/**
 * This class manages the request to destroy the client
 */
public class DestroyClientRequest implements ViewRequest {

    /**
     * Method to be executed
     *
     * @param viewController must be not null
     */
    @Override
    public void execute(ViewController viewController) {
        viewController.killProcess();
    }
}
