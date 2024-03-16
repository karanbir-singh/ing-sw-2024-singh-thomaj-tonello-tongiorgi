package it.polimi.ingsw.gc26.Parser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ParserCore {
    private final String filePath;
    public ParserCore(String filePath) {
        this.filePath = filePath;
    }
    private JsonNode getRootObject() {
        try {
            ObjectMapper JsonMapper = new ObjectMapper();
            return JsonMapper.readTree(new FileReader(this.filePath));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public JsonNode getStarterCards() {
        return this.getRootObject().get("StarterCards");
    }

    public JsonNode getObjectiveCards() {
        return getRootObject().get("ObjectiveCards");
    }

    public JsonNode getResourceCards() {
        return getRootObject().get("ResourceCards");
    }

    public JsonNode getGoldCards() {
        return getRootObject().get("GoldCards");
    }

}


