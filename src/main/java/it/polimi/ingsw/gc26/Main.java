package it.polimi.ingsw.gc26;

import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.gc26.Parser.ParserCore;
import it.polimi.ingsw.gc26.model.card.Card;
import java.util.ArrayList;
import it.polimi.ingsw.gc26.model.deck.*;


public class Main {
    public static void main(String[] args) {
        // This is a main class: for now is useless
        ParserCore p = new ParserCore("src/main/resources/Data/CodexNaturalisCards.json");
        GoldDeck goldCardDeck = p.getGoldCards();
        ResourceDeck resourceCardDeck = p.getResourceCards();
        MissionDeck missionDeck = p.getMissionCards();
        StarterDeck starterDeck = p.getStarterCards();
        System.out.println(goldCardDeck);

    }
}
