package it.polimi.ingsw.gc26.request;

import it.polimi.ingsw.gc26.controller.GameController;

public class PrintPersonalBoardRequest implements Request {
    final private String nickname;
    final private String playerID;

    public PrintPersonalBoardRequest(String nickname, String playerID) {
        this.nickname = nickname;
        this.playerID = playerID;
    }


    @Override
    public void execute(GameController gameController) {
        gameController.printPersonalBoard(this.nickname, this.playerID);
    }
}
