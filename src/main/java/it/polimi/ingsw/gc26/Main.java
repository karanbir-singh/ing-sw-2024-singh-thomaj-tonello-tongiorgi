package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.deck.*;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.player.PersonalBoard;
import it.polimi.ingsw.gc26.model.player.Player;

import java.util.ArrayList;


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
        game.getCurrentPlayer().createPersonalBoard(initialDeck.getCards().get(0).getFront());
        PersonalBoard pb = game.getCurrentPlayer().getPersonalBoard();
        pb.setSecretMission(missionDeck.getCards().get(0));
//        ArrayList<Card> commonMissions = new ArrayList<>();
//        commonMissions.add(missionDeck.getCards().get(1));
//        commonMissions.add(missionDeck.getCards().get(2));


        pb.setPosition(1,1);
        pb.playSide(resourceDeck.getCards().get(0).getFront());
        pb.setPosition(2,2);
        pb.playSide(resourceDeck.getCards().get(1).getFront());
        pb.setPosition(0,2);
        pb.playSide(goldDeck.getCards().get(0).getFront());
        pb.setPosition(1,3);
        pb.playSide(goldDeck.getCards().get(3).getFront());
        pb.setPosition(3,3);
        pb.playSide(resourceDeck.getCards().get(2).getBack());
        pb.setPosition(4,4);



        pb.showBoard();
    }
}
