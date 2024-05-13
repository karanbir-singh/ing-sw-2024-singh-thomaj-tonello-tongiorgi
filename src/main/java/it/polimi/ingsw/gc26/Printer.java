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
import it.polimi.ingsw.gc26.model.utils.SpecialCharacters;
import it.polimi.ingsw.gc26.model.utils.TextStyle;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Printer {
    public static void main(String[] args) throws UnsupportedEncodingException {
        Game game = new Game(new ArrayList<>(), new ArrayList<>());
        Deck missionDeck = game.getCommonTable().getMissionDeck();
        String[][] s;
        Printer printer = new Printer();

        for(int i=0; i<16; i++){
            s = missionDeck.getCards().get(i).getFront().printableSide();
            printer.showEncodedPrintable(s);
            System.out.println("\n");
        }
    }

    public void addPrintable(String[][] printable, String[][] context, int xBase, int yBase){
        int y=0, x;

        for(String[] row: printable){
            x=0;
            for(String col: row){
                context[yBase + y][xBase + x] = col;
                x++;
            }
            y++;
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

    public String[][] emptyPrintable(int xCardDim, int yCardDim){
        String[][] s = new String[yCardDim][xCardDim];

        String decoration = SpecialCharacters.SQUARE_BLACK.getCharacter();
        String backgroundSymbol = SpecialCharacters.SQUARE_BLACK.getCharacter();
        String blank = SpecialCharacters.BACKGROUND_BLANK_WIDE.getCharacter();
        String backgroundColor = TextStyle.BACKGROUND_BLACK.getStyleCode();
        String reset = TextStyle.STYLE_RESET.getStyleCode();

        //corners
        s[0][0] = backgroundSymbol;
        s[0][xCardDim - 1] = backgroundSymbol;
        s[yCardDim - 1][0] = backgroundSymbol;
        s[yCardDim - 1][xCardDim - 1] = backgroundSymbol;

        //decoration
        s[0][xCardDim/2] = blank + decoration  + blank;
        s[yCardDim/2][xCardDim/2] = decoration + decoration + decoration;
        s[yCardDim - 1][xCardDim/2] = blank + decoration  + blank;

        //rest of the card
        for(int i=0; i<yCardDim; i++){
            for(int j=0; j<xCardDim; j++){
                if(s[i][j] == null){
                    s[i][j] = blank;
                }
                s[i][j] = backgroundColor + s[i][j] + reset;
            }
        }

        return s;
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
