package it.polimi.ingsw.gc26.socket;

import it.polimi.ingsw.gc26.model.Message;

public interface VirtualView {
    void showMessage(Message message);
    void reportError(String errorMessage);


}
