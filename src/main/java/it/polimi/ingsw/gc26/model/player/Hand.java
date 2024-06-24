package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card.MissionCard;
import it.polimi.ingsw.gc26.model.card_side.Side;
import it.polimi.ingsw.gc26.network.ModelObservable;
import it.polimi.ingsw.gc26.view_model.SimplifiedHand;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Optional;

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
    private final ModelObservable observable;

    /**
     * Initializes the hand for the player
     *
     * @param cards new cards in hand
     * @param observable observable to notify client
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
        if (selectedCard == null) {
            this.observable.notifyError("Select a card first!", clientID);
            return;
        }

        // Check if the card is already selected
        if (this.selectedCard == selectedCard) {
            return;
        }

        this.selectedCard = selectedCard;
        this.selectedSide = selectedCard.getFront();
        this.observable.notifyUpdateHand(
                new SimplifiedHand(cards, selectedCard, selectedSide),
                "Card selected on hand",
                clientID);
    }

    /**
     * Sets the selected secret mission
     *
     * @param selectedCard selected secret mission on initial phase of the game
     * @param clientID     ID of the client
     */
    public void setSelectedCard(MissionCard selectedCard, String clientID) {
        if (selectedCard == null) {
            this.observable.notifyError("Select a card first!", clientID);
            return;
        }
        this.selectedCard = selectedCard;
        this.selectedSide = selectedCard.getFront();
        this.observable.notifyUpdateSecretHand(
                new SimplifiedHand(cards, selectedCard, selectedSide),
                "Card selected on hand",
                clientID
        );
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
     *
     * @param clientID client unique identifier
     */
    public void turnSide(String clientID) {
        Optional<Card> selectedCard = Optional.ofNullable(this.selectedCard);
        if (!selectedCard.isPresent()) {
            this.observable.notifyError("Select a card first!", clientID);
            return;
        }

        if (selectedCard.get().getFront().equals(selectedSide)) {
            this.selectedSide = selectedCard.get().getBack();
        } else {
            this.selectedSide = selectedCard.get().getFront();
        }
        this.observable.notifyUpdateHand(
                new SimplifiedHand(cards, this.selectedCard, selectedSide),
                "Turned side of selected card",
                clientID);

    }

    /**
     * Removes the card given as parameter from the hand
     *
     * @param card card to be removed
     * @param clientID client unique identifier
     */
    public void removeCard(Card card, String clientID) {
        cards.remove(card);
        selectedSide = null;
        selectedCard = null;

        this.observable.notifyUpdateHand(
                new SimplifiedHand(cards, null, null),
                "Card removed from hand",
                clientID
        );
    }

    /**
     * Removes the secret mission card given as parameter from the secret hand
     *
     * @param card card to be removed
     * @param clientID client unique identifier
     */
    public void removeCard(MissionCard card, String clientID) {
        cards.remove(card);
        selectedSide = null;
        selectedCard = null;

        this.observable.notifyUpdateSecretHand(
                new SimplifiedHand(cards, null, null),
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
        this.observable.notifyUpdateHand(
                new SimplifiedHand(cards, selectedCard, selectedSide),
                "Card added to hand",
                clientID);
    }

    /**
     * Adds a secret mission card into secret hand
     *
     * @param card new secret mission card in secret hand
     * @param clientID unique client identifier
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
     * @param clientID client's identifier
     * @param leftLimit  min value for index
     * @param rightLimit max value for index
     * @return card in cards at position cardIndex
     */
    public Card getCard(int leftLimit, int rightLimit, int cardIndex, String clientID) {
        // Check if the given index is correct
        if (cardIndex >= leftLimit && cardIndex < rightLimit) {
            return cards.get(cardIndex);
        }
        this.observable.notifyError("Invalid card position!", clientID);
        return null;
    }
}
