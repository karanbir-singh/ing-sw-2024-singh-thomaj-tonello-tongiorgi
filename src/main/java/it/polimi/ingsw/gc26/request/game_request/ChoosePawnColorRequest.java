package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;
import java.io.Serializable;

/**
 * This class manages the request to choose a pawn color.
 */
public class ChoosePawnColorRequest implements GameRequest, Serializable {
    final private String color;
    final private String playerID;

    public ChoosePawnColorRequest(String color, String playerID){
        this.color = color;
        this.playerID = playerID;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.choosePawnColor(this.color,this.playerID);
    }
}
