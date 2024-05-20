package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.Printer;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.utils.SpecialCharacters;
import it.polimi.ingsw.gc26.model.utils.TextStyle;

import java.io.Serializable;
import java.util.ArrayList;

public class SimplifiedCommonTable implements Serializable {
    /**
     * This attribute represents the resource cards deck
     */
    private Card resourceDeck;
    /**
     * This attribute represents the gold cards deck
     */
    private Card goldDeck;
    /**
     * This attribute represents the two common mission that every player has
     */
    private ArrayList<Card> commonMissions;
    /**
     * This attribute represents the two resource cards that are visible on the table
     */
    private ArrayList<Card> resourceCards;
    /**
     * This attribute represents the two gold cards that are visible on the table
     */
    private ArrayList<Card> goldCards;

    public SimplifiedCommonTable(Card resourceDeck, Card goldDeck, ArrayList<Card> commonMissions, ArrayList<Card> resourceCards, ArrayList<Card> goldCards) {
        this.resourceDeck = resourceDeck;
        this.goldDeck = goldDeck;
        this.commonMissions = commonMissions;
        this.resourceCards = resourceCards;
        this.goldCards = goldCards;
    }

    public Card getResourceDeck() {
        return resourceDeck;
    }

    public Card getGoldDeck() {
        return goldDeck;
    }

    public ArrayList<Card> getCommonMissions() {
        return commonMissions;
    }

    public ArrayList<Card> getResourceCards() {
        return resourceCards;
    }

    public ArrayList<Card> getGoldCards() {
        return goldCards;
    }

    private void decorateDeck(String[][] ct, int xDeck, int yDeck, int xCardDim, int yCardDim){
        //ct[yDeck + yCardDim][xDeck] = "▔▔▔▔▔▔▔▔▔▔▔▔▔";
        xDeck += xCardDim;
        for(int yOff=0; yOff<yCardDim; yOff++){
            ct[yDeck + yOff][xDeck] = "║      ";
        }
        //ct[yResource + yCardDim][0] = "╚═" + whiteSquare + "══" + whiteSquare + "══" + whiteSquare + "═╝";
    }

    public String[][] printableCommonTable() {
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
            if(i<resourceCards.size()){
                Card r = resourceCards.get(i);
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

        if(resourceDeck == null){
            printer.addPrintable(printer.emptyPrintable(xCardDim,yCardDim), ct, xResource, yResource);
        } else {
            printer.addPrintable(resourceDeck.getBack().printableSide(), ct, xResource, yResource);
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

            if(i < goldCards.size()){
                Card g = goldCards.get(i);
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

        if(goldDeck == null){
            printer.addPrintable(printer.emptyPrintable(xCardDim, yCardDim), ct, xGold, yGold);
        } else {
            printer.addPrintable(goldDeck.getBack().printableSide(), ct, xGold, yGold);
            decorateDeck(ct, xGold, yGold, xCardDim, yCardDim);
        }

        return ct;
    }

    public String[][] printableCommonTableAndMissions(){
        //get printable components
        String[][] commonTable = printableCommonTable();
        String[][] missions = printableCommonMissionsVertical();
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

    public String[][] printableCommonMissionsHorizontal() {
        //utils
        Printer printer = new Printer();
        //dimensions
        int yDim = commonMissions.get(0).getFront().printableSide().length + 1;
        int xDim = (commonMissions.get(0).getFront().printableSide()[0].length)*2 + 1;
        //initialize empty matrix
        String[][] missions = new String[yDim][xDim];
        for(int i=0; i<yDim; i++){
            for(int j=0; j<xDim; j++){
                missions[i][j] = "";
            }
        }

        //titles;
        missions[0][0] = "Common Mission 0             " ;
        missions[0][1] = "Common Mission 1             " ;

        //insert printable cards horizontally
        int x = 0;
        for(Card m: commonMissions){
            printer.addPrintable(m.getFront().printableSide(), missions, x, 1);
            if (commonMissions.indexOf(m) == 0){
                x += m.getFront().printableSide()[0].length;
                for(int i=1; i<(yDim); i++){
                    missions[i][x] = "        ";
                }
                x++;
            }
        }

        return missions;
    }

    public String[][] printableCommonMissionsVertical() {
        //utils
        Printer printer = new Printer();
        //calculate dimensions
        int yDim = (commonMissions.get(0).getFront().printableSide().length+ 1)*2 + 1;
        int xDim = commonMissions.get(0).getFront().printableSide()[0].length;
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
        for(Card m: commonMissions){
            printer.addPrintable(m.getFront().printableSide(), missions, 0, y);
            y += m.getFront().printableSide().length + 2;
        }

        return missions;
    }
}
