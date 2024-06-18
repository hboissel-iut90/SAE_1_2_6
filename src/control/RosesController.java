package control;

import boardifier.control.ActionPlayer;
import boardifier.control.Controller;
import boardifier.control.Logger;
import boardifier.model.*;
import boardifier.view.View;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.view.View;
import javafx.scene.control.Dialog;
import model.RosesPawn;
import model.RosesStageModel;
import view.RosesMenuPane;
import view.RosesStageView;
import view.RosesView;

import java.util.Optional;
import java.util.concurrent.CountDownLatch;

import static model.RosesPawn.PAWN_BLUE;
import static model.RosesPawn.PAWN_RED;

public class RosesController extends Controller {

    private String difficulty;
    int nb = 0;


    public RosesController(Model model, View view) {
        super(model, view);
        setControlKey(new ControllerRosesKey(model, view, this));
        setControlMouse(new ControllerRosesMouse(model, view, this));
        this.difficulty = "";
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public void startGame() throws GameException {
        super.startGame();
        // get the new player
        // get the new player
        Player p = model.getCurrentPlayer();
        ActionPlayer play = null;
        // change the text of the TextElement
        RosesStageModel stageModel = (RosesStageModel) model.getGameStage();
        stageModel.getPlayerName().setText(p.getName());
        if (p.getType() == Player.COMPUTER) {
            Logger.debug("COMPUTER PLAYS");
            if (difficulty.equals("EE")|| difficulty.equals("E")) {
                RosesDeciderEasy decider = new RosesDeciderEasy(model, view, this);
                play = new ActionPlayer(model, this, decider, null);
            } else if (difficulty.equals("HH") || difficulty.equals("H")) {
                RosesDeciderHard decider = new RosesDeciderHard(model, this, view);
                play = new ActionPlayer(model, this, decider, null);
            } else {
                if (model.getIdPlayer() == 0) {
                    RosesDeciderEasy decider = new RosesDeciderEasy(model, view,this);
                    play = new ActionPlayer(model, this, decider, null);
                } else {
                    RosesDeciderHard decider = new RosesDeciderHard(model, this,view);
                    play = new ActionPlayer(model, this, decider, null);
                }
            }
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            play.start();
            stageModel.update();
        } else {
            Logger.debug("PLAYER PLAYS");
            stageModel.update();
        }
    }


    @Override
    public void endGame() {
        if (nb > 0) {
            return;
        }
        nb++;
        Stage stage = view.getStage();
        model.setCaptureEvents(false);
        RosesStageModel gameStage = (RosesStageModel) model.getGameStage();
        int[] results = gameStage.computePartyResult();
        Alert end = new Alert(Alert.AlertType.CONFIRMATION);
        end.initOwner(stage);
        end.initModality(Modality.APPLICATION_MODAL);
        end.getDialogPane().getStylesheets().add(("file:src/css/style.css"));
        ButtonType Restart = new ButtonType("Restart");
        ButtonType mainMenu = new ButtonType("Main Menu");
        end.getButtonTypes().clear();
        end.getButtonTypes().addAll(Restart, mainMenu);
        end.setTitle("End of the game");
        end.setHeaderText("The game is over");
        if (results[2] > results[3]) {
            end.setContentText("The winner is " + model.getPlayers().get(0).getName() + " with " + results[2] + " points\n" +
                    "Player 1 has " + results[0] + " pawns on the board with " + results[2] + " pts.\n" +
                    "Player 2 has " + results[1] + " pawns on the board with " + results[3] + " pts.");
        } else if (results[2] < results[3]) {
            end.setContentText("The winner is " + model.getPlayers().get(1).getName() + " with " + results[3] + " points\n" +
                    "Player 1 has " + results[0] + " pawns on the board with " + results[2] + " pts.\n" +
                    "Player 2 has " + results[1] + " pawns on the board with " + results[3] + " pts.");
        } else {
            end.setContentText("It's a draw with " + results[0] + " points\n" +
                    "Player 1 has " + results[0] + " pawns on the board with " + results[2] + " pts.\n" +
                    "Player 2 has " + results[1] + " pawns on the board with " + results[3] + " pts.");
        }

        Optional<ButtonType> option = end.showAndWait();
        if (option.get() == Restart) {
            try {
                nb=0;
                startGame();
                stage.setFullScreen(true);
            } catch (GameException e) {
                throw new RuntimeException(e);
            }
        } else {
            nb=0;
            double width = Screen.getPrimary().getBounds().getWidth();
            double height = Screen.getPrimary().getBounds().getHeight();
            model.getPlayers().clear();
            RosesMenuPane rootPane = new RosesMenuPane(width, height);
            view = new RosesView(model, stage, rootPane);
            setControlAction(new RosesMenuController(model, view, this));
            setControlKey(new ControllerRosesKey(model, view, this));
            setControlMouse(new ControllerRosesMouse(model, view, this));
        }
    }

    public void endOfTurn() {
        // use the default method to compute next player
        model.setNextPlayer();
        // get the new player
        Player p = model.getCurrentPlayer();
        ActionPlayer play = null;
        // change the text of the TextElement
        RosesStageModel stageModel = (RosesStageModel) model.getGameStage();
        stageModel.getPlayerName().setText(p.getName());
        if (p.getType() == Player.COMPUTER) {
            Logger.debug("COMPUTER PLAYS");
            if (difficulty.equals("EE")|| difficulty.equals("E")) {
                RosesDeciderEasy decider = new RosesDeciderEasy(model, view, this);
                play = new ActionPlayer(model, this, decider, null);
            } else if (difficulty.equals("HH") || difficulty.equals("H")) {
                RosesDeciderHard decider = new RosesDeciderHard(model, this, view);
                play = new ActionPlayer(model, this, decider, null);
            } else {
                if (model.getIdPlayer() == 0) {
                    RosesDeciderEasy decider = new RosesDeciderEasy(model, view,this);
                    play = new ActionPlayer(model, this, decider, null);
                } else {
                    RosesDeciderHard decider = new RosesDeciderHard(model, this, view);
                    play = new ActionPlayer(model, this, decider, null);
                }
            }
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            play.start();
            stageModel.update();
        } else {
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
                }
            }

        }

        if (!tempCheck && model.getIdPlayer() == 0) {
            for (int i = 0; i < gameStage.getPlayer1HeroCards().length; i++) {
                int row = stageModel.getBoard().getElementCell(stageModel.getCrownPawn())[0];
                int col = stageModel.getBoard().getElementCell(stageModel.getCrownPawn())[1];
                String checkDirection = gameStage.getPlayer1MovementCards()[i].getDirection();
                int checkCardValue = gameStage.getPlayer1MovementCards()[i].getValue();
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
                if (row >= 0 && col >= 0 && row <= 8 && col <= 8 && i > 0 && gameStage.getBoard().isElementAt(row, col)) {
                    RosesPawn tempPawn = (RosesPawn) gameStage.getBoard().getElement(row, col);
                    if ((model.getIdPlayer() == 0 && stageModel.getPlayer1HeroCards().length > 0 && tempPawn.getColor() == PAWN_RED)) {
                        tempCheck = true;
                        System.out.println("can play hero cards ID PLAYER 0");
                        gameStage.setChecked(tempCheck);
                        return;
                    }
                }
            }
        } else if (!tempCheck && model.getIdPlayer() == 1) {
            for (int i = 0; i < gameStage.getPlayer2HeroCards().length; i++) {
                int row = stageModel.getBoard().getElementCell(stageModel.getCrownPawn())[0];
                int col = stageModel.getBoard().getElementCell(stageModel.getCrownPawn())[1];
                String checkDirection = gameStage.getPlayer2MovementCards()[i].getDirection();
                int checkCardValue = gameStage.getPlayer2MovementCards()[i].getValue();
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
                if (row >= 0 && col >= 0 && row <= 8 && col <= 8 && i > 0 && gameStage.getBoard().isElementAt(row, col)) {
                    RosesPawn tempPawn = (RosesPawn) gameStage.getBoard().getElement(row, col);
                    if (model.getIdPlayer() == 1 && stageModel.getPlayer2HeroCards().length > 0 && tempPawn.getColor() == PAWN_BLUE) {
                        tempCheck = true;
                        System.out.println("can play hero cards ID PLAYER 1");
                        gameStage.setChecked(tempCheck);
                        return;
                    }
                }
            }
        }
        System.out.println("c'est au tour de : " + model.getIdPlayer());
        endGame();
    }
}
