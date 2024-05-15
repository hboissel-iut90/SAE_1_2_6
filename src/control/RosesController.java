package control;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.model.*;
import boardifier.model.action.ActionList;
import boardifier.view.View;
import model.RosesCard;
import model.RosesStageModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class RosesController extends Controller {

    BufferedReader consoleIn;
    boolean firstPlayer;
    boolean isTheFirstTime = true;
    int lastRow = 0;
    int lastCol = 0;

    public RosesController(Model model, View view) {
        super(model, view);
        firstPlayer = true;
    }

    /**
     * Defines what to do within the single stage of the single party
     * It is pretty straight forward to write :
     */
    public void stageLoop() {
        consoleIn = new BufferedReader(new InputStreamReader(System.in));
        update();
        while (!model.isEndStage()) {
            playTurn();
            endOfTurn();
            update();
        }
        endGame();
    }

    private void playTurn() {
        // get the new player
        Player p = model.getCurrentPlayer();
        if (p.getType() == Player.COMPUTER) {
            System.out.println("COMPUTER PLAYS");
            RosesDecider decider = new RosesDecider(model, this);
            ActionPlayer play = new ActionPlayer(model, this, decider, null);
            play.start();
        } else {
            boolean ok = false;
            while (!ok) {
                System.out.print(p.getName() + " > ");
                try {
                    String line = consoleIn.readLine();
                    if (line.length() == 2) {
                        ok = analyseAndPlay(line);
                    }
                    if (!ok) {
                        System.out.println("incorrect instruction. retry !");
                    }
                } catch (IOException e) {
                }
            }
        }
    }

    public void endOfTurn() {

        model.setNextPlayer();
        // get the new player to display its name
        Player p = model.getCurrentPlayer();
        RosesStageModel stageModel = (RosesStageModel) model.getGameStage();
        stageModel.getPlayerName().setText(p.getName());
        stageModel.updatePawnsToPlay(view.getGameStageView());
    }

    private boolean analyseAndPlay(String line) {

        int row, col;
        if (isTheFirstTime) {
            row = 4;
            col = 4;
        } else {
            row = lastRow;
            col = lastCol;
        }



        RosesStageModel gameStage = (RosesStageModel) model.getGameStage();
        int pawnIndex = 0;
        System.out.println("derniere colonne : " + col + ", derniere ligne : " + row);
        String direction = "";
        String cardType = "";
        int number = 0;
        cardType = String.valueOf(line.charAt(0));
        int choice = (int) (line.charAt(1) - '1');
        if (choice >= 0 && choice < gameStage.getPlayer1MovementCards().length && model.getIdPlayer() == 0 && cardType.equals("M") &&
        gameStage.getPlayer1MovementCards()[choice] != null) {
            direction = gameStage.getPlayer1MovementCards()[choice].getDirection();
            number = gameStage.getPlayer1MovementCards()[choice].getValue();
        } else if (choice >= 0 && choice < gameStage.getPlayer2MovementCards().length && model.getIdPlayer() == 1 && cardType.equals("M") &&
                gameStage.getPlayer2MovementCards()[choice] != null) {
            direction = gameStage.getPlayer2MovementCards()[choice].getDirection();
            number = gameStage.getPlayer2MovementCards()[choice].getValue();
        } else {
            System.out.println("Invalid choice. Retry!");
            return false;
        }

        System.out.println("neuille 1 : " + direction);
        System.out.println("neuille 2 : " + number);

        // get the pawn value from the first char



        switch (direction) {
            case "W":
                col = col - number;
                break;
            case "N-E":
                col = col + number;
                row = row - number;
                break;
            case "E":
                col = col + number;
                break;
            case "S-E":
                col = col + number;
                row = row + number;
                break;
            case "S":
                row = row + number;
                break;
            case "S-W":
                row = row + number;
                col = col - number;
                break;
            case "N":
                row = row - number;
                break;
            default:
                row = row - number;
                col = col - number;

        }
        // check coords validity
        if ((row < 0) || (row > 8)) {
            col = lastCol;
            row = lastRow;
            return false;
        }
        if ((col < 0) || (col > 8)) {
            col = lastCol;
            row = lastRow;
            return false;
        }
        // check if the pawn is still in its pot
        ContainerElement pot = null;
        if (model.getIdPlayer() == 0) {
            pot = gameStage.getBluePot();
        } else {
            pot = gameStage.getRedPot();
        }
        if (pot.isEmptyAt(pawnIndex, 0)) {
            col = lastCol;
            row = lastRow;
            return false;
        }
        GameElement pawn = pot.getElement(pawnIndex, 0);
        // compute valid cells for the chosen pawn
        gameStage.getBoard().setValidCells(pawnIndex);
        if (!gameStage.getBoard().canReachCell(row, col)) {
            col = lastCol;
            row = lastRow;
            return false;
        }
        System.out.println("row : " + row);
        System.out.println("col : " + col);


        ActionList actions = ActionFactory.generatePutInContainer(model, pawn, "RoseBoard", row, col);
        actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.
        ActionPlayer play = new ActionPlayer(model, this, actions);
        lastRow = row;
        lastCol = col;
        isTheFirstTime = false;
        play.start();
        if (model.getIdPlayer() == 0) {
            gameStage.removeElement(gameStage.getPlayer1MovementCards()[choice]);
            gameStage.getPlayer1MovementCards()[choice] = null;
        } else {
            gameStage.removeElement(gameStage.getPlayer2MovementCards()[choice]);
            gameStage.getPlayer2MovementCards()[choice] = null;
        }


        return true;
    }
}
