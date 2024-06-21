package it.polimi.ingsw.gc26.request.main_request;

import it.polimi.ingsw.gc26.controller.MainController;
import it.polimi.ingsw.gc26.network.VirtualView;

/**
 * This class represent a request to create a new game.
 * Uses a priority queue to handle important requests first.
 * Zero priority is a normal request and two is the max priority.
 */
public class GameCreationRequest implements MainRequest {
    /**
     * Object that represent the client and contains methods to be call in the client side.
     */
    private final VirtualView client;
    /**
     * Client's nickname
     */
    private final String nickname;
    /**
     * Number of player that will be present in the new game.
     */
    private final int numberOfPlayers;
    /**
     * Integer between zero and two. The higher the number, the higher the priority is.
     */
    private final int priority;

    /**
     * Creates a new game creation request
     *
     * @param client          Object that represent the client and contains methods to be call in the client side.
     * @param nickname        Client's nickname
     * @param numberOfPlayers Number of player that will be present in the new game.
     * @param priority        Integer between zero and two. The higher the number, the higher the priority is.
     */
    public GameCreationRequest(VirtualView client, String nickname, int numberOfPlayers, int priority) {
        this.client = client;
        this.nickname = nickname;
        this.numberOfPlayers = numberOfPlayers;
        this.priority = priority;
    }

    /**
     * Method to be executed
     *
     * @param mainController main controller, must be not equals to null
     */
    @Override
    public void execute(MainController mainController) {
        mainController.createWaitingList(client, nickname, numberOfPlayers);
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
