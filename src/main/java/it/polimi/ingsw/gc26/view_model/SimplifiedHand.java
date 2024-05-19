package it.polimi.ingsw.gc26.view_model;

import it.polimi.ingsw.gc26.client.Printer;
import it.polimi.ingsw.gc26.model.card.*;
import it.polimi.ingsw.gc26.model.card_side.Side;
import it.polimi.ingsw.gc26.model.card_side.Symbol;
import it.polimi.ingsw.gc26.model.card_side.ability.CornerCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.InkwellCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.ManuscriptCounter;
import it.polimi.ingsw.gc26.model.card_side.ability.QuillCounter;
import it.polimi.ingsw.gc26.model.utils.SpecialCharacters;
import it.polimi.ingsw.gc26.model.utils.TextStyle;

import java.io.Serializable;
import java.util.ArrayList;

public class SimplifiedHand implements Serializable {
    /**
     * This attribute represent the current cards in the hand
     */
    private final ArrayList<Card> cards;
    /**
     * This attribute represents the selected card to be played
     */
    private Card selectedCard;
    /**
     * This attribute represent the selected side of the selected card to be played
     */
    private Side selectedSide;

    /**
     * Initializes the hand for the player
     *
     * @param cards new cards in hand
     */
    public SimplifiedHand(ArrayList<Card> cards, Card selectedCard, Side selectedSide) {
        this.cards = cards;
        this.selectedCard = selectedCard;
        this.selectedSide = selectedSide;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public Card getSelectedCard() {
        return selectedCard;
    }

    public Side getSelectedSide() {
        return selectedSide;
    }

    /**
     * Creates a String matrix with a printable representation of the hand
     */
    public String[][] printableHand() {
        if(cards.isEmpty()){
            String[][] hand = new String[1][1];
            hand[0][0] = "Your hand is empty, the cards will be distributed after the game setup.\n";
            return hand;
        } else {
            //utils
            String selectedStyle = TextStyle.BACKGROUND_BEIGE.getStyleCode() + TextStyle.BLACK.getStyleCode();
            Printer printer = new Printer();
            String blackSquare = SpecialCharacters.SQUARE_BLACK.getCharacter();

            if(cards.get(0) instanceof MissionCard){
                //MISSION HAND

                //calculate dimensions
                int xCardDim = cards.get(0).getFront().printableSide()[0].length;
                int yCardDim = cards.get(0).getFront().printableSide().length;
                int xMax = (xCardDim + 1) * 2;
                int yMax = yCardDim + 1;
                //cursors
                int y=0, x=0;
                //initialize matrix
                String[][] myHand = new String[yMax][xMax];
                for(int j=0; j<yMax; j++){
                    for(int i=0; i<xMax; i++){
                        myHand[j][i] = " ";
                    }
                }
                //titles
                for(int i=0; i<2; i++) {
                    if(this.selectedCard == cards.get(i)){
                        myHand[y][x] = " " + selectedStyle + " Mission  " + i + " \u001B[0m  ";
                    } else {
                        myHand[y][x] = "  Mission " + i + "   ";
                    }
                    x++;
                }
                x = 0;
                y++;
                //printable cards
                for(Card c: cards){
                    printer.addPrintable(c.getFront().printableSide(), myHand, x, y);
                    x += xCardDim;
                    for(int j=0; j<yCardDim; j++){
                        myHand[y+j][x] = "        ";
                    }
                }

                return myHand;

            } else {
                //NORMAL HAND

                //calculate dimensions
                int xCardDim = cards.get(0).getFront().printableSide()[0].length;
                int yCardDim = cards.get(0).getFront().printableSide().length;
                int xMax = (xCardDim + 1) * 3 + 1;
                int yMax = yCardDim + 4;

                //index to select the card
                int index = 0;

                //initialize empty matrix
                String[][] myHand = new String[yMax][xMax];
                for(int j=0; j<yMax; j++){
                    for(int i=0; i<xMax; i++){
                        myHand[j][i] = " ";
                    }
                }

                //titles
                myHand[0][0] = "Card:      ";
                for(int i=1; i<4; i++){
                    myHand[i][0] = "            ";
                }
                myHand[4][0] = "Type:     ";
                myHand[5][0] = "Points:   ";
                myHand[6][0] = "Requires: ";

                for(int i=0; i<3; i++) {
                    int x = 1 + i*(xCardDim+1);
                    int y = 0;
                    if (i < cards.size()) {
                        Card c = cards.get(i);

                        //titles
                        if (c == this.selectedCard) {
                            if (this.selectedSide == c.getBack()) {
                                myHand[y][x] =  index + ") " + selectedStyle + " Back  \u001B[0m   ";
                            } else {
                                myHand[y][x] = index + ") " + selectedStyle + " Front \u001B[0m   ";
                            }
                        } else {
                            myHand[y][x] = index + ")" + "  Front    ";
                        }
                        myHand[y][x] = myHand[y][x] + blackSquare + blackSquare + blackSquare;
                        y++;

                        //printable side
                        if (c == this.selectedCard && this.selectedSide.equals(c.getBack())) {
                            printer.addPrintable(c.getBack().printableSide(), myHand, x, y);
                        } else {
                            printer.addPrintable(c.getFront().printableSide(), myHand, x, y);
                        }
                        x += xCardDim;
                        for (int j = 0; j < yCardDim; j++) {
                            myHand[y + j][x] = "          ";
                        }
                        y += yCardDim;

                        //card type
                        if (c instanceof GoldCard) {
                            myHand[y][x] = "Gold         ";
                        } else if (c instanceof ResourceCard) {
                            myHand[y][x] = "Resource     ";
                        } else if (c instanceof StarterCard) {
                            myHand[y][x] = "Starter      ";
                        }
                        myHand[y][x] = myHand[y][x] + blackSquare + blackSquare + blackSquare;
                        y++;

                        //points
                        if (!(c == this.selectedCard && this.selectedSide.equals(c.getBack()))) {
                            switch (c.getFront()) {
                                case CornerCounter cornerCounter -> myHand[y][x] = "2 pt " + "x" + Character.toString(0x2B1C);
                                case InkwellCounter inkwellCounter -> myHand[y][x] = "1 pt " + "x" + Symbol.INKWELL.getAlias();
                                case ManuscriptCounter manuscriptCounter -> myHand[y][x] = "1 pt " + "x" + Symbol.MANUSCRIPT.getAlias();
                                case QuillCounter quillCounter -> myHand[y][x] = "1 pt " + "x" + Symbol.QUILL.getAlias();
                                case null, default -> myHand[y][x] = c.getFront().getPoints() + " pt " + "        ";
                            }
                            if (c.getFront() instanceof CornerCounter || c.getFront() instanceof InkwellCounter || c.getFront() instanceof ManuscriptCounter || c.getFront() instanceof QuillCounter) {
                                myHand[y][x] = myHand[y][x] + "       " + blackSquare + blackSquare;
                            } else {
                                myHand[y][x] = myHand[y][x] + blackSquare + blackSquare + blackSquare;
                            }

                        } else {
                            myHand[y][x] = "0 pt         " + blackSquare + blackSquare + blackSquare;
                        }
                        y++;

                        //requirements
                        myHand[y][x] = "";
                        int n;
                        int spaces = 5;

                        if(!(c == this.selectedCard && this.selectedSide.equals(c.getBack()))){
                            for(Symbol s: c.getFront().getRequestedResources().keySet()){
                                n = c.getFront().getRequestedResources().get(s);
                                for(int j=0; j<n; j++){
                                    myHand[y][x] = myHand[y][x] + s.getAlias();
                                }
                                spaces = spaces - n;
                            }
                        }
                        while (spaces>0){
                            myHand[y][x] = myHand[y][x] +  blackSquare;
                            spaces--;
                        }
                        myHand[y][x] = myHand[y][x] + "      "+ blackSquare ;

                    } else if(!(!cards.isEmpty() && cards.get(0) instanceof StarterCard)) {
                        //EMPTY CARDS
                        myHand[y][x] = "            ";
                        y++;
                        //add empty printable
                        printer.addPrintable(printer.emptyPrintable(xCardDim, yCardDim), myHand, x, y);
                        x += xCardDim;
                        for (int j = 0; j < yCardDim; j++) {
                            myHand[y + j][x] = "        ";
                        }
                        y += yCardDim;

                        //type
                        myHand[y][x] = "           " + blackSquare + blackSquare + blackSquare;
                        y++;

                        //points
                        myHand[y][x] = "           " + blackSquare + blackSquare + blackSquare;
                        y++;

                        //requirements
                        myHand[y][x] = blackSquare + blackSquare + blackSquare + blackSquare + blackSquare + "    "+ blackSquare;
                    }
                    index++;
                }

                //align right border
                for(int i=yCardDim+1; i<yMax; i++){
                    myHand[i][xMax-1] = myHand[i][xMax-1] + "  ";
                }

                return myHand;
            }
        }
    }
}
