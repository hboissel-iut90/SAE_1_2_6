package control;

import boardifier.control.Controller;
import boardifier.control.ControllerAction;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.RosesDiffPane;
import view.RosesModePane;
import view.RosesView;

public class RosesDiffController extends ControllerAction implements EventHandler<ActionEvent> {
    private RosesView rosesView;
    private int mode;
    private RosesController control;

    private Stage stage;

    public RosesDiffController(Model model, View view, int mode, Controller control, Stage stage) {
        super(model, view, control);
        rosesView = (RosesView) view;
        this.mode = mode;
        this.control = (RosesController) control;
        this.stage = stage;
        System.out.println(model.getPlayers());
        // Get the buttons from the view
        RosesDiffPane modePane = (RosesDiffPane) rosesView.getRootPane();
        Button Diff1;
        Button Diff2;
        Button Diff3 = null;
        Button back;
        if (mode == 1) {
            Diff1 = modePane.getEasyButton();
            Diff2 = modePane.getHardButton();
            back = modePane.getBackButton();
        } else {
            Diff1 = modePane.getEvEButton();
            Diff2 = modePane.getEvHButton();
            Diff3 = modePane.getHvHButton();
            back = modePane.getBackButton();
        }

        // Add event handlers to the buttons
        Diff1.setOnAction(this);
        Diff2.setOnAction(this);
        if (mode != 1 ) {
            Diff3.setOnAction(this);
        }
        back.setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        RosesDiffPane modePane = (RosesDiffPane) rosesView.getRootPane();
        double width = modePane.Width();
        double height = modePane.Height();
        if (mode == 1) {
            if (event.getSource() == modePane.getEasyButton()) {
                // control.setDifficulty("E");
            } else if (event.getSource() == modePane.getHardButton()) {
                // control.setDifficulty("H");
            } else if (event.getSource() == modePane.getBackButton()) {
                model = new Model();
                rosesView = new RosesView(model, stage, new RosesModePane(width, height));
                control.setControlAction(new RosesModeController(model, rosesView, control));
                return;
            }
        } else {
            if (event.getSource() == modePane.getEvEButton()) {
                // control.setDifficulty("EE");
            } else if (event.getSource() == modePane.getEvHButton()) {
                // control.setDifficulty("EH");
            } else if (event.getSource() == modePane.getHvHButton()) {
                // control.setDifficulty("HH");
            } else if (event.getSource() == modePane.getBackButton()) {
                model = new Model();
                rosesView = new RosesView(model, stage, new RosesModePane(width, height));
                control.setControlAction(new RosesModeController(model, rosesView, control));
                return;
            }
        }
        try {
            control.startGame();
            stage.setFullScreen(true);
        } catch (GameException err) {
            System.err.println(err.getMessage());
            System.exit(1);
        }

    }
}
