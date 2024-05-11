package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;
import it.polimi.ingsw.gc26.network.VirtualView;

public class ReAddViewRequest implements GameRequest{
    VirtualView view;
    String clientID;
    public ReAddViewRequest(VirtualView view, String clientID){
        this.clientID = clientID;
        this.view = view;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.reAddView(this.view, this.clientID);
    }
}
