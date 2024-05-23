package it.polimi.ingsw.gc26.model.hand;

import it.polimi.ingsw.gc26.Printer;
import it.polimi.ingsw.gc26.model.ModelObservable;
import it.polimi.ingsw.gc26.model.card.*;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card.GoldCard;
import it.polimi.ingsw.gc26.model.card.MissionCard;
import it.polimi.ingsw.gc26.model.card.ResourceCard;
import it.polimi.ingsw.gc26.model.card_side.Side;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.card_side.ability.CornerCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.InkwellCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.ManuscriptCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.QuillCounter;
import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import it.polimi.ingsw.gc26.model.utils.SpecialCharacters;
import it.polimi.ingsw.gc26.model.utils.TextStyle;

import java.io.Serializable;
import java.util.*;

/**
 * This class represents the current hand for a player
 */
public class Hand implements Serializable {
    /**
     * This attribute represent the current cards in the hand
     */
    private final ArrayList<Card> cards;
    /**
     * This attribute represents the selected card to be played
     */
    private Card selectedCard;
    /**
     * This attribute represent the selected side of the selected card to be played
     */
    private Side selectedSide;

    /**
     * Observable to notify client
     */
    private ModelObservable observable;

    /**
     * Initializes the hand for the player
     *
     * @param cards new cards in hand
     */
    public Hand(ArrayList<Card> cards, ModelObservable observable) {
        this.observable = observable;
        this.cards = cards;
        this.selectedCard = null;
        this.selectedSide = null;
    }

    /**
     * Returns an optional with the selected card if present
     *
     * @return selected card
     */
    public Optional<Card> getSelectedCard() {
        return Optional.ofNullable(this.selectedCard);
    }

    /**
     * Sets the selected card to the parameter given
     *
     * @param selectedCard new selected card
     */
    public void setSelectedCard(Card selectedCard, String clientID) {
        if (selectedCard != null) {
            this.selectedCard = selectedCard;
            this.selectedSide = selectedCard.getFront();
            // TODO notify view

//            try {
//                this.modelObservable.notifySelectedCardFromHand(clientID);
                this.observable.notifyUpdateHand(
                        new SimplifiedHand(cards, selectedCard, selectedSide),
                        "Card selected on hand",
                        clientID
                );
//            } catch (RemoteException e) {
//                throw new RuntimeException(e);
//            }
        } else {
            // TODO notify
            this.observable.notifyError("Select a card first!", clientID);
        }
    }

    public void setSelectedCard(MissionCard selectedCard, String clientID) {
        if (selectedCard != null) {
            this.selectedCard = selectedCard;
            this.selectedSide = selectedCard.getFront();
            // TODO notify view
            this.observable.notifyUpdateSecretHand(
                    new SimplifiedHand(cards, selectedCard, selectedSide),
                    "Card selected on hand",
                    clientID
            );
        } else {
            // TODO notify
            this.observable.notifyError("Select a card first!", clientID);
        }
    }

    /**
     * Returns the selected side of the selected card
     *
     * @return selected side
     */
    public Optional<Side> getSelectedSide() {
        return Optional.ofNullable(this.selectedSide);
    }

    /**
     * Sets selected side to the opposite side if there is a selected card
     */
    public void turnSide(String clientID) {
        Optional<Card> selectedCard = Optional.ofNullable(this.selectedCard);
        if (selectedCard.isPresent()) {
            if (selectedCard.get().getFront().equals(selectedSide)) {
                this.selectedSide = selectedCard.get().getBack();
            } else {
                this.selectedSide = selectedCard.get().getFront();
            }
//            try {
//                ModelObservable.getInstance().notifyMessage("You have turned a card", clientID);
//            } catch (RemoteException e) {
//                throw new RuntimeException(e);
//            }
            this.observable.notifyUpdateHand(
                    new SimplifiedHand(cards, this.selectedCard, selectedSide),
                    "Turned side of selected card",
                    clientID
            );
        } else {
            // TODO lancia eccezione di carta non selezionata
            this.observable.notifyError("Select a card first!", clientID);
        }
    }

    /**
     * Removes the card given as parameter from the hand
     *
     * @param card card to be removed
     */
    public void removeCard(Card card, String clientID) {
        cards.remove(card);
        selectedSide = null;
        selectedCard = null;

        this.observable.notifyUpdateHand(
                new SimplifiedHand(cards, selectedCard, selectedSide),
                "Card removed from hand",
                clientID
        );
    }

    public void removeCard(MissionCard card, String clientID) {
        cards.remove(card);
        selectedSide = null;
        selectedCard = null;

        this.observable.notifyUpdateSecretHand(
                new SimplifiedHand(cards, selectedCard, selectedSide),
                "Card removed from hand",
                clientID
        );
    }

    /**
     * Adds the card given as a parameter in the hand
     *
     * @param card new card in hand
     */
    public void addCard(Card card, String clientID) {
        cards.add(card);
//        try {
//            this.modelObservable.notifyMessage("Added a card to the hand", clientID);

            this.observable.notifyUpdateHand(
                    new SimplifiedHand(cards, selectedCard, selectedSide),
                    "Card added to hand",
                    clientID
            );
//        } catch (RemoteException e) {
//            throw new RuntimeException(e);
//        }
    }

    /**
     *
     * @param card new card in hand
     */
    public void addCard(MissionCard card, String clientID) {
        cards.add(card);
        this.observable.notifyUpdateSecretHand(
                new SimplifiedHand(cards, selectedCard, selectedSide),
                "Card added to hand",
                clientID
        );
    }

    /**
     * Returns all the card in the hand
     *
     * @return cards
     */
    public ArrayList<Card> getCards() {
        return cards;
    }

    /**
     * Return the card in cardIndex position
     *
     * @param cardIndex index of the card
     * @return card in cards at position cardIndex
     */
    public Card getCard(int leftLimit, int rightLimit, int cardIndex, String clientID) {
        // Check if the given index is correct
        if (cardIndex >= leftLimit && cardIndex < rightLimit) {
//            try {
                this.observable.notifyMessage("Card selected at index: " + cardIndex, clientID);
//            } catch (RemoteException e) {
//                throw new RuntimeException(e);
//            }
            return cards.get(cardIndex);
        }
        // TODO notify view
        this.observable.notifyError("Invalid position!", clientID);
        return null;
    }


    /**
     * Creates a String matrix with a printable representation of the hand
     */
    public String[][] printableHand() {
        Printer printer = new Printer();

        if(cards.isEmpty()){
            String[][] hand = new String[1][1];
            hand[0][0] = "\t\n";
            return hand;
        } else {
            //utils
            String selectedStyle = TextStyle.BACKGROUND_BEIGE.getStyleCode() + TextStyle.BLACK.getStyleCode();
            String blackSquare = SpecialCharacters.SQUARE_BLACK.getCharacter();

            //calculate dimensions
            int xCardDim = cards.get(0).getFront().printableSide()[0].length;
            int yCardDim = cards.get(0).getFront().printableSide().length;
            int xMax = (xCardDim + 1) * 3 + 1;
            int yMax = yCardDim + 6;

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

            myHand[0][0] = "\nYOUR HAND:\n";
            myHand[1][0] = "Card:      ";
            for(int i=2; i<xCardDim + 2; i++){
                myHand[i][0] = "            ";
            }
            myHand[5][0] = "Type:     ";
            myHand[6][0] = "Points:   ";
            myHand[7][0] = "Requires: ";

            for(int i=0; i<3; i++) {
                int x = 1 + i*(xCardDim+1);
                int y = 1;
                if (i < cards.size()) {
                    Card c = cards.get(i);

                    //titles
                    if (c == selectedCard) {
                        if (selectedSide == c.getBack()) {
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
                    if (c == selectedCard && selectedSide.equals(c.getBack())) {
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
                    if (!(c == selectedCard && selectedSide.equals(c.getBack()))) {
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

                    if(!(c == selectedCard && selectedSide.equals(c.getBack()))){
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

                } else if(!(!cards.isEmpty() && cards.get(0) instanceof StarterCard)) {
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
