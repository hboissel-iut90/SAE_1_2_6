import boardifier.control.Logger;
import boardifier.model.GameException;
import boardifier.view.View;
import boardifier.control.StageFactory;
import boardifier.model.Model;
import control.RosesController;

public class RosesConsole {

    public static void main(String[] args) {
        int mode = 0;
        String difficulty = "";
        if (args.length == 1) {
            try {
                mode = Integer.parseInt(args[0]);
                if ((mode < 0) || (mode > 2)) mode = 0;
            } catch (NumberFormatException e) {
                mode = 0;
            }
        } else if (args.length == 2) {
            try {
                mode = Integer.parseInt(args[0]);
                difficulty = args[1].toUpperCase();
                if ((mode < 0) || (mode > 2)) mode = 0;
                if (!difficulty.equals("H") && !difficulty.equals("E")) difficulty = "";
            } catch (NumberFormatException e) {
                mode = 0;
            }
        } else if (args.length == 3) {
            try {
                mode = Integer.parseInt(args[0]);
                difficulty = args[1].toUpperCase();
                difficulty = difficulty.concat(args[2]);
                if ((mode < 0) || (mode > 2)) mode = 0;
                if (!difficulty.equals("HE") && !difficulty.equals("EE") && !difficulty.equals("HH")) difficulty = "";
            } catch (NumberFormatException e) {
                mode = 0;
            }
        }

        Model model = new Model();
        if (mode == 0) {
            model.addHumanPlayer("player1");
            model.addHumanPlayer("player2");
        } else if (mode == 1) {
            model.addHumanPlayer("player");
            model.addComputerPlayer("computer");
        } else if (mode == 2) {
            model.addComputerPlayer("computer1");
            model.addComputerPlayer("computer2");
        }

        StageFactory.registerModelAndView("RoseBoard", "model.RosesStageModel", "view.RosesStageView");
        View rosesStageView = new View(model);
        RosesController control = new RosesController(model, rosesStageView, difficulty);
        control.setFirstStageName("RoseBoard");
        try {
            control.startGame();
            control.stageLoop();
        } catch (GameException e) {
            System.out.println(mode);
            System.out.println(difficulty);
            System.out.println("Cannot start the game. Abort");
        }
    }
}
