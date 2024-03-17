package it.polimi.ingsw.gc26.model.game;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card.GoldCard;
import it.polimi.ingsw.gc26.model.card.MissionCard;
import it.polimi.ingsw.gc26.model.card.ResourceCard;
import it.polimi.ingsw.gc26.model.deck.Deck;

import java.util.*;
public class CommonTable {
    private final Deck resourceDeck;
    private final Deck goldDeck;
    private final Deck initialDeck;
    private final Deck missionDeck;
    private final ArrayList<MissionCard> commonMissions;
    private final ArrayList<Card> resourceCardsOnTable;
    private final ArrayList<Card> goldCardsOnTable;
    private Optional<Deck> selectedDeck;
    private Optional<Card> selectedCard;

    public CommonTable(Deck resourceDeck, Deck goldDeck, Deck initialDeck, Deck missionDeck){
        commonMissions = new ArrayList<MissionCard>();
        resourceCardsOnTable = new ArrayList<Card>();
        goldCardsOnTable = new ArrayList<Card>();
        this.resourceDeck = resourceDeck;
        this.goldDeck = goldDeck;
        this.initialDeck = initialDeck;
        this.missionDeck = missionDeck;
        selectedDeck = Optional.empty();
        selectedCard = Optional.empty();
    }

    public void selectCard(Card cardSelected){
        this.selectedCard = Optional.of(cardSelected);
    }

    public void addCardToBoard(Card card){
        if(card instanceof ResourceCard){
            resourceCardsOnTable.add(card);
        }else if(card instanceof GoldCard){
            goldCardsOnTable.add(card);
        }
    }


    public void removeCardFromBoard() throws NullPointerException{
        for(Card c: resourceCardsOnTable){
            if(c.equals(selectedCard.orElseThrow(NullPointerException::new)){ // TODO in card ridefinisci equals
                resourceCardsOnTable.remove(c);
            }
        }
        //equals da ridefinire per il metodo card
        for(Card c: goldCardsOnTable){
            if(c.equals(selectedCard.orElseThrow(NullPointerException::new)){
                resourceCardsOnTable.remove(c);
            }
        }
    }

    public void selectDeck(Deck deck){
        this.selectedDeck = Optional.of(deck);
    }

    public void drawFromDeck(){
        if(selectedDeck.isPresent()){
            this.selectedDeck.get().removeCard();
        }

    }

    public Deck getSelectedDeck() throws NullPointerException{
        return this.selectedDeck.orElseThrow(NullPointerException::new);
    }

    public Card getSelectedCard() throws NullPointerException{
        return this.selectedCard.orElseThrow(NullPointerException::new);
    }

    public ArrayList<Card> getResourceCardsOnTable(){
        return new ArrayList<Card>(resourceCardsOnTable);
    }
    public ArrayList<Card> getGoldCardsOnTable(){
        return new ArrayList<Card>(goldCardsOnTable);
    }

}
