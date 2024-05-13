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
        Game game = new Game(new ArrayList<>());
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        String[][] s;
        Printer printer = new Printer();

        for(int i=0; i<16; i++){
            s = missionDeck.getCards().get(i).getFront().printableSide();
            printer.showEncodedPrintable(s);
            System.out.println("\n");
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

    public void showEncodedPrintable(String[][] printable) throws UnsupportedEncodingException {

        PrintStream ps = new PrintStream(System.out, true, "UTF-8");

        for (String[] row: printable) {
            for (String col: row) {
                ps.print(col);
            }
            ps.print("\n");
        }
    }

}
