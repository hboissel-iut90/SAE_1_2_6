package control;

import boardifier.control.*;
import boardifier.model.*;
import boardifier.model.action.ActionList;
import boardifier.model.animation.AnimationTypes;
import boardifier.view.GridLook;
import boardifier.view.View;
import javafx.event.*;
import javafx.scene.input.*;
import model.RosesBoard;
import model.RosesPawnPot;
import model.RosesStageModel;
import model.RosesPawn;

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
            if (list.get(list.size() - 1).getType() == ElementTypes.getType("card")) {
                System.out.println("OK");
                // get the board, pot,  and the selected pawn to simplify code in the following
                RosesBoard board = stageModel.getBoard();

                // by default get blue pot
                RosesPawnPot pot = stageModel.getBluePot();

                // but if it's player2 that plays, get red pot
                if (model.getIdPlayer() == 1) {
                    pot = stageModel.getRedPot();
                }
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
                    stageModel.setState(RosesStageModel.STATE_SELECTDEST);
                    ActionPlayer play = new ActionPlayer(model, control, actions);
                    play.start();
                }
            }
        }
        catch (Exception e) {return;}
        /*
        if (stageModel.getState() == RosesStageModel.STATE_SELECTPAWN) {
            for (GameElement element : list) {;
                if (element.getType() == ElementTypes.getType("pawn")) {
                    RosesPawn pawn = (RosesPawn)element;
                    // check if color of the pawn corresponds to the current player id
                    if (pawn.getColor() == model.getIdPlayer()) {
                        element.toggleSelected();
                        stageModel.setState(RosesStageModel.STATE_SELECTDEST);
                        return; // do not allow another element to be selected
                    }
                }
            }
        }

        else if (stageModel.getState() == RosesStageModel.STATE_SELECTDEST) {
            // first check if the click is on the current selected pawn. In this case, unselect it
            for (GameElement element : list) {
                if (element.isSelected()) {
                    element.toggleSelected();
                    stageModel.setState(RosesStageModel.STATE_SELECTPAWN);
                    return;
                }
            }

            // secondly, search if the board has been clicked. If not just return
            boolean boardClicked = false;
            for (GameElement element : list) {
                if (element == stageModel.getBoard()) {
                    boardClicked = true; break;
                }
            }
            if (!boardClicked) return;

            // get the board, pot,  and the selected pawn to simplify code in the following
            RosesBoard board = stageModel.getBoard();

            // by default get blue pot
            RosesPawnPot pot = stageModel.getBluePot();

            // but if it's player2 that plays, get red pot
            if (model.getIdPlayer() == 1) {
                pot = stageModel.getRedPot();
            }
            GameElement pawn = model.getSelected().get(0);

            // thirdly, get the clicked cell in the 3x3 board
            GridLook lookBoard = (GridLook) control.getElementLook(board);
            int[] dest = lookBoard.getCellFromSceneLocation(clic);

            // get the cell in the pot that owns the selected pawn
            int[] from = pot.getElementCell(pawn);
            Logger.debug("try to move pawn from pot "+from[0]+","+from[1]+ " to board "+ dest[0]+","+dest[1]);

            // if the destination cell is valid for for the selected pawn
            if (board.canReachCell(dest[0], dest[1])) {
                ActionList actions = ActionFactory.generatePutInContainer(control, model, pawn, "RoseBoard", dest[0], dest[1], AnimationTypes.MOVE_LINEARPROP, 10);
                actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.
                stageModel.unselectAll();
                stageModel.setState(RosesStageModel.STATE_SELECTPAWN);
                ActionPlayer play = new ActionPlayer(model, control, actions);
                play.start();
            }
        }
        */
    }
}