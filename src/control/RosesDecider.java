package control;

import boardifier.control.ActionFactory;
import boardifier.control.Controller;
import boardifier.control.Decider;
import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.model.action.ActionList;
import model.RosesBoard;
import model.RosesPawnPot;
import model.RosesStageModel;
import model.RosesPawn;

import java.awt.*;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class RosesDecider extends Decider {

    private static final Random loto = new Random(Calendar.getInstance().getTimeInMillis());

    public RosesDecider(Model model, Controller control) {
        super(model, control);
    }

    @Override
    public ActionList decide() {
        // do a cast get a variable of the real type to get access to the attributes of HoleStageModel
        RosesStageModel stage = (RosesStageModel)model.getGameStage();
        RosesBoard board = stage.getBoard(); // get the board
        RosesPawnPot pot = null; // the pot where to take a pawn
        GameElement pawn = null; // the pawn that is moved
        int rowDest = 0; // the dest. row in board
        int colDest = 0; // the dest. col in board

        if (model.getIdPlayer() == RosesPawn.PAWN_BLUE) {
            pot = stage.getBluePot();
        }
        else {
            pot = stage.getRedPot();
        }

        for(int i=0;i<1;i++) {
            RosesPawn p = (RosesPawn)pot.getElement(0,0);
            // if there is a pawn in i.
            if (p != null) {
                // get the valid cells
                List<Point> valid = board.computeValidCells(p.getNumber());
                if (!valid.isEmpty()) {
                    // choose at random one of the valid cells
                    int id = loto.nextInt(valid.size());
                    pawn = p;
                    rowDest = valid.get(id).y;
                    colDest = valid.get(id).x;
                    break; // stop the loop
                }
            }
        }

        ActionList actions = ActionFactory.generatePutInContainer( model, pawn, "holeboard", rowDest, colDest);
        actions.setDoEndOfTurn(true); // after playing this action list, it will be the end of turn for current player.

        return actions;
    }
}
