package model;

import boardifier.model.*;

public class RosesStageFactory extends StageElementsFactory {
    private RosesStageModel stageModel;

    public RosesStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (RosesStageModel) gameStageModel;
    }

    @Override
    public void setup() {

//        TextElement instructions = new TextElement("Enter P to pick a movement card. \n" + "Enter M + card number to play a move card. The number of cards is done from top to bottom (1 at the top, 5 at the bottom). Ex : M1. \n" + "Enter H + card number to play a hero card + number of card movement. Ex : H1", stageModel);
//        instructions.setLocation(800, 290);
//        stageModel.setInstructions1(instructions);
        // create the board
        RosesBoard board = new RosesBoard(130, 150, stageModel);
        stageModel.setBoard(board);
        //create the pots
        RosesPawnPot bluePot = new RosesPawnPot(720,210, stageModel);
        stageModel.setBluePot(bluePot);
        RosesPawnPot redPot = new RosesPawnPot(720, 520, stageModel);
        stageModel.setRedPot(redPot);


        // create the pawns
        RosesPawn[] bluePawns = new RosesPawn[26];
        for(int i=0;i<26;i++) {
            bluePawns[i] = new RosesPawn(RosesPawn.PAWN_BLUE, stageModel);
        }
        stageModel.setBluePawns(bluePawns);
        RosesPawn[] redPawns = new RosesPawn[26];
        for(int i=0;i<26;i++) {
            redPawns[i] = new RosesPawn(RosesPawn.PAWN_RED, stageModel);
        }
        stageModel.setRedPawns(redPawns);

        // assign pawns to their pot
        for (int i=0;i<26;i++) {
            bluePot.addElement(bluePawns[i], 0,0);
            redPot.addElement(redPawns[i], 0,0);
        }

        RosesCardPot moovBluePot = new RosesCardPot(50, 210, stageModel);
        stageModel.setMoovBluePot(moovBluePot);
        RosesCardPot moovRedPot = new RosesCardPot(610, 210, stageModel);
        stageModel.setMoovRedPot(moovRedPot);
        RosesCardPot heroBluePot = new RosesCardPot(608, 40, 1, 1, stageModel);
        RosesCardPot heroRedPot = new RosesCardPot(45, 640, 1, 1, stageModel);
        RosesCardPot discardPot = new RosesCardPot(338, 40, 1, 1, stageModel);
        RosesCardPot pickPot = new RosesCardPot(338, 640, 1, 1, stageModel);
        stageModel.setBlueHeroPot(heroBluePot);
        stageModel.setRedHeroPot(heroRedPot);
        stageModel.setDiscardPot(discardPot);
        stageModel.setPickPot(pickPot);
        // create the text
        TextElement text = new TextElement(stageModel.getCurrentPlayerName(), stageModel);
        text.setLocation(10,20);
        stageModel.setPlayerName(text);

        /*
        // create the main container
        ContainerElement mainContainer = new ContainerElement("rootcontainer",0,0,2,3, stageModel);
        mainContainer.setCellSpan(0,1,2,1);
        mainContainer.setCellSpan(0,2,2,1);
        stageModel.setRootContainer(mainContainer);
        // assign element to root container cells
        mainContainer.addElement(text,0,0);
        mainContainer.addElement(board, 1,0);
        mainContainer.addElement(blackPot,0,1);
        mainContainer.addElement(redPot,0,2);

         */
    }
}
