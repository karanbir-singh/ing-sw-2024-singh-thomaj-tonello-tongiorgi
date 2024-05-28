package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.model.card_side.CardBack;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PointTest {

    @Test
    void creation() {
        //     public Point(int x, int y, Map<Integer, Boolean> flags, Side side) {
        Point point = new Point(0, 0, new HashMap<>(), new CardBack());

        assertNotNull(point);
        assertEquals(0, point.getFlags().size());
    }
}