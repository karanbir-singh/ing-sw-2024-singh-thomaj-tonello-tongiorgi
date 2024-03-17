package it.polimi.ingsw.gc26.model.hand;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Side;

import java.util.*;

public class Hand {
    private ArrayList<Card> cards;
    private HandState state;
    private Optional<Card> selectedCard;
    private Optional<Side> selectedSide;

    public void setState(HandState state){
        this.state = state;
    }

    public Optional<Card> getSelectedCard() {
        if(selectedCard.isPresent()){
            return this.selectedCard;
        }
        return Optional.empty();
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
        return state.playCard(selectedSide, selectedCard);
    }
}
