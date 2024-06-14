package model;

import boardifier.model.GameStageModel;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class RosesCardPotTest {

    @Test
    void testConstructorWith4Parameters() {
        RosesCardPot pot = new RosesCardPot(100, 200, 2, 3, Mockito.mock(GameStageModel.class));

        assertEquals("othercardpot", pot.getName(), "Pot name should be 'othercardpot'.");
        assertEquals(2, pot.getWidth(), "Pot width should be 2.");
        assertEquals(3, pot.getHeight(), "Pot height should be 3.");
        assertEquals(100, pot.getX(), "Pot X position should be 100.");
        assertEquals(200, pot.getY(), "Pot Y position should be 200.");
    }

    @Test
    void testConstructorWith3Parameters() {
        RosesCardPot pot = new RosesCardPot(300, 400, Mockito.mock(GameStageModel.class));

        assertEquals("moovcardpot", pot.getName(), "Pot name should be 'moovcardpot'.");
        assertEquals(300, pot.getX(), "Pot X position should be 300.");
        assertEquals(400, pot.getY(), "Pot Y position should be 400.");
    }

    // Add more tests for functionality involving adding elements, etc.
}
