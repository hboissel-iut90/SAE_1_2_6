package model;

import boardifier.model.GameStageModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RosesBoardTest {

    private GameStageModel gameStageModel;
    private RosesBoard rosesBoard;

    @BeforeEach
    void setUp() {
        gameStageModel = mock(GameStageModel.class);
        rosesBoard = new RosesBoard(0, 0, gameStageModel);
    }

    @Test
    void testSetValidCells_emptyBoard() {
        rosesBoard.setValidCells(1);
        // Check that all cells are reachable
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Assertions.assertTrue(rosesBoard.isEmptyAt(j, i), "Cell (" + j + ", " + i + ") should be reachable on an empty board.");
            }
        }
    }

    @Test
    void testComputeValidCells_emptyBoard() {
        List<Point> validCells = rosesBoard.computeValidCells(1);
        // Check that all cells are returned
        assertEquals(81, validCells.size(), "All cells should be valid on an empty board.");
    }

    @Test
    void testComputeValidCells_withAdjacentPawns() {
        RosesPawn pawn1 = Mockito.mock(RosesPawn.class);
        RosesPawn pawn2 = Mockito.mock(RosesPawn.class);

        Mockito.when(pawn1.getNumber()).thenReturn(1);
        Mockito.when(pawn2.getNumber()).thenReturn(2);

        rosesBoard.addElement(pawn1, 4, 4);
        rosesBoard.addElement(pawn2, 4, 5);

        List<Point> validCells = rosesBoard.computeValidCells(1);
        // Check that cells adjacent to the pions are calculated correctly
        Assertions.assertTrue(validCells.contains(new Point(3, 3)), "Cell (3, 3) should be valid.");
        Assertions.assertTrue(validCells.contains(new Point(3, 4)), "Cell (3, 4) should be valid.");
        Assertions.assertTrue(validCells.contains(new Point(3, 5)), "Cell (3, 5) should be valid.");
        Assertions.assertTrue(validCells.contains(new Point(4, 3)), "Cell (4, 3) should be valid.");
        Assertions.assertTrue(validCells.contains(new Point(5, 3)), "Cell (5, 3) should be valid.");
        Assertions.assertTrue(validCells.contains(new Point(5, 4)), "Cell (5, 4) should be valid.");
        Assertions.assertTrue(validCells.contains(new Point(5, 5)), "Cell (5, 5) should be valid.");
    }

    @Test
    void testSetValidCells_withAdjacentPawns() {
        RosesPawn pawn1 = mock(RosesPawn.class);
        RosesPawn pawn2 = mock(RosesPawn.class);

        Mockito.when(pawn1.getNumber()).thenReturn(1);
        Mockito.when(pawn2.getNumber()).thenReturn(2);

        rosesBoard.addElement(pawn1, 4, 4);
        rosesBoard.addElement(pawn2, 4, 5);

        rosesBoard.setValidCells(1);

        // Check that the correct cells are reachable
        assertTrue(rosesBoard.isEmptyAt(3, 3), "Cell (3, 3) should be reachable.");
        assertTrue(rosesBoard.isEmptyAt(3, 4), "Cell (3, 4) should be reachable.");
        assertTrue(rosesBoard.isEmptyAt(3, 5), "Cell (3, 5) should be reachable.");
        assertTrue(rosesBoard.isEmptyAt(4, 3), "Cell (4, 3) should be reachable.");
        assertTrue(rosesBoard.isEmptyAt(5, 3), "Cell (5, 3) should be reachable.");
        assertTrue(rosesBoard.isEmptyAt(5, 4), "Cell (5, 4) should be reachable.");
        assertTrue(rosesBoard.isEmptyAt(5, 5), "Cell (5, 5) should be reachable.");
    }
}
