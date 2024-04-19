package it.polimi.ingsw.gc26.network.socket.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.network.VirtualView;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.HashMap;

public class VirtualSocketView implements VirtualView {
    final PrintWriter outputToClient;

    public VirtualSocketView(PrintWriter output) {
        this.outputToClient = output;
    }

    @Override
    public void showMessage(String message) {
        HashMap<String, String> data = VirtualSocketView.getBasicMessage();
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

    @Override
    public void reportMessage(String message) {
        String msg = STR."{\"function\": \"reportMessage\", \"value\" : \" \{message} \"}";
        this.outputToClient.println(msg);
        this.outputToClient.flush();
    }

    @Override
    public void reportError(String errorMessage) {
        String msg = STR."{\"function\": \"reportError\", \"value\" : \" \{errorMessage} \"}";
        this.outputToClient.println(msg);
        this.outputToClient.flush();

    }

    private static HashMap<String, String> getBasicMessage() {
        HashMap<String, String> data = new HashMap<>();
        data.put("function", "");
        data.put("value", "");
        return data;
    }

    public void updateState(ClientState clientState){
        HashMap<String, String> data = VirtualSocketView.getBasicMessage();
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

    @Override
    public void setClientID(String clientID) throws RemoteException {
        HashMap<String, String> data = VirtualSocketView.getBasicMessage();
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

    @Override
    public void setGameController() throws RemoteException{
        HashMap<String, String> data = VirtualSocketView.getBasicMessage();
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
