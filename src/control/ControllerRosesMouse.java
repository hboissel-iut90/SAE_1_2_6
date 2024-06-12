package control;

import boardifier.control.*;
import boardifier.model.*;
import boardifier.model.action.ActionList;
import boardifier.model.animation.AnimationTypes;
import boardifier.view.GridLook;
import boardifier.view.View;
import javafx.event.*;
import javafx.scene.input.*;
import model.*;

import java.util.List;

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

        try {
            for (GameElement element : list) {
                if (element.getType() == ElementTypes.getType("card")) {
                    System.out.println("Clique sur carte OK");
                    if (element == stageModel.getPickCards()[0]) {
                        System.out.println("Carte piochée");
                        return;
                    }

                    if (element == stageModel.getPlayer1HeroCards()[0]) {
                        System.out.println("Carte héros P1 appuyé");
                        return;
                    }

                    if (element == stageModel.getPlayer2HeroCards()[0]) {
                        System.out.println("Carte héros P2 appuyé");
                        return;
                    }

                    for (int index = 0; index < stageModel.getPlayer1MovementCards().length; index++) {
                        if (element == stageModel.getPlayer1MovementCards()[index]) {
                            System.out.println("Carte mouvement P1 appuyé");
                            RosesCard move = (RosesCard) element;
                            System.out.println(move.getColor());

                            // Get the board, pot, and the selected pawn to simplify code in the following
                            RosesBoard board = stageModel.getBoard();

                            // Get blue pot
                            RosesPawnPot pot = stageModel.getBluePot();


                            GameElement pawn = model.getSelected().get(0);

                            // thirdly, get the clicked cell in the 3x3 board
                            GridLook lookBoard = (GridLook) control.getElementLook(board);
                            int[] dest = lookBoard.getCellFromSceneLocation(clic);

                            // get the cell in the pot that owns the selected pawn
                            int[] from = pot.getElementCell(pawn);
                            Logger.debug("try to move pawn from pot " + from[0] + "," + from[1] + " to board " + dest[0] + "," + dest[1]);

                            // if the destination cell is valid for for the selected pawn
                            if (board.canReachCell(dest[0], dest[1])) {
                                ActionList actions = ActionFactory.generatePutInContainer(control, model, pawn, "RoseBoard", dest[0], dest[1], AnimationTypes.MOVE_LINEARPROP, 10);
                                actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.
                                stageModel.unselectAll();
                                ActionPlayer play = new ActionPlayer(model, control, actions);
                                play.start();
                            }
                        }

                        if (element == stageModel.getPlayer2MovementCards()[index]) {
                            System.out.println("Carte mouvement P2 appuyé");
                            RosesCard move = (RosesCard) element;
                            System.out.println(move.getColor());

                            // Get the board, pot, and the selected pawn to simplify code in the following
                            RosesBoard board = stageModel.getBoard();

                            // Get blue pot
                            RosesPawnPot pot = stageModel.getBluePot();


                            GameElement pawn = model.getSelected().get(0);

                            // thirdly, get the clicked cell in the 3x3 board
                            GridLook lookBoard = (GridLook) control.getElementLook(board);
                            int[] dest = lookBoard.getCellFromSceneLocation(clic);

                            // get the cell in the pot that owns the selected pawn
                            int[] from = pot.getElementCell(pawn);
                            Logger.debug("try to move pawn from pot " + from[0] + "," + from[1] + " to board " + dest[0] + "," + dest[1]);

                            // if the destination cell is valid for for the selected pawn
                            if (board.canReachCell(dest[0], dest[1])) {
                                ActionList actions = ActionFactory.generatePutInContainer(control, model, pawn, "RoseBoard", dest[0], dest[1], AnimationTypes.MOVE_LINEARPROP, 10);
                                actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.
                                stageModel.unselectAll();
                                ActionPlayer play = new ActionPlayer(model, control, actions);
                                play.start();
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e) {return;}
    }
}

