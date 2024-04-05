package it.polimi.ingsw.gc26.socket;

import it.polimi.ingsw.gc26.model.card.Card;

import java.io.BufferedWriter;
import java.io.PrintWriter;

public class VirtualSocketServer implements VirtualServer {
    private final PrintWriter output;

    public VirtualSocketServer(BufferedWriter output) {
        this.output = new PrintWriter(output);
    }

    @Override
    public void selectCardFromHand(Card card) {

    }

    @Override
    public void turnSelectedCardSide() {

    }

    @Override
    public void selectPositionOnBoard(int x, int y) {

    }

    @Override
    public void playCardFromHand() {

    }

    @Override
    public void selectCardFromCommonTable(Card card) {

    }

    @Override
    public void drawSelectedCard() {

    }

}
