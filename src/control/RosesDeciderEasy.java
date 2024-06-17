package control;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import boardifier.model.animation.AnimationTypes;
import boardifier.view.GameStageView;
import boardifier.view.View;
import model.*;
import view.PawnLook;
import view.RosesView;

import java.awt.*;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import static model.RosesPawn.PAWN_BLUE;
import static model.RosesPawn.PAWN_RED;

// This is the class of the "dumb" AI, this is how she works :

// First she will get all of her pawns of the board then put them in a table,
// then see if she can play a pawn to put a pawn adjacent to a pawn she just saved, if she can't
// she will pick a card in the pickcard, if she still can't, she will try to play an hero card, and if she can't
// the game is over.
public class RosesDeciderEasy extends Decider {

    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());

    private static final String[] typeOfMoves = {"M", "H", "P"};
    private final String[] possibleMovements = new String[5];
    private final View view;
    int[] possibleValues = new int[5];

    List<Point> ValidCells = null;


    public RosesDeciderEasy(Model model, View view, Controller control) {
        super(model, control);
        this.view = view;
    }

    @Override
    public ActionList decide() {
        model.getPlayers().get(model.getIdPlayer()).setName("Easy computer");
        RosesStageModel stage = (RosesStageModel) model.getGameStage();
        RosesBoard board = stage.getBoard();
        RosesPawnPot pawnPot = null;
        GameElement pawn = null;
        GameElement yellowPawn = stage.getCrownPawn();
        int rowDest = 0;
        int colDest = 0;
        int row = 0;
        int col = 0;
        int choice = 0;

        ActionList actions = new ActionList();
        int[][] redPositions = new int[30][2];
        int[][] bluePositions = new int[30][2];
        int redIndex = 0;
        int blueIndex = 0;
        int nbMovements = stage.getNbMovements();
        RosesPawn tempPawn = null;
        if (model.getIdPlayer() == RosesPawn.PAWN_BLUE) {
            if (stage.getBluePawnsToPlay() == 0) { // if the AI has no pawns left
                stage.computePartyResult();
                return actions;
            }
            pawnPot = stage.getBluePot();
            for (int k = 0; k < stage.getPlayer1MovementCards().length; k++) {
                if (stage.getPlayer1MovementCards()[k] != null) {
                    possibleMovements[k] = stage.getPlayer1MovementCards()[k].getDirection();
                    possibleValues[k] = stage.getPlayer1MovementCards()[k].getValue();
                }
            }
            for (int i = 0; i < board.getNbRows(); i++) {
                for (int j = 0; j < board.getNbCols(); j++) {
                    if (board.getElement(i, j) != null) {
                        tempPawn = (RosesPawn) board.getElement(i, j);
                        if (tempPawn.getColor() == RosesPawn.PAWN_BLUE) { // get every blue pawns in the board
                            bluePositions[blueIndex][0] = i;
                            bluePositions[blueIndex][1] = j;
                            blueIndex++;
                        } else if (tempPawn.getColor() == RosesPawn.PAWN_YELLOW) {
                            row = i;
                            col = j; // Here we get the crown pawn position, this is for the case if this is the beggining of the game

                        }
                    }
                    if (board.getElement(i, j, 1) != null) { // Take the position of the actual row and col cause sometimes the crown pawn is not detected due to
                        // the fact that there is two pawns on the same col/row, if there is two elements on the same cell, so the crown pawn must be here
                        row = i;
                        col = j;
                    }
                }
            }

            for (int i = 0; i < possibleMovements.length; i++) {
                if (possibleMovements[i] == null) continue;
                rowDest = row;
                colDest = col;
                // Get the destination of where we will go
                switch (possibleMovements[i]) {
                    case "W":
                        colDest = col - possibleValues[i];
                        choice = i;
                        break;
                    case "N-E":
                        colDest = col + possibleValues[i];
                        rowDest = row - possibleValues[i];
                        choice = i;
                        break;
                    case "E":
                        colDest = col + possibleValues[i];
                        choice = i;
                        break;
                    case "S-E":
                        colDest = col + possibleValues[i];
                        rowDest = row + possibleValues[i];
                        choice = i;
                        break;
                    case "S":
                        rowDest = row + possibleValues[i];
                        choice = i;
                        break;
                    case "S-W":
                        rowDest = row + possibleValues[i];
                        colDest = col - possibleValues[i];
                        choice = i;
                        break;
                    case "N":
                        rowDest = row - possibleValues[i];
                        choice = i;
                        break;
                    default:
                        rowDest = row - possibleValues[i];
                        colDest = col - possibleValues[i];
                        choice = i;
                }
                if (board.canReachCell(rowDest, colDest)) { // Check if we can put our pawn adjacent to a pawn of the colour of the IA
                    for (int m = 0; m < blueIndex; m++) {
                        if (rowDest - 1 == bluePositions[m][0] && (!board.isElementAt(rowDest, colDest))) {
                            actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer(), control);
                            return actions;
                        } else if (rowDest + 1 == bluePositions[m][0] && (!board.isElementAt(rowDest, colDest))) {
                            actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer(), control);
                            return actions;
                        }
                        if (bluePositions[m][1] >= 0) {
                            if (colDest + 1 == bluePositions[m][1] && (!board.isElementAt(rowDest, colDest))) {
                                actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer(), control);
                                return actions;
                            } else if (rowDest + 1 == bluePositions[m][1] && (!board.isElementAt(rowDest, colDest))) {
                                actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer(), control);
                                return actions;
                            }
                        }
                    } // Else, play the first movement card he can play
                    if (!board.isElementAt(rowDest, colDest)) {
                        actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer(), control);
                        return actions;
                    }
                }

            } // if he can't, he will check if he can pick
            for (int i = 0; i < stage.getPlayer1MovementCards().length; i++) {
                if (stage.getPlayer1MovementCards()[i] == null && stage.getPickCards().length > 0) {
                    stage.getPlayer1MovementCards()[i] = stage.getPickCards()[stage.getPickCards().length - 1];
                    stage.getPlayer1MovementCards()[i].flip();
                    stage.getMoveBluePot().addElement(stage.getPlayer1MovementCards()[i], i, 0);
                    setTempPickCards(stage);
                    setBackCardsInCardPot(stage);
                    actions.setDoEndOfTurn(true);
                    return actions;
                }
            }
            rowDest = row;
            colDest = col;
            // if he can't, he will try to play a hero card
            for (int i = 0; i < stage.getPlayer1MovementCards().length; i++) {
                actions = playHeroCards(model, stage, rowDest, colDest, choice, nbMovements, i);
                if (actions != null) {
                    i = stage.getPlayer1MovementCards().length;
                    return actions;
                }
            }
            // if he cant play a hero card, action will be null so the game will be over, else, this will play a hero card
            if (actions == null) {
                stage.computePartyResult();
            }
            actions = new ActionList();
            return actions;
        } else { // basically, this is the same logic if the player has the red pawns
            pawnPot = stage.getRedPot();
            if (model.getIdPlayer() == RosesPawn.PAWN_RED) {
                if (stage.getRedPawnsToPlay() == 0) {
                    stage.computePartyResult();
                    return actions;
                }
                for (int k = 0; k < stage.getPlayer2MovementCards().length; k++) {
                    if (stage.getPlayer2MovementCards()[k] != null) {
                        possibleMovements[k] = stage.getPlayer2MovementCards()[k].getDirection();
                        possibleValues[k] = stage.getPlayer2MovementCards()[k].getValue();
                    }
                }
                for (int i = 0; i < board.getNbRows(); i++) {
                    for (int j = 0; j < board.getNbCols(); j++) {
                        if (board.getElement(i, j) != null) {
                            tempPawn = (RosesPawn) board.getElement(i, j);
                            if (tempPawn.getColor() == RosesPawn.PAWN_RED) {
                                redPositions[redIndex][0] = i;
                                redPositions[redIndex][1] = j;
                                redIndex++;
                            } else if (tempPawn.getColor() == RosesPawn.PAWN_YELLOW) {
                                row = i;
                                col = j;
                            }
                        }
                        if (board.getElement(i, j, 1) != null) {
                            row = i;
                            col = j;
                        }
                    }
                }
                for (int i = 0; i < possibleMovements.length; i++) {
                    if (possibleMovements[i] == null) continue;
                    rowDest = row;
                    colDest = col;
                    switch (possibleMovements[i]) {
                        case "W":
                            colDest = col + possibleValues[i];
                            choice = i;
                            break;
                        case "N-E":
                            colDest = col - possibleValues[i];
                            rowDest = row + possibleValues[i];
                            choice = i;
                            break;
                        case "E":
                            colDest = col - possibleValues[i];
                            choice = i;
                            break;
                        case "S-E":
                            colDest = col - possibleValues[i];
                            rowDest = row - possibleValues[i];
                            choice = i;
                            break;
                        case "S":
                            rowDest = row - possibleValues[i];
                            choice = i;
                            break;
                        case "S-W":
                            rowDest = row - possibleValues[i];
                            colDest = col + possibleValues[i];
                            choice = i;
                            break;
                        case "N":
                            rowDest = row + possibleValues[i];
                            choice = i;
                            break;
                        default:
                            rowDest = row + possibleValues[i];
                            colDest = col + possibleValues[i];
                            choice = i;
                    }
                    if (board.canReachCell(rowDest, colDest)) {
                        for (int m = 0; m < redIndex; m++) {
                            if (rowDest - 1 == redPositions[m][0] && (!board.isElementAt(rowDest, colDest))) {
                                actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer(), control);
                                return actions;
                            } else if (rowDest + 1 == redPositions[m][0] && (!board.isElementAt(rowDest, colDest))) {
                                actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer(), control);
                                return actions;
                            }
                            if (colDest + 1 == redPositions[m][1] && (!board.isElementAt(rowDest, colDest))) {
                                actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer(), control);
                                return actions;
                            } else if (rowDest + 1 == redPositions[m][1] && (!board.isElementAt(rowDest, colDest))) {
                                actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer(), control);
                                return actions;
                            }

                        }
                        if (!board.isElementAt(rowDest, colDest)) {
                            actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer(), control);
                            return actions;
                        }
                    }

                }
                for (int i = 0; i < stage.getPlayer2MovementCards().length; i++) {
                    if (stage.getPlayer2MovementCards()[i] == null && stage.getPickCards().length > 0) {
                        stage.getPlayer2MovementCards()[i] = stage.getPickCards()[stage.getPickCards().length - 1];
                        stage.getPlayer2MovementCards()[i].flip();
                        stage.getMoveRedPot().addElement(stage.getPlayer2MovementCards()[i], i, 0);
                        setTempPickCards(stage);
                        setBackCardsInCardPot(stage);
                        actions.setDoEndOfTurn(true);
                        return actions;
                    }
                }
            }
        }
        rowDest = row;
        colDest = col;
        for (int i = 0; i < stage.getPlayer2MovementCards().length; i++) {
            actions = playHeroCards(model, stage, rowDest, colDest, choice, nbMovements, i);
            if (actions != null) {
                i = stage.getPlayer2MovementCards().length;
            }
        }
        if (actions == null) {
            stage.computePartyResult();
            actions = new ActionList(true);
            return actions;
        } else {
            actions.setDoEndOfTurn(true);
            return actions;
        }

    }


    private ActionList doAction(int nbMovements, Model model, GameElement pawn, String name, int rowDest, int colDest, int choice, int id, Controller control) {
        RosesStageModel stage = (RosesStageModel) model.getGameStage();
        if (id == 0) {
            movePawn(stage, stage.getBluePawns(), stage.getBluePawnsToPlay(), rowDest, colDest);
            discardACard(stage, stage.getPlayer1MovementCards(), choice, nbMovements);
            // Move the pawn to the destination then place it to the discard
           /* pawn = stage.getBluePot().getElement(0, 0);
            ActionList actions = ActionFactory.generatePutInContainer(control, model, pawn, name, rowDest, colDest, AnimationTypes.MOVE_LINEARPROP, 35);
            ActionPlayer play = new ActionPlayer(model, control, actions);
            stage.removeElement(stage.getPlayer1MovementCards()[choice]);
            stage.getDiscardCards()[nbMovements] = stage.getPlayer1MovementCards()[choice];
            stage.getPlayer1MovementCards()[choice].flip();
            stage.getDiscardPot().addElement(stage.getPlayer1MovementCards()[choice], 0, 0);
            stage.getPlayer1MovementCards()[choice] = null;
             stage.getBoard().moveElement(stage.getCrownPawn(), rowDest, colDest);
            play.start();
            actions = ActionFactory.generatePutInContainer(control, model, stage.getCrownPawn(), stage.getBoard().getName(), rowDest, colDest, AnimationTypes.MOVE_LINEARPROP, 5);

            nbMovements++;
            stage.setNbMovements(nbMovements);
            actions.setDoEndOfTurn(true);
            return actions;*/

        } else {
            movePawn(stage, stage.getRedPawns(), stage.getRedPawnsToPlay(), rowDest, colDest);
            discardACard(stage, stage.getPlayer2MovementCards(), choice, nbMovements);
            /*pawn = stage.getRedPot().getElement(0, 0);
            ActionList actions = ActionFactory.generatePutInContainer(control, model, pawn, name, rowDest, colDest, AnimationTypes.MOVE_LINEARPROP, 35);
            ActionPlayer play = new ActionPlayer(model, control, actions);
            stage.removeElement(stage.getPlayer2MovementCards()[choice]);
            stage.getDiscardCards()[nbMovements] = stage.getPlayer2MovementCards()[choice];
            stage.getPlayer2MovementCards()[choice].flip();
            stage.getDiscardPot().addElement(stage.getPlayer2MovementCards()[choice], 0, 0);
            stage.getPlayer2MovementCards()[choice] = null;
            play.start();
            // stage.getBoard().moveElement(stage.getCrownPawn(), rowDest, colDest);
            actions = ActionFactory.generatePutInContainer(control, model, stage.getCrownPawn(), stage.getBoard().getName(), rowDest, colDest, AnimationTypes.MOVE_LINEARPROP, 5);
            nbMovements++;
            stage.setNbMovements(nbMovements);
            actions.setDoEndOfTurn(true);
            return actions;*/
        }
        return null;
    }

    public void setTempPickCards(RosesStageModel stageModel) {
        // Remove the last element of the pickCard data table
        RosesCard[] tempPickCard = stageModel.getPickCards();
        RosesCard[] copyOfPickPotCards = new RosesCard[tempPickCard.length - 1];
        System.arraycopy(tempPickCard, 0, copyOfPickPotCards, 0, copyOfPickPotCards.length);
        tempPickCard = copyOfPickPotCards;
        stageModel.setPickCards(tempPickCard);
    }

    public void setBackCardsInCardPot(RosesStageModel stageModel) {
        // Set back cards in card pot when the card pot is empty
        if (stageModel.getPickCards().length == 0) {
            int newLength = 0;
            for (int n = 0; n < stageModel.getDiscardCards().length; n++) {
                if (stageModel.getDiscardCards()[n] != null) {
                    newLength++;
                }
            }
            RosesCard[] tempPickCard = new RosesCard[newLength];
            int index = 0;
            for (int p = 0; p < stageModel.getDiscardCards().length; p++) {
                if (stageModel.getDiscardCards()[p] != null) {
                    tempPickCard[index] = stageModel.getDiscardCards()[p];
                    index++;
                }
                stageModel.getDiscardPot().removeElement(stageModel.getDiscardCards()[p]);
            }
            RosesCard[] newEmptyDiscard = new RosesCard[26];
            stageModel.setPickCards(tempPickCard);
            stageModel.setDiscardCards(newEmptyDiscard);
            int nbMovements = 0;
            stageModel.setNbMovements(nbMovements);
        }
    }

    public ActionList playHeroCards(Model model, RosesStageModel gameStage, int row, int col, int choice, int nbMovements, int i) {
        if (model.getIdPlayer() == RosesPawn.PAWN_BLUE){
            if (possibleMovements[i] == null) return null;
            switch (possibleMovements[i]) { // get the destination of the possible hero card
                case "W":
                    col = col - possibleValues[i];
                    choice = i;
                    break;
                case "N-E":
                    col = col + possibleValues[i];
                    row = row - possibleValues[i];
                    choice = i;
                    break;
                case "E":
                    col = col + possibleValues[i];
                    choice = i;
                    break;
                case "S-E":
                    col = col + possibleValues[i];
                    row = row + possibleValues[i];
                    choice = i;
                    break;
                case "S":
                    row = row + possibleValues[i];
                    choice = i;
                    break;
                case "S-W":
                    row = row + possibleValues[i];
                    col = col - possibleValues[i];
                    choice = i;
                    break;
                case "N":
                    row = row - possibleValues[i];
                    choice = i;
                    break;
                default:
                    row = row - possibleValues[i];
                    col = col - possibleValues[i];
                    choice = i;
            }
        }else {
            if (possibleMovements[i] == null) return null;
            switch (possibleMovements[i]) { // get the destination of the possible hero card
                case "W":
                    col = col + possibleValues[i];
                    choice = i;
                    break;
                case "N-E":
                    col = col - possibleValues[i];
                    row = row + possibleValues[i];
                    choice = i;
                    break;
                case "E":
                    col = col - possibleValues[i];
                    choice = i;
                    break;
                case "S-E":
                    col = col - possibleValues[i];
                    row = row - possibleValues[i];
                    choice = i;
                    break;
                case "S":
                    row = row - possibleValues[i];
                    choice = i;
                    break;
                case "S-W":
                    row = row - possibleValues[i];
                    col = col + possibleValues[i];
                    choice = i;
                    break;
                case "N":
                    row = row + possibleValues[i];
                    choice = i;
                    break;
                default:
                    row = row + possibleValues[i];
                    col = col + possibleValues[i];
                    choice = i;
            }
        }
        ActionList actions = new ActionList(true);
        ValidCells = gameStage.getBoard().computeValidCells("H", model.getIdPlayer());
        gameStage.getBoard().setValidCells(ValidCells); // valid cells for the hero cards
        System.out.println("Valid cells : " + ValidCells);
        if (row > 8 || col > 8 || row < 0 || col < 0) { // verifications
            return null;
        }
        if (model.getIdPlayer() == 0 && gameStage.getPlayer1HeroCards().length > 0) {
            actions = new ActionList(true);
            RosesPawn pawnToSwap = (RosesPawn) gameStage.getBoard().getElement(row, col);
            if (pawnToSwap != null && pawnToSwap.getColor() == PAWN_RED) {
                pawnToSwap.setColor(PAWN_BLUE);
                System.out.println(pawnToSwap.getColor());
            } else {
                System.out.println("Invalid move. No pawn at the specified location or the pawn is already of your color.");
                return null;
            }
        } else if (model.getIdPlayer() == 1 && gameStage.getPlayer2HeroCards().length > 0) {
            actions = new ActionList(true);
            RosesPawn pawnToSwap = (RosesPawn) gameStage.getBoard().getElement(row, col);
            if (pawnToSwap != null && pawnToSwap.getColor() == PAWN_BLUE) {
                pawnToSwap.setColor(PAWN_RED);
                System.out.println(pawnToSwap.getColor());
            } else {
                System.out.println("Invalid move. No pawn at the specified location or the pawn is already of your color.");
                return null;
            }
        } else {
            System.out.println("Invalid choice. No hero cards available. Retry!");
            return null;
        }
        if (model.getIdPlayer() == 0) { // if the verifications has been done and the function did not return, then place the hero card
            gameStage.removeElement(gameStage.getPlayer1MovementCards()[choice]);
            gameStage.removeElement(gameStage.getPlayer1HeroCards()[gameStage.getPlayer1HeroCards().length - 1]);
            RosesCard[] tempHeroCards = gameStage.getPlayer1HeroCards();
            RosesCard[] copyOfPickPotCards = new RosesCard[tempHeroCards.length - 1];
            System.arraycopy(tempHeroCards, 0, copyOfPickPotCards, 0, copyOfPickPotCards.length);
            tempHeroCards = copyOfPickPotCards;
            gameStage.setPlayer1HeroCards(tempHeroCards);
            gameStage.getDiscardCards()[nbMovements] = gameStage.getPlayer1MovementCards()[choice];
            gameStage.getPlayer1MovementCards()[choice].flip();
            gameStage.getDiscardPot().addElement(gameStage.getPlayer1MovementCards()[choice], 0, 0);
            gameStage.getPlayer1MovementCards()[choice] = null;
        } else {
            gameStage.removeElement(gameStage.getPlayer2MovementCards()[choice]);
            gameStage.removeElement(gameStage.getPlayer2HeroCards()[gameStage.getPlayer2HeroCards().length - 1]);
            RosesCard[] tempHeroCards = gameStage.getPlayer2HeroCards();
            RosesCard[] copyOfPickPotCards = new RosesCard[tempHeroCards.length - 1];
            System.arraycopy(tempHeroCards, 0, copyOfPickPotCards, 0, copyOfPickPotCards.length);
            tempHeroCards = copyOfPickPotCards;
            gameStage.setPlayer2HeroCards(tempHeroCards);
            gameStage.getDiscardCards()[nbMovements] = gameStage.getPlayer2MovementCards()[choice];
            gameStage.getPlayer2MovementCards()[choice].flip();
            gameStage.getDiscardPot().addElement(gameStage.getPlayer2MovementCards()[choice], 0, 0);
            gameStage.getPlayer2MovementCards()[choice] = null;
        }

        nbMovements++;
        gameStage.setNbMovements(nbMovements);
        gameStage.getBoard().moveElement(gameStage.getCrownPawn(), row, col);
        return actions;
    }

    private void movePawn(RosesStageModel stageModel, RosesPawn[] pawnPot, int nbPionRest, int row, int col) {
        ActionList actions = ActionFactory.generatePutInContainer(control, model, pawnPot[nbPionRest - 1], stageModel.getBoard().getName(), row, col, AnimationTypes.MOVE_LINEARPROP, 35);
        ActionPlayer play = new ActionPlayer(model, control, actions);
        play.start();
        actions = ActionFactory.generatePutInContainer(control, model, stageModel.getCrownPawn(), stageModel.getBoard().getName(), row, col, AnimationTypes.MOVE_LINEARPROP, 5); // je le fais apres avec des facteurs différent pour qu'il soit au dessus de l'autre pion ce neuille
        play = new ActionPlayer(model, control, actions);
        play.start();
        actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.
    }

    private void discardACard(RosesStageModel stageModel, /*GameStageView stageView,*/ RosesCard[] movePot, int index, int i) {
        movePot[index].flip();
        System.out.println("is flipped : " + movePot[index].isFlipped());
        ActionList actions = ActionFactory.generatePutInContainer(control, model, movePot[index], stageModel.getDiscardPot().getName(), 0, 0, AnimationTypes.LOOK_SIMPLE, 10);
        ActionPlayer play = new ActionPlayer(model, control, actions);
        play.start();
        stageModel.getDiscardCards()[i] = movePot[index];
        movePot[index] = null;
        stageModel.playSound("cardpick.mp3");
    }

    public void playHeroCard(RosesStageModel stageModel, int row, int col, int idPlayer, RosesPawn pawnToSwap, int index) {
        GameStageView stageView = view.getGameStageView();
        RosesCard[] movePot;
        ActionList actions = ActionFactory.generatePutInContainer(control, model, stageModel.getCrownPawn(), stageModel.getBoard().getName(), row, col, AnimationTypes.MOVE_LINEARPROP, 5);
        ActionPlayer play = new ActionPlayer(model, control, actions);
        play.start();
        if (idPlayer == 1) {
            pawnToSwap.setColor(PAWN_RED);
            stageModel.removeElement(stageModel.getPlayer2HeroCards()[stageModel.getPlayer2HeroCards().length - 1]);
            RosesCard[] tempHeroCards = stageModel.getPlayer2HeroCards();
            RosesCard[] copyOfPickPotCards = new RosesCard[tempHeroCards.length - 1];
            System.arraycopy(tempHeroCards, 0, copyOfPickPotCards, 0, copyOfPickPotCards.length);
            stageModel.setPlayer2HeroCards(copyOfPickPotCards);
        } else {
            pawnToSwap.setColor(PAWN_BLUE);
            stageModel.removeElement(stageModel.getPlayer1HeroCards()[stageModel.getPlayer1HeroCards().length - 1]);
            RosesCard[] tempHeroCards = stageModel.getPlayer1HeroCards();
            RosesCard[] copyOfPickPotCards = new RosesCard[tempHeroCards.length - 1];
            System.arraycopy(tempHeroCards, 0, copyOfPickPotCards, 0, copyOfPickPotCards.length);
            stageModel.setPlayer1HeroCards(copyOfPickPotCards);
        }
        PawnLook look = (PawnLook) stageView.getElementLook(pawnToSwap);
        look.updatePawn(pawnToSwap, look);
        stageView.addLook(look);
        if (idPlayer == 0) {
            movePot = stageModel.getPlayer1MovementCards();
        } else {
            movePot = stageModel.getPlayer2MovementCards();
        }
        for (int i = 0; i < stageModel.getDiscardCards().length - 1; i++) {
            if (stageModel.getDiscardCards()[i] == null) {
                discardACard(stageModel, movePot, index, i);
                actions.setDoEndOfTurn(true);
            }
        }
        stageModel.playSound("cardpick.mp3");
    }

}

