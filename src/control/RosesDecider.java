package control;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
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

import static model.RosesPawn.PAWN_BLUE;
import static model.RosesPawn.PAWN_RED;

public class RosesDecider extends Decider {

    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());

    private static final String[] typeOfMoves = {"M", "H", "P"};
    private String[] possibleMovements = new String[5];
    int[] possibleValues = new int[5];

    List<Point> ValidCells = null;


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
        int[][] bluePositions = new int[26][2];
        int redIndex = 0;
        int blueIndex = 0;
        int nbMovements = stage.getNbMovements();
        RosesPawn tempPawn = null;
        if (model.getIdPlayer() == RosesPawn.PAWN_BLUE) {
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
                        if (tempPawn.getColor() == RosesPawn.PAWN_BLUE) {
                            bluePositions[blueIndex][0] = i;
                            bluePositions[blueIndex][1] = j;
                            blueIndex++;
                        } else if (tempPawn.getColor() == RosesPawn.PAWN_YELLOW) {
                            row = i;
                            col = j; // ici on gere juste au tout début de partie la position si ya aucun pion encore placé
                        }
                    }
                    if (board.getElement(i, j, 1) != null) { // prend la position du pion couronne car parfois il n'est pas detecté a cause du pion bleu au dessus
                        row = i;
                        col = j;
                        System.out.println("neuille");

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
                    for (int m = 0; m < blueIndex; m++) {
                        if (rowDest - 1 == bluePositions[m][0] && (!board.isElementAt(rowDest, colDest))) {
                            actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer());
                            return actions;
                        } else if (rowDest + 1 == bluePositions[m][0] && (!board.isElementAt(rowDest, colDest))) {
                            actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer());
                            return actions;
                        }
                        if (bluePositions[m][1] >= 0) {
                            if (colDest + 1 == bluePositions[m][1] && (!board.isElementAt(rowDest, colDest))) {
                                actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer());
                                return actions;
                            } else if (rowDest + 1 == bluePositions[m][1] && (!board.isElementAt(rowDest, colDest))) {
                                actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer());
                                return actions;
                            }
                        }
                    }
                    if (!board.isElementAt(rowDest, colDest)) {
                        actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer());
                        return actions;
                    }
                }

            }
            for (int i = 0; i < stage.getPlayer1MovementCards().length; i++) {
                if (stage.getPlayer1MovementCards()[i] == null && stage.getPickCards().length > 0) {
                    stage.getPlayer1MovementCards()[i] = stage.getPickCards()[stage.getPickCards().length - 1];
                    stage.getPlayer1MovementCards()[i].flip();
                    stage.getMoovBluePot().addElement(stage.getPlayer1MovementCards()[i], i, 0);
                    setTempPickCards(stage);
                    setBackCardsInCardPot(stage);
                    actions.setDoEndOfTurn(true);
                    return actions;
                }
            }
            rowDest = row;
            colDest = col;
            for (int i = 0; i < stage.getPlayer1MovementCards().length; i++) {
                actions = playHeroCards(model, stage, rowDest, colDest, choice, nbMovements, i);
                if (actions != null) {
                    i = stage.getPlayer1MovementCards().length;
                    return actions;
                }
            }
            if (actions == null) {
                System.out.println("fulcrand");
                stage.computePartyResult();
            }
            actions = new ActionList();
            return actions;
        } else {
            pawnPot = stage.getRedPot();
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
                            tempPawn = (RosesPawn) board.getElement(i, j);
                            if (tempPawn.getColor() == RosesPawn.PAWN_RED) {
                                redPositions[redIndex][0] = i;
                                redPositions[redIndex][1] = j;
                                redIndex++;
                            } else if (tempPawn.getColor() == RosesPawn.PAWN_YELLOW) {
                                row = i;
                                col = j; // ici on gere juste au tout début de partie la position si ya aucun pion encore placé
                            }
                        }
                        if (board.getElement(i, j, 1) != null) { // prend la position du pion couronne car parfois il n'est pas detecté a cause du pion bleu au dessus
                            row = i;
                            col = j;
                            System.out.println("neuille");

                        }

                    }
                }

                System.out.println("POS CROWN PAWN, ROW : " + row + ", col : " + col);

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
                                actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer());
                                return actions;
                            } else if (rowDest + 1 == redPositions[m][0] && (!board.isElementAt(rowDest, colDest))) {
                                actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer());
                                return actions;
                            }
                            if (colDest + 1 == redPositions[m][1] && (!board.isElementAt(rowDest, colDest))) {
                                actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer());
                                return actions;
                            } else if (rowDest + 1 == redPositions[m][1] && (!board.isElementAt(rowDest, colDest))) {
                                actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer());
                                return actions;
                            }

                        }
                        if (!board.isElementAt(rowDest, colDest)) {
                            actions = doAction(nbMovements, model, pawn, "RoseBoard", rowDest, colDest, choice, model.getIdPlayer());
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


//  CARTE HEROS,

    private static ActionList doAction(int nbMovements, Model model, GameElement pawn, String name, int rowDest, int colDest, int choice, int id) {
        RosesStageModel stage = (RosesStageModel) model.getGameStage();
        if (id == 0) {
            pawn = stage.getBluePot().getElement(0, 0);
            ActionList actions = ActionFactory.generatePutInContainer(model, pawn, name, rowDest, colDest);
            stage.removeElement(stage.getPlayer1MovementCards()[choice]);
            stage.getDiscardCards()[nbMovements] = stage.getPlayer1MovementCards()[choice];
            stage.getPlayer1MovementCards()[choice].flip();
            stage.getDiscardPot().addElement(stage.getPlayer1MovementCards()[choice], 0, 0);
            stage.getPlayer1MovementCards()[choice] = null;
            stage.getBoard().moveElement(stage.getCrownPawn(), rowDest, colDest);
            nbMovements++;
            stage.setNbMovements(nbMovements);
            actions.setDoEndOfTurn(true);
            return actions;
        } else {
            pawn = stage.getRedPot().getElement(0, 0);
            ActionList actions = ActionFactory.generatePutInContainer(model, pawn, name, rowDest, colDest);
            stage.removeElement(stage.getPlayer2MovementCards()[choice]);
            stage.getDiscardCards()[nbMovements] = stage.getPlayer2MovementCards()[choice];
            stage.getPlayer2MovementCards()[choice].flip();
            stage.getDiscardPot().addElement(stage.getPlayer2MovementCards()[choice], 0, 0);
            stage.getPlayer2MovementCards()[choice] = null;
            stage.getBoard().moveElement(stage.getCrownPawn(), rowDest, colDest);
            nbMovements++;
            stage.setNbMovements(nbMovements);
            actions.setDoEndOfTurn(true);
            return actions;
        }
    }

    public void setTempPickCards(RosesStageModel stageModel) {
        RosesCard[] tempPickCard = stageModel.getPickCards();
        RosesCard[] copyOfPickPotCards = new RosesCard[tempPickCard.length - 1];
        System.arraycopy(tempPickCard, 0, copyOfPickPotCards, 0, copyOfPickPotCards.length);
        tempPickCard = copyOfPickPotCards;
        stageModel.setPickCards(tempPickCard);
    }

    public void setBackCardsInCardPot(RosesStageModel stageModel) {
        if (stageModel.getPickCards().length == 0) {
            System.out.println("The pick pot is empty.");

            // Compter le nombre de cartes non nulles dans la défausse
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

            }

            RosesCard[] newEmptyDiscard = new RosesCard[26];
            stageModel.setPickCards(tempPickCard);
            stageModel.setDiscardCards(newEmptyDiscard);


            int nbMovements = 0;
            stageModel.setNbMovements(nbMovements);
        }
    }

    public ActionList playHeroCards(Model model, RosesStageModel gameStage, int row, int col, int choice, int nbMovements, int i) {
            if (possibleMovements[i] == null) return null;
            switch (possibleMovements[i]) {
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
            ActionList actions = new ActionList(true);
            ValidCells = gameStage.getBoard().computeValidCells("H", model.getIdPlayer());
            gameStage.getBoard().setValidCells(ValidCells);
            System.out.println("Valid cells : " + ValidCells);
            if (row > 8 || col > 8 || row < 0 || col < 0) {
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

            if (model.getIdPlayer() == 0) {
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
        System.out.println("ARRIVER A LARRIVER");
        return actions;
    }

}

