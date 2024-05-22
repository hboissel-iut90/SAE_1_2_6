import boardifier.model.GameStageModel;
import control.RosesController;
import model.RosesBoard;
import model.RosesPawn;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RosesBoardTest {

    @Test
    public void testComputeValidCells_EmptyGridHPawn() {
        RosesBoard rosesBoard = Mockito.mock(RosesBoard.class);
        Mockito.when(rosesBoard.isEmpty()).thenReturn(true);

        List<Point> result = rosesBoard.computeValidCells("H", 0);

        assertTrue(result.isEmpty(), "Expected an empty list when the grid is empty and string is 'H'.");
    }

    @Test
    public void testComputeValidCells_FullHPawn() {
        //RosesController rosesController = Mockito.mock(RosesController.class);
        RosesBoard rosesBoard = Mockito.mock(RosesBoard.class);

        // Create a player with id 0
        // Create a list of pawns
        // use computeValidCells method to get the valid cells
        // here a hero has been played

        assertEquals(0.0, rosesBoard.computeValidCells("H", 1));


    }



}