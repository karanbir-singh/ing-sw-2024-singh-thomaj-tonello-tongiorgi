package it.polimi.ingsw.gc26.model.hand;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Side;

import java.util.*;

public class Hand {
    private ArrayList<Card> cards;
    private Optional<Card> selectedCard;
    private Optional<Side> selectedSide;

    public Hand(ArrayList<Card> c){
        this.cards = c;
        this.selectedCard = Optional.empty();
        this.selectedSide = Optional.empty();
    }

    public Optional<Card> getSelectedCard() {
        return this.selectedCard;
    }

    public void setSelectedCard(Card selected){
        this.selectedCard = Optional.ofNullable(selected);
        if(selected != null)
            this.selectedSide = Optional.of(selected.getFront());
    }
    public Optional<Side> getSelectedSide(){
        return this.selectedSide;
    }

    public void turnSide(){
        if(selectedCard.isEmpty()){
            return;
        }
        if(selectedCard.get().getFront().equals(selectedSide.get())){
            this.selectedSide = Optional.ofNullable(selectedCard.get().getBack());
        } else {
            this.selectedSide = Optional.ofNullable(selectedCard.get().getFront());
        }
    }

    public void removeCard(Card card){
        cards.remove(card);
        selectedSide = Optional.empty();
        selectedCard = Optional.empty();
    }

    public void addCard(Card card){
        cards.add(card);
    }


    public ArrayList<Card> getCards(){
        return new ArrayList<>(this.cards);
    }
}
