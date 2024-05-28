package it.polimi.ingsw.gc26.model.game;

import com.fasterxml.jackson.core.JsonProcessingException;
import it.polimi.ingsw.gc26.model.player.Player;
import it.polimi.ingsw.gc26.network.ModelObservable;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ChatTest {

    static Chat chat;

    static void beforeAll() {
        chat = new Chat(new ModelObservable());
    }

    @Test
    void getMessages() {
        beforeAll();

        chat.addMessage(new Message(
                "This is a message",
                new Player("0", "user0"),
                new Player("1", "user1"),
                LocalTime.now().toString())
        );

        assertEquals(1,chat.getMessages().size());
        assertNotNull(chat.getMessages().get(0).toJson());

        try {
            Message fromJson = new Message(chat.getMessages().get(0).toJson());
            assertNotNull(fromJson);
        } catch (JsonProcessingException e) {
        }
    }

    @Test
    void filterMessages() {
        beforeAll();

        Player player = new Player("0", "user0");
        chat.addMessage(new Message(
                "This is a message",
                player,
                player,
                LocalTime.now().toString())
        );

        assertEquals(2,chat.filterMessages(player).size());
    }
}