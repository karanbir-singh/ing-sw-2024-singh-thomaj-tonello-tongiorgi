package it.polimi.ingsw.gc26.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.gc26.model.ModelObservable;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card.GoldCard;
import it.polimi.ingsw.gc26.model.deck.Deck;
import it.polimi.ingsw.gc26.model.utils.SpecialCharacters;
import it.polimi.ingsw.gc26.model.utils.TextStyle;
import it.polimi.ingsw.gc26.view_model.SimplifiedCommonTable;
import it.polimi.ingsw.gc26.Printer;

import java.io.Serializable;
import java.util.*;

/**
 * This class represents a table containing decks every player can interact with
 */
public class CommonTable implements Serializable {
    /**
     * This attribute represents the resource cards deck
     */
    private final Deck resourceDeck;
    /**
     * This attribute represents the gold cards deck
     */
    private final Deck goldDeck;
    /**
     * This attribute represents the initial cards decl
     */
    private final Deck starterDeck;
    /**
     * This attribute represents the mission cards deck
     */
    private final Deck missionDeck;
    /**
     * This attribute represents the two common mission that every player has
     */
    private final ArrayList<Card> commonMissions;
    /**
     * This attribute represents the two resource cards that are visible on the table
     */
    private final ArrayList<Card> resourceCards;
    /**
     * This attribute represents the two gold cards that are visible on the table
     */
    private final ArrayList<Card> goldCards;
    /**
     * Coordinate X of the selected card on the table
     */
    private int selectedX;
    /**
     * Coordinate Y of the selected card on the table
     */
    private int selectedY;

    /**
     * Observable to notify client
     */
    private ModelObservable observable;

    /**
     * Initializes the common table with the decks
     *
     * @param resourceDeck resources cards deck
     * @param goldDeck     gold cards deck
     * @param starterDeck  initial cards deck
     * @param missionDeck  mission cards deck
     */
    public CommonTable(Deck resourceDeck, Deck goldDeck, Deck starterDeck, Deck missionDeck, ModelObservable observable) {
        commonMissions = new ArrayList<>();
        resourceCards = new ArrayList<>();
        goldCards = new ArrayList<>();
        this.resourceDeck = resourceDeck;
        this.goldDeck = goldDeck;
        this.starterDeck = starterDeck;
        this.missionDeck = missionDeck;
        selectedX = -1;
        selectedY = -1;
        this.observable = observable;
    }

    /**
     * Sets the attribute selectedX and selectedY of the chosen card to select
     *
     * @param cardIndex index of the selected card on the common table
     */
    public void selectCard(int cardIndex, String clientID) {
        // Check if the card index are correct
        if (cardIndex < 0 || cardIndex > 6) {
            this.observable.notifyError("Position not valid!", clientID);
            return;
        }

        switch (cardIndex) {
            case 0:
                selectedX = 0;
                selectedY = 0;
                break;
            case 1:
                selectedX = 1;
                selectedY = 0;
                break;
            case 2:
                selectedX = 2;
                selectedY = 0;
                break;
            case 3:
                selectedX = 0;
                selectedY = 1;
                break;
            case 4:
                selectedX = 1;
                selectedY = 1;
                break;
            case 5:
                selectedX = 2;
                selectedY = 1;
                break;
            default:
                this.observable.notifyError("Select a position!", clientID);
                return;
        }
        this.observable.notifyMessage("Card selected on common table", clientID);
        // TODO replace notify with update Common Table
    }

    /**
     * Adds the card to the given list and given index
     *
     * @param card  new card to be added
     * @param list  list where the card will be added
     * @param index position where the card will be added
     */
    public void addCard(Card card, ArrayList<Card> list, int index) {
        list.add(index, card);
    }

    /**
     * Removes the card on the given index from the list and replaced by a card from the given deck
     *
     * @param list  list of cards
     * @param index position of the card to remove
     * @param deck  deck containing the card that replaces the old one
     * @return removed card
     */
    private Card removeFromTable(ArrayList<Card> list, int index, Deck deck, String clientID) {
        Card toRemove = null;
        if (list.get(index) != null) {
            if (!deck.getCards().isEmpty())
                toRemove = list.set(index, deck.removeCard());
            else
                toRemove = list.set(index, null);
        } else {
            // TODO gestire quando la posizione selezionata non contiene una carta
            this.observable.notifyError("Position not valid!", clientID);
        }
        return toRemove;
    }

    /**
     * Removes the selected card from the table and returns it
     *
     * @return removed card
     */
    public Card removeSelectedCard(String clientID) {
        if (getSelectedCard().isPresent()) {
            Card toRemove = null;
            if (selectedY == 0) {
                if (selectedX == 2) {
                    if (!resourceDeck.getCards().isEmpty())
                        toRemove = resourceDeck.removeCard();
                    else {
                        // TODO gestire quando il mazzo è finito
                    }
                } else {
                    toRemove = removeFromTable(resourceCards, selectedX, resourceDeck, clientID);
                }
            } else if (selectedY == 1) {
                if (selectedX == 2) {
                    if (!goldDeck.getCards().isEmpty())
                        toRemove = goldDeck.removeCard();
                    else {
                        // TODO gestire quando il mazzo è finito
                    }
                } else {
                    toRemove = removeFromTable(goldCards, selectedX, goldDeck, clientID);
                }
            }
            selectedX = -1;
            selectedY = -1;

            this.observable.notifyUpdateCommonTable(
                    new SimplifiedCommonTable(
                            resourceDeck.getTopCard(),
                            goldDeck.getTopCard(),
                            commonMissions,
                            resourceCards,
                            goldCards),
                    "Card removed from common table"
            );

            return toRemove;
        } else {
            // TODO notify view
            this.observable.notifyError("Select a position first!", clientID);
            return null;
        }

    }

    /**
     * Returns the card selected by the player
     *
     * @return selected card
     */
    public Optional<Card> getSelectedCard() {
        Card selectedCard = null;
        if (selectedY == 0) {
            if (selectedX == 0) {
                selectedCard = resourceCards.getFirst();
            } else if (selectedX == 1) {
                selectedCard = resourceCards.get(1);
            } else if (selectedX == 2) {
                selectedCard = resourceDeck.getTopCard();
            }
        } else if (selectedY == 1) {
            if (selectedX == 0) {
                selectedCard = goldCards.getFirst();
            } else if (selectedX == 1) {
                selectedCard = goldCards.get(1);
            } else if (selectedX == 2) {
                selectedCard = goldDeck.getTopCard();
            }
        }
        return Optional.ofNullable(selectedCard);
    }

    /**
     * Returns the Resources cards visible
     *
     * @return resource cards visible
     */
    public ArrayList<Card> getResourceCards() {
        return resourceCards;
    }

    /**
     * Returns the Gold cards visible
     *
     * @return gold cards visible
     */
    public ArrayList<Card> getGoldCards() {
        return goldCards;
    }

    /**
     * Return the common missions
     *
     * @return common mission
     */
    public ArrayList<Card> getCommonMissions() {
        return commonMissions;
    }

    /**
     * Returns the resource cards deck
     *
     * @return resource cards deck
     */
    public Deck getResourceDeck() {
        return resourceDeck;
    }

    /**
     * Returns the gold cards deck
     *
     * @return gold cards deck
     */
    public Deck getGoldDeck() {
        return goldDeck;
    }

    /**
     * Returns the initial cards deck
     *
     * @return initial cards deck
     */
    public Deck getStarterDeck() {
        return starterDeck;
    }

    /**
     * Returns the mission cards deck
     *
     * @return mission cards deck
     */
    public Deck getMissionDeck() {
        return missionDeck;
    }

    /**
     * Returns the selected card's X coordinate
     *
     * @return selectedX
     */
    public int getSelectedX() {
        return selectedX;
    }

    /**
     * Returns the selected card's Y coordinate
     *
     * @return selectedY
     */
    public int getSelectedY() {
        return selectedY;
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

        if(resourceDeck.getTopCard() == null){
            printer.addPrintable(printer.emptyPrintable(xCardDim,yCardDim), ct, xResource, yResource);
        } else {
            printer.addPrintable(resourceDeck.printableDeck(), ct, xResource, yResource);
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

        if(goldDeck.getTopCard() == null){
            printer.addPrintable(printer.emptyPrintable(xCardDim, yCardDim), ct, xGold, yGold);
        } else {
            printer.addPrintable(goldDeck.printableDeck(), ct, xGold, yGold);
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
