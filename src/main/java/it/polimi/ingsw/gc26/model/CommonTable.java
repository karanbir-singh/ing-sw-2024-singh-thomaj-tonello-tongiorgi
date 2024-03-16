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
    private Optional<Deck> selectedDeck;
    private Optional<Card> selectedCard;

    public CommonTable(Deck resourceDeck, Deck goldDeck, Deck initialDeck, Deck missionDeck){
        commonMission = new ArrayList<MissionCard>();
        resourceCardsOnTable = new ArrayList<Card>();
        goldCardsOnTable = new ArrayList<Card>();
        this.resourceDeck = resourceDeck;
        this.goldDeck = goldDeck;
        this.initialDeck = initialDeck;
        selectedDeck = Optional.empty();
        selectedCard = Optional.empty();
    }

    public void selectCard(Card cardSelected){
        this.selectedCard = Optional.of(cardSelected);
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


    public void removeCardFromBoard() throws NullPointerException{
        for(Card c: resourceCardsOnTable){
            if(c.equals(selectedCard.orElseThrow(() -> (new NullPointerException()))){
                resourceCardsOnTable.remove(c);
            }
        }
        //equals da ridefinire per il metodo card
        for(Card c: goldCardsOnTable){
            if(c.equals(selectedCard.orElseThrow(() -> (new NullPointerException()))){
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
        return this.selectedDeck.orElseThrow(() -> (new NullPointerException());
    }

    public Card getSelectedCard() throws NullPointerException{
        return this.selectedCard.orElseThrow(() -> (new NullPointerException());
    }

    public ArrayList<Card> getResourceCardsOnTable(){
        return new ArrayList<Card>(resourceCardsOnTable);
    }
    public ArrayList<Card> getResourceCardsOnTable(){
        return new ArrayList<Card>(goldCardsOnTable);
    }

}
