package it.polimi.ingsw.gc26.Parser;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.deck.Deck;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserCoreTest {

    ParserCore parser = new ParserCore("src/main/resources/Data/CodexNaturalisCards.json");

    @Test
    void getStarterCards() {
        Deck starterDeck = parser.getStarterCards();
        Card firstCard = starterDeck.removeCard();

    }

    @Test
    void getMissionCards() {
        Deck missionDeck = parser.getMissionCards();
        Card firstCard = missionDeck.removeCard();
    }

    @Test
    void getResourceCards() {
        Deck resourceCardDeck = parser.getResourceCards();
        Card firstCard = resourceCardDeck.removeCard();
    }

    @Test
    void getGoldCards() {
        Deck goldCardDeck = parser.getGoldCards();
        Card firstCard = goldCardDeck.removeCard();
    }
}