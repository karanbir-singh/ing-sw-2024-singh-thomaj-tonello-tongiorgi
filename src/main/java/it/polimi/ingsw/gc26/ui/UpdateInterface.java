package it.polimi.ingsw.gc26.ui;

import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.view_model.SimplifiedChat;
import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import it.polimi.ingsw.gc26.view_model.SimplifiedPlayer;

public interface UpdateInterface {

    void updateViewCommonTable(SimplifiedCommonTable simplifiedCommonTable);

    void updateViewPlayer(SimplifiedPlayer simplifiedPlayer);

    void updateViewHand(SimplifiedHand simplifiedHand);

    void updateViewSecretHand(SimplifiedHand simplifiedSecretHand);

    void updateViewPersonalBoard(PersonalBoard personalBoard);

    void updateViewOtherPersonalBoard(PersonalBoard otherPersonalBoard);

    void updateViewSimplifiedChat(SimplifiedChat simplifiedChat);

    void showMessage(String message);

    void showError(String message);
}
