import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import boardifier.model.TextElement;
import model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestRosesStageFactory {

    private RosesStageModel stageModel;
    private RosesBoard board;
    private RosesPawnPot bluePot;
    private RosesPawnPot redPot;
    private RosesCardPot pickPot;
    private RosesCardPot discardPot;
    private RosesCardPot redHeroPot;
    private RosesCardPot blueHeroPot;
    private RosesCardPot moovRedPot;
    private RosesCardPot moovBluePot;

    private RosesStageFactory factory;

    @BeforeEach
    public void setUp() {
        stageModel = mock(RosesStageModel.class);
        board = mock(RosesBoard.class);
        bluePot = mock(RosesPawnPot.class);
        redPot = mock(RosesPawnPot.class);
        pickPot = mock(RosesCardPot.class);
        discardPot = mock(RosesCardPot.class);
        redHeroPot = mock(RosesCardPot.class);
        blueHeroPot = mock(RosesCardPot.class);
        moovRedPot = mock(RosesCardPot.class);
        moovBluePot = mock(RosesCardPot.class);

        when(stageModel.getMovementsList()).thenReturn(new String[]{"Move1", "Move2"});
        when(stageModel.getNumberList()).thenReturn(new int[]{1, 2, 3, 4, 5});

        // Initialize the factory with the mocked stageModel
        factory = new RosesStageFactory(stageModel);

        // Call the setup method
        factory.setup();
    }

    @Test
    public void testSetup() {
        // Verify that text elements are set up correctly
        verify(stageModel).setPlayerName(any(TextElement.class));
        verify(stageModel).setBluePawnsCounter(any(TextElement.class));
        verify(stageModel).setRedPawnsCounter(any(TextElement.class));
        verify(stageModel).setPick(any(TextElement.class));
        verify(stageModel).setDiscard(any(TextElement.class));

        // Verify that the board is set up correctly
        verify(stageModel).setBoard(any(RosesBoard.class));
        verify(board).addElement(any(RosesPawn.class), eq(24), eq(24));

        // Verify that the pots are set up correctly
        verify(stageModel).setBluePot(any(RosesPawnPot.class));
        verify(stageModel).setRedPot(any(RosesPawnPot.class));
        verify(stageModel).setPickPot(any(RosesCardPot.class));
        verify(stageModel).setDiscardPot(any(RosesCardPot.class));
        verify(stageModel).setRedHeroPot(any(RosesCardPot.class));
        verify(stageModel).setBlueHeroPot(any(RosesCardPot.class));
        verify(stageModel).setMoovRedPot(any(RosesCardPot.class));
        verify(stageModel).setMoovBluePot(any(RosesCardPot.class));

        // Verify that the pawns are added to the pots
        verify(bluePot, times(26)).addElement(any(RosesPawn.class), eq(0), eq(0));
        verify(redPot, times(26)).addElement(any(RosesPawn.class), eq(0), eq(0));

        // Verify that the movement cards are set up correctly
        verify(stageModel).setPlayer1MovementCards(any(RosesCard[].class));
        verify(stageModel).setPlayer2MovementCards(any(RosesCard[].class));

        // Verify that the hero cards are set up correctly
        verify(stageModel).setPlayer1HeroCards(any(RosesCard[].class));
        verify(stageModel).setPlayer2HeroCards(any(RosesCard[].class));

        // Verify that the text elements for instructions are set up correctly
        verify(stageModel).setInstructions1(any(TextElement.class));
        verify(stageModel).setInstructions2(any(TextElement.class));
        verify(stageModel).setInstructions3(any(TextElement.class));

        // Verify that the card pick counter is set up correctly
        verify(stageModel).setCardPickCounter(any(TextElement.class));

        // Verify that the hero card counters are set up correctly
        verify(stageModel).setRedHeroCardsCounter(any(TextElement.class));
        verify(stageModel).setBlueHeroCardsCounter(any(TextElement.class));
    }
}