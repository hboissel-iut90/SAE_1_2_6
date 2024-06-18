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
import view.RosesRulesPane;
import view.RosesView;

public class RosesMenuController extends ControllerAction implements EventHandler<ActionEvent> {
        private RosesView rosesView;
        private Stage stage;

        public RosesMenuController(Model model, View view, Controller control){
            super(model, view, control);
            this.rosesView = (RosesView) view;
            this.stage = rosesView.getStage();

            // Get the buttons from the view
            RosesMenuPane menuPane = (RosesMenuPane) rosesView.getRootPane();
            Button startButton = menuPane.getStartButton();
            Button helpButton = menuPane.getRulesButton();
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
                control.setControlAction(new RosesModeController(model, rosesView, control));
            } else if (event.getSource() == menuPane.getRulesButton()) {
                rosesView = new RosesView(model, stage, new RosesRulesPane(width , height));
                control.setControlAction(new RosesRulesController(model, rosesView, control));
            } else if (event.getSource() == menuPane.getQuitButton()) {
                // Handle quit button click
                System.exit(0);
            }
        }


}
