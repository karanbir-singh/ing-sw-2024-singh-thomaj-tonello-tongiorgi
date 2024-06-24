package it.polimi.ingsw.gc26.request.game_request;

import it.polimi.ingsw.gc26.controller.GameController;

import java.io.Serializable;

/**
 * This class manages the request to add a new message in the chat.
 */
public class AddMessageRequest implements GameRequest, Serializable {
    /**
     * Message to be added in the chat
     */
    final private String message;
    /**
     * Player who is going to receive the message. Empty string if is a group message.
     */
    final private String receiverNickname;
    /**
     * Player's Unique ID. Must be not null.
     */
    final private String senderID;
    /**
     * Local time of client.
     */
    final private String time;

    /**
     * Creates the new message request.
     *
     * @param message          Message to be added in the chat
     * @param receiverNickname Player who is going to receive the message. Empty string if is a group message.
     * @param senderID         Player's Unique ID. Must be not null.
     * @param time             Local time of client.
     */
    public AddMessageRequest(String message, String receiverNickname, String senderID, String time) {
        this.message = message;
        this.receiverNickname = receiverNickname;
        this.senderID = senderID;
        this.time = time;
    }

    /**
     * Adds the message using the game controller.
     *
     * @param gameController game's controller, must be not equals to null
     */
    @Override
    public void execute(GameController gameController) {
        gameController.addMessage(this.message, this.receiverNickname, this.senderID, this.time);
    }
}
