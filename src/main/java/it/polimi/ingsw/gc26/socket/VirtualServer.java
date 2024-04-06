package it.polimi.ingsw.gc26.socket;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.Message;

public interface VirtualServer {
    void selectCardFromHand(Card card);
    void turnSelectedCardSide();
    void selectPositionOnBoard(int x, int y);
    void playCardFromHand();
    void selectCardFromCommonTable(Card card);
    void drawSelectedCard();

    void addMessage(Message message);

}
