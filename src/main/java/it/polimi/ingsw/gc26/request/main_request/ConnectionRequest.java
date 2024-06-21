package it.polimi.ingsw.gc26.request.main_request;

import it.polimi.ingsw.gc26.controller.MainController;
import it.polimi.ingsw.gc26.network.VirtualView;

/**
 * This class represent a request to join a game that has already been created.
 * Uses a priority queue to handle important requests first.
 * Zero priority is a normal request and two is the max priority.
 */
public class ConnectionRequest implements MainRequest {
    /**
     * Object that represent the client and contains methods to be call in the client side.
     */
    private final VirtualView client;
    /**
     * Client's nickname
     */
    private final String nickname;
    /**
     * Integer between zero and two. The higher the number, the higher the priority is.
     */
    private final int priority;

    /**
     * Creates the new request.
     *
     * @param client
     * @param nickname client's nickname to be set
     * @param priority integer between zero and two. The higher the number, the higher the priority is.
     */
    public ConnectionRequest(VirtualView client, String nickname, int priority) {
        this.client = client;
        this.nickname = nickname;
        this.priority = priority;
    }

    /**
     * Method to be executed
     *
     * @param mainController main controller, must be not equals to null
     */
    @Override
    public void execute(MainController mainController) {
        mainController.connect(client, nickname);
    }

    /**
     * Return the priority of the request.
     *
     * @return integer between zero and two
     */
    @Override
    public int getPriority() {
        return this.priority;
    }
}
