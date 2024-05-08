package it.polimi.ingsw.gc26.model.game;

import com.fasterxml.jackson.annotation.JsonCreator;
import it.polimi.ingsw.gc26.model.ModelObservable;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card.GoldCard;
import it.polimi.ingsw.gc26.model.deck.Deck;
import it.polimi.ingsw.gc26.model.utils.SpecialCharacters;
import it.polimi.ingsw.gc26.model.utils.TextStyle;

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
     * Initializes the common table with the decks
     *
     * @param resourceDeck resources cards deck
     * @param goldDeck     gold cards deck
     * @param starterDeck  initial cards deck
     * @param missionDeck  mission cards deck
     */
    public CommonTable(Deck resourceDeck, Deck goldDeck, Deck starterDeck, Deck missionDeck) {
        commonMissions = new ArrayList<>();
        resourceCards = new ArrayList<>();
        goldCards = new ArrayList<>();
        this.resourceDeck = resourceDeck;
        this.goldDeck = goldDeck;
        this.starterDeck = starterDeck;
        this.missionDeck = missionDeck;
        selectedX = -1;
        selectedY = -1;
    }

    /**
     * Sets the attribute selectedX and selectedY of the chosen card to select
     *
     * @param selectedX coordinate X of the selected card
     * @param selectedY coordinate Y of the selected card
     */
    public void selectCard(int selectedX, int selectedY, String clientID) {
        // Check if the selectedX and selectedY are correct
        if (selectedY >= 0 && selectedY < 2) {
            if (selectedX >= 0 && selectedX < 3) {
                this.selectedX = selectedX;
                this.selectedY = selectedY;
            } else {
                // TODO gestire quando la posizione X non è corretta
                ModelObservable.getInstance().notifyError("Position X not valid!", clientID);
            }
        } else {
            ModelObservable.getInstance().notifyError("Position Y not valid!", clientID);
            // TODO gestire quando la posizione Y non è corretta
        }
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
            ModelObservable.getInstance().notifyError("Position not valid!", clientID);
        }
        return toRemove;
    }

    /**
     * Removes the selected card from the table and returns it
     *
     * @return removed card
     */
    public Card removeSelectedCard(String clientID) {
        if(getSelectedCard().isPresent()){
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
            return toRemove;
        }else{
            // TODO notify view
            ModelObservable.getInstance().notifyError("Select a position first!", clientID);
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

    public String[][] emptyPrintable(int xCardDim, int yCardDim){
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

    private void decorateDeck(String[][] ct, int xDeck, int yDeck, int xCardDim, int yCardDim){
        //ct[yDeck + yCardDim][xDeck] = "▔▔▔▔▔▔▔▔▔▔▔▔▔";
        xDeck += xCardDim;
        for(int yOff=0; yOff<yCardDim; yOff++){
            ct[yDeck + yOff][xDeck] = "║";
        }
        //ct[yResource + yCardDim][0] = "╚═" + whiteSquare + "══" + whiteSquare + "══" + whiteSquare + "═╝";
    }

    public String[][] printableCommonTable(){
        int xCardDim = 3;
        int yCardDim = 3;
        int xResource = 1, yResource = 1;
        int xGold = 1, yGold = yResource + yCardDim + 2;
        int xMissionDim = 3;
        int yMissionDim = 5;
        int xMission1 = 0;
        int xMission2 = xMission1 + xMissionDim + 2;
        int yMission = yGold + yCardDim + 2;

        int xDim = 2*(xCardDim+2) + yMissionDim+1 +1;
        int yDim = 16;
        int index = 0;

        String blackSquare = SpecialCharacters.SQUARE_BLACK.getCharacter();
        String space = "    ";
                //SpecialCharacters.BACKGROUND_BLANK_MEDIUM.getCharacter();

        String[][] ct = new String[yDim][xDim];

        //initialize empty common table
        for(int i=0; i<yDim; i++){
            for(int j=0; j<xDim; j++){
                ct[i][j] = space;
            }
        }

        //insert uncovered resource cards
        for (int i=0; i<2; i++) {
            Card r = resourceCards.get(i);
            ct[yResource-1][i] = "(" + index + ") Resource Card    ";

            if(r == null){
                addPrintable(emptyPrintable(xCardDim,yCardDim), ct, xResource, yResource);
            } else {
                addPrintable(r.getFront().printableSide(), ct, xResource, yResource);
            }

            xResource += xCardDim + 2;
            index++;
        }

        xResource ++;

        //insert resource deck
        ct[yResource-1][2] = "   (" + index + ") Resource Deck";
        if(resourceDeck.getTopCard() == null){
            addPrintable(emptyPrintable(xCardDim,yCardDim), ct, xResource, yResource);
        } else {
            addPrintable(resourceDeck.printableDeck(), ct, xResource, yResource);
            decorateDeck(ct, xResource, yResource, xCardDim, yCardDim);
        }

        index++;

        //insert uncovered gold cards
        for (int i=0; i<2; i++) {
            Card g = goldCards.get(i);
            ct[yGold-1][i] = "(" + index + ") Gold Card        ";

            if(g == null){
                addPrintable(emptyPrintable(xCardDim, yCardDim), ct, xGold, yGold);
            } else {
                addPrintable(g.getFront().printableSide(), ct, xGold, yGold);
            }
            xGold += xCardDim + 2;
            index++;
        }

        xGold ++;

        //insert gold deck
        ct[yGold-1][2] = "   (" + index + ") Gold Deck" ;

        if(goldDeck.getTopCard() == null){
            addPrintable(emptyPrintable(xCardDim, yCardDim), ct, xGold, yGold);
        } else {
            addPrintable(goldDeck.printableDeck(), ct, xGold, yGold);
            decorateDeck(ct, xGold, yGold, xCardDim, yCardDim);
        }

        //insert common mission cards
        ct[yMission-1][0] = "\nCommon Mission 0             " ;
        ct[yMission-1][1] = "Common Mission 1" ;
        addPrintable(commonMissions.get(0).getFront().printableSide(), ct, xMission1, yMission);
        addPrintable(commonMissions.get(1).getFront().printableSide(), ct, xMission2, yMission);


        return ct;
    }

}
