package control;

import boardifier.control.ActionFactory;
import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.model.*;
import boardifier.model.action.ActionList;
import boardifier.view.View;
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
                    if (line.length() == 1) {
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
            int row = lastRow;
            int col = lastCol;
        RosesStageModel gameStage = (RosesStageModel) model.getGameStage();
        String direction = "";
        int number = 0;
        int choice = (int) (line.charAt(0) - '1');
        switch (choice) {
            case 0:
                direction = gameStage.getPlayer1MovementCards()[choice].getDirection();
                number = gameStage.getPlayer1MovementCards()[choice].getValue();
            case 1:
                direction = gameStage.getPlayer1MovementCards()[choice].getDirection();
                number = gameStage.getPlayer1MovementCards()[choice].getValue();
            case 2:
                direction = gameStage.getPlayer1MovementCards()[choice].getDirection();
                number = gameStage.getPlayer1MovementCards()[choice].getValue();
            case 3:
                direction = gameStage.getPlayer1MovementCards()[choice].getDirection();
                number = gameStage.getPlayer1MovementCards()[choice].getValue();
            case 4:
                direction = gameStage.getPlayer1MovementCards()[choice].getDirection();
                number = gameStage.getPlayer1MovementCards()[choice].getValue();

        }

        // get the pawn value from the first char
        if (isTheFirstTime) {
             row = 5;
             col = 5;
        }

        isTheFirstTime = false;

            if (direction.equals("W")) {
            row = row + number;
            System.out.println("neeeeee : " + row + "xdxd" + col);
        }
        // check coords validity
        if ((row < 0) || (row > 8)) return false;
        if ((col < 0) || (col > 8)) return false;
        // check if the pawn is still in its pot
        ContainerElement pot = null;
        if (model.getIdPlayer() == 0) {
            pot = gameStage.getBluePot();
        } else {
            pot = gameStage.getRedPot();
        }
        System.out.println(row);
        if (pot.isEmptyAt(0, 0)) return false;
        GameElement pawn = pot.getElement(0, 0);
        // compute valid cells for the chosen pawn
        gameStage.getBoard().setValidCells(1);
        System.out.println("apayian");
        if (!gameStage.getBoard().canReachCell(row, col)) return false;





        ActionList actions = ActionFactory.generatePutInContainer(model, pawn, "holeboard", row, col);
        actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.
        ActionPlayer play = new ActionPlayer(model, this, actions);
        lastCol = col;
        lastRow = row;
        play.start();
        return true;
    }
}
