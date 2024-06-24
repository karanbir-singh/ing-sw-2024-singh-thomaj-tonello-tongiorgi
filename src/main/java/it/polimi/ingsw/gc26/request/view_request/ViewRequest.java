package it.polimi.ingsw.gc26.request.view_request;
import it.polimi.ingsw.gc26.view_model.ViewController;

/**
 * This interface contains the method to be executed with the command design pattern.
 * This pattern manages requests from the server to be executed async in the client.
 */
public interface ViewRequest {
    /**
     * Method to be executed
     *
     * @param viewController must be not null
     */
    void execute(ViewController viewController);
}
