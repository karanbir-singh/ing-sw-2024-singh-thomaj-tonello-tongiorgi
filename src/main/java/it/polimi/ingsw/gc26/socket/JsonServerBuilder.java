package it.polimi.ingsw.gc26.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.polimi.ingsw.gc26.model.game.Message;

import java.util.HashMap;

public class JsonServerBuilder {

    private static HashMap<String, String> getBasicMessage() {
        HashMap<String, String> data = new HashMap<>();
        data.put("function", "");
        data.put("value", "");
        return data;
    }

    public static String buildMessage(Message message) {
        HashMap<String, String> data = JsonServerBuilder.getBasicMessage();
        data.replace("function", "showMessage");
        data.replace("value", message.toJson());
        try {
            ObjectMapper mappedData = new ObjectMapper();
            return mappedData.writeValueAsString(data);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "";
        }

    }
}


