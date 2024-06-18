package control;

import boardifier.control.Controller;
import boardifier.control.ControllerKey;
import boardifier.control.Logger;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.*;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;


/**
 * A basic keystrokes handler.
 * Generally useless for board games, but it can still be used if needed
 */
public class ControllerRosesKey extends ControllerKey implements EventHandler<KeyEvent> {
    private Stage stage;
    private RosesController control;
    public ControllerRosesKey(Model model, View view, Controller control) {
        super(model, view, control);
        this.stage = view.getStage();
        this.control = (RosesController) control;
    }

    public void handle(KeyEvent e) {
        if (!model.isCaptureKeyEvent()) return;
        if (e.getEventType() == KeyEvent.KEY_PRESSED) {
            if (e.getCode() == KeyCode.BACK_SPACE) {
                model.pauseGame();
                Dialog<ButtonType> pause = new Dialog<>();
                pause.initModality(Modality.APPLICATION_MODAL);
                pause.getDialogPane().getStylesheets().add("file:src/css/style.css");
                pause.initOwner(stage);
                pause.setTitle("Pause");
                ButtonType resume = new ButtonType("Resume");
                ButtonType stopGame = new ButtonType("Stop Game");
                ButtonType exit = new ButtonType("Exit");

                Button resumeButton = new Button(resume.getText());
                resumeButton.setOnAction(event -> pause.setResult(resume));
                Button restartButton = new Button(stopGame.getText());
                restartButton.setOnAction(event -> pause.setResult(stopGame));
                Button stopGameButton = new Button(exit.getText());
                stopGameButton.setOnAction(event -> pause.setResult(exit));

                VBox pauseVBox = new VBox();
                pauseVBox.getChildren().addAll(resumeButton, restartButton, stopGameButton);

                pause.getDialogPane().setContent(pauseVBox);
                Optional<ButtonType> result = pause.showAndWait();

                if (result.isPresent()) {
                    ButtonType clickedButton = result.get();
                    if (clickedButton == resume) {
                        model.resumeGame();
                    } else if (clickedButton == stopGame) {
                        model.stopGame();
                    } else if (clickedButton == exit) {
                        //control.stopGame();
                        System.exit(0);
                    }
                }

            }
        }
        // if a key is pressed, just prints its code
        Logger.debug(e.getCode().toString());
    }
}

