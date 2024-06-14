package control;

import boardifier.control.Controller;
import boardifier.control.ControllerAction;
import boardifier.model.GameException;
import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import view.RosesDiffPane;
import view.RosesMenuPane;
import view.RosesModePane;
import view.RosesView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static javafx.beans.binding.Bindings.isEmpty;

public class RosesModeController extends ControllerAction implements EventHandler<ActionEvent> {
    private RosesView rosesView;
    private Stage stage;

    public RosesModeController(Model model, View view, Controller control) {
        super(model, view, control);
        this.rosesView = (RosesView) view;
        this.stage = rosesView.getStage();

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

    public List<String> playersNames(Model model){
        List<Player> players = model.getPlayers();
        List<String> playersNames = new ArrayList<>();
        String[] result = null;

        do {
            Dialog<String[]> dialog = new Dialog<>();
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.setTitle("Players Names");

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField player1 = new TextField();
            player1.setText(players.get(0).getName());
            TextField player2 = new TextField();
            player2.setText(players.get(1).getName());

            grid.add(new Label("Player 1:"), 0, 0);
            grid.add(player1, 1, 0);
            grid.add(new Label("Player 2:"), 0, 1);
            grid.add(player2, 1, 1);

            dialog.getDialogPane().setContent(grid);

            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == ButtonType.OK) {
                    // Check if the text fields are not empty
                    if (!player1.getText().trim().isEmpty() && !player2.getText().trim().isEmpty()) {
                        return new String[]{player1.getText(), player2.getText()};
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Empty fields");
                        alert.setContentText("Please fill in the fields");
                        alert.showAndWait();
                    }
                } else if (dialogButton == ButtonType.CANCEL) {
                    return new String[]{"cancel", "cancel"};
                }
            return null;
            });
            stage.setFullScreen(false);
            result = dialog.showAndWait().orElse(null);
            stage.setFullScreen(true);
        } while (result == null || result.length != 2 || result[0].trim().isEmpty() || result[1].trim().isEmpty());
        playersNames = Arrays.asList(result);
        return playersNames;
    }

    @Override
    public void handle(ActionEvent event) {
        RosesModePane modePane = (RosesModePane) rosesView.getRootPane();
        double width = modePane.Width();
        double height = modePane.Height();
        List<String> playerNames = new ArrayList<>();
        if (event.getSource() == modePane.getPvPButton()) {
            model.addHumanPlayer("player1");
            model.addHumanPlayer("player2");
            playerNames = playersNames(model);
            if (playerNames.get(0).equals("cancel") || playerNames.get(1).equals("cancel")) {
                model = new Model();
                return;
            }
            model.getPlayers().get(0).setName(playerNames.get(0));
            model.getPlayers().get(1).setName(playerNames.get(1));
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
            playerNames = playersNames(model);
            if (playerNames.get(0).equals("cancel") || playerNames.get(1).equals("cancel")) {
                model = new Model();
                return;
            }
            model.getPlayers().get(0).setName(playerNames.get(0));
            model.getPlayers().get(1).setName(playerNames.get(1));
            rosesView = new RosesView(model, stage, new RosesDiffPane(1, width, height));
            control.setControlAction(new RosesDiffController(model, rosesView, 1, control, stage));
        } else if (event.getSource() == modePane.getCvCButton()) {
            model.addComputerPlayer("computer1");
            model.addComputerPlayer("computer2");
            playerNames = playersNames(model);
            if (playerNames.get(0).equals("cancel") || playerNames.get(1).equals("cancel")) {
                model = new Model();
                return;
            }
            model.getPlayers().get(0).setName(playerNames.get(0));
            model.getPlayers().get(1).setName(playerNames.get(1));
            rosesView = new RosesView(model, stage, new RosesDiffPane(2, width, height));
            control.setControlAction(new RosesDiffController(model, rosesView, 2, control, stage));
        } else if (event.getSource() == modePane.getBackButton()) {
            rosesView = new RosesView(model, stage,new RosesMenuPane(width, height));
            control.setControlAction(new RosesMenuController(model, rosesView, control));

        }
    }
}
