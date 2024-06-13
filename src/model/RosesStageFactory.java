package model;

import boardifier.model.*;

import java.awt.*;

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
        RosesBoard board = new RosesBoard(370, 250, stageModel);
        stageModel.setBoard(board);
        //create the pots
        RosesPawnPot bluePot = new RosesPawnPot(900,620, stageModel);
        stageModel.setBluePot(bluePot);
        RosesPawnPot redPot = new RosesPawnPot(900, 280, stageModel);
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

        RosesPawn crownPawn = new RosesPawn("♔",RosesPawn.PAWN_YELLOW, stageModel);
        stageModel.setCrownPawn(crownPawn);
        board.addElement(crownPawn, 4, 4);

        // assign pawns to their pot
        for (int i=0;i<26;i++) {
            bluePot.addElement(bluePawns[i], 0,0);
            redPot.addElement(redPawns[i], 0,0);
        }


        RosesCardPot moovBluePot = new RosesCardPot(350, 750, stageModel);
        stageModel.setMoovBluePot(moovBluePot);
        RosesCardPot moovRedPot = new RosesCardPot(450, 100, stageModel);
        stageModel.setMoovRedPot(moovRedPot);
        RosesCardPot heroBluePot = new RosesCardPot(780, 750, 1, 1, stageModel);
        RosesCardPot heroRedPot = new RosesCardPot(330, 80, 1, 1, stageModel);
        RosesCardPot discardPot = new RosesCardPot(250, 500, 1, 1, stageModel);
        RosesCardPot pickPot = new RosesCardPot(250, 350, 1, 1, stageModel);
        stageModel.setBlueHeroPot(heroBluePot);
        stageModel.setRedHeroPot(heroRedPot);
        stageModel.setDiscardPot(discardPot);
        stageModel.setPickPot(pickPot);

        // create the text
        TextElement text = new TextElement(stageModel.getCurrentPlayerName(), stageModel);
        text.setLocation(10,20);
        stageModel.setPlayerName(text);

        TextElement pick = new TextElement("Pick pot", stageModel);
        pick.setLocation(130, 395);
        stageModel.setPick(pick);

        TextElement redPawnsCounter = new TextElement("" + stageModel.getRedPawnsToPlay(), stageModel);
        redPawnsCounter.setLocation(980, 315);
        stageModel.setRedPawnsCounter(redPawnsCounter);

        TextElement bluePawnsCounter = new TextElement("" + stageModel.getBluePawnsToPlay(), stageModel);
        bluePawnsCounter.setLocation(980, 655);
        stageModel.setBluePawnsCounter(bluePawnsCounter);

        RosesCard[] pickPotCards = new RosesCard[24];
        int index = 0;
        for (int i = 0; i < stageModel.getMovementsList().length; i++) {
            for (int j = 0; j < stageModel.getNumberList().length; j++) {
                pickPotCards[index++] = new RosesCard(stageModel.getNumberList()[j], stageModel.getMovementsList()[i], stageModel);
                if (index >= 24) // Exit the loop when all 24 cards are created
                    break;
            }
            if (index >= 24) // Exit the outer loop when all 24 cards are created
                break;
        }


        for (int i = 0; i < 24; i++) {
            pickPot.addElement(pickPotCards[i], 0, 0);
        }




        RosesCard[] player1MovementCards = new RosesCard[5];
        RosesCard[] player2MovementCards = new RosesCard[5];

        for (int i = 0; i < 5; i++) {
            if (player1MovementCards[i] == null) {
                player1MovementCards[i] = new RosesCard(pickPotCards[pickPotCards.length - 1]);
                player1MovementCards[i].flip();
            }
            RosesCard[] copyOfPickPotCards = new RosesCard[pickPotCards.length - 1];
            System.arraycopy(pickPotCards, 0, copyOfPickPotCards, 0, copyOfPickPotCards.length);
            pickPotCards = copyOfPickPotCards;
        }

        for (int i = 0; i < 5; i++) {
            if (player2MovementCards[i] == null) {
                player2MovementCards[i] = new RosesCard(pickPotCards[pickPotCards.length - 1]);
                player2MovementCards[i].flip();
            }
            RosesCard[] copyOfPickPotCards = new RosesCard[pickPotCards.length - 1];
            System.arraycopy(pickPotCards, 0, copyOfPickPotCards, 0, copyOfPickPotCards.length);
            pickPotCards = copyOfPickPotCards;

        }

        stageModel.setPlayer1MovementCards(player1MovementCards);
        stageModel.setPlayer2MovementCards(player2MovementCards);

        for (int i = 0; i < 5; i++) {
            moovBluePot.addElement(player1MovementCards[i], 0,i);
            moovRedPot.addElement(player2MovementCards[i], 0,i);
        }

        RosesCard[] heroRedCards = new RosesCard[4];
        RosesCard[] heroBlueCards = new RosesCard[4];

        for (int i = 0; i < 4; i++) {
            heroRedCards[i] = new RosesCard(RosesCard.CARD_RED, stageModel);
            heroBlueCards[i] = new RosesCard(RosesCard.CARD_BLUE, stageModel);
        }

        for (int i = 0; i < 4; i++) {
            heroBluePot.addElement(heroBlueCards[i], 0,0);
            heroRedPot.addElement(heroRedCards[i], 0,0);
        }

        TextElement numberOfBlueHeroCards = new TextElement("" + heroBlueCards.length, stageModel);
        numberOfBlueHeroCards.setLocation(816, 920);
        stageModel.setBlueHeroCardsCounter(numberOfBlueHeroCards);
        stageModel.setPlayer1HeroCards(heroBlueCards);

        TextElement numberOfRedHeroCards = new TextElement("" + heroRedCards.length, stageModel);
        numberOfRedHeroCards.setLocation(365, 50);
        stageModel.setRedHeroCardsCounter(numberOfRedHeroCards);
        stageModel.setPlayer2HeroCards(heroRedCards);

        stageModel.setPickCards(pickPotCards);
        TextElement cardPickCounter = new TextElement("" + pickPotCards.length, stageModel);
        cardPickCounter.setLocation(275, 335);
        stageModel.setCardPickCounter(cardPickCounter);


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
