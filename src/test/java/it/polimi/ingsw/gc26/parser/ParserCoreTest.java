package it.polimi.ingsw.gc26.parser;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.deck.Deck;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParserCoreTest {

    ParserCore parser = new ParserCore("src/main/resources/Data/CodexNaturalisCards.json");

    @Test
    void getStarterCards() {
        Deck starterDeck = parser.getStarterCards();
        //check deck's length
        assertEquals(6, starterDeck.getCards().size());

        Card firstCard = starterDeck.getCards().getFirst();
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
        Card firstCard = missionDeck.getCards().getFirst();
        //check deck's length
        assertEquals(16, missionDeck.getCards().size());

        //check front side
        assertEquals(Optional.empty(), firstCard.getFront().getUPRIGHT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getFront().getUPLEFT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getFront().getDOWNRIGHT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getFront().getDOWNLEFT().getSymbol());
        assertEquals(0, firstCard.getFront().getPoints());
        assertEquals(0, firstCard.getFront().getRequestedResources().size());
        assertTrue(firstCard.getFront().getPermanentResources().isEmpty());
        assertEquals(1, firstCard.getFront().getType());

        //check backside
        assertEquals(Optional.empty(), firstCard.getBack().getSideSymbol());
        assertEquals(Optional.empty(), firstCard.getBack().getUPRIGHT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getBack().getUPLEFT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getBack().getDOWNRIGHT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getBack().getDOWNLEFT().getSymbol());
        assertEquals(0, firstCard.getBack().getPoints());
        assertTrue(firstCard.getBack().getRequestedResources().isEmpty());
        assertTrue(firstCard.getBack().getPermanentResources().isEmpty());
        assertEquals(0, firstCard.getBack().getType());

    }

    @Test
    void getResourceCards() {
        Deck resourceCardDeck = parser.getResourceCards();
        Card firstCard = resourceCardDeck.getCards().getFirst();
        //check decks's length
        assertEquals(40, resourceCardDeck.getCards().size());
        //check front side
        assertEquals(Optional.empty(), firstCard.getFront().getUPRIGHT().getSymbol());
        assertEquals(Optional.of(Symbol.PLANT), firstCard.getFront().getUPLEFT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getFront().getDOWNRIGHT().getSymbol());
        assertTrue(firstCard.getFront().getDOWNRIGHT().isEvil());
        assertEquals(Optional.of(Symbol.PLANT), firstCard.getFront().getDOWNLEFT().getSymbol());
        assertEquals(0, firstCard.getFront().getPoints());
        assertEquals(0, firstCard.getFront().getRequestedResources().size());
        assertTrue(firstCard.getFront().getPermanentResources().isEmpty());
        assertEquals(0, firstCard.getFront().getType());
        //check backside
        assertEquals(Optional.of(Symbol.PLANT), firstCard.getBack().getSideSymbol());
        assertEquals(Optional.empty(), firstCard.getBack().getUPRIGHT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getBack().getUPLEFT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getBack().getDOWNRIGHT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getBack().getDOWNLEFT().getSymbol());
        assertEquals(0, firstCard.getBack().getPoints());
        assertTrue(firstCard.getBack().getRequestedResources().isEmpty());
        assertEquals(1, firstCard.getBack().getPermanentResources().size());
        assertEquals(0, firstCard.getFront().getType());

    }

    @Test
    void getGoldCards() {
        Deck goldCardDeck = parser.getGoldCards();
        Card firstCard = goldCardDeck.getCards().getFirst();
        //check decks's length
        assertEquals(40, goldCardDeck.getCards().size());
        //check front side
        assertEquals(Optional.empty(), firstCard.getFront().getUPRIGHT().getSymbol());
        assertEquals(Optional.of(Symbol.QUILL), firstCard.getFront().getUPLEFT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getFront().getDOWNRIGHT().getSymbol());
        assertTrue(firstCard.getFront().getDOWNRIGHT().isEvil());
        assertEquals(Optional.empty(), firstCard.getFront().getDOWNLEFT().getSymbol());
        assertEquals(0, firstCard.getFront().getPoints());
        assertEquals(2, firstCard.getFront().getRequestedResources().size());
        assertTrue(firstCard.getFront().getRequestedResources().containsKey(Symbol.PLANT));
        assertTrue(firstCard.getFront().getRequestedResources().containsKey(Symbol.INSECT));
        assertEquals(2, firstCard.getFront().getRequestedResources().get(Symbol.PLANT));
        assertEquals(1, firstCard.getFront().getRequestedResources().get(Symbol.INSECT));
        assertTrue(firstCard.getFront().getPermanentResources().isEmpty());
        assertEquals(0, firstCard.getFront().getType());
        //check backside
        assertEquals(Optional.of(Symbol.PLANT), firstCard.getBack().getSideSymbol());
        assertEquals(Optional.empty(), firstCard.getBack().getUPRIGHT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getBack().getUPLEFT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getBack().getDOWNRIGHT().getSymbol());
        assertEquals(Optional.empty(), firstCard.getBack().getDOWNLEFT().getSymbol());
        assertEquals(0, firstCard.getBack().getPoints());
        assertTrue(firstCard.getBack().getRequestedResources().isEmpty());
        assertEquals(1, firstCard.getBack().getPermanentResources().size());
        assertEquals(0, firstCard.getFront().getType());

    }
}