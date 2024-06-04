package it.polimi.ingsw.gc26.model.player;

import it.polimi.ingsw.gc26.model.utils.TextStyle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PawnTest {

    @Test
    void getFontColor() {
        Pawn pawn = Pawn.RED;

        assertEquals(TextStyle.RED.getStyleCode(), pawn.getFontColor());
    }
}