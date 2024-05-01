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

    @Override
    public ClientState getState() throws RemoteException {
        return null;
    }

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
    @Override
    public void notifyMessage(String message) {
        sendToClient("notifyMessage", message);
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     *
     * @param message message to be reported in the client
     */
    @Override
    public void reportMessage(String message) {
        sendToClient("reportMessage", message);
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     *
     * @param errorMessage error message to be reported in the client
     */
    @Override
    public void reportError(String errorMessage) {
        sendToClient("reportError", errorMessage);
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
}
