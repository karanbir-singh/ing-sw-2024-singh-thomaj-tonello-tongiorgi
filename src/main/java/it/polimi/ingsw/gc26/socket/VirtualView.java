package it.polimi.ingsw.gc26.socket;

import it.polimi.ingsw.gc26.model.game.Message;

public interface VirtualView {
    void showMessage(String message);
    void reportMessage(String message);
    void reportError(String errorMessage);

}
