package it.polimi.ingsw.gc26.Parser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.databind.node.ArrayNode;
import it.polimi.ingsw.gc26.model.card.Card;
import it.polimi.ingsw.gc26.model.card_side.*;

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

    public ArrayList<Card> getGoldCards() {
        JsonNode goldCardsJson = getRootObject().get("GoldCards");
        ArrayList<Card> goldCardDeck = new ArrayList<>();
        for ( JsonNode cardJson : goldCardsJson) {
            JsonNode frontCardJson = cardJson.get("Front");
            JsonNode backCardJson = cardJson.get("Back");
            HashMap<JsonNode, Integer> requestedResources = new HashMap<>();
            for (JsonNode resource : frontCardJson.get("Requirements")) {
                requestedResources.put(resource, 1);
            }
            if (frontCardJson.get("Constraint").asText().equals("Corner")) {
                //Side front = CornerCounter();
            } else if (frontCardJson.get("Constraint").asText().equals("Inkwell")) {
                // d
            } else if (frontCardJson.get("Constraint").asText().equals("Quill")) {
                Side front = new QuillCounter(backCardJson.get("Resource"), null, requestedResources, frontCardJson.get("CornerUpSx"))
            } else if (frontCardJson.get("Constraint").asText().equals("Manuscript")) {
                //d
            } else {
                //s
            }
            Side front = new GoldCardFront(backCardJson.get("Resource"), null, requestedResources, frontCardJson.get());
            //Side back = new CardBack();

        }
        return null;
    }

}


