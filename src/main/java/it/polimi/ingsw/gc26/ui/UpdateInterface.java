package it.polimi.ingsw.gc26.ui;

import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.view_model.*;

public interface UpdateInterface {

    void updateViewCommonTable(SimplifiedCommonTable simplifiedCommonTable);

    void updateViewPlayer(SimplifiedPlayer simplifiedPlayer);

    void updateViewHand(SimplifiedHand simplifiedHand);

    void updateViewSecretHand(SimplifiedHand simplifiedSecretHand);

    void updateViewPersonalBoard(SimplifiedPersonalBoard personalBoard);

    void updateViewOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard);

    void updateViewSimplifiedChat(SimplifiedChat simplifiedChat);

    void updateGame(SimplifiedGame simplifiedGame);

    void showMessage(String message);

    void showError(String message);
    void closeErrorPopup();
}
