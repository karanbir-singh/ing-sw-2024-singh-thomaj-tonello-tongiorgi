package it.polimi.ingsw.gc26.socket;

import it.polimi.ingsw.gc26.model.game.Message;

public interface VirtualView {
    void showMessage(Message message);
    void reportError(String errorMessage);

}
