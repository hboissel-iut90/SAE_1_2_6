package control;

import boardifier.control.Controller;
import boardifier.control.ControllerAction;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import view.RosesMenuPane;
import view.RosesModePane;
import view.RosesView;

public class RosesMenuController extends ControllerAction implements EventHandler<ActionEvent> {
        private RosesView rosesView;
        private Stage stage;
        public RosesMenuController(Model model, View view, Controller control, Stage stage){
            super(model, view, control);
            rosesView = (RosesView) view;
            this.stage = stage;

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
            double width = menuPane.Width();
            double height = menuPane.Height();
            if (event.getSource() == menuPane.getStartButton()) {
                rosesView = new RosesView(model, stage, new RosesModePane(width , height));
                control.setControlAction(new RosesModeController(model, rosesView, control, stage));
            } else if (event.getSource() == menuPane.getHelpButton()) {
                // Handle help button click
                System.out.println("Help button clicked");
            } else if (event.getSource() == menuPane.getQuitButton()) {
                // Handle quit button click
                System.exit(0);
            }
        }


}
