package it.polimi.ingsw.gc26.request.view_request;

import it.polimi.ingsw.gc26.view_model.SimplifiedChat;
import it.polimi.ingsw.gc26.view_model.ViewController;

/**
 * This class manages the request to update the chat
 */
public class ChatUpdateRequest implements ViewRequest {
    /**
     * Simplified version of chat containing only relevant information for the client
     */
    private final SimplifiedChat simplifiedChat;
    /**
     * Info message to be displayed with the update
     */
    private final String message;

    /**
     * Creates the new update request
     *
     * @param simplifiedChat Simplified version of chat containing only relevant information for the client
     * @param message        Info message to be displayed with the update
     */
    public ChatUpdateRequest(SimplifiedChat simplifiedChat, String message) {
        this.simplifiedChat = simplifiedChat;
        this.message = message;
    }

    /**
     * Method to be executed
     *
     * @param viewController must be not null
     */
    @Override
    public void execute(ViewController viewController) {
        viewController.updateChat(this.simplifiedChat, this.message);
    }
}
