package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Side;

import java.io.Serializable;
import java.util.ArrayList;

public class SimplifiedHand implements Serializable {
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
     *
     * @param cards new cards in hand
     */
    public SimplifiedHand(ArrayList<Card> cards, Card selectedCard, Side selectedSide) {
        this.cards = cards;
        this.selectedCard = selectedCard;
        this.selectedSide = selectedSide;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public Side getSelectedSide() {
        return selectedSide;
    }



    //    /**
//     * Adds the card given as a parameter in the hand
//     *
//     * @param card new card in hand
//     */
//    public void addCard(Card card) {
//        cards.add(card);
//    }

//    /**
//     * Removes the card given as parameter from the hand
//     *
//     * @param card card to be removed
//     */
//    public void removeCard(Card card) {
//        cards.remove(card);
//        selectedSide = null;
//        selectedCard = null;
//    }

//    /**
//     * Sets the selected card to the parameter given
//     *
//     * @param selectedCard new selected card
//     */
//    public void setSelectedCard(Card selectedCard) {
//        if (selectedCard != null) {
//            this.selectedCard = selectedCard;
//            this.selectedSide = selectedCard.getFront();
//        }
//    }

//    /**
//     * Sets selected side to the opposite side if there is a selected card
//     */
//    public void turnSide() {
//        Optional<Card> selectedCard = Optional.ofNullable(this.selectedCard);
//        if (selectedCard.isPresent()) {
//            if (selectedCard.get().getFront().equals(selectedSide)) {
//                this.selectedSide = selectedCard.get().getBack();
//            } else {
//                this.selectedSide = selectedCard.get().getFront();
//            }
//        } else {
//            // TODO lancia eccezione di carta non selezionata
//        }
//    }
}
