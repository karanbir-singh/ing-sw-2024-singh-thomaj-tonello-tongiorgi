package it.polimi.ingsw.gc26.socket;

import it.polimi.ingsw.gc26.GameController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import it.polimi.ingsw.gc26.MainController;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.Message;

/**
 * logica con cui vado a leggere i messaggi, Fa parte del server
 */
public class SocketClientHandler implements VirtualServer {
    final MainController controller;
    final GameController gameController;
    final SocketServer server;
    final BufferedReader inputFromClient;
    final VirtualSocketClient virtualClient;

    public SocketClientHandler(MainController controller, SocketServer server, BufferedReader input, PrintWriter output) {
        this.controller = controller;
        this.server = server;
        this.inputFromClient = input;
        this.virtualClient = new VirtualSocketClient(output);
        System.out.println("New client!");
    }

    public void runVirtualView() throws IOException {
        String line;
        while ((line = inputFromClient.readLine()) != null) {
            Message message = new Message(line);
            System.out.println(message);
            this.controller.addMessage( message);
            this.server.broadCastUpdate(message, this);
            if (gameController) {
                this.virtualClient.showMessage();
            }
        }
    }

    @Override
    public void selectCardFromHand(Card card) {
        this.gameController.selectCardFromHand(card);
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
    public void addMessage(Message message) {

    }
}
