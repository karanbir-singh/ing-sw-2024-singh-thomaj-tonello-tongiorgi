package it.polimi.ingsw.gc26.model;

import java.util.*;

public class CommonTable {
    private Card selectedCard;
    private Deck selectedDeck;
    private ScoreBoard scoreBoard;
    private ArrayList<Deck> decks;
    private ArrayList<MissionCard> commonMissions;
    /*to be decided: is it better to handle shown cards as ArrayLists or as separated variables?*/
    private ArrayList<ResourceCard> shownResourceCard;
    private ArrayList<GoldCard> shownGoldCard;

    public Card getSelectedCard(){
        return this.selectedCard;
    }

    public void selectCard(Card selected){
        this.selectedCard = selected;
    }




}
