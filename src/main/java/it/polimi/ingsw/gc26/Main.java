package it.polimi.ingsw.gc26;

import com.fasterxml.jackson.databind.JsonNode;
import it.polimi.ingsw.gc26.Parser.ParserCore;
import it.polimi.ingsw.gc26.model.card.Card;
import java.util.ArrayList;
import it.polimi.ingsw.gc26.model.deck.*;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.model.player.Player;


public class Main {
    public static void main(String[] args) throws Exception {
        Game game = new Game(2);
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getInitialDeck();
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        Player p1 = new Player(3,"Bob");
        game.addPlayer(p1);
        game.setCurrentPlayer(p1);
        game.getCurrentPlayer().setPersonalBoard(initialDeck.getDeck().get(0).getFront(),
                            missionDeck.getDeck().get(0), missionDeck.getDeck().get(1), missionDeck.getDeck().get(2));
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();


        pb.setPosition(1,1);
        pb.playSide(resourceDeck.getDeck().get(0).getFront());
        pb.setPosition(2,2);
        pb.playSide(resourceDeck.getDeck().get(1).getFront());
        pb.setPosition(0,2);
        pb.playSide(goldDeck.getDeck().get(0).getFront());
        pb.setPosition(1,3);
        pb.playSide(goldDeck.getDeck().get(3).getFront());
        pb = null;
    }
}
