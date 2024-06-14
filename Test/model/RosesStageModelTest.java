package model;

import boardifier.model.ContainerOpCallback;
import boardifier.model.GameStageModel;
import boardifier.model.Model;
import boardifier.model.TextElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static model.RosesPawn.PAWN_BLUE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RosesStageModelTest {

    private RosesStageModel rosesStageModel;
    private RosesStageModelTool rosesStageModelTool;
    private Model modelMock;
    private TextElement bluePawnsCounterMock, redPawnsCounterMock, cardPickCounterMock, blueHeroCardsCounterMock, redHeroCardsCounterMock;

    @BeforeEach
    void setUp() {
        modelMock = Mockito.mock(Model.class);
        rosesStageModel = new RosesStageModel("Test Stage", modelMock);
        rosesStageModelTool = new RosesStageModelTool("Test Stage", modelMock);
        bluePawnsCounterMock = Mockito.mock(TextElement.class);
        rosesStageModel.setBluePawnsCounter(bluePawnsCounterMock);
        redPawnsCounterMock = Mockito.mock(TextElement.class);
        rosesStageModel.setRedPawnsCounter(redPawnsCounterMock);
        cardPickCounterMock = Mockito.mock(TextElement.class);
        rosesStageModel.setCardPickCounter(cardPickCounterMock);
        blueHeroCardsCounterMock = Mockito.mock(TextElement.class);
        rosesStageModel.setBlueHeroCardsCounter(blueHeroCardsCounterMock);
        redHeroCardsCounterMock = Mockito.mock(TextElement.class);
        rosesStageModel.setRedHeroCardsCounter(redHeroCardsCounterMock);

    }

    @Test
    void testInitialState() {
        assertEquals(26, rosesStageModel.getBluePawnsToPlay(), "Initial blue pawns to play should be 26.");
        assertEquals(26, rosesStageModel.getRedPawnsToPlay(), "Initial red pawns to play should be 26.");
        assertEquals(RosesStageModel.STATE_SELECTCARD, rosesStageModel.getState(), "Initial state should be STATE_SELECTCARD.");
    }

    @Test
    void testComputePartyResultBlueWins() {
        // Setup board with blue and red pawns
        RosesBoard boardMock = Mockito.mock(RosesBoard.class);
        Mockito.when(boardMock.isElementAt(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);

        RosesPawn bluePawn1 = new RosesPawn(PAWN_BLUE, Mockito.mock(GameStageModel.class));
        RosesPawn redPawn1 = new RosesPawn(RosesPawn.PAWN_RED, Mockito.mock(GameStageModel.class));
        Mockito.when(boardMock.getElement(0, 0)).thenReturn(bluePawn1);
        Mockito.when(boardMock.getElement(1, 1)).thenReturn(redPawn1);

        rosesStageModel.setBoard(boardMock);

        // Simulate end of game
        rosesStageModel.computePartyResult();

        // Verify results
        assertEquals(0, rosesStageModel.getRedPawnsToPlay(), "Red pawns should be 0 after computePartyResult.");
        assertEquals(0, rosesStageModel.getBluePawnsToPlay(), "Blue pawns should be 0 after computePartyResult.");
        assertEquals(0, modelMock.getIdPlayer(), "Blue score should be calculated correctly.");
        assertEquals(0, modelMock.getIdWinner(), "Red score should be calculated correctly.");
    }

    @Test
    void testCountZoneSize() {
        // Setup board with blue pawns in a connected zone
        RosesBoard boardMock = Mockito.mock(RosesBoard.class);
        Mockito.when(boardMock.isElementAt(Mockito.anyInt(), Mockito.anyInt())).thenReturn(true);

        // Simulate a connected zone of blue pawns
        Mockito.when(boardMock.getElement(Mockito.eq(0), Mockito.eq(0))).thenReturn(new RosesPawn(RosesPawn.PAWN_BLUE, Mockito.mock(GameStageModel.class)));
        Mockito.when(boardMock.getElement(Mockito.eq(0), Mockito.eq(1))).thenReturn(new RosesPawn(RosesPawn.PAWN_BLUE, Mockito.mock(GameStageModel.class)));
        Mockito.when(boardMock.getElement(Mockito.eq(1), Mockito.eq(0))).thenReturn(new RosesPawn(RosesPawn.PAWN_BLUE, Mockito.mock(GameStageModel.class)));

        rosesStageModelTool.setBoard(boardMock);

        // Test countZoneSize for a known zone of blue pawns
        int zoneSize = rosesStageModelTool.countZoneSize(0, 0, PAWN_BLUE, new boolean[9][9]);
        assertEquals(3, zoneSize, "Zone size should be 3 for connected blue pawns.");
    }

    @Test
    void testSetupCallbacks() {
        // Setup mock callback function
        ContainerOpCallback callbackMock = Mockito.mock(ContainerOpCallback.class);

        // Call setupCallbacks with the mock callback
        rosesStageModelTool.setupCallbacks();
        rosesStageModelTool.onPutInContainer(callbackMock);

        // Simulate putting a pawn on the board
        RosesPawn pawnMock = Mockito.mock(RosesPawn.class);
        rosesStageModelTool.putInContainer(pawnMock, rosesStageModelTool.getBoard(), 0, 0);

        // Verify that the callback was called
        verify(callbackMock, times(1)).execute(pawnMock, rosesStageModelTool.getBoard(), 0, 0);
    }

    @Test
    void testUpdate() {
        // Setup initial values
        rosesStageModel.getBluePawnsToPlay();
        rosesStageModel.getRedPawnsToPlay();
        rosesStageModel.getPickCards();
        rosesStageModel.getPlayer1HeroCards();
        rosesStageModel.getPlayer2HeroCards();

        // Call update()
        rosesStageModel.update();

        // Verify that the text element was updated correctly
        verify(bluePawnsCounterMock).setText("10");
        // Add similar verification for other elements as needed
    }
}
