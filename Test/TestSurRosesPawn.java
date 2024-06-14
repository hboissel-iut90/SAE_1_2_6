import boardifier.model.GameStageModel;
import model.RosesPawn;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSurRosesPawn {

    @Test
    public void testGetColor() {
        RosesPawn pawn1 = new RosesPawn(0, Mockito.mock(GameStageModel.class));
        RosesPawn pawn2 = new RosesPawn(1, Mockito.mock(GameStageModel.class));
        RosesPawn pawn3 = new RosesPawn("C", 2, Mockito.mock(GameStageModel.class));

        assertEquals(0, pawn1.getColor());
        assertEquals(1, pawn2.getColor());
        assertEquals(2, pawn3.getColor());
    }

    @Test
    public void testGetCaracter() {
        RosesPawn pawn = new RosesPawn("C", 2, Mockito.mock(GameStageModel.class));

        assertEquals("C", pawn.getText());
    }
}