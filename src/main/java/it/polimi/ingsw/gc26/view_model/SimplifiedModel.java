package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.Printer;
import it.polimi.ingsw.gc26.ui.UpdateInterface;

public class SimplifiedModel {
    private SimplifiedGame simplifiedGame;
    private SimplifiedCommonTable simplifiedCommonTable;
    private SimplifiedPlayer simplifiedPlayer;
    private SimplifiedHand simplifiedHand;
    private SimplifiedHand simplifiedSecretHand;
    private SimplifiedPersonalBoard personalBoard;
    private SimplifiedPersonalBoard otherPersonalBoard;
    private SimplifiedChat simplifiedChat;
//    private OptionsMenu optionsMenu;

    UpdateInterface view;

    public SimplifiedModel(UpdateInterface view){
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
        this.otherPersonalBoard = otherPersonalBoard;
        this.view.updateViewOtherPersonalBoard(otherPersonalBoard);
        this.view.showMessage(message);
    }

    public void setSimplifiedChat(SimplifiedChat simplifiedChat, String message) {
        this.simplifiedChat = simplifiedChat;
        this.view.updateViewSimplifiedChat(simplifiedChat);
        this.view.showMessage(message);
    }

    public String[][] printableGame() {
        //COMMON TABLE: check if missions are already present
        String[][] commonTablePrint;
        if(simplifiedCommonTable.getCommonMissions().isEmpty()){
            commonTablePrint = simplifiedCommonTable.printableCommonTable();
        } else {
            commonTablePrint = simplifiedCommonTable.printableCommonTableAndMissions();
        }
        //SCORES
        String[][] scores = printableScores();
        //PERSONAL BOARD
        String[][] personalBoardPrint = personalBoard.printablePersonalBoard();
        //HAND: check if secret mission is already present
        String[][] handPrint;
        if(personalBoard.getSecretMission() == null){
            handPrint = simplifiedHand.printableHand();
        } else {
            handPrint = simplifiedPlayer.printableHandAndMission(personalBoard, simplifiedHand);
        }
        //calculate dimensions
        int yDim = commonTablePrint.length + personalBoardPrint.length + handPrint.length + 4;
        int xDim = Math.max(Math.max(commonTablePrint[0].length + scores[0].length + 1, personalBoardPrint[0].length), handPrint[0].length);
        //utils
        Printer printer = new Printer();
        int y=0;

        //initialize empty matrix
        String[][] printableGame = new String[yDim][xDim];
        for(int i=0; i<yDim; i++){
            for(int j=0; j<xDim; j++){
                printableGame[i][j] = "\t";
            }
        }

        //DESIGN THE MATRIX
        //show common table
        printableGame[y][0] = "COMMON TABLE:";
        y++;
        printer.addPrintable(commonTablePrint, printableGame, 0, y);

        //show scores

        printer.addPrintable(scores, printableGame, commonTablePrint[0].length + 1, y + 1);
        y += commonTablePrint.length;

        //show personal board
        printableGame[y][0] = "\nYOUR PERSONAL BOARD:\n";
        y++;
        printer.addPrintable(personalBoardPrint, printableGame, (xDim-personalBoardPrint[0].length)/2, y);
        y += personalBoardPrint.length;

        //show player's hand
        printableGame[y][0] = "\nYOUR HAND:\n";
        y++;
        printer.addPrintable(handPrint, printableGame, 0, y);

        return printableGame;
    }

    public String[][] printableScores() {
        //dimensions
        int xDim = 2;
        int yDim = 1 + 1;
//        int yDim = players.size() + 1;

        String[][] scores = new String[yDim][xDim];
//
//        //calculate the spaces needed to align the names
//        int maxLenght = 0;
//        StringBuilder spaces;
//        for (Player p: players) {
//            maxLenght = Math.max(maxLenght, p.getNickname().length());
//        }
//        maxLenght ++;
//
//        //design the matrix
//        scores[0][0] = "CURRENT SCORES:";
//        scores[0][1] = "";
//        for (Player p: players) {
//            int i = 0;
//            spaces = new StringBuilder();
//            while(i + p.getNickname().length() < maxLenght){
//                spaces.append(" ");
//                i++;
//            }
//            scores[players.indexOf(p)+1][0] = p.getNickname() + spaces;
//            scores[players.indexOf(p)+1][1] = p.printableScore();
//        }

        return scores;
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

    public SimplifiedPersonalBoard getOtherPersonalBoard() {
        return otherPersonalBoard;
    }

    public SimplifiedChat getSimplifiedChat() {
        return simplifiedChat;
    }
}
