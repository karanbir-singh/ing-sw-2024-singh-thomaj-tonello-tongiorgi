package it.polimi.ingsw.gc26.request.view_request;

import it.polimi.ingsw.gc26.view_model.SimplifiedChat;
import it.polimi.ingsw.gc26.view_model.ViewController;

public class ChatUpdateRequest implements ViewRequest {
    private final SimplifiedChat simplifiedChat;
    private final String message;

    public ChatUpdateRequest(SimplifiedChat simplifiedChat, String message) {
        this.simplifiedChat = simplifiedChat;
        this.message = message;
    }

    @Override
    public void execute(ViewController viewController) {
        viewController.updateChat(this.simplifiedChat, this.message);
    }
}
