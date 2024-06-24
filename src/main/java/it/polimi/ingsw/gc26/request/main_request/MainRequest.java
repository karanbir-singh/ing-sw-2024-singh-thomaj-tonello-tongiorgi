package it.polimi.ingsw.gc26.request.main_request;

import it.polimi.ingsw.gc26.controller.MainController;

/**
 * This interface contains the methods to be executed with the command design pattern.
 * This pattern manages requests from the client to be executed async in the main controller.
 * Uses a priority queue to handle important requests first.
 * Zero priority is a normal request and two is the max priority.
 */
public interface MainRequest {
    /**
     * Method to be executed.
     *
     * @param mainController main controller, must be not equals to null
     */
    void execute(MainController mainController);

    /**
     * Return the priority of the request.
     *
     * @return integer between zero and two
     */
    int getPriority();
}
