package model;

import boardifier.model.TextElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class TestRosesStageFactory {

    private RosesStageModel stageModel;
    private RosesStageFactory factory;

    @BeforeEach
    void setUp() {
        RosesStageModel stageModel = Mockito.mock(RosesStageModel.class);
        RosesStageFactory factory = new RosesStageFactory(stageModel);
    }

    @Test
    void testSetup() {
        factory.setup();

        // Verify that board is set
        Mockito.verify(stageModel).setBoard(Mockito.any(RosesBoard.class));

        // Verify that blue and red pots are set
        Mockito.verify(stageModel).setBluePot(Mockito.any(RosesPawnPot.class));
        Mockito.verify(stageModel).setRedPot(Mockito.any(RosesPawnPot.class));

        // Verify that blue and red pawns are set
        Mockito.verify(stageModel).setBluePawns(Mockito.any(RosesPawn[].class));
        Mockito.verify(stageModel).setRedPawns(Mockito.any(RosesPawn[].class));

        // Verify that crown pawn is set and added to board
        Mockito.verify(stageModel).setCrownPawn(Mockito.any(RosesPawn.class));
        Mockito.verify(stageModel.getBoard()).addElement(Mockito.any(RosesPawn.class), Mockito.eq(4), Mockito.eq(4));

        // Verify that the card pots are set
        Mockito.verify(stageModel).setMoovBluePot(Mockito.any(RosesCardPot.class));
        Mockito.verify(stageModel).setMoovRedPot(Mockito.any(RosesCardPot.class));
        Mockito.verify(stageModel).setBlueHeroPot(Mockito.any(RosesCardPot.class));
        Mockito.verify(stageModel).setRedHeroPot(Mockito.any(RosesCardPot.class));
        Mockito.verify(stageModel).setPickPot(Mockito.any(RosesCardPot.class));
        Mockito.verify(stageModel).setDiscardPot(Mockito.any(RosesCardPot.class));

        // Verify that the text elements are set
        Mockito.verify(stageModel).setPlayerName(Mockito.any(TextElement.class));
        Mockito.verify(stageModel).setPick(Mockito.any(TextElement.class));
        Mockito.verify(stageModel).setRedPawnsCounter(Mockito.any(TextElement.class));
        Mockito.verify(stageModel).setBluePawnsCounter(Mockito.any(TextElement.class));
        Mockito.verify(stageModel).setBlueHeroCardsCounter(Mockito.any(TextElement.class));
        Mockito.verify(stageModel).setRedHeroCardsCounter(Mockito.any(TextElement.class));
        Mockito.verify(stageModel).setCardPickCounter(Mockito.any(TextElement.class));

        // Verify that the player movement and hero cards are set
        Mockito.verify(stageModel).setPlayer1MovementCards(Mockito.any(RosesCard[].class));
        Mockito.verify(stageModel).setPlayer2MovementCards(Mockito.any(RosesCard[].class));
        Mockito.verify(stageModel).setPlayer1HeroCards(Mockito.any(RosesCard[].class));
        Mockito.verify(stageModel).setPlayer2HeroCards(Mockito.any(RosesCard[].class));

        // Verify that the pick pot cards are set
        Mockito.verify(stageModel).setPickCards(Mockito.any(RosesCard[].class));
    }
}
