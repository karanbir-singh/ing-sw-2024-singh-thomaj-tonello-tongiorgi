package it.polimi.ingsw.gc26.model.game;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card.MissionCard;
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
    private Optional<Card> selectedCard;

    public CommonTable(Deck resourceDeck, Deck goldDeck, Deck initialDeck, Deck missionDeck){
        commonMissions = new ArrayList<MissionCard>();
        resourceCardsOnTable = new ArrayList<Card>();
        goldCardsOnTable = new ArrayList<Card>();
        this.resourceDeck = resourceDeck;
        this.goldDeck = goldDeck;
        this.initialDeck = initialDeck;
        this.missionDeck = missionDeck;
        selectedCard = Optional.empty();
    }

    public void selectCard(Card cardSelected){ //TODO controllare se carta e nel top del deck
        this.selectedCard = Optional.of(cardSelected);
    }

    public void addCardToTable(Card card, ArrayList<Card> cards, int index){
        cards.add(index,card);
    }



    //passi l'array su cui fare il controllo
    public Card removeCardFromTable(ArrayList<Card> cards) throws NullPointerException{
        return cards.remove(cards.indexOf(this.selectedCard.orElseThrow(NullPointerException::new)));
    }


    public Card removeCardFromDeck(Deck d){
        return d.removeCard();

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
    public ArrayList<MissionCard> getCommonMissions(){
        return new ArrayList<MissionCard>(commonMission);
    }

}
