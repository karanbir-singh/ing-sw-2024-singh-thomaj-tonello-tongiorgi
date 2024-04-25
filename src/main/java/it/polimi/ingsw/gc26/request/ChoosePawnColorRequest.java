package it.polimi.ingsw.gc26.request;

import it.polimi.ingsw.gc26.controller.GameController;

public class ChoosePawnColorRequest implements Request {
    final private String color;
    final private String playerID;

    public ChoosePawnColorRequest(String color, String playerID) {
        this.color = color;
        this.playerID = playerID;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.choosePawnColor(this.color, this.playerID);
    }
}
