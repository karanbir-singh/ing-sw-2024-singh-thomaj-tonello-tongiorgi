package it.polimi.ingsw.gc26.model.hand;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Side;

import java.util.*;

public class Hand {
    private ArrayList<Card> cards;
    private Optional<Card> selectedCard;
    private Optional<Side> selectedSide;

    public Hand(){
        this.cards = null;
        this.selectedCard = Optional.empty();
        this.selectedSide = Optional.empty();
    }

    public Optional<Card> getSelectedCard() {
        return this.selectedCard;
    }

    public void setSelectedCard(Optional<Card> selected){
        if(selected.isEmpty()){
            this.selectedSide = Optional.empty();
            return;
        }
        this.selectedCard = selected;
        this.selectedSide = Optional.of(selected.get().getFront());
    }
    public Optional<Side> getSelectedSide(){
        return this.selectedSide;
    }

    public void turnSide(Side currentSide){
        if(selectedCard.isEmpty()){
            return;
        }
        if(currentSide.equals(selectedCard.get().getFront())){
            this.selectedSide = Optional.ofNullable(selectedCard.get().getBack());
        } else {
            this.selectedSide = Optional.ofNullable(selectedCard.get().getFront());
        }
    }

    public void removeCard(Card card){
        cards.remove(card);
    }

    public void addCard(Card card){
        cards.add(card);
    }

    public Side playCard(Side selectedSide, Card selectedCard){
        Side playSide;
        playSide = selectedSide;
        removeCard(selectedCard);
        setSelectedCard(java.util.Optional.empty());
        return playSide;
    }
}
