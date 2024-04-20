package it.polimi.ingsw.gc26.model.player;


import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.deck.Deck;
import it.polimi.ingsw.gc26.model.card_side.Side;
import it.polimi.ingsw.gc26.model.game.Game;
import it.polimi.ingsw.gc26.model.hand.Hand;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CLITest {
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    @Test
    public void showHand(){
        Game game = new Game(new ArrayList<>());
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
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
    public void missionCardAesthetic(){
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
    public void printSide(){
        Game game = new Game(new ArrayList<>());
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();

        Side starter = initialDeck.getCards().get(3).getFront();
        Side starter1 = initialDeck.getCards().get(4).getFront();
        Side s1 = resourceDeck.getCards().get(0).getBack();
        Side s5 = goldDeck.getCards().get(10).getFront();
        Side s6 = goldDeck.getCards().get(1).getFront();
        Side s2 = resourceDeck.getCards().get(30).getFront();
        Side s3 = goldDeck.getCards().get(0).getFront();
        Side s4 = goldDeck.getCards().get(30).getFront();

        /*starter.printSide();
        System.out.print("\n");
        starter1.printSide();
        System.out.print("\n");
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
        */

    }


    @Test
    void personalBoardAesthetic() {
        Game game = new Game(new ArrayList<>());
        Deck goldDeck = game.getCommonTable().getGoldDeck();
        Deck resourceDeck = game.getCommonTable().getResourceDeck();
        Deck initialDeck = game.getCommonTable().getStarterDeck();
        PersonalBoard pb = new PersonalBoard(initialDeck.getCards().get(0).getFront());
        pb.setPosition(-1, -1);
        pb.playSide(resourceDeck.getCards().get(38).getFront());
        pb.setPosition(1, -1);
        pb.playSide(resourceDeck.getCards().get(0).getFront());

        pb.showBoard();
        System.out.print("\n");

    }

}
