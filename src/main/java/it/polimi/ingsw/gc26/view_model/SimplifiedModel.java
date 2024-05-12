package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.model.player.PersonalBoard;

public class SimplifiedModel {
    private SimplifiedCommonTable simplifiedCommonTable;
    private SimplifiedPlayer simplifiedPlayer;
    private SimplifiedHand simplifiedHand;
    private SimplifiedHand simplifiedSecretHand;
    private PersonalBoard personalBoard;
    private PersonalBoard otherPersonalBoard;
    private SimplifiedChat simplifiedChat;
//    private OptionsMenu optionsMenu;

    public void setSimplifiedCommonTable(SimplifiedCommonTable simplifiedCommonTable) {
        this.simplifiedCommonTable = simplifiedCommonTable;
    }

    public void setSimplifiedPlayer(SimplifiedPlayer simplifiedPlayer) {
        this.simplifiedPlayer = simplifiedPlayer;
    }

    public void setSimplifiedHand(SimplifiedHand simplifiedHand) {
        this.simplifiedHand = simplifiedHand;
    }

    public void setSimplifiedSecretHand(SimplifiedHand simplifiedSecretHand) {
        this.simplifiedSecretHand = simplifiedSecretHand;
    }

    public void setPersonalBoard(PersonalBoard personalBoard) {
        this.personalBoard = personalBoard;
    }

    public void setOtherPersonalBoard(PersonalBoard otherPersonalBoard) {
        this.otherPersonalBoard = otherPersonalBoard;
    }

    public void setSimplifiedChat(SimplifiedChat simplifiedChat) {
        this.simplifiedChat = simplifiedChat;
    }

//    public void setOptionsMenu(OptionsMenu optionsMenu) {
//        this.optionsMenu = optionsMenu;
//    }

    private void updateView() {
        // TODO: qua ci sono tutti i reload della TUI e aggiornamenti della GUI

    }
}
