package it.polimi.ingsw.gc26.view_model;


import it.polimi.ingsw.gc26.ui.UpdateInterface;
import java.util.HashMap;

public class SimplifiedModel {
    private SimplifiedGame simplifiedGame;
    private SimplifiedCommonTable simplifiedCommonTable;
    private SimplifiedPlayer simplifiedPlayer;
    private SimplifiedHand simplifiedHand;
    private SimplifiedHand simplifiedSecretHand;
    private SimplifiedPersonalBoard personalBoard;
    private HashMap<String, SimplifiedPersonalBoard> otherPersonalBoards = new HashMap<>();
    private SimplifiedChat simplifiedChat;
//    private OptionsMenu optionsMenu;

    UpdateInterface view;

    public void setViewUpdater(UpdateInterface view) {
        this.view = view;
    }

    public void setSimplifiedGame(SimplifiedGame simplifiedGame, String message) {
        this.simplifiedGame = simplifiedGame;
        view.updateGame(simplifiedGame);
        view.showMessage(message);
    }

    public void setSimplifiedCommonTable(SimplifiedCommonTable simplifiedCommonTable, String message) {
        this.simplifiedCommonTable = simplifiedCommonTable;
        this.view.updateViewCommonTable(simplifiedCommonTable);
        this.view.showMessage(message);
    }

    public void setSimplifiedPlayer(SimplifiedPlayer simplifiedPlayer, String message) {
        this.simplifiedPlayer = simplifiedPlayer;
        this.view.updateViewPlayer(simplifiedPlayer);
        this.view.showMessage(message);
    }

    public void setSimplifiedHand(SimplifiedHand simplifiedHand, String message) {
        this.simplifiedHand = simplifiedHand;
        this.view.updateViewHand(simplifiedHand);
        this.view.showMessage(message);
    }

    public void setSimplifiedSecretHand(SimplifiedHand simplifiedSecretHand, String message) {
        this.simplifiedSecretHand = simplifiedSecretHand;
        this.view.updateViewSecretHand(simplifiedSecretHand);
        this.view.showMessage(message);
    }

    public void setPersonalBoard(SimplifiedPersonalBoard personalBoard, String message) {
        this.personalBoard = personalBoard;
        this.view.updateViewPersonalBoard(personalBoard);
        this.view.showMessage(message);
    }

    public void setOtherPersonalBoard(SimplifiedPersonalBoard otherPersonalBoard, String message) {
        this.otherPersonalBoards.put(otherPersonalBoard.getNickname(), otherPersonalBoard);
        this.view.updateViewOtherPersonalBoard(otherPersonalBoard); //I do not want to show the new personal board
        // this.view.showMessage(message);
    }

    public void setSimplifiedChat(SimplifiedChat simplifiedChat, String message) {
        this.simplifiedChat = simplifiedChat;
        this.view.updateViewSimplifiedChat(simplifiedChat);
        this.view.showMessage(message);
    }

//    public void setOptionsMenu(OptionsMenu optionsMenu) {
//        this.optionsMenu = optionsMenu;
//    }


    public SimplifiedCommonTable getSimplifiedCommonTable() {
        return simplifiedCommonTable;
    }

    public SimplifiedGame getSimplifiedGame() {
        return simplifiedGame;
    }

    public SimplifiedPlayer getSimplifiedPlayer() {
        return simplifiedPlayer;
    }

    public SimplifiedHand getSimplifiedHand() {
        return simplifiedHand;
    }

    public SimplifiedHand getSimplifiedSecretHand() {
        return simplifiedSecretHand;
    }

    public SimplifiedPersonalBoard getPersonalBoard() {
        return personalBoard;
    }

    public HashMap<String, SimplifiedPersonalBoard> getOthersPersonalBoards() {
        return otherPersonalBoards;
    }

    public SimplifiedChat getSimplifiedChat() {
        return simplifiedChat;
    }

    public UpdateInterface getView() {
        return view;
    }
}
