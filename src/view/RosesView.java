package view;

import boardifier.model.Model;
import boardifier.view.RootPane;
import boardifier.view.View;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class RosesView extends View {

    public RosesView(Model model, Stage stage, RootPane rootPane) {
        super(model, stage, rootPane);
    }

    public void setRootPane(RootPane rootPane) {
        this.rootPane = rootPane;
    }
}