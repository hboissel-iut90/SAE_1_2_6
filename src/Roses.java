import boardifier.control.Logger;
import boardifier.control.StageFactory;
import boardifier.model.Model;
import control.RosesController;
import control.RosesMenuController;
import javafx.application.Application;
import javafx.stage.Screen;
import javafx.stage.Stage;
import view.RosesMenuPane;
import view.RosesView;

public class Roses extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Logger.setLevel(Logger.LOGGER_DEBUG); // show info and debug messages
        Model model = new Model();
        double width = Screen.getPrimary().getBounds().getWidth();
        double height = Screen.getPrimary().getBounds().getHeight();
        StageFactory.registerModelAndView("roses", "model.RosesStageModel", "view.RosesStageView");
        RosesMenuPane rootPane = new RosesMenuPane(width, height);
        RosesView view = new RosesView(model, stage, rootPane);
        RosesController control = new RosesController(model, view);
        control.setControlAction(new RosesMenuController(model, view, control));
        control.setFirstStageName("roses");
        stage.setTitle("King of Roses");
        stage.show();

    }
}
