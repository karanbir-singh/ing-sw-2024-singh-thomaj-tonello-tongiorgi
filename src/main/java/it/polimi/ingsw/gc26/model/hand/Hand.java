package it.polimi.ingsw.gc26.model.hand;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card.GoldCard;
import it.polimi.ingsw.gc26.model.card.ResourceCard;
import it.polimi.ingsw.gc26.model.card_side.Side;
import it.polimi.ingsw.gc26.model.card_side.Symbol;

import java.util.*;

/**
 * This class represents the current hand for a player
 */
public class Hand {
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
     * Initializes the hand for the player
     * @param c new cards in hand
     */
    public Hand(ArrayList<Card> c){
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
    public void setSelectedCard(Card selectedCard){
        if(Optional.ofNullable(selectedCard).isPresent()){
            this.selectedCard = selectedCard;
            this.selectedSide = selectedCard.getFront();
        }
    }

    /**
     * Returns the selected side of the selected card
     * @return selected side
     */
    public Optional<Side> getSelectedSide(){
        return Optional.ofNullable(this.selectedSide);
    }

    /**
     * Sets selected side to the opposite side if there is a selected card
     */
    public void turnSide(){
        Optional<Card> selectedCard = Optional.ofNullable(this.selectedCard);
        if(selectedCard.isPresent()){
            if(selectedCard.get().getFront().equals(selectedSide)){
                this.selectedSide = selectedCard.get().getBack();
            } else {
                this.selectedSide = selectedCard.get().getFront();
            }
        }else{
            // TODO lancia eccezione di carta non selezionata
        }

    }

    /**
     * Removes the card given as parameter from the hand
     * @param card card to be removed
     */
    public void removeCard(Card card){
        cards.remove(card);
        selectedSide = null;
        selectedCard = null;
    }

    /**
     * Adds the card given as a parameter in the hand
     * @param card new card in hand
     */
    public void addCard(Card card){
        cards.add(card);
    }

    /**
     * Returns all the card in the hand
     * @return cards
     */
    public ArrayList<Card> getCards(){
        return cards;
    }

    public void showHand(){
        int xMax = (cards.size()+1) * 3 +2;
        int yMax = 8;
        String[][] myHand = new String[yMax][xMax];
        int y=0, x=0;

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
            myHand[y][x] = "   Front  " + cards.indexOf(c);
            if(c != cards.getLast()){
                myHand[y][x] = myHand[y][x] + "  " + "▪\uFE0F"+"▪\uFE0F"+"▪\uFE0F";
            }
            x++;
        }

        myHand[y][x] = "\n";
        y++;
        x = 1;

        for (int j = 0; j < 3; j++) {
            for(Card c: cards) {
                for (int i = 0; i < 3; i++) {
                    myHand[y][x] = c.getFront().printableSide()[j][i];
                    x++;
                }
                myHand[y][x] = "       ";
                x++;
            }
            myHand[y][x] = "\n";
            y++;
            x = 1;
        }
/*
        for(Card c: cards) {
            myHand[y][x] = "    Back " + cards.indexOf(c);
            if(c != cards.getLast()){
                myHand[y][x] = myHand[y][x] + "  " + "▪\uFE0F"+"▪\uFE0F"+"▪\uFE0F";
            }
            x++;
        }

        myHand[y][x] = "\n";
        y++;
        x = 0;

        for (int j = 0; j < 3; j++) {
            for(Card c: cards) {
                for (int i = 0; i < 3; i++) {
                    myHand[y][x] = c.getBack().printableSide()[j][i];
                    x++;
                }
                myHand[y][x] = "      ";
                x++;
            }
            myHand[y][x] = "\n";
            y++;
            x = 0;
        }

        myHand[y][x] = "\n";
        y++;
        x = 0;
*/
        for(Card c: cards) {
            if(c instanceof GoldCard){
                myHand[y][x] = " Gold    ";
            } else if(c instanceof ResourceCard){
                myHand[y][x] = " Resource    ";
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
            myHand[y][x] = " " + c.getFront().getPoints() + " pt        ";
            if(c != cards.getLast()){
                myHand[y][x] = myHand[y][x] + "▪\uFE0F"+"▪\uFE0F"+"▪\uFE0F";
            }
            x++;
        }

        myHand[y][x] = "\n";
        y++;
        x = 1;

        for(Card c: cards) {
            myHand[y][x] = " ";
            if(c.getFront().getRequestedResources() != null){
                int n;
                int spaces = 5;

                for(Symbol s: c.getFront().getRequestedResources().keySet()){
                    n = c.getFront().getRequestedResources().get(s);
                    for(int i=0; i<n; i++){
                        myHand[y][x] = myHand[y][x] + s.getAlias();
                    }
                    spaces = spaces - n;
                }

                while (spaces>0){
                    myHand[y][x] = myHand[y][x] + "  ";
                    spaces--;
                }

            }
            if(c != cards.getLast()){
                myHand[y][x] = "  " + myHand[y][x] + "▪\uFE0F"+"▪\uFE0F"+"▪\uFE0F" ;
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
