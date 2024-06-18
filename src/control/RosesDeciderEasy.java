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
import javafx.application.Platform;
import model.*;
import view.PawnLook;
import view.RosesCardLook;
import view.RosesStageView;
import view.RosesView;

import javax.swing.*;
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
        RosesStageView stageView = (RosesStageView) view.getGameStageView();
        model.getPlayers().get(model.getIdPlayer()).setName("Easy computer");
        RosesStageModel stage = (RosesStageModel) model.getGameStage();
        RosesBoard board = stage.getBoard();
        RosesPawnPot pawnPot = null;
        GameElement pawn = null;
        GameElement yellowPawn = stage.getCrownPawn();
        int rowDest = 0;
        int colDest = 0;
        int row = stage.getBoard().getElementCell(stage.getCrownPawn())[0];
        int col = stage.getBoard().getElementCell(stage.getCrownPawn())[1];
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
                control.endGame();
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
                        }
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
                if (colDest >= 0 && colDest <= 8 && rowDest >= 0 && rowDest <= 8) { // Check if we can put our pawn adjacent to a pawn of the colour of the IA
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
            for (int i = stage.getPickCards().length - 1; i > -1; i--) {
                if (stage.getPickCards()[i] != null) {
                    // stageModel.getPickCards()[i].flip();
                    actions = this.pickACard(stage, stageView, model.getIdPlayer(), i);
                    boolean isNotEmpty = false;

                    for (int k = 0; k < stage.getPickCards().length; k++) {
                        if (stage.getPickCards()[k] != null) {
                            isNotEmpty = true;
                        }
                    }
                    if (!isNotEmpty) {
                        moveDiscardCardsToPickPot(stage);
                    }
                    if (actions != null) {
                        actions.setDoEndOfTurn(true);
                        return actions;
                    }
                }
            }
            // if he can't, he will try to play a hero card
            for (int i = 0; i < stage.getPlayer1MovementCards().length; i++) {
                rowDest = row;
                colDest = col;
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
                if (rowDest <= 8 && rowDest >= 0 && colDest <= 8 && colDest >= 0) {
                    RosesPawn pawnToSwap = (RosesPawn) stage.getBoard().getElement(rowDest, colDest);
                    if (board.isElementAt(rowDest, colDest) && pawnToSwap.getColor() == PAWN_RED && stage.getPlayer1HeroCards().length > 0) {
                        actions = playHeroCard(stage, rowDest, colDest, model.getIdPlayer(), pawnToSwap, i);
                    }
                    if (actions != null) {
                        i = stage.getPlayer1MovementCards().length;
                        return actions;
                    }
                }
            }
            // if he cant play a hero card, action will be null so the game will be over, else, this will play a hero card
            if (actions == null) {
                control.endGame();
            }
            actions = new ActionList();
            return actions;
        } else { // basically, this is the same logic if the player has the red pawns
            row = stage.getBoard().getElementCell(stage.getCrownPawn())[0];
            col = stage.getBoard().getElementCell(stage.getCrownPawn())[1];
            pawnPot = stage.getRedPot();
            if (model.getIdPlayer() == RosesPawn.PAWN_RED) {
                if (stage.getRedPawnsToPlay() == 0) {
                    control.endGame();
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
                            }
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
                for (int i = stage.getPickCards().length - 1; i > -1; i--) {
                    if (stage.getPickCards()[i] != null) {
                        // stageModel.getPickCards()[i].flip();
                        actions = this.pickACard(stage, stageView, model.getIdPlayer(), i);
                        boolean isNotEmpty = false;

                        for (int k = 0; k < stage.getPickCards().length; k++) {
                            if (stage.getPickCards()[k] != null) {
                                isNotEmpty = true;
                            }
                        }
                        if (!isNotEmpty) {
                            moveDiscardCardsToPickPot(stage);
                        }
                        if (actions != null) {
                            actions.setDoEndOfTurn(true);
                            return actions;
                        }
                    }
                }
            }
        }
        for (int i = 0; i < stage.getPlayer2MovementCards().length; i++) {
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
            if (rowDest <= 8 && rowDest >= 0 && colDest <= 8 && colDest >= 0) {
                RosesPawn pawnToSwap = (RosesPawn) stage.getBoard().getElement(rowDest, colDest);
                if (board.isElementAt(rowDest, colDest) && pawnToSwap.getColor() == PAWN_BLUE && stage.getPlayer2HeroCards().length > 0)
                    actions = playHeroCard(stage, rowDest, colDest, model.getIdPlayer(), pawnToSwap, i);
                if (actions != null) {
                    i = stage.getPlayer2MovementCards().length;
                    return actions;
                }
            }
        }
        if (actions == null) {
            control.endGame();
            actions = new ActionList(true);
            return actions;
        } else {
            actions.setDoEndOfTurn(true);
            return actions;
        }

    }


    private ActionList doAction(int nbMovements, Model model, GameElement pawn, String name, int rowDest, int colDest, int choice, int id, Controller control) {
        RosesStageModel stage = (RosesStageModel) model.getGameStage();
        ActionList actions = new ActionList();
        if (id == 0) {
            actions.addAll(movePawn(stage, stage.getBluePawns(), stage.getBluePawnsToPlay(), rowDest, colDest));
            for (int i = 0; i < stage.getDiscardCards().length - 1; i++) {
                if (stage.getDiscardCards()[i] == null) {
                    actions.addAll(discardACard(stage, stage.getPlayer1MovementCards(), choice, i));
                    break;
                }
            }
        } else {
            actions.addAll(movePawn(stage, stage.getRedPawns(), stage.getRedPawnsToPlay(), rowDest, colDest));
            for (int i = 0; i < stage.getDiscardCards().length - 1; i++) {
                if (stage.getDiscardCards()[i] == null) {
                    actions.addAll(discardACard(stage, stage.getPlayer2MovementCards(), choice, i));
                    break;
                }
            }
        }
        actions.setDoEndOfTurn(true);
        return actions;
    }


    private ActionList movePawn(RosesStageModel stageModel, RosesPawn[] pawnPot, int nbPionRest, int row, int col) {
        ActionList actions = ActionFactory.generatePutInContainer(control, model, pawnPot[nbPionRest - 1], stageModel.getBoard().getName(), row, col, AnimationTypes.MOVE_LINEARPROP, 40);
        ActionPlayer play = new ActionPlayer(model, control, actions);
        play.start();
        actions = ActionFactory.generatePutInContainer(control, model, stageModel.getCrownPawn(), stageModel.getBoard().getName(), row, col, AnimationTypes.MOVE_LINEARPROP, 4); // je le fais apres avec des facteurs différent pour qu'il soit au dessus de l'autre pion ce neuille
        play = new ActionPlayer(model, control, actions);
        actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.
        return actions;
    }

    private ActionList discardACard(RosesStageModel stageModel, /*GameStageView stageView,*/ RosesCard[] movePot, int index, int i) {
        movePot[index].flip();
        ActionList actions = ActionFactory.generatePutInContainer(control, model, movePot[index], stageModel.getDiscardPot().getName(), 0, 0, AnimationTypes.LOOK_SIMPLE, 10);
        ActionPlayer play = new ActionPlayer(model, control, actions);
        stageModel.getDiscardCards()[i] = movePot[index];
        movePot[index] = null;
        stageModel.playSound("cardpick.mp3");
        return actions;
    }

    public ActionList playHeroCard(RosesStageModel stageModel, int row, int col, int idPlayer, RosesPawn pawnToSwap, int index) {
        GameStageView stageView = view.getGameStageView();
        RosesCard[] movePot;
        ActionList actions = new ActionList();
        actions.addAll(ActionFactory.generatePutInContainer(control, model, stageModel.getCrownPawn(), stageModel.getBoard().getName(), row, col, AnimationTypes.MOVE_LINEARPROP, 5));
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
        Platform.runLater(() -> look.updatePawn(pawnToSwap, look));
        Platform.runLater(() -> stageView.addLook(look));
        if (idPlayer == 0) {
            movePot = stageModel.getPlayer1MovementCards();
        } else {
            movePot = stageModel.getPlayer2MovementCards();
        }
        for (int i = 0; i < stageModel.getDiscardCards().length - 1; i++) {
            if (stageModel.getDiscardCards()[i] == null) {
                discardACard(stageModel, movePot, index, i);
                actions.setDoEndOfTurn(true);
                return actions;
            }
        }
        stageModel.playSound("cardpick.mp3");
        return actions;
    }

    private ActionList pickACard(RosesStageModel stageModel, GameStageView stageView, int numberOfThePlayer, int lengthOfPickPot) {
        RosesCard[] tmp;
        ActionList actions = new ActionList();
        if (numberOfThePlayer == 0) {
            tmp = stageModel.getPlayer1MovementCards().clone();
        } else if (numberOfThePlayer == 1) {
            tmp = stageModel.getPlayer2MovementCards().clone();
        } else {
            return null; // if the player number is invalid, exit the method (should be impossible but its good to clear this aspect)
        }

        for (int j = 0; j < tmp.length; j++) {
            if (tmp[j] == null) {
                ActionPlayer play;

                if (numberOfThePlayer == 0) {
                    actions = ActionFactory.generatePutInContainer(control, model, stageModel.getPickCards()[lengthOfPickPot], stageModel.getMoveBluePot().getName(), 0, j, AnimationTypes.LOOK_SIMPLE, 10);
                    play = new ActionPlayer(model, control, actions);
                    stageModel.getPlayer1MovementCards()[j] = stageModel.getPickCards()[lengthOfPickPot];
                    RosesCardLook look = (RosesCardLook) stageView.getElementLook(stageModel.getPickCards()[lengthOfPickPot]);
                    RosesCard card = stageModel.getPlayer1MovementCards()[j];
                    card.flip();
                    // Update the look on the JavaFX Application Thread
                    Platform.runLater(() -> look.update(card, look));
                } else {
                    actions = ActionFactory.generatePutInContainer(control, model, stageModel.getPickCards()[lengthOfPickPot], stageModel.getMoveRedPot().getName(), 0, j, AnimationTypes.LOOK_SIMPLE, 10);
                    play = new ActionPlayer(model, control, actions);
                    stageModel.getPlayer2MovementCards()[j] = stageModel.getPickCards()[lengthOfPickPot];
                    RosesCardLook look = (RosesCardLook) stageView.getElementLook(stageModel.getPickCards()[lengthOfPickPot]);
                    RosesCard card = stageModel.getPlayer2MovementCards()[j];
                    card.flip();
                    // Update the look on the JavaFX Application Thread
                    Platform.runLater(() -> look.update(card, look));
                }

                actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.
                stageModel.getPickCards()[lengthOfPickPot] = null;
                stageModel.playSound("cardpick.mp3");
                return actions;
            }
        }
        return null;
    }


    private void moveDiscardCardsToPickPot(RosesStageModel stageModel) {
        int nb = 0;
        stageModel.unselectAll();
        RosesStageView stageView = (RosesStageView) view.getGameStageView();

        for (int i = 0; i < stageModel.getDiscardCards().length; i++) {
            if (stageModel.getDiscardCards()[i] != null) {
                if (nb == 14) {
                    break;
                }
                RosesCard card = stageModel.getDiscardCards()[i];
                stageModel.getPickCards()[nb] = card;
                stageModel.getDiscardCards()[i] = null;


                // Create actions to animate the card movement
                ActionList actions = ActionFactory.generatePutInContainer(control, model, card, stageModel.getPickPot().getName(), 0, 0, AnimationTypes.LOOK_SIMPLE, 10);
                ActionPlayer actionPlayer = new ActionPlayer(model, control, actions);
                actionPlayer.start();


                // Update the visual representation of the card in the stage view
                RosesCardLook look = (RosesCardLook) stageView.getElementLook(card);
                if (look != null) {
                    Platform.runLater(() -> look.update(card, look));
                }

                nb++;
            }
        }
    }

}
