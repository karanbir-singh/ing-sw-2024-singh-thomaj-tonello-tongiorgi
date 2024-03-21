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
        pb.setPosition(3,3);
        pb.playSide(resourceDeck.getDeck().get(2).getBack());
        pb.setPosition(4,4);
//        pb.playSide(resourceDeck.getDeck().get(3).getBack());
//        pb.setPosition(5,5);
//        pb.playSide(resourceDeck.getDeck().get(4).getBack());
//        pb.setPosition(6,6);
//        pb.playSide(resourceDeck.getDeck().get(5).getBack());
//        pb.setPosition(7,7);
//        pb.playSide(resourceDeck.getDeck().get(6).getBack());
//        pb.setPosition(8,8);
//        pb.playSide(resourceDeck.getDeck().get(7).getBack());
//        pb.setPosition(9,9);
//        pb.playSide(resourceDeck.getDeck().get(8).getBack());
//        pb.setPosition(10,10);
//        pb.playSide(resourceDeck.getDeck().get(9).getBack());
//        pb.setPosition(11,11);
//        pb.playSide(resourceDeck.getDeck().get(10).getBack());
//        pb.setPosition(12,12);
//        pb.playSide(resourceDeck.getDeck().get(11).getBack());
//        pb.setPosition(13,13);
//        pb.playSide(resourceDeck.getDeck().get(12).getBack());
//        pb.setPosition(14,14);
//        pb.playSide(resourceDeck.getDeck().get(13).getBack());
//        pb.setPosition(15,15);
//        pb.playSide(resourceDeck.getDeck().get(14).getBack());
//        pb.setPosition(16,16);
//        pb.playSide(resourceDeck.getDeck().get(15).getBack());
//        pb.setPosition(17,17);
//        pb.playSide(resourceDeck.getDeck().get(16).getBack());
//        pb.setPosition(18,18);
//        pb.playSide(resourceDeck.getDeck().get(17).getBack());
//        pb.setPosition(19,19);
//        pb.playSide(resourceDeck.getDeck().get(18).getBack());
//        pb.setPosition(20,20);
//        pb.playSide(resourceDeck.getDeck().get(19).getBack());
//        pb.setPosition(21,21);
//        pb.playSide(resourceDeck.getDeck().get(20).getBack());




        pb.showBoard();
    }
}
