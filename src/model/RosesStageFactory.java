package model;

import boardifier.model.*;

import java.util.Random;

public class RosesStageFactory extends StageElementsFactory {
    private RosesStageModel stageModel;

    public RosesStageFactory(GameStageModel gameStageModel) {
        super(gameStageModel);
        stageModel = (RosesStageModel) gameStageModel;
    }

    @Override
    public void setup() {
         stageModel.playSound("gamestart.mp3");

        TextElement instructions = new TextElement("Enter P to pick a movement card. \n" + "Enter M + card number to play a move card. The number of cards is done from top to bottom (1 at the top, 5 at the bottom). Ex : M1. \n" + "Enter H + card number to play a hero card + number of card movement. Ex : H1", stageModel);
        instructions.setLocation(800, 290);
        stageModel.setInstructions1(instructions);

        // ============================== Create and set the board and pots ==============================


        RosesBoard board = new RosesBoard(700, 270, stageModel);

        RosesPawnPot bluePot = new RosesPawnPot(1300,720, stageModel);
        RosesPawnPot redPot = new RosesPawnPot(1300, 300, stageModel);

        RosesCardPot moveBluePot = new RosesCardPot("moveBluePot", 660, 840, stageModel);
        RosesCardPot moveRedPot = new RosesCardPot("moveRedPot", 780, 100, stageModel);
        RosesCardPot heroBluePot = new RosesCardPot("heroBlueCard", 1185, 840, 1, 1, stageModel);
        RosesCardPot heroRedPot = new RosesCardPot("heroRedCard", 660, 100, 1, 1, stageModel);
        RosesCardPot pickPot = new RosesCardPot("pickPot", 550, 350, 1, 1, stageModel);
        RosesCardPot discardPot = new RosesCardPot("discardPot", 550, 590, 1, 1, stageModel);

        stageModel.setBoard(board);

        stageModel.setBluePot(bluePot);
        stageModel.setRedPot(redPot);

        stageModel.setMoveBluePot(moveBluePot);
        stageModel.setMoveRedPot(moveRedPot);
        stageModel.setBlueHeroPot(heroBluePot);
        stageModel.setRedHeroPot(heroRedPot);
        stageModel.setPickPot(pickPot);
        stageModel.setDiscardPot(discardPot);

        // ======================= create, add and set the pawns and the crown =======================

        RosesPawn[] bluePawns = new RosesPawn[26];
        RosesPawn[] redPawns = new RosesPawn[26];
        RosesPawn crownPawn = new RosesPawn("♔",RosesPawn.PAWN_YELLOW, stageModel);

        for(int i=0;i<26;i++) {
            bluePawns[i] = new RosesPawn(RosesPawn.PAWN_BLUE, stageModel);
        }

        for(int i=0;i<26;i++) {
            redPawns[i] = new RosesPawn(RosesPawn.PAWN_RED, stageModel);
        }

        for (int i=0;i<26;i++) {
            bluePot.addElement(bluePawns[i], 0,0);
            redPot.addElement(redPawns[i], 0,0);
        }

        board.addElement(crownPawn, 4, 4);

        stageModel.setBluePawns(bluePawns);
        stageModel.setRedPawns(redPawns);
        stageModel.setCrownPawn(crownPawn);

        // ============================== Create the text elements ==============================

        TextElement text = new TextElement(stageModel.getCurrentPlayerName(), stageModel);
        text.setLocation(150,100);
        stageModel.setPlayerName(text);

        TextElement pick = new TextElement("Pick", stageModel);
        pick.setLocation(470, 430);
        stageModel.setPick(pick);

        // ============================== Create the cards and shuffles them ==============================

        RosesCard[] pickPotCards = new RosesCard[24]; // Create an array of 24 cards
        RosesCard[] player1MovementCards = new RosesCard[5];
        RosesCard[] player2MovementCards = new RosesCard[5];
        RosesCard[] heroRedCards = new RosesCard[4];
        RosesCard[] heroBlueCards = new RosesCard[4];

        int index = 0;
        // Create the 24 cards
        for (int i = 0; i < stageModel.getMovementsList().length; i++) {
            for (int j = 0; j < stageModel.getNumberList().length; j++) {
                pickPotCards[index++] = new RosesCard(stageModel.getNumberList()[j], stageModel.getMovementsList()[i], stageModel);
                if (index >= 24) // Exit the loop when all 24 cards are created
                    break;
            }
            if (index >= 24) // Exit the outer loop when all 24 cards are created
                break;
        }

        shuffleCard(pickPotCards); // Shuffle the cards

        for (int i = 0; i < 24; i++) { // Add the cards to the pick pot
            pickPot.addElement(pickPotCards[i], 0, 0);
        }

        // Add the 5 cards to the player by using a card copying system. It copies 5 cards from the deck and then removes them, this copy is then added to the player's pot
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

        for (int i = 0; i < 5; i++) {
            moveBluePot.addElement(player1MovementCards[i], 0,i);
            moveRedPot.addElement(player2MovementCards[i], 0,i);
        }

        for (int i = 0; i < 4; i++) {
            heroRedCards[i] = new RosesCard(RosesCard.CARD_RED, stageModel);
            heroBlueCards[i] = new RosesCard(RosesCard.CARD_BLUE, stageModel);
        }

        for (int i = 0; i < 4; i++) {
            heroBluePot.addElement(heroBlueCards[i], 0,0);
            heroRedPot.addElement(heroRedCards[i], 0,0);
        }

        stageModel.setPlayer1MovementCards(player1MovementCards);
        stageModel.setPlayer2MovementCards(player2MovementCards);

        // ============================== Counter of pawns and cards left ==============================

        TextElement redPawnsCounter = new TextElement("" + stageModel.getRedPawnsToPlay(), stageModel);
        redPawnsCounter.setLocation(1390, 345);
        TextElement bluePawnsCounter = new TextElement("" + stageModel.getBluePawnsToPlay(), stageModel);
        bluePawnsCounter.setLocation(1390, 765);

        TextElement numberOfBlueHeroCards = new TextElement("" + heroBlueCards.length, stageModel);
        numberOfBlueHeroCards.setLocation(1230, 1040);
        TextElement numberOfRedHeroCards = new TextElement("" + heroRedCards.length, stageModel);
        numberOfRedHeroCards.setLocation(700, 70);

        TextElement cardPickCounter = new TextElement("" + pickPotCards.length, stageModel);
        cardPickCounter.setLocation(585, 335);


        stageModel.setRedPawnsCounter(redPawnsCounter);
        stageModel.setBluePawnsCounter(bluePawnsCounter);

        stageModel.setBlueHeroCardsCounter(numberOfBlueHeroCards);
        stageModel.setPlayer1HeroCards(heroBlueCards);
        stageModel.setRedHeroCardsCounter(numberOfRedHeroCards);
        stageModel.setPlayer2HeroCards(heroRedCards);

        stageModel.setCardPickCounter(cardPickCounter);
        stageModel.setPickCards(pickPotCards);


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

    // The Fisher-Yates method allows shuffling all the cards in the pick so that players have random cards in their deck.
    private void shuffleCard(RosesCard[] pickPotCards) {
        Random random = new Random();
        for (int i = pickPotCards.length - 1; i > 0; i--) {
            int j = random.nextInt(i +1);
            RosesCard temp = pickPotCards[i];
            pickPotCards[i] = pickPotCards[j];
            pickPotCards[j] = temp;
        }
    }
}
