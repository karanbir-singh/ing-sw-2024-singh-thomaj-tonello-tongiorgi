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
     * This method creates the basic structure for this protocol.
     * @return base structure
     */
    private static HashMap<String, String> getBaseMessage() {
        HashMap<String, String> data = new HashMap<>();
        data.put("function", "");
        data.put("value", "");
        return data;
    }

    /**
     * Sends message in JSON format to client
     *
     * @param functionName represents the function to call client side
     * @param valueMsg represents a value that is need to the called function
     */
    private void sendToClient(String functionName, HashMap<String, String> valueMsg) {
        HashMap<String, String> data = getBaseMessage();
        data.replace("function", functionName);
        ObjectMapper mappedmsg = new ObjectMapper();
        try {
            data.replace("value", mappedmsg.writeValueAsString(valueMsg));
            ObjectMapper mappedData = new ObjectMapper();
            this.outputToClient.println(mappedData.writeValueAsString(data));
            this.outputToClient.flush();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public void showMessage(String message,  String clientID) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("message", message);
        msg.put("clientID", clientID);
        sendToClient("showMessage", msg);
    }


    /**
     * Encodes the parameters to play this function in the real controller.
     *
     * @param errorMessage error message to be reported in the client
     */
    @Override
    public void showError(String errorMessage, String clientID) {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("errorMessage", errorMessage);
        msg.put("clientID", clientID);
        sendToClient("showError", msg);
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     *
     * @param clientState new client's state
     */
    @Override
    public void updateState(ClientState clientState) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("clientState", clientState.toString());
        sendToClient("updateState", msg);
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     *
     * @param clientID new client IS
     * @throws RemoteException
     */
    @Override
    public void setClientID(String clientID) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("clientID", clientID);
        sendToClient("setClientID", msg);
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
        HashMap<String, String> msg = new HashMap<>();
        msg.put("pawnColor", pawnColor);
        msg.put("clientID", clientID);
        sendToClient("updateChosenPawn", msg);
    }

    /**
     * To notify the current player the successful selection of its mission
     *
     * @param clientID
     * @throws RemoteException
     */
    @Override
    public void updateSelectedMission(String clientID) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("clientID", clientID);
        sendToClient("updateSelectedMission", msg);
    }

    @Override
    public void updateSelectedCardFromHand( String clientID) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("clientID", clientID);
        sendToClient("updateSelectedCardFromHand", msg);
    }

    @Override
    public void updateSelectedSide(String cardIndex, String clientID) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("cardIndex", cardIndex);
        msg.put("clientID", clientID);
        sendToClient("updateSelectedSide", msg);
    }

    @Override
    public void updateSelectedPositionOnBoard(String selectedX, String selectedY, String playerID, String success) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("selectedX", selectedX);
        msg.put("selectedY", selectedY);
        msg.put("clientID", playerID);
        msg.put("success", success);
        sendToClient("updateSelectedPositionOnBoard", msg);
    }

    @Override
    public void updatePlayedCardFromHand(String clientID, String success) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("clientID", clientID);
        msg.put("success", success);
        sendToClient("updatePlayedCardFromHand", msg);
    }

    @Override
    public void updatePoints(String clientID, String points) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("clientID", clientID);
        msg.put("points", points);
        sendToClient("updatePoints", msg);
    }

    @Override
    public void updateSelectedCardFromCommonTable(String clientID, String success) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("clientID", clientID);
        msg.put("success", success);
        sendToClient("updateSelectedCardFromCommonTable", msg);
    }

    @Override
    public void showCard(String playerID, String cardSerialization) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("clientID", playerID);
        msg.put("cardSerialization", cardSerialization);
        sendToClient("showCard", msg);
    }

    @Override
    public void showChat(String message) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("message", message);
        sendToClient("showChat", msg);
    }

    @Override
    public void showPersonalBoard(String clientID, String ownerNickname, String personalBoardSerialization) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("clientID", clientID);
        msg.put("ownerNickname", ownerNickname);
        msg.put("personalBoardSerialization", personalBoardSerialization);
        sendToClient("showPersonalBoard", msg);
    }

    @Override
    public void updateFirstPlayer(String nickname) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("nickname", nickname);
        sendToClient("updateFirstPlayer", msg);
    }

    /**
     * Notifies the clients the new game's state
     *
     * @param gameState
     * @throws RemoteException
     */
    @Override
    public void updateGameState(String gameState) throws RemoteException {
        HashMap<String, String> msg = new HashMap<>();
        msg.put("gameState", gameState);
        sendToClient("updateGameState", msg);
    }

    /**
     * @return
     * @throws RemoteException
     */
    @Override
    public String getClientID() throws RemoteException {
        sendToClient("getClientID", null);
        return null;
    }

    /**
     * @return
     * @throws RemoteException
     */
    @Override
    public ClientState getState() throws RemoteException {
        sendToClient("getState", null);
        return null;
    }
}
