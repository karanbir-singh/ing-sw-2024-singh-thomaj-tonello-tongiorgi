package it.polimi.ingsw.gc26.ui.tui;

import it.polimi.ingsw.gc26.Printer;
import it.polimi.ingsw.gc26.model.card.*;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.card_side.ability.CornerCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.InkwellCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.ManuscriptCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.QuillCounter;
import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.model.player.Point;
import it.polimi.ingsw.gc26.model.utils.SpecialCharacters;
import it.polimi.ingsw.gc26.model.utils.TextStyle;
import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import it.polimi.ingsw.gc26.view_model.SimplifiedModel;
import it.polimi.ingsw.gc26.view_model.SimplifiedPersonalBoard;

public class CLI {
    private SimplifiedModel miniModel;

    public CLI(SimplifiedModel miniModel){
        this.miniModel = miniModel;
    }

    //GAME
    public void printGame() {
        SimplifiedCommonTable simplifiedCommonTable = miniModel.getSimplifiedCommonTable();
        SimplifiedPersonalBoard personalBoard = miniModel.getPersonalBoard();

        //COMMON TABLE: check if missions are already present
        String[][] commonTablePrint;
        if(simplifiedCommonTable.getCommonMissions().isEmpty()){
            commonTablePrint = printableCommonTable();
        } else {
            commonTablePrint = printableCommonTableAndMissions();
        }

        //SCORES
        String[][] scores;
        //if(miniModel.getSimplifiedGame().getGameState() == GameState.GAME_STARTED ||
          //      miniModel.getSimplifiedGame().getGameState() == GameState.END_STAGE){
            //scores = printableScores();
        //} else {
            scores = new String[0][0];
        //}

        //PERSONAL BOARD
        String[][] personalBoardPrint;
        if(miniModel.getPersonalBoard() != null){
            personalBoardPrint = printablePersonalBoard(miniModel.getPersonalBoard());
        } else {
            personalBoardPrint = new String[0][0];
        }

        //HAND: check if secret mission is already present
        String[][] handPrint;
        if(personalBoard.getSecretMission() == null){
            handPrint = printableHand();
        } else if(miniModel.getSimplifiedHand() != null){
            handPrint = printableHandAndMission();
        } else {
            handPrint = new String[0][0];
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

        showPrintable(printableGame);
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

    //COMMON TABLE
    private void decorateDeck(String[][] ct, int xDeck, int yDeck, int xCardDim, int yCardDim){
        //ct[yDeck + yCardDim][xDeck] = "▔▔▔▔▔▔▔▔▔▔▔▔▔";
        xDeck += xCardDim;
        for(int yOff=0; yOff<yCardDim; yOff++){
            ct[yDeck + yOff][xDeck] = "║      ";
        }
        //ct[yResource + yCardDim][0] = "╚═" + whiteSquare + "══" + whiteSquare + "══" + whiteSquare + "═╝";
    }

    public String[][] printableCommonTable() {
        SimplifiedCommonTable miniCT = miniModel.getSimplifiedCommonTable();

        //dimensions
        int xCardDim = 3;
        int yCardDim = 3;
        int xDim = 3*(xCardDim+2);
        int yDim = 2*(yCardDim+1) + 2;
        //components offsets
        int xResource = 0, yResource = 1;
        int xGold = 0, yGold = yResource + yCardDim + 3;
        //decoration offsets
        int yLine = yResource + yCardDim ;
        //utils
        int index = 0; //index to select the drawable element
        Printer printer = new Printer();
        String selectedStyle = TextStyle.BACKGROUND_BEIGE.getStyleCode() + TextStyle.BLACK.getStyleCode();
        String blackSquare = SpecialCharacters.SQUARE_BLACK.getCharacter();
        String leftPadding = "    ";
        String rightPadding = "       ";

        //initialize empty matrix
        String[][] ct = new String[yDim][xDim];
        for(int i=0; i<yDim; i++){
            for(int j=0; j<xDim; j+=5){
                ct[i][j] = leftPadding;
                ct[i][j+1] = "";
                ct[i][j+2] = "";
                ct[i][j+3] = "";
                ct[i][j+4] = rightPadding;
            }
        }

        //RESOURCES

        for (int i=0; i<2; i++) {
            int offSet = index*5;
            //titles and separators for alignment
            ct[yResource-1][offSet] = "(" + index + ")";
            ct[yResource-1][offSet+1] = " Resource Card";
            ct[yResource-1][offSet+2] = blackSquare;
            ct[yResource-1][offSet+3] = blackSquare;
            ct[yResource-1][offSet+4] = blackSquare;
            xResource++;

            //insert uncovered cards
            if(i< miniCT.getResourceCards().size()){
                Card r = miniCT.getResourceCards().get(i);
                printer.addPrintable(r.getFront().printableSide(), ct, xResource, yResource);
            } else {
                printer.addPrintable(printer.emptyPrintable(xCardDim,yCardDim), ct, xResource, yResource);
            }
            xResource += xCardDim + 1;
            index++;
        }

        //insert resource deck
        ct[yResource-1][index*5] = "(" + index + ") Resource Deck"; //title
        ct[yResource-1][index*5 + 4] = blackSquare + blackSquare + blackSquare; //decoration for alignment
        xResource ++;

        if(miniCT.getResourceDeck() == null){
            printer.addPrintable(printer.emptyPrintable(xCardDim,yCardDim), ct, xResource, yResource);
        } else {
            printer.addPrintable(miniCT.getResourceDeck().getBack().printableSide(), ct, xResource, yResource);
            decorateDeck(ct, xResource, yResource, xCardDim, yCardDim);
        }
        index++;

        //empty lines
        for(int j=0; j<2; j++){
            for(int i=0; i<xDim; i+=5){
                ct[yLine + j][i+1] = blackSquare;
                ct[yLine + j][i+2] = "   " + blackSquare + "   ";
                ct[yLine + j][i+3] = blackSquare;
            }
        }

        //insert uncovered gold cards
        for (int i=0; i<2; i++) {
            int offSet = (index-3)*5;
            ct[yGold-1][offSet] = "(" + index + ")";
            ct[yGold-1][offSet+1] = " Gold Card    ";
            ct[yGold-1][offSet+2] = blackSquare;
            ct[yGold-1][offSet+3] = blackSquare;
            ct[yGold-1][offSet+4] = blackSquare;

            xGold ++;

            if(i < miniCT.getGoldCards().size()){
                Card g = miniCT.getGoldCards().get(i);
                printer.addPrintable(g.getFront().printableSide(), ct, xGold, yGold);
            } else {
                printer.addPrintable(printer.emptyPrintable(xCardDim, yCardDim), ct, xGold, yGold);
            }
            xGold += xCardDim + 1;

            index++;
        }

        //insert gold deck
        ct[yGold-1][(index-3)*5] = "(" + index + ") Gold Deck    " ;
        ct[yGold-1][(index-3)*5 + 4] = blackSquare + blackSquare + blackSquare;

        xGold ++;

        if(miniCT.getGoldDeck() == null){
            printer.addPrintable(printer.emptyPrintable(xCardDim, yCardDim), ct, xGold, yGold);
        } else {
            printer.addPrintable(miniCT.getGoldDeck().getBack().printableSide(), ct, xGold, yGold);
            decorateDeck(ct, xGold, yGold, xCardDim, yCardDim);
        }

        return ct;
    }

    public String[][] printableCommonTableAndMissions(){

        //get printable components
        String[][] commonTable = printableCommonTable();
        String[][] missions = printableCommonMissions();
        //utils
        String blackSquare = SpecialCharacters.SQUARE_BLACK.getCharacter();
        Printer printer = new Printer();
        //dimensions
        int xDim = commonTable[0].length + missions[0].length + 1;
        int yDim = Math.max(commonTable.length, missions.length);
        //decorations offsets
        int xSeparator = commonTable[0].length;
        int yLine = commonTable.length;

        //initialize empty matrix
        String[][] commonTableAndMissions = new String[yDim][xDim];
        for(int i=0; i<yDim; i++){
            for(int j=0; j<xDim; j++){
                commonTableAndMissions[i][j] = "";
            }
        }

        //insert decks and drawable cards
        printer.addPrintable(commonTable, commonTableAndMissions, 0, 1);

        //insert empty lines for alignment
        for(int j=0; j<(missions.length - commonTable.length); j++){
            if(j==0){
                for(int i=0; i<(xDim-5); i+=5){
                    commonTableAndMissions[j][i] = "    ";
                    commonTableAndMissions[j][i+1] = blackSquare;
                    commonTableAndMissions[j][i+2] = "   " + blackSquare + "   ";
                    commonTableAndMissions[j][i+3] = blackSquare;
                    commonTableAndMissions[j][i+4] = "       ";
                }
            } else {
                for(int i=0; i<(xDim-5); i+=5){
                    commonTableAndMissions[yLine + j][i] = "    ";
                    commonTableAndMissions[yLine + j][i+1] = blackSquare;
                    commonTableAndMissions[yLine + j][i+2] = "   " + blackSquare + "   ";
                    commonTableAndMissions[yLine + j][i+3] = blackSquare;
                    commonTableAndMissions[yLine + j][i+4] = "       ";
                }
            }
        }

        //insert vertical separator between drawable and missions
        for(int i=0; i<yDim; i++){
            commonTableAndMissions[i][xSeparator] = "||      ";
        }

        //insert common mission cards (vertical)
        printer.addPrintable(missions, commonTableAndMissions, commonTable[0].length + 1, 0);

        return commonTableAndMissions;
    }
    public String[][] printableCommonMissions() {
        SimplifiedCommonTable miniCT = miniModel.getSimplifiedCommonTable();
        //utils
        Printer printer = new Printer();
        //calculate dimensions
        int yDim = (miniCT.getCommonMissions().get(0).getFront().printableSide().length+ 1)*2 + 1;
        int xDim = miniCT.getCommonMissions().get(0).getFront().printableSide()[0].length;
        int y;
        //initialize empty matrix
        String[][] missions = new String[yDim][xDim];
        for(int i=0; i<yDim; i++){
            for(int j=0; j<xDim; j++){
                missions[i][j] = "";
            }
        }

        //titles
        missions[0][0] = "Common Mission 0           " ;
        missions[yDim/2 + 1][0] = "Common Mission 1           " ;

        //add printable cards vertically
        y = 1;
        for(Card m: miniCT.getCommonMissions()){
            printer.addPrintable(m.getFront().printableSide(), missions, 0, y);
            y += m.getFront().printableSide().length + 2;
        }

        return missions;
    }

    //PERSONAL BOARD
    public String[][] printablePersonalBoard(SimplifiedPersonalBoard miniPB) {
        int xDim = (miniPB.getxMax() - miniPB.getxMin())*2 + 3;
        int yDim = (miniPB.getyMax() - miniPB.getyMin())*2 + 3;
        int xOff = miniPB.getxMin()*2 -1;
        int yOff = miniPB.getyMin()*2 -1;
        String[][] board = new String[yDim][xDim];
        String[][] reverseBoard = new String[yDim+1][xDim];

        //utils
        String blackSquare = SpecialCharacters.SQUARE_BLACK.getCharacter();
        String verticalLine = SpecialCharacters.WHITE_VERTICAL_STRING.getCharacter();
        String blocked =  SpecialCharacters.BLOCKED_POSITION.getCharacter();
        String background = SpecialCharacters.BACKGROUND_BLANK_WIDE.getCharacter();
        String playableSeparator = SpecialCharacters.ORANGE_DIAMOND.getCharacter();
        String styleReset = TextStyle.STYLE_RESET.getStyleCode();
        String selectedStyle = TextStyle.BACKGROUND_BEIGE.getStyleCode() + TextStyle.BLACK.getStyleCode();

        //initialize empty board
        for(int j=0; j<yDim; j++) {
            for(int i=0; i<xDim; i+=2){
                board[j][i] = blackSquare;
            }
            for(int i=1; i<xDim; i+=2){
                board[j][i]= background + blackSquare + background;
            }
        }

        //mark playable positions
        for(Point p: miniPB.getPlayablePositions()) {
            int x = p.getX()*2 - xOff;
            int y = p.getY()*2 - yOff;

            String selected = "";

            if(miniPB.getSelectedX() == p.getX() && miniPB.getSelectedY() == p.getY()){
                selected = selectedStyle;
            }

            //x dimension with alignment handling
            if(p.getX() <= -10){
                board[y][x] = p.getX() + playableSeparator ;
            } else if (p.getX() < 0 || p.getX() >= 10){
                board[y][x] = " " + p.getX() + playableSeparator ;
            } else {
                board[y][x] = "  " + p.getX() + playableSeparator ;
            }

            //y dimension with alignment handling
            if(p.getY() <= -10){
                board[y][x] = selected + board[y][x] + p.getY() ;
            } else if (p.getY() < 0 || p.getY() >= 10){
                board[y][x] = selected + board[y][x] + p.getY() + " " ;
            } else {
                board[y][x] = selected + board[y][x] + p.getY() + "  " ;
            }

            board[y+1][x] =  "‾‾‾" + blackSquare + "‾‾‾";
            board[y-1][x] =  "___" + blackSquare + "___";
            board[y-1][x-1] =  blackSquare;
            board[y][x-1] = verticalLine;
            board[y+1][x-1] =  blackSquare;
            board[y][x+1] = verticalLine;
            board[y-1][x+1] = blackSquare;
            board[y+1][x+1] = blackSquare;
        }

        //mark blocked positions
        for(Point p: miniPB.getBlockedPositions()) {
            int x = p.getX()*2 - xOff;
            int y = p.getY()*2 - yOff;
            board[y][x] = background + blocked + background;
        }

        //represent played cards
        for(Point p: miniPB.getOccupiedPositions()) {
            String[][] s = p.getSide().printableSide();
            int j=0;
            for(int y=(p.getY())*2 -yOff +1; y>=(p.getY())*2 -yOff - 1; y--){
                int i=2;
                for(int x = p.getX()*2 - xOff +1; x >= (p.getX())*2 - xOff -1; x--){
                    board[y][x] = s[j][i];
                    i--;
                }
                j++;
            }
        }

        int y=0;
        //reverse the board
        for(int j=yDim-1; j>=0; j--){
            for(int i=0; i<xDim; i++) {
                reverseBoard[y][i] = board[j][i] + styleReset;
            }
            y++;
        }
        int x = 1;
        while(x<xDim){
            reverseBoard[yDim][x] = " ";
            x++;
        }

        reverseBoard[yDim][0] = "\nYour resources: ";
        for (Symbol s: Symbol.values()) {
            reverseBoard[yDim][1] = reverseBoard[yDim][1] + miniPB.getVisibleResources().get(s) + " " + s.name() + "   ";
        }
        return reverseBoard;
    }

    //PLAYER
    //TODO gestire la stampa del punteggio
    /*public String printableScore(PersonalBoard personalBoard) {
        int score = personalBoard.getScore();
        int i;
        StringBuilder s = new StringBuilder();
        String background = "▒";
        String fill = "█";

        if(pawnColor != null){
            s.append(pawnColor.getFontColor());
        }

        for (i=0; i<score; i++){
            s.append(fill);
        }
        while (i<29){
            s.append(background);
            i++;
        }

        s.append(TextStyle.STYLE_RESET.getStyleCode()).append(" ").append(score);

        return s.toString();
    }*/

    public String[][] printableHandAndMission() {
        SimplifiedPersonalBoard miniPB = miniModel.getOtherPersonalBoard();

        if(miniPB.getSecretMission() == null){
            return new String[0][0];
        }
        String[][] printableHand = printableHand();
        String[][] secretMission = miniPB.getSecretMission().getFront().printableSide();
        Printer printer = new Printer();

        int xDim = printableHand[0].length + secretMission[0].length + 1;
        int yDim = printableHand.length;
        int xSeparator = printableHand[0].length;

        String[][] handAndMission = new String[yDim][xDim];


        for(int i=0; i<handAndMission.length; i++){
            for(int j=0; j<handAndMission[0].length; j++){
                handAndMission[i][j] = " ";
            }
        }

        printer.addPrintable(printableHand, handAndMission, 0,0);
        for(int i=0; i<yDim-1; i++){
            handAndMission[i][xSeparator] = "||      ";
        }
        handAndMission[0][printableHand[0].length] = "        Your Secret Mission: ";
        printer.addPrintable(secretMission, handAndMission, printableHand[0].length + 1, 1);

        return handAndMission;
    }

    //HAND
    /**
     * Creates a String matrix with a printable representation of the hand
     */
    public String[][] printableHand() {
        SimplifiedHand miniHand = miniModel.getSimplifiedHand();

        if(miniHand.getCards().isEmpty()){
            String[][] hand = new String[1][1];
            hand[0][0] = "Your hand is empty, the cards will be distributed after the game setup.\n";
            return hand;
        } else {
            //utils
            String selectedStyle = TextStyle.BACKGROUND_BEIGE.getStyleCode() + TextStyle.BLACK.getStyleCode();
            Printer printer = new Printer();
            String blackSquare = SpecialCharacters.SQUARE_BLACK.getCharacter();

            if(miniHand.getCards().get(0) instanceof MissionCard){
                //MISSION HAND

                //calculate dimensions
                int xCardDim = miniHand.getCards().get(0).getFront().printableSide()[0].length;
                int yCardDim = miniHand.getCards().get(0).getFront().printableSide().length;
                int xMax = (xCardDim + 1) * 2;
                int yMax = yCardDim + 1;
                //cursors
                int y=0, x=0;
                //initialize matrix
                String[][] myHand = new String[yMax][xMax];
                for(int j=0; j<yMax; j++){
                    for(int i=0; i<xMax; i++){
                        myHand[j][i] = " ";
                    }
                }
                //titles
                for(int i=0; i<2; i++) {
                    if(miniHand.getSelectedCard() == miniHand.getCards().get(i)){
                        myHand[y][x] = " " + selectedStyle + " Mission  " + i + " \u001B[0m  ";
                    } else {
                        myHand[y][x] = "  Mission " + i + "   ";
                    }
                    x++;
                }
                x = 0;
                y++;
                //printable cards
                for(Card c: miniHand.getCards()){
                    printer.addPrintable(c.getFront().printableSide(), myHand, x, y);
                    x += xCardDim;
                    for(int j=0; j<yCardDim; j++){
                        myHand[y+j][x] = "        ";
                    }
                }

                return myHand;

            } else {
                //NORMAL HAND

                //calculate dimensions
                int xCardDim = miniHand.getCards().get(0).getFront().printableSide()[0].length;
                int yCardDim = miniHand.getCards().get(0).getFront().printableSide().length;
                int xMax = (xCardDim + 1) * 3 + 1;
                int yMax = yCardDim + 4;

                //index to select the card
                int index = 0;

                //initialize empty matrix
                String[][] myHand = new String[yMax][xMax];
                for(int j=0; j<yMax; j++){
                    for(int i=0; i<xMax; i++){
                        myHand[j][i] = " ";
                    }
                }

                //titles
                myHand[0][0] = "Card:      ";
                for(int i=1; i<4; i++){
                    myHand[i][0] = "            ";
                }
                myHand[4][0] = "Type:     ";
                myHand[5][0] = "Points:   ";
                myHand[6][0] = "Requires: ";

                for(int i=0; i<3; i++) {
                    int x = 1 + i*(xCardDim+1);
                    int y = 0;
                    if (i < miniHand.getCards().size()) {
                        Card c = miniHand.getCards().get(i);

                        //titles
                        if (c == miniHand.getSelectedCard()) {
                            if (miniHand.getSelectedSide() == c.getBack()) {
                                myHand[y][x] =  index + ") " + selectedStyle + " Back  \u001B[0m   ";
                            } else {
                                myHand[y][x] = index + ") " + selectedStyle + " Front \u001B[0m   ";
                            }
                        } else {
                            myHand[y][x] = index + ")" + "  Front    ";
                        }
                        myHand[y][x] = myHand[y][x] + blackSquare + blackSquare + blackSquare;
                        y++;

                        //printable side
                        if (c == miniHand.getSelectedCard() && miniHand.getSelectedSide().equals(c.getBack())) {
                            printer.addPrintable(c.getBack().printableSide(), myHand, x, y);
                        } else {
                            printer.addPrintable(c.getFront().printableSide(), myHand, x, y);
                        }
                        x += xCardDim;
                        for (int j = 0; j < yCardDim; j++) {
                            myHand[y + j][x] = "          ";
                        }
                        y += yCardDim;

                        //card type
                        if (c instanceof GoldCard) {
                            myHand[y][x] = "Gold         ";
                        } else if (c instanceof ResourceCard) {
                            myHand[y][x] = "Resource     ";
                        } else if (c instanceof StarterCard) {
                            myHand[y][x] = "Starter      ";
                        }
                        myHand[y][x] = myHand[y][x] + blackSquare + blackSquare + blackSquare;
                        y++;

                        //points
                        if (!(c == miniHand.getSelectedCard() && miniHand.getSelectedSide().equals(c.getBack()))) {
                            switch (c.getFront()) {
                                case CornerCounter cornerCounter -> myHand[y][x] = "2 pt " + "x" + Character.toString(0x2B1C);
                                case InkwellCounter inkwellCounter -> myHand[y][x] = "1 pt " + "x" + Symbol.INKWELL.getAlias();
                                case ManuscriptCounter manuscriptCounter -> myHand[y][x] = "1 pt " + "x" + Symbol.MANUSCRIPT.getAlias();
                                case QuillCounter quillCounter -> myHand[y][x] = "1 pt " + "x" + Symbol.QUILL.getAlias();
                                case null, default -> myHand[y][x] = c.getFront().getPoints() + " pt " + "        ";
                            }
                            if (c.getFront() instanceof CornerCounter || c.getFront() instanceof InkwellCounter || c.getFront() instanceof ManuscriptCounter || c.getFront() instanceof QuillCounter) {
                                myHand[y][x] = myHand[y][x] + "       " + blackSquare + blackSquare;
                            } else {
                                myHand[y][x] = myHand[y][x] + blackSquare + blackSquare + blackSquare;
                            }

                        } else {
                            myHand[y][x] = "0 pt         " + blackSquare + blackSquare + blackSquare;
                        }
                        y++;

                        //requirements
                        myHand[y][x] = "";
                        int n;
                        int spaces = 5;

                        if(!(c == miniHand.getSelectedCard() && miniHand.getSelectedSide().equals(c.getBack()))){
                            for(Symbol s: c.getFront().getRequestedResources().keySet()){
                                n = c.getFront().getRequestedResources().get(s);
                                for(int j=0; j<n; j++){
                                    myHand[y][x] = myHand[y][x] + s.getAlias();
                                }
                                spaces = spaces - n;
                            }
                        }
                        while (spaces>0){
                            myHand[y][x] = myHand[y][x] +  blackSquare;
                            spaces--;
                        }
                        myHand[y][x] = myHand[y][x] + "      "+ blackSquare ;

                    } else if(!(!miniHand.getCards().isEmpty() && miniHand.getCards().get(0) instanceof StarterCard)) {
                        //EMPTY CARDS
                        myHand[y][x] = "            ";
                        y++;
                        //add empty printable
                        printer.addPrintable(printer.emptyPrintable(xCardDim, yCardDim), myHand, x, y);
                        x += xCardDim;
                        for (int j = 0; j < yCardDim; j++) {
                            myHand[y + j][x] = "        ";
                        }
                        y += yCardDim;

                        //type
                        myHand[y][x] = "           " + blackSquare + blackSquare + blackSquare;
                        y++;

                        //points
                        myHand[y][x] = "           " + blackSquare + blackSquare + blackSquare;
                        y++;

                        //requirements
                        myHand[y][x] = blackSquare + blackSquare + blackSquare + blackSquare + blackSquare + "    "+ blackSquare;
                    }
                    index++;
                }

                //align right border
                for(int i=yCardDim+1; i<yMax; i++){
                    myHand[i][xMax-1] = myHand[i][xMax-1] + "  ";
                }

                return myHand;
            }
        }
    }

    private void addPrintable(String[][] printable, String[][] context, int xBase, int yBase){
        int y=0, x;

        for(String[] row: printable){
            x=0;
            for(String col: row){
                context[yBase + y][xBase + x] = col;
                x++;
            }
            y++;
        }
    }

    private void showPrintable(String[][] printable){
        for (String[] row: printable) {
            for (String col: row) {
                System.out.print(col);
            }
            System.out.print("\n");
        }
    }

    private String[][] emptyPrintable(int xCardDim, int yCardDim){
        String[][] s = new String[yCardDim][xCardDim];

        String decoration = SpecialCharacters.SQUARE_BLACK.getCharacter();
        String backgroundSymbol = SpecialCharacters.SQUARE_BLACK.getCharacter();
        String blank = SpecialCharacters.BACKGROUND_BLANK_WIDE.getCharacter();
        String backgroundColor = TextStyle.BACKGROUND_BLACK.getStyleCode();
        String reset = TextStyle.STYLE_RESET.getStyleCode();

        //corners
        s[0][0] = backgroundSymbol;
        s[0][xCardDim - 1] = backgroundSymbol;
        s[yCardDim - 1][0] = backgroundSymbol;
        s[yCardDim - 1][xCardDim - 1] = backgroundSymbol;

        //decoration
        s[0][xCardDim/2] = blank + decoration  + blank;
        s[yCardDim/2][xCardDim/2] = decoration + decoration + decoration;
        s[yCardDim - 1][xCardDim/2] = blank + decoration  + blank;

        //rest of the card
        for(int i=0; i<yCardDim; i++){
            for(int j=0; j<xCardDim; j++){
                if(s[i][j] == null){
                    s[i][j] = blank;
                }
                s[i][j] = backgroundColor + s[i][j] + reset;
            }
        }

        return s;
    }

}
