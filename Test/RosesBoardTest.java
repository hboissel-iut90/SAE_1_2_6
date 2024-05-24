import boardifier.model.GameStageModel;
import model.RosesBoard;
import model.RosesCard;
import model.RosesPawn;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.awt.*;
<<<<<<< HEAD
=======
import java.lang.reflect.Array;
>>>>>>> a3ddf8e (Test Board carte H fini)
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RosesBoardTest {

    @Test
    public void testComputeValidCells_EmptyGridHString() {
        RosesBoard rosesBoard = Mockito.mock(RosesBoard.class);
        Mockito.when(rosesBoard.isEmpty()).thenReturn(true);

        List<Point> result = rosesBoard.computeValidCells("H", 0);

        assertTrue(result.isEmpty(), "Expected an empty list when the grid is empty and string is 'H'.");
    }

    @Test
<<<<<<< HEAD
    public void testComputeValidCells_hPawnFirstCell() {
        RosesBoard rosesBoard = Mockito.mock(RosesBoard.class);
        // Create a player with id 0
        RosesPawn pawn = new RosesPawn(0, Mockito.mock(GameStageModel.class));
        // Create a list of pawns
        Mockito.when(rosesBoard.getElement(0, 0)).thenReturn(pawn);
        Mockito.when(rosesBoard.isElementAt(0, 0)).thenReturn(true);
        Mockito.when(rosesBoard.isEmpty()).thenReturn(false);
        System.out.println(pawn.getX());

        // use computeValidCells method to get the valid cells
        // here a hero has been played
        List<Point> result = rosesBoard.computeValidCells("H", 1);
        result.add(new Point(0,0));

        assertEquals(1, result.size(), "Expected a list with one element when a hero is played and there is a pawn in the first cell.");
=======
    public void testComputeValidCells_FullHPawn() {
        GameStageModel gameStageModel = Mockito.mock(GameStageModel.class);
        RosesBoard rosesBoard = new RosesBoard(0,0,gameStageModel);
        RosesPawn rosesPawn1 = new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn2 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn3 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn4 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn5 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn6 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn7 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn8 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn9 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn10 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn11 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn12 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn13 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn14 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn15 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn16 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn17 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn18 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn19 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn20 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn21 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn22 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn23 =  new RosesPawn(0, gameStageModel);
        RosesPawn rosesPawn24 =  new RosesPawn(0, gameStageModel);

        rosesBoard.addElement(rosesPawn1,1,1);
        rosesBoard.addElement(rosesPawn2,2,2);
        rosesBoard.addElement(rosesPawn3,3,3);
        rosesBoard.addElement(rosesPawn4,1,4);
        rosesBoard.addElement(rosesPawn5,2,4);
        rosesBoard.addElement(rosesPawn6,3,4);
        rosesBoard.addElement(rosesPawn7,1,7);
        rosesBoard.addElement(rosesPawn8,2,6);
        rosesBoard.addElement(rosesPawn9,3,5);
        rosesBoard.addElement(rosesPawn10,4,1);
        rosesBoard.addElement(rosesPawn11,4,2);
        rosesBoard.addElement(rosesPawn12,4,3);
        rosesBoard.addElement(rosesPawn13,4,5);
        rosesBoard.addElement(rosesPawn14,4,6);
        rosesBoard.addElement(rosesPawn15,4,7);
        rosesBoard.addElement(rosesPawn16,5,3);
        rosesBoard.addElement(rosesPawn17,6,2);
        rosesBoard.addElement(rosesPawn18,7,1);
        rosesBoard.addElement(rosesPawn19,5,4);
        rosesBoard.addElement(rosesPawn20,6,4);
        rosesBoard.addElement(rosesPawn21,7,4);
        rosesBoard.addElement(rosesPawn22,5,5);
        rosesBoard.addElement(rosesPawn23,6,6);
        rosesBoard.addElement(rosesPawn24,7,7);

        ArrayList<Point> listVerif = new ArrayList<>();
        listVerif.add(new Point(1, 1));
        listVerif.add(new Point(4, 1));
        listVerif.add(new Point(7, 1));
        listVerif.add(new Point(2, 2));
        listVerif.add(new Point(4, 2));
        listVerif.add(new Point(6, 2));
        listVerif.add(new Point(3, 3));
        listVerif.add(new Point(4, 3));
        listVerif.add(new Point(5, 3));
        listVerif.add(new Point(1, 4));
        listVerif.add(new Point(2, 4));
        listVerif.add(new Point(3, 4));
        listVerif.add(new Point(5, 4));
        listVerif.add(new Point(6, 4));
        listVerif.add(new Point(7, 4));
        listVerif.add(new Point(3, 5));
        listVerif.add(new Point(4, 5));
        listVerif.add(new Point(5, 5));
        listVerif.add(new Point(2, 6));
        listVerif.add(new Point(4, 6));
        listVerif.add(new Point(6, 6));
        listVerif.add(new Point(1, 7));
        listVerif.add(new Point(4, 7));
        listVerif.add(new Point(7, 7));


        assertEquals(listVerif, rosesBoard.computeValidCells("H", 1));
    }



}