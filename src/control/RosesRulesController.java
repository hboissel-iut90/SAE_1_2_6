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
import view.RosesRulesPane;
import view.RosesView;

public class RosesRulesController extends ControllerAction implements EventHandler<ActionEvent>{
    private RosesView rosesView;
    private Stage stage;

    public RosesRulesController(Model model, View view, Controller control){
        super(model, view, control);
        this.rosesView = (RosesView) view;
        this.stage = rosesView.getStage();

        // Get the buttons from the view
        RosesRulesPane rulesPane = (RosesRulesPane) rosesView.getRootPane();
        Button backButton = rulesPane.getBackButton();

        // Add event handlers to the buttons
        backButton.setOnAction(this);
    }

    @Override
    public void handle(ActionEvent event) {
        RosesRulesPane rulesPane = (RosesRulesPane) rosesView.getRootPane();
        double width = rulesPane.Width();
        double height = rulesPane.Height();
        if (event.getSource() == rulesPane.getBackButton()) {
            rosesView = new RosesView(model, stage, new RosesMenuPane(width, height));
            control.setControlAction(new RosesMenuController(model, rosesView, control));
        }
    }
}
