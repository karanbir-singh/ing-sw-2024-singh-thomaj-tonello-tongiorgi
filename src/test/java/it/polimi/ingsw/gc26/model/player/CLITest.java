package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.controller.GameController;
import it.polimi.ingsw.gc26.model.card.StarterCard;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.deck.Deck;
import it.polimi.ingsw.gc26.model.card_side.Side;
import it.polimi.ingsw.gc26.model.game.CommonTable;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.game.GameState;
import it.polimi.ingsw.gc26.model.hand.Hand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;


public class CLITest {
    @Test
    public void showHand(){
        Game game = new Game(new ArrayList<>());
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        ArrayList<Card> cards = new ArrayList<>();
        Hand myHand;

        cards.add(resourceDeck.getCards().get(7));
        cards.add(goldDeck.getCards().get(4));
        cards.add(goldDeck.getCards().get(10));

        myHand = new Hand(cards);

        myHand.setSelectedCard(goldDeck.getCards().get(4));
        myHand.turnSide();
        myHand.turnSide();
        myHand.turnSide();
        myHand.setSelectedCard(goldDeck.getCards().get(10));
        myHand.turnSide();

        myHand.showHand();

    }

    @Test
    public void deckCLI() {
        Game game = new Game(new ArrayList<>());
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        String[][] s = goldDeck.printableDeck();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(s[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    @Test
    public void commonTableCLI() {
        Game game = new Game(new ArrayList<>());
        GameController gc = new GameController(game);
        game.setState(GameState.COMMON_TABLE_PREPARATION);
        gc.prepareCommonTable();
        game.setState(GameState.COMMON_MISSION_PREPARATION);
        gc.prepareCommonMissions();
        CommonTable ct = game.getCommonTable();
        String[][] s = ct.printableCommonTable();

        System.out.println("\n");
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 12; j++) {
                System.out.print(s[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }

    @Test
    public void scoreCLI() {
        Game game = new Game(new ArrayList<>());
        GameController gameController = new GameController(game);
        ArrayList<Player> players;
        String score;

        players = new ArrayList<>();
        players.add(new Player("0", "Pippo"));
        players.add(new Player("1", "Baudo"));
        players.add(new Player("2", "Carlo"));
        players.add(new Player("4", "Kevin"));

        players.get(0).setPawn("BLUE", game.getAvailablePawns());
        players.get(1).setPawn("YELLOW", game.getAvailablePawns());
        players.get(2).setPawn("RED", game.getAvailablePawns());
        players.get(3).setPawn("GREEN", game.getAvailablePawns());

        for (Player p: players) {
            p.createPersonalBoard();
            p.getPersonalBoard().setScore(players.indexOf(p)*3 + 7);
            score = p.printableScore();
            System.out.println(p.getNickname() + " " + score);
        }
    }

    @Test
    public void missionCardCLI(){
        Game game = new Game(new ArrayList<>());
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        String[][] s = missionDeck.getCards().get(0).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                System.out.print(s[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        s = missionDeck.getCards().get(2).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                System.out.print(s[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        s = missionDeck.getCards().get(1).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                System.out.print(s[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        s = missionDeck.getCards().get(3).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                System.out.print(s[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        s = missionDeck.getCards().get(4).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                System.out.print(s[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        s = missionDeck.getCards().get(5).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                System.out.print(s[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        s = missionDeck.getCards().get(6).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                System.out.print(s[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        s = missionDeck.getCards().get(7).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                System.out.print(s[i][j]);
            }
            System.out.print("\n");
        }

        System.out.print("\n");

        s = missionDeck.getCards().get(8).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                System.out.print(s[i][j]);
            }
            System.out.print("\n");
        }

        System.out.print("\n");

        s = missionDeck.getCards().get(9).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                System.out.print(s[i][j]);
            }
            System.out.print("\n");
        }

        System.out.print("\n");

        s = missionDeck.getCards().get(10).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                System.out.print(s[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        s = missionDeck.getCards().get(11).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                System.out.print(s[i][j]);
            }
            System.out.print("\n");
        }

        System.out.print("\n");

        s = missionDeck.getCards().get(12).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                System.out.print(s[i][j]);
            }
            System.out.print("\n");
        }

        System.out.print("\n");

        s = missionDeck.getCards().get(13).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                System.out.print(s[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        s = missionDeck.getCards().get(14).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                System.out.print(s[i][j]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");

        s = missionDeck.getCards().get(15).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                System.out.print(s[i][j]);
            }
            System.out.print("\n");
        }
    }

    @Test
    void personalBoardCLI1() {
        Game game = new Game(new ArrayList<>());
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        PersonalBoard pb = new PersonalBoard();
        pb.setPosition(0,0);
        pb.playSide(initialDeck.getCards().get(0).getFront());
        pb.setPosition(-1, -1);
        pb.playSide(resourceDeck.getCards().get(10).getBack());
        pb.setPosition(1, -1);
        pb.playSide(resourceDeck.getCards().get(0).getBack());
        pb.setPosition(-1, 1);
        pb.playSide(resourceDeck.getCards().get(20).getBack());
        pb.setPosition(1, 1);
        pb.playSide(resourceDeck.getCards().get(39).getBack());
        pb.setPosition(-2, -2);
        pb.playSide(resourceDeck.getCards().get(10).getFront());
        pb.setPosition(2, -2);
        pb.playSide(resourceDeck.getCards().get(0).getFront());
        pb.setPosition(-2, 2);
        pb.playSide(resourceDeck.getCards().get(20).getFront());
        pb.setPosition(2, 2);
        pb.playSide(resourceDeck.getCards().get(39).getFront());

        pb.showBoard();
    }

    @Test
    void personalBoardCLI2() {
        Game game = new Game(new ArrayList<>());
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        PersonalBoard pb = new PersonalBoard();
        pb.setPosition(0,0);
        pb.playSide(initialDeck.getCards().get(0).getFront());
        pb.setPosition(-1, -1);
        pb.playSide(resourceDeck.getCards().get(38).getFront());
        pb.setPosition(1, -1);
        pb.playSide(resourceDeck.getCards().get(0).getFront());

        pb.showBoard();
        System.out.print("\n");

    }

}
