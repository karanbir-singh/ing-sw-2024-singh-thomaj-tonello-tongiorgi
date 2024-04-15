package it.polimi.ingsw.gc26.socket;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.Message;

import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.util.Objects;
import java.util.concurrent.Callable;

public class VirtualSocketServer implements VirtualServer {
    private final PrintWriter outputToServer;

    public VirtualSocketServer(BufferedWriter output) {
        this.outputToServer = new PrintWriter(output);
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

    @Override
    public void addMessage(String message)  {
        this.outputToServer.println(message);
        this.outputToServer.flush();
    }

    @Override
    public void sendText(String text) {
        this.outputToServer.println(text);
        this.outputToServer.flush();
    }


}


