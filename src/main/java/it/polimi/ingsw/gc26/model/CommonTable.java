package it.polimi.ingsw.gc26.model;

import java.util.*;
public class CommonTable {
    private final Deck resourceDeck;
    private final Deck goldDeck;
    private final Deck initialDeck;
    private final Deck missionDeck;
    private final ArrayList<MissionCard> commonMissions;
    private final ArrayList<Card> resourceCardsOnTable;
    private final ArrayList<Card> goldCardsOnTable;
    private Deck selectedDeck;
    private Card selectedCard;

    public CommonTable(Deck resourceDeck, Deck goldDeck, Deck initialDeck, Deck missionDeck){
        commonMission = null;
        resourceCardsOnTable = null;
        goldCardsOnTable = null;
        this.resourceDeck = resourceDeck;
        this.goldDeck = goldDeck;
        this.initialDeck = initialDeck;
        selectedDeck = null;
        selectedCard = null;
    }

    public void selectCard(Card cardSelected){
        this.selectedCard = cardSelected;
    }

    public void addCardToBoard(Card card){
        if(card istanceof ResourceCard){
            resourceCardsOnTable.add(card);
        }else if(card istanceof GoldCard){
            goldCardsOnTable.add(card);
        }

        //NON GLI PIACE INSTANCEOF AL COMPILATORE PERCHé BHOO??
        //NON è BUONA PRATICA USARE INSTACEOF, MA IN STO CASO è NECESSARIO
    }


    public void removeCardFromBoard(){
        for(Card c: resourceCardsOnTable){
            if(c.equals(selectedCard)){
                resourceCardsOnTable.remove(c);
            }
        }

        for(Card c: goldCardsOnTable){
            if(c.equals(selectedCard)){
                resourceCardsOnTable.remove(c);
            }
        }
    }

    public void selectDeck(Deck deck){
        this.selectedDeck = deck;
    }

    public void drawFromDeck(){
         this.selectedDeck.removeCard();
    }

    public Deck getSelectedDeck(){
        return this.selectedDeck;
    }

    public Card getSelectedCard(){
        return this.selectedCard;
    }
}
