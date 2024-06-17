package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.Logger;
import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.view.View;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import model.RosesPawn;
import model.RosesStageModel;
import view.RosesGameResultView;
import view.RosesStageView;

import static model.RosesPawn.PAWN_BLUE;
import static model.RosesPawn.PAWN_RED;

public class RosesController extends Controller {

    private RosesGameResultView resultView;

    public RosesController(Model model, View view) {
        super(model, view);
        setControlKey(new ControllerRosesKey(model, view, this));
        setControlMouse(new ControllerRosesMouse(model, view, this));
    }

    Stage primaryStage;

    public void endOfTurn() {
        // use the default method to compute next player
        model.setNextPlayer();
        // get the new player
        Player p = model.getCurrentPlayer();
        // change the text of the TextElement
        RosesStageModel stageModel = (RosesStageModel) model.getGameStage();
        stageModel.getPlayerName().setText(p.getName());
        if (p.getType() == Player.COMPUTER) {
            Logger.debug("COMPUTER PLAYS");
            RosesDeciderEasy decider = new RosesDeciderEasy(model, view, this);
            ActionPlayer play = new ActionPlayer(model, this, decider, null);
            play.start();
            stageModel.update();
        }
        else {
            checkIfPlayerCanPlay(stageModel);
            Logger.debug("PLAYER PLAYS");
            stageModel.update();
        }
    }

    private void checkIfPlayerCanPlay(RosesStageModel stageModel) {
        RosesStageModel gameStage = (RosesStageModel) model.getGameStage();
        RosesStageView viewStage = (RosesStageView) view.getGameStageView();

        boolean tempCheck = false;

        for (int i = 0; i < gameStage.getPlayer1MovementCards().length; i++) {
            int row = stageModel.getBoard().getElementCell(stageModel.getCrownPawn())[0];
            int col = stageModel.getBoard().getElementCell(stageModel.getCrownPawn())[1];

            if (model.getIdPlayer() == 0 && gameStage.getPlayer1MovementCards()[i] == null) {
                tempCheck = true; // il peut piocher donc jouer
                return;
            } else if (model.getIdPlayer() == 1 && gameStage.getPlayer2MovementCards()[i] == null) {
                tempCheck = true;
                return;
            }

            if (!tempCheck) {
                String checkDirection;
                int checkCardValue;

                if (model.getIdPlayer() == 0) {
                    checkDirection = gameStage.getPlayer1MovementCards()[i].getDirection();
                    checkCardValue = gameStage.getPlayer1MovementCards()[i].getValue();
                } else {
                    checkDirection = gameStage.getPlayer2MovementCards()[i].getDirection();
                    checkCardValue = gameStage.getPlayer2MovementCards()[i].getValue();
                }

                if (model.getIdPlayer() == 1) {
                    switch (checkDirection) {
                        case "W":
                            col = col + checkCardValue;
                            break;
                        case "N-E":
                            col = col - checkCardValue;
                            row = row + checkCardValue;
                            break;
                        case "E":
                            col = col - checkCardValue;
                            break;
                        case "S-E":
                            col = col - checkCardValue;
                            row = row - checkCardValue;
                            break;
                        case "S":
                            row = row - checkCardValue;
                            break;
                        case "S-W":
                            row = row - checkCardValue;
                            col = col + checkCardValue;
                            break;
                        case "N":
                            row = row + checkCardValue;
                            break;
                        default:
                            row = row + checkCardValue;
                            col = col + checkCardValue;
                    }
                } else {
                    switch (checkDirection) {
                        case "W":
                            col = col - checkCardValue;
                            break;
                        case "N-E":
                            col = col + checkCardValue;
                            row = row - checkCardValue;
                            break;
                        case "E":
                            col = col + checkCardValue;
                            break;
                        case "S-E":
                            col = col + checkCardValue;
                            row = row + checkCardValue;
                            break;
                        case "S":
                            row = row + checkCardValue;
                            break;
                        case "S-W":
                            row = row + checkCardValue;
                            col = col - checkCardValue;
                            break;
                        case "N":
                            row = row - checkCardValue;
                            break;
                        default:
                            row = row - checkCardValue;
                            col = col - checkCardValue;
                    }
                }

                if (row >= 0 && col >= 0 && gameStage.getBoard().canReachCell(row, col) && !gameStage.getBoard().isElementAt(row, col)) {
                    tempCheck = true;
                    return;
                } else if (row >= 0 && col >= 0 && row <= 8 && col <= 8 && i > 0 && gameStage.getBoard().isElementAt(row, col)) {
                    RosesPawn tempPawn = (RosesPawn) gameStage.getBoard().getElement(row, col);
                    if (model.getIdPlayer() == 0 && i < gameStage.getPlayer1HeroCards().length && gameStage.getPlayer1HeroCards()[i - 1] != null && tempPawn.getColor() == PAWN_RED) {
                        tempCheck = true;
                        System.out.println("can play hero cards");
                        gameStage.setChecked(tempCheck);
                        return;
                    } else if (model.getIdPlayer() == 1 && i < gameStage.getPlayer2HeroCards().length && gameStage.getPlayer2HeroCards()[i - 1] != null && tempPawn.getColor() == PAWN_BLUE) {
                        tempCheck = true;
                        System.out.println("can play hero cards");
                        gameStage.setChecked(tempCheck);
                        return;
                    }
                }
            }

        }
        System.out.println("c'est au tour de : " + model.getIdPlayer());
        stageModel.computePartyResult();
    }

    public void handleEndOfGame(int nbBlue, int nbRed, int blueScore, int redScore, int idWinner) {
        // Afficher les résultats de la partie dans la nouvelle vue des résultats
        System.out.println(Color.BLUE + "[Player 1]" + Color.BLACK + " Blue pawns on the field : " + nbBlue);
        System.out.println(Color.RED + "[Player 2]" + Color.BLACK + " Red pawns on the field : " + nbRed);
        System.out.println(Color.BLUE + "[Player 1]" + Color.BLACK + " Blue score : " + blueScore);
        System.out.println(Color.RED + "[Player 2]" + Color.BLACK + " Red score : " + redScore);
        resultView.show(nbBlue, nbRed, blueScore, redScore, idWinner);
    }
}
