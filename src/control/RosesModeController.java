package control;

import boardifier.control.Controller;
import boardifier.control.ControllerAction;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.BackgroundImage;
import javafx.stage.Stage;
import view.RosesDiffPane;
import view.RosesMenuPane;
import view.RosesModePane;
import view.RosesView;

public class RosesModeController extends ControllerAction implements EventHandler<ActionEvent> {
    private RosesView rosesView;
    private Stage stage;

    public RosesModeController(Model model, View view, Controller control, Stage stage) {
        super(model, view, control);
        rosesView = (RosesView) view;
        this.stage = stage;

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
        double width = modePane.Width();
        double height = modePane.Height();
        if (event.getSource() == modePane.getPvPButton()) {
            model.addHumanPlayer("player1");
            model.addHumanPlayer("player2");
            try {
                control.startGame();
                stage.setFullScreen(true);
            } catch (GameException err) {
                System.err.println(err.getMessage());
                System.exit(1);
            }

        } else if (event.getSource() == modePane.getPvCButton()) {
            model.addHumanPlayer("player");
            model.addComputerPlayer("computer");
            rosesView = new RosesView(model, stage, new RosesDiffPane(1, width, height));
            control.setControlAction(new RosesDiffController(model, rosesView, 1, control, stage));
        } else if (event.getSource() == modePane.getCvCButton()) {
            model.addComputerPlayer("computer1");
            model.addComputerPlayer("computer2");
            rosesView = new RosesView(model, stage, new RosesDiffPane(2, width, height));
            control.setControlAction(new RosesDiffController(model, rosesView, 2, control, stage));
        } else if (event.getSource() == modePane.getBackButton()) {
            rosesView = new RosesView(model, stage,new RosesMenuPane(width, height));
            control.setControlAction(new RosesMenuController(model, rosesView, control, stage));

        }
    }
}
