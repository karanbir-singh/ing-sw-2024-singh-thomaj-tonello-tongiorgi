package it.polimi.ingsw.gc26.model.hand;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Side;

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
}
