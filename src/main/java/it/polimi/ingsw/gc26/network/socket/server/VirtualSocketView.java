package it.polimi.ingsw.gc26.network.socket.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.ClientState;
import it.polimi.ingsw.gc26.network.VirtualView;

import java.io.PrintWriter;
import java.util.HashMap;

public class VirtualSocketView implements VirtualView {
    final PrintWriter outputToServer;

    public VirtualSocketView(PrintWriter output) {
        this.outputToServer = output;
    }

    @Override
    public void showMessage(String message) {
        HashMap<String, String> data = VirtualSocketView.getBasicMessage();
        data.replace("function", "showMessage");
        data.replace("value", message);
        try {
            ObjectMapper mappedData = new ObjectMapper();
            this.outputToServer.println(mappedData.writeValueAsString(data));
            this.outputToServer.flush();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void reportMessage(String message) {
        String msg = STR."{\"function\": \"reportMessage\", \"value\" : \" \{message} \"}";
        this.outputToServer.println(msg);
        this.outputToServer.flush();
    }

    @Override
    public void reportError(String errorMessage) {
        String msg = STR."{\"function\": \"reportError\", \"value\" : \" \{errorMessage} \"}";
        this.outputToServer.println(msg);
        this.outputToServer.flush();

    }

    private static HashMap<String, String> getBasicMessage() {
        HashMap<String, String> data = new HashMap<>();
        data.put("function", "");
        data.put("value", "");
        return data;
    }

    public void updateState(ClientState clientState){

    }
}
