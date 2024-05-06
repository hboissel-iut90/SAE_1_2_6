package model;

import boardifier.model.GameStageModel;
import boardifier.model.StageElementsFactory;
import boardifier.model.TextElement;
import org.w3c.dom.Text;

/**
 * HoleStageFactory must create the game elements that are defined in HoleStageModel
 * WARNING: it just creates the game element and NOT their look, which is done in HoleStageView.
 *
 * If there must be a precise position in the display for the look of a game element, then this element must be created
 * with that position in the virtual space and MUST NOT be placed in a container element. Indeed, for such
 * elements, the position in their virtual space will match the position on the display. For example, in the following,
 * the black pot is placed in 18,0. When displayed on screen, the top-left character of the black pot will be effectively
 * placed at column 18 and row 0.
 *
 * Otherwise, game elements must be put in a container and it will be the look of the container that will manage
 * the position of element looks on the display. For example, pawns are put in a ContainerElement. Thus, their virtual space is
 * in fact the virtual space of the container and their location in that space in managed by boardifier, depending of the
 * look of the container.
 *
 */
public class RosesStageFactory extends StageElementsFactory {
    private RosesStageModel stageModel;

    public RosesStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (RosesStageModel) gameStageModel;
    }

    @Override
    public void setup() {

        // create the text that displays the player name and put it in 0,0 in the virtual space
        TextElement text = new TextElement(stageModel.getCurrentPlayerName(), stageModel);
        text.setLocation(0,0);
        stageModel.setPlayerName(text);

        TextElement bluePawnsCounter = new TextElement("Blue pawns left : " + stageModel.getBlackPawnsToPlay(), stageModel);
        bluePawnsCounter.setLocation(47, 11);
        stageModel.setBluePawnsCounter(bluePawnsCounter);

        TextElement redPawnsCounter = new TextElement("Red pawns left : " + stageModel.getBlackPawnsToPlay(), stageModel);
        redPawnsCounter.setLocation(47, 21);
        stageModel.setRedPawnsCounter(redPawnsCounter);

        TextElement pick = new TextElement("Pick", stageModel);
        pick.setLocation(41, 14);
        stageModel.setPick(pick);

        TextElement discard = new TextElement("Dis", stageModel);
        discard.setLocation(47, 14);
        stageModel.setDiscard(discard);

        // create the board, in 0,1 in the virtual space
        RosesBoard board = new RosesBoard(0, 5, stageModel);
        // assign the board to the game stage model
        stageModel.setBoard(board);

        //create the black pot in 18,0 in the virtual space
        RosesPawnPot bluePot = new RosesPawnPot(42,10, stageModel);
        // assign the black pot to the game stage model
        stageModel.setBlackPot(bluePot);
        //create the black pot in 25,0 in the virtual space
        RosesPawnPot redPot = new RosesPawnPot(42,20, stageModel);
        // assign the red pot to the game stage model
        stageModel.setRedPot(redPot);

        //create the pick pot in the virtual space
        RosesCardPot pickPot = new RosesCardPot(42, 15, stageModel);
        //assign the pick pot to the game stage model
        stageModel.setPickPot(pickPot);

        RosesCardPot discardPot = new RosesCardPot(47, 15, stageModel);
        stageModel.setDiscardPot(discardPot);

        /* create the pawns
            NB: their coordinates are by default 0,0 but since they are put
            within the pots, their real coordinates will be computed by the view
         */
        RosesPawn[] bluePawns = new RosesPawn[26];
        for(int i=0;i<26;i++) {
            bluePawns[i] = new RosesPawn(RosesPawn.PAWN_BLUE, stageModel);
        }
        // assign the black pawns to the game stage model
        stageModel.setBlackPawns(bluePawns);
        RosesPawn[] redPawns = new RosesPawn[26];
        for(int i=0;i<26;i++) {
            redPawns[i] = new RosesPawn(RosesPawn.PAWN_RED, stageModel);
        }
        // assign the black pawns to the game stage model
        stageModel.setRedPawns(redPawns);

        RosesPawn[] yellowPawns = new RosesPawn[1];
        for(int i=0;i<0;i++) {
            yellowPawns[i] = new RosesPawn(RosesPawn.PAWN_YELLOW, stageModel);
        }

        // finally put the pawns to their pot
        for (int i=0;i<26;i++) {
            bluePot.addElement(bluePawns[i], 0,0);
            redPot.addElement(redPawns[i], 0,0);
        }






        /* Example with a main container that takes the ownership of the location
           of the element that are put within.
           If we put text, board, black/red pots within this container, their initial
           location in the virtual space is no more relevant.
           In such a case, we also need to create a look for the main container, see HoleStageView
           comment at the end of the class.

        // create the main container with 2 rows and 3 columns, in 0,0 in the virtual space
        ContainerElement mainContainer = new ContainerElement("rootcontainer",0,0,2,3, stageModel);
        // for cell 0,1, span over the row below => the column 1 goes from top to bottom of the container
        mainContainer.setCellSpan(0,1,2,1);
        // for cell 0,2, span over the row below => the column 2 goes from top to bottom of the container
        mainContainer.setCellSpan(0,2,2,1);
        // assign the
        stageModel.setMainContainer(mainContainer);
        // assign elements to main container cells
        mainContainer.addElement(text,0,0);
        mainContainer.addElement(board, 1,0);
        mainContainer.addElement(bluePot,0,1);
        mainContainer.addElement(redPot,0,2);
        */
    }
}
