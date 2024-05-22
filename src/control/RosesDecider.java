package control;

import boardifier.control.ActionFactory;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.*;
import view.RosesStageView;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class RosesDecider extends Decider {

    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());

    private static final String[] typeOfMoves = {"M", "H", "P"};
    private String[] possibleMovements = new String[5];
    int[] possibleValues = new int[5];


    public RosesDecider(Model model, Controller control) {
        super(model, control);
    }

    @Override
    public ActionList decide() {
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
        int[][] redPositions = new int[26][2];
        int redIndex = 0;
        int nbMovements = 0;
        RosesPawn tempPawn = null;
        if (model.getIdPlayer() == RosesPawn.PAWN_BLUE) {
            pawnPot = stage.getBluePot();
        } else {
            pawnPot = stage.getRedPot();
        }

        if (model.getIdPlayer() == RosesPawn.PAWN_RED) {
            for (int k = 0; k < stage.getPlayer2MovementCards().length; k++) {
                if (stage.getPlayer2MovementCards()[k] != null) {
                    possibleMovements[k] = stage.getPlayer2MovementCards()[k].getDirection();
                    possibleValues[k] = stage.getPlayer2MovementCards()[k].getValue();
                }
            }
            for (int i = 0; i < board.getNbRows(); i++) {
                for (int j = 0; j < board.getNbCols(); j++) {
                    if (board.getElement(i, j) != null) {
                        tempPawn= (RosesPawn)board.getElement(i,j);
                        if (tempPawn.getColor() == RosesPawn.PAWN_RED) {
                            redPositions[redIndex][0] = i;
                            redPositions[redIndex][1] = j;
                            redIndex++;
                        } else if (tempPawn.getColor() == RosesPawn.PAWN_YELLOW) {
                            row = i;
                            col = j;
                            System.out.println("neuille");
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
                System.out.println("choice : " + choice);
                System.out.println("Checking destination: rowDest =" + rowDest + " colDest =" + colDest);
                System.out.println("Row : " + row + ", col : " + col);
                if (board.canReachCell(rowDest, colDest)) {
                    for (int m = 0; m < redIndex; m++) {
                        if (rowDest - 1 == redPositions[m][0] && (!board.isElementAt(rowDest, colDest))) {
                            actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice);
                            return actions;
                        } else if (rowDest + 1 == redPositions[m][0] && (!board.isElementAt(rowDest, colDest))) {
                            actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice);
                            return actions;
                        }
                        if (redPositions[m][1] >= 0) {
                            if (colDest + 1 == redPositions[m][1] && (!board.isElementAt(rowDest, colDest))) {
                                actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice);
                                return actions;
                            } else if (rowDest + 1 == redPositions[m][1] && (!board.isElementAt(rowDest, colDest))) {
                                actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice);
                                return actions;
                            }
                        }
                    }
                    if (!board.isElementAt(rowDest, colDest)) {
                        actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice);
                        return actions;
                    }
                }

            }
                for (int i = 0; i < stage.getPlayer2MovementCards().length; i++) {
                    if (stage.getPlayer2MovementCards()[i] == null && stage.getPickCards().length > 0) {
                        stage.getPlayer2MovementCards()[i] = stage.getPickCards()[stage.getPickCards().length - 1];
                        stage.getPlayer2MovementCards()[i].flip();
                        stage.getMoovRedPot().addElement(stage.getPlayer2MovementCards()[i], i, 0);
                        setTempPickCards(stage);

                    }
                }
        }
        return null;
    }



    private static ActionList doAction(int nbMovements, Model model, GameElement pawn, String name, int rowDest, int colDest, int choice) {
        RosesStageModel stage = (RosesStageModel) model.getGameStage();
        pawn = stage.getRedPot().getElement(0, 0, nbMovements);
        ActionList actions = ActionFactory.generatePutInContainer(model, pawn, name, rowDest, colDest);
        stage.removeElement(stage.getPlayer2MovementCards()[choice]);
        stage.getDiscardCards()[nbMovements] = stage.getPlayer2MovementCards()[choice];
        stage.getPlayer2MovementCards()[choice].flip();
        stage.getDiscardPot().addElement(stage.getPlayer2MovementCards()[choice], 0, 0);
        stage.getPlayer2MovementCards()[choice] = null;
        stage.getBoard().moveElement(stage.getCrownPawn(), rowDest, colDest);
        nbMovements++;
        actions.setDoEndOfTurn(true);
        return actions;
    }

    public void setTempPickCards(RosesStageModel stageModel) {
        RosesCard[] tempPickCard = stageModel.getPickCards();
        RosesCard[] copyOfPickPotCards = new RosesCard[tempPickCard.length - 1];
        System.arraycopy(tempPickCard, 0, copyOfPickPotCards, 0, copyOfPickPotCards.length);
        tempPickCard = copyOfPickPotCards;
        stageModel.setPickCards(tempPickCard);
    }



}