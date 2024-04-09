package it.polimi.ingsw.gc26;

import it.polimi.ingsw.gc26.model.game.Game;

import java.rmi.Remote;

public interface GameControllerInterface extends Remote {
    void prepareCommonTable();
    void prepareStarterCards();
    void preparePlayersHand();
    void prepareCommonMissions();
    void prepareSecretMissions();
    void selectSecretMission(int cardIndex, String playerID);
    void setSecretMission(String playerID);
    void setFirstPlayer(String playerID);
    void selectCardFromHand(int cardIndex, String playerID);
    void turnSelectedCardSide(String playerID);
    void selectPositionOnBoard(int selectedX, int selectedY, String playerID);
    void playCardFromHand(String playerID);
    void selectCardFromCommonTable(int cardX, int cardY, String playerID);
    void drawSelectedCard(String playerID);
    void changeTurn();
    Game getGame();
}
