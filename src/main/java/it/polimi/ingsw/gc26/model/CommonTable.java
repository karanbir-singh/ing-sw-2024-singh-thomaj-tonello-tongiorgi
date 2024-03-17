package it.polimi.ingsw.gc26.model;

import java.util.List;

public class CommonTable {
    private Card selectedCard;
    private ScoreBoard scoreBoard;
    private List<Deck> availableDecks;
    private ResourceCard resourceCard;
    private GoldCard goldCard;
    private ObjectiveCard objectiveCard;

    public CommonTable() {

    }

    public void selectCard(Card cardInTable) {
        this.selectedCard = cardInTable.clone();
    }

    public void drawCard(Card selectedCard) {

    }

    public void selectDeck(Deck deckInTable) {

    }

    public void drawFromDeck(Deck deckInTable) {

    }
}
