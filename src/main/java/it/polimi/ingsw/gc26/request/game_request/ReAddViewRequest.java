package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;
import it.polimi.ingsw.gc26.network.VirtualView;

/**
 * This class manages the request to re-add the client to the game after the server went down.
 */
public class ReAddViewRequest implements GameRequest {
    /**
     * Instance that represent the client and allow to call methods on its side
     */
    final private VirtualView view;
    /**
     * ID client that has created the request
     */
    final private String clientID;

    /**
     * Crates the new request
     *
     * @param view     new client's view
     * @param clientID ID client that has created the request
     */
    public ReAddViewRequest(VirtualView view, String clientID) {
        this.clientID = clientID;
        this.view = view;
    }

    /**
     * Adds the request using the game controller.
     *
     * @param gameController game's controller, must be not equals to null
     */
    @Override
    public void execute(GameController gameController) {
        gameController.reAddView(this.view, this.clientID);
    }
}
