package control;

import boardifier.control.Controller;
import boardifier.control.ControllerAction;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import view.RosesDiffPane;
import view.RosesMenuPane;
import view.RosesModePane;
import view.RosesView;

public class RosesModeController extends ControllerAction implements EventHandler<ActionEvent> {
    private RosesView rosesView;

    public RosesModeController(Model model, View view, Controller control) {
        super(model, view, control);
        rosesView = (RosesView) view;

        // Get the buttons from the view
        RosesModePane modePane = (RosesModePane) rosesView.getRootPane();
        Button PvPButton = modePane.getPvPButton();
        Button PvCButton = modePane.getPvCButton();
        Button CvCButton = modePane.getCvCButton();
        Button backButton = modePane.getBackButton();

        // Add event handlers to the buttons
        PvPButton.setOnAction(this);
        PvCButton.setOnAction(this);
        CvCButton.setOnAction(this);
        backButton.setOnAction(this);

    }

    @Override
    public void handle(ActionEvent event) {
        RosesModePane modePane = (RosesModePane) rosesView.getRootPane();
        if (event.getSource() == modePane.getPvPButton()) {
            model.addHumanPlayer("player1");
            model.addHumanPlayer("player2");
            try {
                control.startGame();
            } catch (GameException err) {
                System.err.println(err.getMessage());
                System.exit(1);
            }

        } else if (event.getSource() == modePane.getPvCButton()) {
            model.addHumanPlayer("player");
            model.addComputerPlayer("computer");
            rosesView.setRootPane(new RosesDiffPane(1));
            control.setControlAction(new RosesDiffController(model, rosesView, 1, control));
        } else if (event.getSource() == modePane.getCvCButton()) {
            model.addComputerPlayer("computer1");
            model.addComputerPlayer("computer2");
            rosesView.setRootPane(new RosesDiffPane(2));
            control.setControlAction(new RosesDiffController(model, rosesView, 2, control));
        } else if (event.getSource() == modePane.getBackButton()) {
            rosesView.setRootPane(new RosesMenuPane());
            control.setControlAction(new RosesMenuController(model, rosesView, control));

        }
    }
}
