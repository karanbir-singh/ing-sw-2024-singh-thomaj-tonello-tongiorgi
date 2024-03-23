package it.polimi.ingsw.gc26.Parser;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.deck.Deck;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ParserCoreTest {

    ParserCore parser = new ParserCore("src/main/resources/Data/CodexNaturalisCards.json");

    @Test
    void getStarterCards() {
        Deck starterDeck = parser.getStarterCards();
        Card firstCard = starterDeck.removeCard();
        assertEquals(firstCard.getFront().getUPRIGHT().getSymbol(), Optional.of(Symbol.PLANT));
        assertEquals(firstCard.getFront().getUPLEFT().getSymbol(), Optional.empty());
        assertEquals(firstCard.getFront().getDOWNRIGHT().getSymbol(), Optional.empty());
        assertEquals(firstCard.getFront().getDOWNLEFT().getSymbol(), Optional.of(Symbol.INSECT));
        assertEquals(firstCard.getFront().getPoints(), 0);
        assertTrue(firstCard.getFront().getRequestedResources().isEmpty());
        assertTrue(firstCard.getFront().getPermanentResources().isEmpty());
        assertEquals(0, firstCard.getFront().getType());

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