package it.polimi.ingsw.gc26;

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

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Printer {
    public static void main(String[] args) throws UnsupportedEncodingException {
        PrintStream ps = new PrintStream(System.out, true, "UTF-8");
        Game game = new Game(new ArrayList<>());
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        String[][] s = missionDeck.getCards().get(0).getFront().printableSide();
        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                ps.print(s[i][j]);
            }
            ps.print("\n");
        }
        ps.print("\n");

        s = missionDeck.getCards().get(2).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                ps.print(s[i][j]);
            }
            ps.print("\n");
        }
        ps.print("\n");

        s = missionDeck.getCards().get(1).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                ps.print(s[i][j]);
            }
            ps.print("\n");
        }
        ps.print("\n");

        s = missionDeck.getCards().get(3).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                ps.print(s[i][j]);
            }
            ps.print("\n");
        }
        ps.print("\n");

        s = missionDeck.getCards().get(4).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                ps.print(s[i][j]);
            }
            ps.print("\n");
        }
        ps.print("\n");

        s = missionDeck.getCards().get(5).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                ps.print(s[i][j]);
            }
            ps.print("\n");
        }
        ps.print("\n");

        s = missionDeck.getCards().get(6).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                ps.print(s[i][j]);
            }
            ps.print("\n");
        }
        ps.print("\n");

        s = missionDeck.getCards().get(7).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                ps.print(s[i][j]);
            }
            ps.print("\n");
        }

        ps.print("\n");

        s = missionDeck.getCards().get(8).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                ps.print(s[i][j]);
            }
            ps.print("\n");
        }

        ps.print("\n");

        s = missionDeck.getCards().get(9).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                ps.print(s[i][j]);
            }
            ps.print("\n");
        }

        ps.print("\n");

        s = missionDeck.getCards().get(10).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                ps.print(s[i][j]);
            }
            ps.print("\n");
        }
        ps.print("\n");

        s = missionDeck.getCards().get(11).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                ps.print(s[i][j]);
            }
            ps.print("\n");
        }

        ps.print("\n");

        s = missionDeck.getCards().get(12).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                ps.print(s[i][j]);
            }
            ps.print("\n");
        }

        ps.print("\n");

        s = missionDeck.getCards().get(13).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                ps.print(s[i][j]);
            }
            ps.print("\n");
        }
        ps.print("\n");

        s = missionDeck.getCards().get(14).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                ps.print(s[i][j]);
            }
            ps.print("\n");
        }
        ps.print("\n");

        s = missionDeck.getCards().get(15).getFront().printableSide();

        for(int i=0; i<5; i++){
            for(int j=0; j<3; j++){
                ps.print(s[i][j]);
            }
            ps.print("\n");
        }
    }

    public void showPrintable(String[][] printable){
        for (String[] row: printable) {
            for (String col: row) {
                System.out.print(col);
            }
            System.out.print("\n");
        }
    }

}
