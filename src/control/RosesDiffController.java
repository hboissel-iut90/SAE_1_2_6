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
import view.RosesView;

public class RosesDiffController extends ControllerAction implements EventHandler<ActionEvent> {
    private RosesView rosesView;
    private int mode;
    private RosesController control;

    public RosesDiffController(Model model, View view, int mode, Controller control) {
        super(model, view, control);
        rosesView = (RosesView) view;
        this.mode = mode;
        this.control = (RosesController) control;

        // Get the buttons from the view
        RosesDiffPane modePane = (RosesDiffPane) rosesView.getRootPane();
        Button Diff1;
        Button Diff2;
        Button back;
        if (mode == 1) {
            Diff1 = modePane.getEasyButton();
            Diff2 = modePane.getHardButton();
            back = modePane.getBackButton();
        } else {
            Diff1 = modePane.getEvEButton();
            Diff2 = modePane.getEvHButton();
            back = modePane.getBackButton();
        }

        // Add event handlers to the buttons
        Diff1.setOnAction(this);
        Diff2.setOnAction(this);
        back.setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        RosesDiffPane modePane = (RosesDiffPane) rosesView.getRootPane();
        if (mode == 1) {
            if (event.getSource() == modePane.getEasyButton()) {
                control.setDifficulty("E");
            } else if (event.getSource() == modePane.getHardButton()) {
                control.setDifficulty("H");
            } else if (event.getSource() == modePane.getBackButton()) {
                rosesView.setRootPane(new RosesMenuPane());
                control.setControlAction(new RosesMenuController(model, rosesView, control));
                return;
            }
        } else {
            if (event.getSource() == modePane.getEvEButton()) {
                control.setDifficulty("EE");
            } else if (event.getSource() == modePane.getEvHButton()) {
                control.setDifficulty("EH");
            } else if (event.getSource() == modePane.getBackButton()) {
                rosesView.setRootPane(new RosesMenuPane());
                control.setControlAction(new RosesMenuController(model, rosesView, control));
                return;
            }
        }
        try {
            control.startGame();
        } catch (GameException err) {
            System.err.println(err.getMessage());
            System.exit(1);
        }

    }
}
