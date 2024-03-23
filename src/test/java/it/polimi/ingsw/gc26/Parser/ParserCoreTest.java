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
        //check deck's length
        assertEquals(6, starterDeck.getDeck().size());

        Card firstCard = starterDeck.getDeck().getFirst();
        //check front side
        assertEquals(Optional.of(Symbol.PLANT), firstCard.getFront().getUPRIGHT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getFront().getUPLEFT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getFront().getDOWNRIGHT().getSymbol());
        assertEquals(Optional.of(Symbol.INSECT), firstCard.getFront().getDOWNLEFT().getSymbol());
        assertEquals(0, firstCard.getFront().getPoints());
        assertTrue(firstCard.getFront().getRequestedResources().isEmpty());
        assertTrue(firstCard.getFront().getPermanentResources().contains(Symbol.INSECT));
        assertEquals(1, firstCard.getFront().getPermanentResources().size());
        assertEquals(0, firstCard.getFront().getType());

        //check backside
        assertEquals(Optional.empty(), firstCard.getBack().getSideSymbol());
        assertEquals(Optional.of(Symbol.PLANT), firstCard.getBack().getUPRIGHT().getSymbol());
        assertEquals(Optional.of(Symbol.FUNGI), firstCard.getBack().getUPLEFT().getSymbol());
        assertEquals(Optional.of(Symbol.ANIMAL), firstCard.getBack().getDOWNRIGHT().getSymbol());
        assertEquals(Optional.of(Symbol.INSECT), firstCard.getBack().getDOWNLEFT().getSymbol());
        assertEquals(0, firstCard.getBack().getPoints());
        assertTrue(firstCard.getBack().getRequestedResources().isEmpty());
        assertTrue(firstCard.getBack().getPermanentResources().isEmpty());
        assertEquals(0, firstCard.getFront().getType());

    }

    @Test
    void getMissionCards() {
        Deck missionDeck = parser.getMissionCards();
        Card firstCard = missionDeck.getDeck().getFirst();
        //check deck's length
        assertEquals(16, missionDeck.getDeck().size());

        //check front side
        assertEquals(Optional.empty(), firstCard.getFront().getUPRIGHT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getFront().getUPLEFT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getFront().getDOWNRIGHT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getFront().getDOWNLEFT().getSymbol());
        assertEquals(0, firstCard.getFront().getPoints());
        assertTrue(firstCard.getFront().getRequestedResources().containsKey(Symbol.FUNGI));
        assertEquals(1, firstCard.getFront().getRequestedResources().size());
        assertEquals(3, firstCard.getFront().getRequestedResources().get(Symbol.FUNGI));
        assertTrue(firstCard.getFront().getPermanentResources().isEmpty());
        assertEquals(0, firstCard.getFront().getType());

        //check backside
        assertEquals(Optional.empty(), firstCard.getBack().getSideSymbol());
        assertEquals(Optional.empty(), firstCard.getBack().getUPRIGHT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getBack().getUPLEFT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getBack().getDOWNRIGHT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getBack().getDOWNLEFT().getSymbol());
        assertEquals(0, firstCard.getBack().getPoints());
        assertTrue(firstCard.getBack().getRequestedResources().isEmpty());
        assertTrue(firstCard.getBack().getPermanentResources().isEmpty());
        assertEquals(0, firstCard.getFront().getType());

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