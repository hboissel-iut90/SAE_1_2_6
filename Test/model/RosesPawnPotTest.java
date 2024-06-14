package model;

import boardifier.model.GameStageModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RosesPawnPotTest {
    // Test the constructor of RosesPawnPot
    @Test
    public void testConstructor() {
        RosesPawnPot pot = new RosesPawnPot(100, 200, Mockito.mock(GameStageModel.class));

        assertEquals("pawnpot", pot.getName(), "Pot name should be 'pawnpot'.");
        assertEquals(100, pot.getX(), "Pot X position should be 100.");
        assertEquals(200, pot.getY(), "Pot Y position should be 200.");
    }
}

