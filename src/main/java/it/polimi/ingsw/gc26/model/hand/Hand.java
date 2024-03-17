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

    public Card getSelectedCard() {
        return this.selectedCard.get();
    }

    public void setSelectedCard(Optional<Card> selected){
        if(selected.isEmpty()){
            this.selectedSide = Optional.empty();
            return;
        }
        this.selectedCard = selected;
        this.selectedSide = Optional.of(selectedCard.get().getFront());
    }
    public Optional<Side> getSelectedSide(){
        return this.selectedSide;
    }

    public void turnSide(Side selectedSide){
        /* mi servono i metodi di card */
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
