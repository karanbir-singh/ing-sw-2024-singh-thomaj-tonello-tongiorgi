package it.polimi.ingsw.gc26.request;

import it.polimi.ingsw.gc26.controller.GameController;

public class SelectPositionOnBoardRequest implements Request {
    final private int selectedX;
    final private int selectedY;
    final private String playerID;

    public SelectPositionOnBoardRequest(int selectedX, int selectedY, String playerID) {
        this.selectedX = selectedX;
        this.selectedY = selectedY;
        this.playerID = playerID;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.selectPositionOnBoard(selectedX, selectedY, playerID);
    }
}
