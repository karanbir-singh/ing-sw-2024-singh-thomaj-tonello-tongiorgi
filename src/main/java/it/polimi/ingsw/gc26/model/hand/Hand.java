package it.polimi.ingsw.gc26.model.hand;
import it.polimi.ingsw.gc26.model.ModelObservable;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card.GoldCard;
import it.polimi.ingsw.gc26.model.card.ResourceCard;
import it.polimi.ingsw.gc26.model.card_side.Side;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.card_side.ability.CornerCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.InkwellCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.ManuscriptCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.QuillCounter;

import java.io.Serializable;
import java.rmi.RemoteException;
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

    private ModelObservable modelObservable;

    /**
     * Initializes the hand for the player
     * @param c new cards in hand
     */
    public Hand(ArrayList<Card> c) {
        this.modelObservable = ModelObservable.getInstance();
        this.cards = c;
        this.selectedCard = null;
        this.selectedSide = null;
    }

    /**
     * Returns an optional with the selected card if present
     * @return selected card
     */
    public Optional<Card> getSelectedCard() {
        return Optional.ofNullable(this.selectedCard);
    }

    /**
     * Sets the selected card to the parameter given
     * @param selectedCard new selected card
     */
    public void setSelectedCard(Card selectedCard, String clientID) {
        if (selectedCard != null) {
            this.selectedCard = selectedCard;
            this.selectedSide = selectedCard.getFront();
            // TODO notify view

            try {
                this.modelObservable.notifySelectedCardFromHand(clientID);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        } else {
            // TODO notify
            ModelObservable.getInstance().notifyError("Select a card first!", clientID);
        }
    }

    /**
     * Returns the selected side of the selected card
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
            ModelObservable.getInstance().notifyMessage("You have turned a card", clientID);
        } else {
            // TODO lancia eccezione di carta non selezionata
            ModelObservable.getInstance().notifyError("Select a card first!", clientID);
        }

    }

    /**
     * Removes the card given as parameter from the hand
     * @param card card to be removed
     */
    public void removeCard(Card card) {
        cards.remove(card);
        selectedSide = null;
        selectedCard = null;
    }

    /**
     * Adds the card given as a parameter in the hand
     * @param card new card in hand
     */
    public void addCard(Card card, String clientID) {
        cards.add(card);
        this.modelObservable.notifyMessage("Added a card to the hand", clientID);

    }

    /**
     * Returns all the card in the hand
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
            System.out.println("carta selezionata");
            this.modelObservable.notifyMessage("Card selected at index: " + cardIndex, clientID);
            return cards.get(cardIndex);
        }
        // TODO notify view
        ModelObservable.getInstance().notifyError("Invalid position!", clientID);
        return null;
    }


    /**
     * Creates a String matrix with a printable representation of the hand
     */
    public void showHand(){
        int xMax = (cards.size()+1) * 3 +2;
        int yMax = 8;

        String[][] myHand = new String[yMax][xMax];
        int y=0, x;

        for(int j=0; j<yMax; j++){
            for(int i=0; i<xMax; i++){
                myHand[j][i] = "";
            }
        }

        myHand[0][0] = "Card:     ";
        for(int i=1; i<4; i++){
            myHand[i][0] = "          ";
        }
        myHand[4][0] = "Type:     ";
        myHand[5][0] = "Points:   ";
        myHand[6][0] = "Requires: ";

        x=1;

        for(Card c: cards) {
            if(c == this.selectedCard){
                if(this.selectedSide == c.getBack()){
                    myHand[y][x] = "   \u001B[46m \u001B[30mBack  " + cards.indexOf(c) + " \u001B[0m  ";
                } else {
                    myHand[y][x] = "   \u001B[46m \u001B[30mFront " + cards.indexOf(c) + " \u001B[0m  ";
                }
            } else {
                myHand[y][x] = "   Front " + cards.indexOf(c) + "   ";
            }
            if(c != cards.getLast()){
                myHand[y][x] = myHand[y][x]  + "▪\uFE0F"+"▪\uFE0F"+"▪\uFE0F";
            }
            x++;
        }

        myHand[y][x] = "\n";
        y++;
        x = 1;

        for (int j = 0; j < 3; j++) {
            for(Card c: cards) {
                for (int i = 0; i < 3; i++) {
                    if(c == this.selectedCard && this.selectedSide.equals(c.getBack())){
                        myHand[y][x] = c.getBack().printableSide()[j][i];
                    } else {
                        myHand[y][x] = c.getFront().printableSide()[j][i];
                    }
                    x++;
                }
                myHand[y][x] = "        ";
                x++;
            }
            myHand[y][x] = "\n";
            y++;
            x = 1;
        }

        for(Card c: cards) {
            if(c instanceof GoldCard){
                myHand[y][x] = " Gold         ";
            } else if(c instanceof ResourceCard){
                myHand[y][x] = " Resource     ";
            }
            if(c != cards.getLast()){
                myHand[y][x] = myHand[y][x] + "▪\uFE0F"+"▪\uFE0F"+"▪\uFE0F";
            }
            x++;
        }

        myHand[y][x] = "\n";
        y++;
        x = 1;

        for(Card c: cards) {
            if(!(c == this.selectedCard && this.selectedSide.equals(c.getBack()))){
                switch (c.getFront()) {
                    case CornerCounter cornerCounter -> myHand[y][x] = " 2 pt " + "x" + Character.toString(0x2B1C);
                    case InkwellCounter inkwellCounter -> myHand[y][x] =  " 1 pt " + "x" + Symbol.INKWELL.getAlias();
                    case ManuscriptCounter manuscriptCounter -> myHand[y][x] = " 1 pt " + "x" + Symbol.MANUSCRIPT.getAlias();
                    case QuillCounter quillCounter -> myHand[y][x] = " 1 pt " + "x" + Symbol.QUILL.getAlias();
                    case null, default -> myHand[y][x] = " " + c.getFront().getPoints() + " pt " + "        ";
                }
                if(c != cards.getLast()){
                    if(c.getFront() instanceof CornerCounter || c.getFront() instanceof InkwellCounter || c.getFront() instanceof ManuscriptCounter || c.getFront() instanceof QuillCounter){
                        myHand[y][x] = myHand[y][x] + "       " + "▪\uFE0F"+"▪\uFE0F";
                    } else {
                        myHand[y][x] = myHand[y][x] + "▪\uFE0F"+"▪\uFE0F"+"▪\uFE0F";
                    }
                }
            } else {
                myHand[y][x] = " 0 pt         ";
                if(c != cards.getLast()){
                    myHand[y][x] = myHand[y][x] + "▪\uFE0F"+"▪\uFE0F"+"▪\uFE0F";
                }
            }


            x++;
        }

        myHand[y][x] = "\n";
        y++;
        x = 1;

        for(Card c: cards) {
            myHand[y][x] = " ";
            int n;
            int spaces = 5;

            if(!(c == this.selectedCard && this.selectedSide.equals(c.getBack()))){
                for(Symbol s: c.getFront().getRequestedResources().keySet()){
                    n = c.getFront().getRequestedResources().get(s);
                    for(int i=0; i<n; i++){
                        myHand[y][x] = myHand[y][x] + s.getAlias();
                    }
                    spaces = spaces - n;
                }
            }

            while (spaces>0){
                myHand[y][x] = myHand[y][x] + "▪\uFE0F";
                spaces--;
            }


            if(c != cards.getLast()){
                myHand[y][x] = myHand[y][x] + "      "+"▪\uFE0F" ;
            }
            x++;
        }

        for(y=0; y<yMax; y++){
            for(x=0; x<xMax; x++){
                System.out.print(myHand[y][x]);
            }
        }

        System.out.print("\n");
    }
}
