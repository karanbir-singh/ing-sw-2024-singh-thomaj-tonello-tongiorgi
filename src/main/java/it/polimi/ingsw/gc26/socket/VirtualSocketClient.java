package it.polimi.ingsw.gc26.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.model.game.Message;

import java.io.PrintWriter;
import java.util.HashMap;

public class VirtualSocketClient implements  VirtualView {
    final PrintWriter outputToServer;

    public VirtualSocketClient(PrintWriter output) {
        this.outputToServer = output;
    }

    @Override
    public void showMessage(String message) {
        HashMap<String, String> data = VirtualSocketClient.getBasicMessage();
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
}
