package it.polimi.ingsw.gc26.socket;

import it.polimi.ingsw.gc26.model.card.Card;

public interface VirtualServer {
    void selectCardFromHand(Card card);
    void turnSelectedCardSide();
    void selectPositionOnBoard(int x, int y);
    void playCardFromHand();
    void selectCardFromCommonTable(Card card);
    void drawSelectedCard();

}
