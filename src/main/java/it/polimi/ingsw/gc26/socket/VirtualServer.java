package it.polimi.ingsw.gc26.socket;

import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.game.Message;

public interface VirtualServer {
    void selectCardFromHand(int cardIndex, String playerID);
    void turnSelectedCardSide(String playerID);
    void selectPositionOnBoard(int x, int y,String playerID);
    void playCardFromHand(String playerID);
    void selectCardFromCommonTable(int cardX, int cardY, String playerID);
    void drawSelectedCard(String playerID);

    void addMessage(String line, String nicknameReceiver,String nicknameSender, String time);
    void sendText(String text);

}

