package control;

import boardifier.control.Controller;
import boardifier.control.ControllerAction;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import view.RosesMenuPane;
import view.RosesModePane;
import view.RosesView;

public class RosesMenuController extends ControllerAction implements EventHandler<ActionEvent> {
        private RosesView rosesView;
        public RosesMenuController(Model model, View view, Controller control) {
            super(model, view, control);
            rosesView = (RosesView) view;

            // Get the buttons from the view
            RosesMenuPane menuPane = (RosesMenuPane) rosesView.getRootPane();
            Button startButton = menuPane.getStartButton();
            Button helpButton = menuPane.getHelpButton();
            Button quitButton = menuPane.getQuitButton();


            // Add event handlers to the buttons
            startButton.setOnAction(this);
            helpButton.setOnAction(this);
            quitButton.setOnAction(this);
        }

        @Override
        public void handle(ActionEvent event) {
            RosesMenuPane menuPane = (RosesMenuPane) rosesView.getRootPane();
            if (event.getSource() == menuPane.getStartButton()) {
                rosesView.setRootPane(new RosesModePane());
                control.setControlAction(new RosesModeController(model, rosesView, control));
            } else if (event.getSource() == menuPane.getHelpButton()) {
                // Handle help button click
                System.out.println("Help button clicked");
            } else if (event.getSource() == menuPane.getQuitButton()) {
                // Handle quit button click
                System.exit(0);
            }
        }


}
