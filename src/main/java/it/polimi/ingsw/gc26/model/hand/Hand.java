package it.polimi.ingsw.gc26.model.hand;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Side;

import java.util.*;

public class Hand {
    private final ArrayList<Card> cards;
    private Card selectedCard;
    private Side selectedSide;

    public Hand(ArrayList<Card> c){
        this.cards = c;
        this.selectedCard = null;
        this.selectedSide = null;
    }

    public Optional<Card> getSelectedCard() {
        return Optional.ofNullable(this.selectedCard);
    }

    public void setSelectedCard(Card selectedCard){
        if(Optional.ofNullable(selectedCard).isPresent()){
            this.selectedCard = selectedCard;
            this.selectedSide = selectedCard.getFront();
        }
    }
    public Optional<Side> getSelectedSide(){
        return Optional.ofNullable(this.selectedSide);
    }

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

    public void removeCard(Card card){
        cards.remove(card);
        selectedSide = null;
        selectedCard = null;
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public ArrayList<Card> getCards(){
        return cards;
    }
}
