package it.polimi.ingsw.gc26;

import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.gc26.Parser.ParserCore;
import it.polimi.ingsw.gc26.model.card.Card;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        // This is a main class: for now is useless
        ParserCore p = new ParserCore("src/main/resources/Data/CodexNaturalisCards.json");
        ArrayList<Card> p1 = p.getGoldCards();
        System.out.println(p1);
    }
}
