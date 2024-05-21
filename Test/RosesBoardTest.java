import boardifier.model.GameStageModel;
import model.RosesBoard;
import model.RosesCard;
import model.RosesPawn;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RosesBoardTest {

    @Test
    public void testComputeValidCells_EmptyGridHString() {
        RosesBoard rosesBoard = Mockito.mock(RosesBoard.class);
        Mockito.when(rosesBoard.isEmpty()).thenReturn(true);

        List<Point> result = rosesBoard.computeValidCells("H", 0);

        assertTrue(result.isEmpty(), "Expected an empty list when the grid is empty and string is 'H'.");
    }

    @Test
    public void testComputeValidCells_hPawnFirstCell() {
        RosesBoard rosesBoard = Mockito.mock(RosesBoard.class);
        // Create a player with id 0
        RosesPawn pawn = new RosesPawn(0, Mockito.mock(GameStageModel.class));
        // Create a list of pawns
        Mockito.when(rosesBoard.getElement(0, 0)).thenReturn(pawn);
        Mockito.when(rosesBoard.isElementAt(0, 0)).thenReturn(true);
        Mockito.when(rosesBoard.isEmpty()).thenReturn(false);


        // use computeValidCells method to get the valid cells
        // here a hero has been played
        List<Point> result = rosesBoard.computeValidCells("H", 1);


        assertEquals(1, result.size(), "Expected a list with one element when a hero is played and there is a pawn in the first cell.");
    }



}
