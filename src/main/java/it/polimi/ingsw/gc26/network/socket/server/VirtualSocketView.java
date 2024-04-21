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
     * @param output print writer to client
     */
    public VirtualSocketView(PrintWriter output) {
        this.outputToClient = output;
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     * @param message message to show in the client
     */
    @Override
    public void notifyMessage(String message) {
        HashMap<String, String> data = VirtualSocketView.getBaseMessage();
        data.replace("function", "showMessage");
        data.replace("value", message);
        try {
            ObjectMapper mappedData = new ObjectMapper();
            this.outputToClient.println(mappedData.writeValueAsString(data));
            this.outputToClient.flush();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     * @param message message to be reported in the client
     */
    @Override
    public void reportMessage(String message) {
        String msg = STR."{\"function\": \"reportMessage\", \"value\" : \" \{message} \"}";
        this.outputToClient.println(msg);
        this.outputToClient.flush();
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     * @param errorMessage error message to be reported in the client
     */
    @Override
    public void reportError(String errorMessage) {
        String msg = STR."{\"function\": \"reportError\", \"value\" : \" \{errorMessage} \"}";
        this.outputToClient.println(msg);
        this.outputToClient.flush();

    }

    /**
     * This method creates the base message for the protocol
     * @return base message
     */
    private static HashMap<String, String> getBaseMessage() {
        HashMap<String, String> data = new HashMap<>();
        data.put("function", "");
        data.put("value", "");
        return data;
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     * @param clientState new client's state
     */
    public void updateState(ClientState clientState){
        HashMap<String, String> data = VirtualSocketView.getBaseMessage();
        data.replace("function", "updateState");
        data.replace("value", clientState.toString());
        try {
            ObjectMapper mappedData = new ObjectMapper();
            this.outputToClient.println(mappedData.writeValueAsString(data));
            this.outputToClient.flush();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     * @param clientID new client IS
     * @throws RemoteException
     */
    @Override
    public void setClientID(String clientID) throws RemoteException {
        HashMap<String, String> data = VirtualSocketView.getBaseMessage();
        data.replace("function", "setClientID");
        data.replace("value", clientID);
        try {
            ObjectMapper mappedData = new ObjectMapper();
            this.outputToClient.println(mappedData.writeValueAsString(data));
            this.outputToClient.flush();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    /**
     * Encodes the parameters to play this function in the real controller.
     * @throws RemoteException
     */
    @Override
    public void setGameController() throws RemoteException{
        HashMap<String, String> data = VirtualSocketView.getBaseMessage();
        data.replace("function", "setGameController");
        try {
            ObjectMapper mappedData = new ObjectMapper();
            this.outputToClient.println(mappedData.writeValueAsString(data));
            this.outputToClient.flush();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
