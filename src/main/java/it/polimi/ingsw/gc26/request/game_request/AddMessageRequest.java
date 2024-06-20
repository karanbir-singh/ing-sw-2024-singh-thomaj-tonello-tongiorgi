package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;
import java.io.Serializable;

public class AddMessageRequest implements GameRequest, Serializable {
    final private String message;
    final private String receiverNickname;
    final private String senderID;
    final private String time;

    public AddMessageRequest(String message, String receiverNickname, String senderID, String time) {
        this.message = message;
        this.receiverNickname = receiverNickname;
        this.senderID = senderID;
        this.time = time;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.addMessage(this.message, this.receiverNickname, this.senderID, this.time);
    }
}
