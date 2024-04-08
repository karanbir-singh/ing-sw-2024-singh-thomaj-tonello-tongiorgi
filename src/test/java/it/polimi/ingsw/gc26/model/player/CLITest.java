package it.polimi.ingsw.gc26.model.player;


import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.deck.Deck;
import it.polimi.ingsw.gc26.model.card_side.Side;
import it.polimi.ingsw.gc26.model.game.Game;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CLITest {
    @Test
    public void printSide(){
        Game game = new Game(new ArrayList<>());
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();

        Side starter = initialDeck.getCards().get(3).getFront();
        Side starter1 = initialDeck.getCards().get(4).getFront();
        Side s1 = resourceDeck.getCards().get(0).getBack();
        Side s5 = resourceDeck.getCards().get(10).getBack();
        Side s6 = resourceDeck.getCards().get(20).getBack();
        Side s2 = resourceDeck.getCards().get(30).getBack();
        Side s3 = goldDeck.getCards().get(0).getFront();
        Side s4 = goldDeck.getCards().get(30).getFront();

        starter.printSide();
        starter1.printSide();
        s1.printSide();
        System.out.print("\n");
        s5.printSide();
        System.out.print("\n");
        s6.printSide();
        System.out.print("\n");
        s2.printSide();
        System.out.print("\n");
        s3.printSide();
        System.out.print("\n");
        s4.printSide();
    }

}
