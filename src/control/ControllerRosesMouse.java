package control;

import boardifier.control.*;
import boardifier.model.*;
import boardifier.model.action.ActionList;
import boardifier.model.animation.AnimationTypes;
import boardifier.view.GridLook;
import boardifier.view.View;
import javafx.application.Platform;
import javafx.event.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.input.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import model.*;
import view.RosesCardLook;
import view.RosesStageView;

import java.util.List;
import java.util.Optional;

/**
 * A basic mouse controller that just grabs the mouse clicks and prints out some informations.
 * It gets the elements of the scene that are at the clicked position and prints them.
 */
public class ControllerRosesMouse extends ControllerMouse implements EventHandler<MouseEvent> {

    public ControllerRosesMouse(Model model, View view, Controller control) {
        super(model, view, control);
    }

    public void handle(MouseEvent event) {
        // if mouse event capture is disabled in the model, just return
        if (!model.isCaptureMouseEvent()) return;

        // get the clic x,y in the whole scene (this includes the menu bar if it exists)
        Coord2D clic = new Coord2D(event.getSceneX(),event.getSceneY());

        // get elements at that position
        List<GameElement> list = control.elementsAt(clic);

        // for debug, uncomment next instructions to display x,y and elements at that postion
        /*
        Logger.debug("click in "+event.getSceneX()+","+event.getSceneY());
        for(GameElement element : list) {
            Logger.debug(element);
        }
         */

        RosesStageModel stageModel = (RosesStageModel) model.getGameStage();
        RosesStageView stageView = (RosesStageView) view.getGameStageView();
        String direction;
        int number;
        int row = stageModel.getBoard().getElementCell(stageModel.getCrownPawn())[0];
        int col = stageModel.getBoard().getElementCell(stageModel.getCrownPawn())[1];
        System.out.println("row : " + row);
        Rectangle discardPotBounds = stageModel.getDiscardPotBounds();
        System.out.println("col : " + col);

        int playMove = stageModel.getPlayer1MovementCards().length;

        try {
            for (GameElement element : list) {  // Take elements clicked
                if (element.getType() == ElementTypes.getType("card")) { // Watch if the element is a card

                    System.out.println("Clique sur carte OK");

                    /**
                     * Controller to the pick pot
                     * Take the last card in list
                     * and add to the movement list of the player in alphabet order
                     */
                    if (element == stageModel.getPickCards()[0]) { // Watch if the card is in the pick pot
                        System.out.println("Carte piochée");
                        for (int i = stageModel.getPickCards().length-1; i > -1; i--) {
                            if (stageModel.getPickCards()[i] != null) {
                                this.pickACard(stageModel, stageView, model.getIdPlayer(), element, i);
                                return;
                            }
                        }
                        return;
                    }

                    if (element == stageModel.getPlayer1HeroCards()[0]) { // Watch if the card is a hero card of the player 1
                        System.out.println("Carte héros P1 appuyé");
                        return;
                    }

                    if (element == stageModel.getPlayer2HeroCards()[0]) {  // Watch if the card is a hero card of the player 2
                        System.out.println("Carte héros P2 appuyé");
                        return;
                    }



                    for (int index = 0; index < playMove; index++) {

                        if (model.getIdPlayer() == 0 && element == stageModel.getPlayer1MovementCards()[index]) { // Watch if the card is a movement card of the player 1
                            System.out.println("Carte mouvement P1 neuille");
                            direction = stageModel.getPlayer1MovementCards()[index].getDirection();
                            number = stageModel.getPlayer1MovementCards()[index].getValue();
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
                            if (col >= 0 && col <= 8 && row >= 0 && row <= 8) {
                                Alert confirmPlay = new Alert(Alert.AlertType.CONFIRMATION);
                                RosesCard move = (RosesCard) element;
                                confirmMove(element, confirmPlay, row, col);
                                if (confirmPlay.getResult() == ButtonType.OK && !stageModel.getBoard().isElementAt(row, col)) {
                                    movePawn(stageModel, stageModel.getBluePawns(), row, col);
                                } else {
                                    stageModel.unselectAll();
                                }
                            }
                        }

                        if (model.getIdPlayer() == 1 && element == stageModel.getPlayer2MovementCards()[index]) { // Watch if the card is a movement card of the player 2
                            System.out.println("Carte mouvement P2 nouille");
                            direction = stageModel.getPlayer2MovementCards()[index].getDirection();
                            number = stageModel.getPlayer2MovementCards()[index].getValue();
                            switch (direction) {
                                case "W":
                                    col = col + number;
                                    break;
                                case "N-E":
                                    col = col - number;
                                    row = row + number;
                                    break;
                                case "E":
                                    col = col - number;
                                    break;
                                case "S-E":
                                    col = col - number;
                                    row = row - number;
                                    break;
                                case "S":
                                    row = row - number;
                                    break;
                                case "S-W":
                                    row = row - number;
                                    col = col + number;
                                    break;
                                case "N":
                                    row = row + number;
                                    break;
                                default:
                                    row = row + number;
                                    col = col + number;
                            }
                            if (col >= 0 && col <= 8 && row >= 0 && row <= 8) {
                                Alert confirmPlay = new Alert(Alert.AlertType.CONFIRMATION);
                                RosesCard move = (RosesCard) element;
                                confirmMove(element, confirmPlay, row, col);
                                if (confirmPlay.getResult() == ButtonType.OK && !stageModel.getBoard().isElementAt(row, col)) {
                                    movePawn(stageModel, stageModel.getRedPawns(), row, col);
                                } else {
                                    stageModel.unselectAll();
                                }
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {return;}
    }

    private void pickACard(RosesStageModel stageModel, RosesStageView stageView, int numberOfThePlayer, GameElement element, int lengthOfPickPot){
        RosesCard[] tmp = new RosesCard[stageModel.getPlayer1MovementCards().length];

        if (numberOfThePlayer == 0) {
            tmp = stageModel.getPlayer1MovementCards().clone();
        }
        if (numberOfThePlayer == 1) {
            tmp = stageModel.getPlayer2MovementCards().clone();
        }

        RosesCard card = (RosesCard) element;
        card.flip();
        for (int j = 0; j < tmp.length; j++) {
            if (tmp[j] == null) {
                if (numberOfThePlayer == 0) {
                    stageModel.setPlayer1MovementCards(j, card);
                    stageView.addLook(new RosesCardLook(80, 110, stageModel.getPlayer1HeroCards()[j], stageModel));
                }
                if (numberOfThePlayer == 1) {
                    stageModel.setPlayer2MovementCards(j, card);
                    stageView.addLook(new RosesCardLook(80, 110, stageModel.getPlayer2HeroCards()[j], stageModel));
                }

                stageModel.setPickCards(lengthOfPickPot, null);
                break;
            }
        }
    }

    private void confirmMove(GameElement element, Alert confirmPlay, int row, int col){
        element.toggleSelected();
        System.out.println("apayian");
        confirmPlay.setTitle("Confirmation");
        confirmPlay.setContentText("Are u sure to play the card that will make the crown move to the cell " + row + "," + col + " ?");
        confirmPlay.showAndWait();
    }

    private void movePawn(RosesStageModel stageModel, RosesPawn[] pawnPot, int row, int col) {
        ActionList actions = ActionFactory.generatePutInContainer(control, model, pawnPot[pawnPot.length-1], "RoseBoard", row, col, AnimationTypes.MOVE_LINEARPROP, 8);
        stageModel.unselectAll();
        ActionPlayer play = new ActionPlayer(model, control, actions);
        play.start();
        actions = ActionFactory.generatePutInContainer(control, model, stageModel.getCrownPawn(), "RoseBoard", row, col, AnimationTypes.MOVE_LINEARPROP, 1); // je le fais apres avec des facteurs différent pour qu'il soit au dessus de l'autre pion ce neuille
        play = new ActionPlayer(model, control, actions);
        play.start();
        actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.
    }
}

