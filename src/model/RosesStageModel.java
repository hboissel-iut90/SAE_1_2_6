package model;

import boardifier.model.*;
import boardifier.view.ConsoleColor;
import boardifier.view.GameStageView;
import boardifier.view.TextLook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * HoleStageModel defines the model for the single stage in "The Hole". Indeed,
 * there are no levels in this game: a party starts and when it's done, the game is also done.
 * <p>
 * HoleStageModel must define all that is needed to manage a party : state variables and game elements.
 * In the present case, there are only 2 state variables that represent the number of pawns to play by each player.
 * It is used to detect the end of the party.
 * For game elements, it depends on what is chosen as a final UI design. For that demo, there are 12 elements used
 * to represent the state : the main board, 2 pots, 8 pawns, and a text for current player.
 * <p>
 * WARNING ! HoleStageModel DOES NOT create itself the game elements because it would prevent the possibility to mock
 * game element classes for unit testing purposes. This is why HoleStageModel just defines the game elements and the methods
 * to set this elements.
 * The instanciation of the elements is done by the HoleStageFactory, which uses the provided setters.
 * <p>
 * HoleStageModel must also contain methods to check/modify the game state when given events occur. This is the role of
 * setupCallbacks() method that defines a callback function that must be called when a pawn is put in a container.
 * This is done by calling onPutInContainer() method, with the callback function as a parameter. After that call, boardifier
 * will be able to call the callback function automatically when a pawn is put in a container.
 * NB1: callback functions MUST BE defined with a lambda expression (i.e. an arrow function).
 * NB2:  there are other methods to defines callbacks for other events (see onXXX methods in GameStageModel)
 * In "The Hole", everytime a pawn is put in the main board, we have to check if the party is ended and in this case, who is the winner.
 * This is the role of computePartyResult(), which is called by the callback function if there is no more pawn to play.
 */
public class RosesStageModel extends GameStageModel {

    // define stage state variables
    private int blackPawnsToPlay;
    private int redPawnsToPlay;

    // define stage game elements
    private RosesBoard board;

    private RosesPawnPot blackPot;
    private RosesPawnPot redPot;

    private RosesCardPot pickPot;
    private RosesCardPot discardPot;
    private RosesCardPot redHeroPot;
    private RosesCardPot blueHeroPot;
    private RosesCardPot moovRedPot;
    private RosesCardPot moovBluePot;

    private RosesPawn[] blackPawns;
    private RosesPawn[] redPawns;
    private RosesPawn[] yellowPawns;

    private RosesCard[] pickCards;
    private RosesCard[] discardCards;
    private RosesCard[] player1HeroCards;
    private RosesCard[] player1MovementCards;
    private RosesCard[] player2MovementCards;
    private RosesCard[] player2HeroCards;

    private TextElement playerName;
    private TextElement pick;
    private TextElement discard;
    private TextElement bluePawnsCounter;
    private TextElement redPawnsCounter;

    private TextElement cardPickCounter;

    private String[] movementLists = new String[]{"N", "N-E", "E", "S-E", "S", "S-W", "W", "N-W"};
    private int[] numberList = new int[]{1, 2, 3};


    // Uncomment next line if the example with a main container is used. see end of HoleStageFactory and HoleStageView
    //private ContainerElement mainContainer;

    public RosesStageModel(String name, Model model) {
        super(name, model);
        blackPawnsToPlay = 26;
        redPawnsToPlay = 26;
        setupCallbacks();
    }

    //Uncomment this 2 methods if example with a main container is used
    /*
    public ContainerElement getMainContainer() {
        return mainContainer;
    }

    public void setMainContainer(ContainerElement mainContainer) {
        this.mainContainer = mainContainer;
        addContainer(mainContainer);
    }
     */


    public RosesBoard getBoard() {
        return board;
    }

    public void setBoard(RosesBoard board) {
        this.board = board;
        addContainer(board);
    }

    public String[] getMovementsList() {
        return this.movementLists;
    }

    public TextElement getCardPickCounter() {
        return this.cardPickCounter;
    }

    public void setCardPickCounter(TextElement cardPickCounter) {
        this.cardPickCounter = cardPickCounter;
        addElement(cardPickCounter);
    }

    public int[] getNumberList() {
        return this.numberList;
    }

    public RosesPawnPot getBluePot() {
        return blackPot;
    }

    public void setBlackPot(RosesPawnPot blackPot) {
        this.blackPot = blackPot;
        addContainer(blackPot);
    }

    public RosesPawnPot getRedPot() {
        return redPot;
    }

    public void setRedPot(RosesPawnPot redPot) {
        this.redPot = redPot;
        addContainer(redPot);
    }

    public RosesCardPot getPickPot() {
        return pickPot;
    }

    public void setPickPot(RosesCardPot pickPot) {
        this.pickPot = pickPot;
        addContainer(pickPot);
    }

    public RosesCardPot getDiscardPot() {
        return discardPot;
    }

    public RosesCardPot getRedHeroPot() {
        return redHeroPot;
    }

    public void setRedHeroPot(RosesCardPot redHeroPot) {
        this.redHeroPot = redHeroPot;
        addContainer(redHeroPot);
    }

    public RosesCardPot getBlueHeroPot() {
        return blueHeroPot;
    }

    public void setBlueHeroPot(RosesCardPot blueHeroPot) {
        this.blueHeroPot = blueHeroPot;
        addContainer(blueHeroPot);
    }

    public RosesCardPot getMoovRedPot() {
        return moovRedPot;
    }

    public void setMoovRedPot(RosesCardPot moovRedPot) {
        this.moovRedPot = moovRedPot;
        addContainer(moovRedPot);
    }

    public RosesCardPot getMoovBluePot() {
        return moovBluePot;
    }

    public RosesCard[] getPickCards() {
        return this.pickCards;
    }

    public void setPickCards(RosesCard[] pickCards) {
        this.pickCards = pickCards;
        for (int i = 0; i < pickCards.length; i++) {
            addElement(pickCards[i]);
        }
    }

    public void setMoovBluePot(RosesCardPot moovBluePot) {
        this.moovBluePot = moovBluePot;
        addContainer(moovBluePot);
    }

    public void setDiscardPot(RosesCardPot discardPot) {
        this.discardPot = discardPot;
        addContainer(discardPot);
    }

    public RosesPawn[] getBlackPawns() {
        return blackPawns;
    }

    public RosesPawn[] getYellowPawns() {
        return this.yellowPawns;
    }

    public void setYellowPawns(RosesPawn[] yellowPawns) {
        this.yellowPawns = yellowPawns;
        for (int i = 0; i < yellowPawns.length; i++) {
            addElement(yellowPawns[i]);
        }
    }

    public void setBlackPawns(RosesPawn[] blackPawns) {
        this.blackPawns = blackPawns;
        for (int i = 0; i < blackPawns.length; i++) {
            addElement(blackPawns[i]);
        }
    }

    public RosesPawn[] getRedPawns() {
        return redPawns;
    }

    public void setRedPawns(RosesPawn[] redPawns) {
        this.redPawns = redPawns;
        for (int i = 0; i < redPawns.length; i++) {
            addElement(redPawns[i]);
        }
    }

    public void setPlayer1MovementCards(RosesCard[] player1MovementCards) {
        this.player1MovementCards = player1MovementCards;
        for (int i = 0; i < player1MovementCards.length; i++) {
            addElement(player1MovementCards[i]);
        }
    }

    public void setPlayer2MovementCards(RosesCard[] player2MovementCards) {
        this.player2MovementCards = player2MovementCards;
        for (int i = 0; i < player2MovementCards.length; i++) {
            addElement(player2MovementCards[i]);
        }
    }

    public RosesCard[] getPlayer1MovementCards() {
        return this.player1MovementCards;
    }

    public RosesCard[] getPlayer2MovementCards() {
        return this.player2MovementCards;
    }

    public TextElement getPlayerName() {
        return playerName;
    }

    public TextElement getRedPawnsCounter() {
        return this.redPawnsCounter;
    }

    public TextElement getPick() {
        return pick;
    }

    public TextElement getDiscard() {
        return discard;
    }

    public TextElement getBluePawnsCounter() {
        return this.bluePawnsCounter;
    }

    public void setPlayerName(TextElement playerName) {
        this.playerName = playerName;
        addElement(playerName);
    }

    public void setRedPawnsCounter(TextElement redPawnsCounter) {
        this.redPawnsCounter = redPawnsCounter;
        addElement(redPawnsCounter);
    }

    public void setBluePawnsCounter(TextElement bluePawnsCounter) {
        this.bluePawnsCounter = bluePawnsCounter;
        addElement(bluePawnsCounter);
    }

    public void setPick(TextElement pick) {
        this.pick = pick;
        addElement(pick);
    }

    public void setDiscard(TextElement discard) {
        this.discard = discard;
        addElement(discard);
    }

    public int getBlackPawnsToPlay() {
        return this.blackPawnsToPlay;
    }

    public int getRedPawnsToPlay() {
        return this.redPawnsToPlay;
    }

//    public void piocherCartes(RosesCard[] joueurMovementCards, RosesCard[] pickPotCards, int nombre) {
//        for (int i = 0; i < joueurMovementCards.length && nombre > 0; i++) {
//            if (joueurMovementCards[i] == null) {
//                joueurMovementCards[i] = new RosesCard(pickPotCards[pickPotCards.length - 1]);
//                pickPotCards = Arrays.copyOf(pickPotCards, pickPotCards.length - 1);
//                nombre--;
//            }
//        }
//    }



    public void updatePawnsToPlay(GameStageView gameStageView) {
        bluePawnsCounter.setText("Blue pawns left : " + ConsoleColor.BLUE + this.getBlackPawnsToPlay() + ConsoleColor.RESET);
        bluePawnsCounter.setLocation(60, 9);
        this.setBluePawnsCounter(bluePawnsCounter);
        gameStageView.addLook(new TextLook(this.getBluePawnsCounter()));
        redPawnsCounter.setText("Red pawns left : " + ConsoleColor.RED + this.getRedPawnsToPlay() + ConsoleColor.RESET);
        redPawnsCounter.setLocation(60, 21);
        this.setRedPawnsCounter(redPawnsCounter);
        gameStageView.addLook(new TextLook(this.getRedPawnsCounter()));
        cardPickCounter.setText("Cards left : " + ConsoleColor.GREY_BACKGROUND + this.pickCards.length + ConsoleColor.RESET);
        cardPickCounter.setLocation(16, 0);
        this.setCardPickCounter(cardPickCounter);


    }

    private void setupCallbacks() {
        onPutInContainer((element, gridDest, rowDest, colDest) -> {
            // just check when pawns are put in 3x3 board
            if (gridDest != board) return;
            RosesPawn p = (RosesPawn) element;
            if (p.getColor() == 0) {
                blackPawnsToPlay--;
            } else {
                redPawnsToPlay--;
            }
            if ((blackPawnsToPlay == 0) && (redPawnsToPlay == 0)) {
                computePartyResult();
            }
        });
    }

    private void computePartyResult() {
        int idWinner = -1;
        // get the empty cell, which should be in 2D at [0,0], [0,2], [1,1], [2,0] or [2,2]
        // i.e. or in 1D at index 0, 2, 4, 6 or 8
        int i = 0;
        int nbBlack = 0;
        int nbRed = 0;
        int countBlack = 0;
        int countRed = 0;
        RosesPawn p = null;
        int row, col;
        for (i = 0; i < 9; i += 2) {
            if (board.isEmptyAt(i / 3, i % 3)) break;
        }
        // get the 4 adjacent cells (if they exist) starting by the upper one
        row = (i / 3) - 1;
        col = i % 3;
        for (int j = 0; j < 4; j++) {
            // skip invalid cells
            if ((row >= 0) && (row <= 2) && (col >= 0) && (col <= 2)) {
                p = (RosesPawn) (board.getElement(row, col));
                if (p.getColor() == RosesPawn.PAWN_BLUE) {
                    nbBlack++;
                    countBlack += p.getNumber();
                } else {
                    nbRed++;
                    countRed += p.getNumber();
                }
            }
            // change row & col to set them at the correct value for the next iteration
            if ((j == 0) || (j == 2)) {
                row++;
                col--;
            } else if (j == 1) {
                col += 2;
            }

        }

        // decide whose winning
        if (nbBlack < nbRed) {
            idWinner = 0;
        } else if (nbBlack > nbRed) {
            idWinner = 1;
        } else {
            if (countBlack < countRed) {
                idWinner = 0;
            } else if (countBlack > countRed) {
                idWinner = 1;
            }
        }
        System.out.println("nb black: " + nbBlack + ", nb red: " + nbRed + ", count black: " + countBlack + ", count red: " + countRed + ", winner is player " + idWinner);
        // set the winner
        model.setIdWinner(idWinner);
        // stop de the game
        model.stopStage();
    }

    @Override
    public StageElementsFactory getDefaultElementFactory() {
        return new RosesStageFactory(this);
    }
}
