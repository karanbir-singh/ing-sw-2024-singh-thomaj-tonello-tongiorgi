package it.polimi.ingsw.gc26.network.socket.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.network.VirtualView;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.HashMap;

/**
 * This class represents the client for the server
 */
public class VirtualSocketView implements VirtualView {
    /**
     * This represents the print writer to write output to the client
     */
    final PrintWriter outputToClient;

    /**
     * Virtual Socket view constructor. It initializes the print writer to the client
     *
     * @param output print writer to client
     */
    public VirtualSocketView(PrintWriter output) {
        this.outputToClient = output;
    }

    /**
     * Sends message in JSON format to client
     *
     * @param function represents the function to call client side
     * @param value represents a value that is need to the called function
     */
    private void sendToClient(String function, String value) {
        String msg = STR."{\"function\": \"\{function}\", \"value\" : \"\{value}\"}";
        this.outputToClient.println(msg);
        this.outputToClient.flush();
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     *
     * @param message message to show in the client
     */

    public void notifyMessage(String message) {
        sendToClient("notifyMessage", message);
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     *
     * @param message message to be reported in the client
     */

    public void reportMessage(String message) {
        sendToClient("reportMessage", message);
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     *
     * @param errorMessage error message to be reported in the client
     */

    public void reportError(String errorMessage) {
        sendToClient("reportError", errorMessage);
    }

    @Override
    public void showMessage(String message, String clientID) throws RemoteException {

    }

    @Override
    public void showError(String message, String clientID) throws RemoteException {

    }

    /**
     * Encodes the parameters to play this function in the real controller.
     *
     * @param clientState new client's state
     */
    public void updateState(ClientState clientState) {
        sendToClient("updateState", clientState.toString());
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     *
     * @param clientID new client IS
     * @throws RemoteException
     */
    @Override
    public void setClientID(String clientID) throws RemoteException {
        sendToClient("setClientID", clientID);
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     *
     * @throws RemoteException
     */
    @Override
    public void setGameController() throws RemoteException {
        sendToClient("setGameController", null);
    }

    @Override
    public void updateChosenPawn(String pawnColor, String clientID) throws RemoteException {

    }

    @Override
    public void updateSelectedMission(String clientID) throws RemoteException {

    }

    @Override
    public void updateSelectedCardFromHand( String clientID) throws RemoteException {

    }

    @Override
    public void updateSelectedSide(String cardIndex, String clientID) throws RemoteException {

    }

    @Override
    public void updateSelectedPositionOnBoard(String selectedX, String selectedY, String playerID, String success) throws RemoteException {

    }

    @Override
    public void updatePlayedCardFromHand(String clientID, String success) throws RemoteException {

    }

    @Override
    public void updatePoints(String clientID, String points) throws RemoteException {

    }

    @Override
    public void updateSelectedCardFromCommonTable(String clientID, String success) throws RemoteException {

    }

    @Override
    public void showCard(String playerID, String cardSerialization) throws RemoteException {

    }

    @Override
    public void showChat(String message) throws RemoteException {

    }

    @Override
    public void showPersonalBoard(String clientID, String ownerNickname, String personalBoardSerialization) throws RemoteException {

    }

    @Override
    public void updateFirstPlayer(String nickname) throws RemoteException {

    }

    /**
     * Notifies the clients the new game's state
     *
     * @param gameState
     * @throws RemoteException
     */
    @Override
    public void updateGameState(String gameState) throws RemoteException {
        System.out.println(STR."GameState: \{gameState}");
    }
}
