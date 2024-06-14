package model;

import boardifier.model.GameStageModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSurRosesPawn {

    @Test
    public void testGetColor() {
        RosesPawn pawn1 = new RosesPawn(RosesPawn.PAWN_BLUE, Mockito.mock(GameStageModel.class));
        RosesPawn pawn2 = new RosesPawn(RosesPawn.PAWN_RED, Mockito.mock(GameStageModel.class));
        RosesPawn pawn3 = new RosesPawn("C", RosesPawn.PAWN_YELLOW, Mockito.mock(GameStageModel.class));

        assertEquals(0, pawn1.getColor());
        assertEquals(1, pawn2.getColor());
        assertEquals(2, pawn3.getColor());
    }

    @Test
    public void testGetCaracter() {
        RosesPawn pawn = new RosesPawn("C", RosesPawn.PAWN_YELLOW, Mockito.mock(GameStageModel.class));

        assertEquals("C", pawn.getText());
    }
}