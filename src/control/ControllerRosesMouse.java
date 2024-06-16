package control;

import boardifier.control.*;
import boardifier.model.*;
import boardifier.model.action.ActionList;
import boardifier.model.animation.AnimationTypes;
import boardifier.view.GameStageView;
import boardifier.view.View;
import javafx.event.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.*;
import javafx.scene.shape.Rectangle;
import model.*;
import view.PawnLook;
import view.RosesCardLook;

import static model.RosesPawn.PAWN_BLUE;
import static model.RosesPawn.PAWN_RED;

import java.util.ArrayList;
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
        Coord2D clic = new Coord2D(event.getSceneX(), event.getSceneY());

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
        GameStageView stageView = view.getGameStageView();
        String direction;
        int number;
        int row = stageModel.getBoard().getElementCell(stageModel.getCrownPawn())[0];
        int col = stageModel.getBoard().getElementCell(stageModel.getCrownPawn())[1];
        System.out.println("row : " + row);
        Rectangle discardPotBounds = stageModel.getDiscardPotBounds();
        System.out.println("col : " + col);

        int playMove = stageModel.getPlayer1MovementCards().length;

        try {
            boolean isNotEmpty = false;
            for (int i = 0; i < stageModel.getPickCards().length; i++ ) {
                if (stageModel.getPickCards()[i] != null) {
                    isNotEmpty = true;
                }
            }
            if (!isNotEmpty) moveDiscardCardsToPickPot(stageModel);


            for (GameElement element : list) {  // Take elements clicked
                if (element.getType() == ElementTypes.getType("card")) { // Watch if the element is a card

                    System.out.println("Clique sur carte OK");

                    /**
                     * Controller of the pick pot
                     * Take the last card in list
                     * and add to the movement list of the player in alphabet order
                     */
                    if (element == stageModel.getPickCards()[0]) { // Watch if the card is in the pick pot
                        System.out.println("Carte piochée");
                        for (int i = stageModel.getPickCards().length - 1; i > -1; i--) {
                            if (stageModel.getPickCards()[i] != null) {
                                // stageModel.getPickCards()[i].flip();
                                this.pickACard(stageModel, stageView, model.getIdPlayer(), i);
                                stageModel.getPickCards()[i] = null;
                                return;
                            }
                        }
                        return;
                    }


                    /**
                     * Controller of the player 1 hero pot
                     *
                     *
                     */
                    if (element == stageModel.getPlayer1HeroCards()[0] && model.getIdPlayer() == 0) { // Watch if the card is a hero card of the player 1
                        System.out.println("Carte héros P1 appuyé");
                        List<String> possibleMoves = new ArrayList<>();
                        for (int i = 0; i < playMove; i++) {
                            if (stageModel.getPlayer1MovementCards()[i] != null)
                                // Include the index in the string
                                possibleMoves.add(i + 1 + " " + stageModel.getPlayer1MovementCards()[i].getDirection() + " " + stageModel.getPlayer1MovementCards()[i].getValue());
                        }

                        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, possibleMoves);
                        dialog.setTitle("Choose a move");
                        dialog.setHeaderText("Choose a move for your hero card:");
                        dialog.setContentText("Move:");

                        Optional<String> result = dialog.showAndWait();
                        if (result.isPresent()) {
                            String[] parts = result.get().split(" ");
                            // Parse the index from the selected string
                            int index = Integer.parseInt(parts[0]) - 1;
                            direction = parts[1];
                            number = Integer.parseInt(parts[2]);
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

                            if (col < 0 || col > 8 || row < 0 || row > 8 || !stageModel.getBoard().isElementAt(row, col)) {
                                displayError("The move you selected reference a place out of the board. Please select another move"); // sinon il va essayer de prendre le pion en dehors du board
                                return;
                            }
                            RosesPawn pawnToSwap = (RosesPawn) stageModel.getBoard().getElement(row, col);
                            if (stageModel.getPlayer1HeroCards()[0] != null && pawnToSwap.getColor() == PAWN_RED) {
                                playHeroCard(stageModel, row, col, model.getIdPlayer(), pawnToSwap, index);
                            } else {
                                displayError("The move you selected is invalid. Please select another move.");
                            }
                        }
                        return;
                    }

                    /**
                     * Controller of the player 2 hero pot
                     *
                     *
                     */

                    if (element == stageModel.getPlayer2HeroCards()[0] && model.getIdPlayer() == 1) { // Watch if the card is a hero card of the player 2
                        System.out.println("Carte héros P2 appuyé");
                        List<String> possibleMoves = new ArrayList<>();
                        for (int i = 0; i < playMove; i++) {
                            if (stageModel.getPlayer2MovementCards()[i] != null)
                                // Include the index in the string
                                possibleMoves.add(i+1 + " " + stageModel.getPlayer2MovementCards()[i].getDirection() + " " + stageModel.getPlayer2MovementCards()[i].getValue());
                        }

                        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, possibleMoves);
                        dialog.setTitle("Choose a move");
                        dialog.setHeaderText("Choose a move for your hero card:");
                        dialog.setContentText("Move:");

                        Optional<String> result = dialog.showAndWait();
                        if (result.isPresent()) {
                            String[] parts = result.get().split(" ");
                            // Parse the index from the selected string
                            int index = Integer.parseInt(parts[0]) - 1;
                            direction = parts[1];
                            number = Integer.parseInt(parts[2]);
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
                            if (col < 0 || col > 8 || row < 0 || row > 8 || !stageModel.getBoard().isElementAt(row, col)) {
                                displayError("The move you selected reference a place out of the board. Please select another move"); // sinon il va essayer de prendre le pion en dehors du board
                                return;
                            }
                            RosesPawn pawnToSwap = (RosesPawn) stageModel.getBoard().getElement(row, col);
                            if (stageModel.getPlayer2HeroCards()[0] != null && pawnToSwap.getColor() == PAWN_BLUE) {
                                playHeroCard(stageModel, row, col, model.getIdPlayer(), pawnToSwap, index);
                                for (int i = 0; i < stageModel.getDiscardCards().length - 1; i++) {
                                    System.out.println("boucle for");
                                    if (stageModel.getDiscardCards()[i] == null) {
                                        System.out.println("big ampayian");
                                        this.discardACard(stageModel, stageModel.getPlayer2MovementCards(), index);
                                        return;
                                    }
                                }
                            } else {
                                displayError("The move you selected is invalid. Please select another move.");
                            }
                        }
                        return;
                    }


                    for (int index = 0; index < playMove; index++) { // For each card in movementPot

                        /**
                         * Controller of the player 1 movement pot
                         *
                         *
                         */
                        if (model.getIdPlayer() == 0 && element == stageModel.getPlayer1MovementCards()[index]) { // Watch if the card is a movement card of the player 1
                            System.out.println("Carte mouvement P1");
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
                            if (col >= 0 && col <= 8 && row >= 0 && row <= 8 && !stageModel.getBoard().isElementAt(row, col)) {
                                Alert confirmPlay = new Alert(Alert.AlertType.CONFIRMATION);
                                confirmMove(element, confirmPlay, row, col);
                                if (confirmPlay.getResult() == ButtonType.OK && !stageModel.getBoard().isElementAt(row, col)) {
                                    movePawn(stageModel, stageModel.getBluePawns(), stageModel.getBluePawnsToPlay(), row, col);
                                    for (int i = 0; i < stageModel.getDiscardCards().length - 1; i++) {
                                        if (stageModel.getDiscardCards()[i] == null) {
                                            System.out.println("Discard card");
                                            this.discardACard(stageModel, stageModel.getPlayer1MovementCards(), index);
                                            return;
                                        }
                                    }
                                } else {
                                    stageModel.unselectAll();
                                }
                            }
                        }

                        /**
                         * Controller of the player 2 movement pot
                         *
                         *
                         */
                        if (model.getIdPlayer() == 1 && element == stageModel.getPlayer2MovementCards()[index]) { // Watch if the card is a movement card of the player 2
                            System.out.println("Carte mouvement P2");
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
                            if (col >= 0 && col <= 8 && row >= 0 && row <= 8 && !stageModel.getBoard().isElementAt(row, col)) {
                                Alert confirmPlay = new Alert(Alert.AlertType.CONFIRMATION);
                                this.confirmMove(element, confirmPlay, row, col);
                                if (confirmPlay.getResult() == ButtonType.OK && !stageModel.getBoard().isElementAt(row, col)) {
                                    this.movePawn(stageModel, stageModel.getRedPawns(), stageModel.getRedPawnsToPlay(), row, col);
                                    for (int i = 0; i < stageModel.getDiscardCards().length - 1; i++) {
                                        if (stageModel.getDiscardCards()[i] == null) {
                                            this.discardACard(stageModel, stageModel.getPlayer2MovementCards(), index);
                                            return;
                                        }
                                    }
                                } else {
                                    stageModel.unselectAll();
                                }
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            return;
        }
    }

    private void pickACard(RosesStageModel stageModel, GameStageView stageView, int numberOfThePlayer, int lengthOfPickPot) {
        RosesCard[] tmp;

        if (numberOfThePlayer == 0) {
            tmp = stageModel.getPlayer1MovementCards().clone();
        } else if (numberOfThePlayer == 1) {
            tmp = stageModel.getPlayer2MovementCards().clone();
        } else {
            return; // if the player number is invalid, exit the method (should be impossible but its good to clear this aspect)
        }

        for (int j = 0; j < tmp.length; j++) {
            if (tmp[j] == null) {
                ActionList actions;
                ActionPlayer play;

                if (numberOfThePlayer == 0) {
                    actions = ActionFactory.generatePutInContainer(control, model, stageModel.getPickCards()[lengthOfPickPot], stageModel.getMoveBluePot().getName(), 0, j, AnimationTypes.LOOK_SIMPLE, 10);
                    play = new ActionPlayer(model, control, actions);
                    stageModel.getPlayer1MovementCards()[j] = stageModel.getPickCards()[lengthOfPickPot];
                    RosesCardLook look = (RosesCardLook) stageView.getElementLook(stageModel.getPickCards()[lengthOfPickPot]);
                    RosesCard card = stageModel.getPlayer1MovementCards()[j];
                    card.flip();
                    look.update(card, look);
                } else {
                    actions = ActionFactory.generatePutInContainer(control, model, stageModel.getPickCards()[lengthOfPickPot], stageModel.getMoveRedPot().getName(), 0, j, AnimationTypes.LOOK_SIMPLE, 10
                    );
                    play = new ActionPlayer(model, control, actions);
                    stageModel.getPlayer2MovementCards()[j] = stageModel.getPickCards()[lengthOfPickPot];
                    RosesCardLook look = (RosesCardLook) stageView.getElementLook(stageModel.getPickCards()[lengthOfPickPot]);
                    RosesCard card = stageModel.getPlayer2MovementCards()[j];
                    card.flip();
                    look.update(card, look);
                }

                actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.
                play.start();
                break; // once the card is placed, exit the loop to prevent placing the same card multiple times
            }
        }
    }


    private void moveDiscardCardsToPickPot(RosesStageModel stageModel) {
        stageModel.unselectAll();
        for (int i = stageModel.getDiscardCards().length - 1; i > -1; i--) {
            ActionList actions = ActionFactory.generatePutInContainer(control, model, stageModel.getDiscardCards()[i], stageModel.getPickPot().getName(), 0, 0, AnimationTypes.LOOK_SIMPLE, 10);
            ActionPlayer play = new ActionPlayer(model, control, actions);
            play.start();
            stageModel.getDiscardCards()[i] = null;
        }
    }

    private void confirmMove(GameElement element, Alert confirmPlay, int row, int col) {
        element.toggleSelected();
        System.out.println("apayian");
        confirmPlay.setTitle("Confirmation");
        confirmPlay.setContentText("Are you sure to play the card that will make the crown move to the cell (" + row + "," + col + ") ?");
        confirmPlay.showAndWait();
    }

    private void confirmHero(GameElement element, Alert confirmPlay, int row, int col, boolean isHeroCard) { // enfaite ca genere une erreur sur le selected de la carte hero car le look du selected de l'hero card n'est pas faites
        System.out.println("apayian");
        confirmPlay.setTitle("Confirmation");
        confirmPlay.setContentText("Are you sure to play the card that will make the crown move to the cell (" + row + "," + col + ") ?");
        confirmPlay.showAndWait();
    }

    private void movePawn(RosesStageModel stageModel, RosesPawn[] pawnPot, int nbPionRest, int row, int col) {
        ActionList actions = ActionFactory.generatePutInContainer(control, model, pawnPot[nbPionRest - 1], stageModel.getBoard().getName(), row, col, AnimationTypes.MOVE_LINEARPROP, 80);
        ActionPlayer play = new ActionPlayer(model, control, actions);
        play.start();
        actions = ActionFactory.generatePutInContainer(control, model, stageModel.getCrownPawn(), stageModel.getBoard().getName(), row, col, AnimationTypes.MOVE_LINEARPROP, 5); // je le fais apres avec des facteurs différent pour qu'il soit au dessus de l'autre pion ce neuille
        play = new ActionPlayer(model, control, actions);
        play.start();
        actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.
    }

    private void discardACard(RosesStageModel stageModel, /*GameStageView stageView,*/ RosesCard[] movePot, int index) {
        movePot[index].flip();
        System.out.println("is flipped : " + movePot[index].isFlipped());
        ActionList actions = ActionFactory.generatePutInContainer(control, model, movePot[index], stageModel.getDiscardPot().getName(), 0, 0, AnimationTypes.LOOK_SIMPLE, 10);
        ActionPlayer play = new ActionPlayer(model, control, actions);
        play.start();
        movePot[index] = null;
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
                discardACard(stageModel, movePot, index);
                actions.setDoEndOfTurn(true);
            }
        }
    }

    public void displayError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid move");
        alert.setContentText(message);
        alert.showAndWait();
    }
}

