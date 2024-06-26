import boardifier.model.GameStageModel;
import model.RosesBoard;
import model.RosesPawn;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class RosesBoardTest {
    GameStageModel gameStageModel;
    RosesBoard rosesBoard;
    ArrayList<Point> listVerif;
    RosesPawn rosesPawn1, rosesPawn2, rosesPawn3, rosesPawn4, rosesPawn5, rosesPawn6, rosesPawn7, rosesPawn8, rosesPawn9, rosesPawn10,rosesPawn11,rosesPawn12,rosesPawn13,rosesPawn14,rosesPawn15,rosesPawn16,rosesPawn17,rosesPawn18,rosesPawn19,rosesPawn20,rosesPawn21,rosesPawn22,rosesPawn23,rosesPawn24;
    @BeforeEach
    public void setUp(){
        gameStageModel = Mockito.mock(GameStageModel.class);
        rosesBoard = new RosesBoard(0,0,gameStageModel);

        listVerif = new ArrayList<>();

        rosesPawn1 = new RosesPawn(0, gameStageModel);
        rosesPawn2 =  new RosesPawn(0, gameStageModel);
        rosesPawn3 =  new RosesPawn(0, gameStageModel);
        rosesPawn4 =  new RosesPawn(0, gameStageModel);
        rosesPawn5 =  new RosesPawn(0, gameStageModel);
        rosesPawn6 =  new RosesPawn(0, gameStageModel);
        rosesPawn7 =  new RosesPawn(0, gameStageModel);
        rosesPawn8 =  new RosesPawn(0, gameStageModel);
        rosesPawn9 =  new RosesPawn(0, gameStageModel);
        rosesPawn10 =  new RosesPawn(0, gameStageModel);
        rosesPawn11 =  new RosesPawn(0, gameStageModel);
        rosesPawn12 =  new RosesPawn(0, gameStageModel);
        rosesPawn13 =  new RosesPawn(0, gameStageModel);
        rosesPawn14 =  new RosesPawn(0, gameStageModel);
        rosesPawn15 =  new RosesPawn(0, gameStageModel);
        rosesPawn16 =  new RosesPawn(0, gameStageModel);
        rosesPawn17 =  new RosesPawn(0, gameStageModel);
        rosesPawn18 =  new RosesPawn(0, gameStageModel);
        rosesPawn19 =  new RosesPawn(0, gameStageModel);
        rosesPawn20 =  new RosesPawn(0, gameStageModel);
        rosesPawn21 =  new RosesPawn(0, gameStageModel);
        rosesPawn22 =  new RosesPawn(0, gameStageModel);
        rosesPawn23 =  new RosesPawn(0, gameStageModel);
        rosesPawn24 =  new RosesPawn(0, gameStageModel);
    }

    // In this test, we verify when no pawn is on the board
    @Test
    void testComputeValidCells_EmptyGridString() {
        List<Point> result = rosesBoard.computeValidCells("H", 0);
        List<Point> result2 = rosesBoard.computeValidCells("M", 0);

        assertTrue(result.isEmpty(), "Expected an empty list when the grid is empty and string is 'H'.");
        assertFalse(result2.isEmpty(),"Expected at least one element in list when the grid is empty and string is 'M'.");
    }


    // In this test, we verify if the crown pawn can use Hero card in any move
    // (crown pawn don't matter because computeValidCells doesn't considerate crown pawn)
    @Test
    void testComputeValidCells_FullPossibleMoveHPawnWithCPawnInMiddle() {
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

    @Disabled("Cette fonction de limite n'est pas possible dû à boardifier et à l'exception ArrayIndexOutOfBoundsException.")
    @Test
    void testComputeValidCells_MoveHPawnWithCPawnInCornerLeftUp() {
        rosesBoard.addElement(rosesPawn1,-1,-1);
        rosesBoard.addElement(rosesPawn2,0,-1);
        rosesBoard.addElement(rosesPawn3,-1,0);
        rosesBoard.addElement(rosesPawn4,1,0);
        rosesBoard.addElement(rosesPawn5,2,0);
        rosesBoard.addElement(rosesPawn6,3,0);
        rosesBoard.addElement(rosesPawn7,0,1);
        rosesBoard.addElement(rosesPawn8,1,1);
        rosesBoard.addElement(rosesPawn9,0,2);
        rosesBoard.addElement(rosesPawn10,2,2);
        rosesBoard.addElement(rosesPawn11,0,3);
        rosesBoard.addElement(rosesPawn12,3,3);

        listVerif.add(new Point(1,0));
        listVerif.add(new Point(2, 0));
        listVerif.add(new Point(3, 0));
        listVerif.add(new Point(0, 1));
        listVerif.add(new Point(1, 1));
        listVerif.add(new Point(0, 2));
        listVerif.add(new Point(2, 2));
        listVerif.add(new Point(0, 3));
        listVerif.add(new Point(3, 3));


        assertEquals(listVerif, rosesBoard.computeValidCells("H", 1));
    }


    @Test
    void testComputeValidCells_MoveHPawnWithCPawnInCornerRightDown() {
        rosesBoard.addElement(rosesPawn1,5,5);
        rosesBoard.addElement(rosesPawn2,8,5);
        rosesBoard.addElement(rosesPawn3,6,6);
        rosesBoard.addElement(rosesPawn4,8,6);
        rosesBoard.addElement(rosesPawn5,7,7);
        rosesBoard.addElement(rosesPawn6,8,7);
        rosesBoard.addElement(rosesPawn7,5,8);
        rosesBoard.addElement(rosesPawn8,6,8);
        rosesBoard.addElement(rosesPawn9,7,8);
        rosesBoard.addElement(rosesPawn10,9,8);
        rosesBoard.addElement(rosesPawn11,8,9);
        rosesBoard.addElement(rosesPawn12,9,9);

        listVerif.add(new Point(5,5));
        listVerif.add(new Point(8, 5));
        listVerif.add(new Point(6, 6));
        listVerif.add(new Point(8, 6));
        listVerif.add(new Point(7, 7));
        listVerif.add(new Point(8, 7));
        listVerif.add(new Point(5, 8));
        listVerif.add(new Point(6, 8));
        listVerif.add(new Point(7, 8));


        assertEquals(listVerif, rosesBoard.computeValidCells("H", 1));
    }

    @Test
    void testComputeValidCells_MPawnWithCPawnInMiddle() {
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
        ArrayList<Point> listVerif = new ArrayList<>(81);
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                listVerif.add(new Point(j, i));
            }
        }

        listVerif.remove(10);
        listVerif.remove(12);
        listVerif.remove(14);
        listVerif.remove(17);
        listVerif.remove(18);
        listVerif.remove(19);
        listVerif.remove(24);
        listVerif.remove(24);
        listVerif.remove(24);
        listVerif.remove(28);
        listVerif.remove(28);
        listVerif.remove(28);
        listVerif.remove(29);
        listVerif.remove(29);
        listVerif.remove(29);
        listVerif.remove(33);
        listVerif.remove(33);
        listVerif.remove(33);
        listVerif.remove(38);
        listVerif.remove(39);
        listVerif.remove(40);
        listVerif.remove(43);
        listVerif.remove(45);
        listVerif.remove(47);



        assertEquals(listVerif, rosesBoard.computeValidCells("M", 1));
    }
}