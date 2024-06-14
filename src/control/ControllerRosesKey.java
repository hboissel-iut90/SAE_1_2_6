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
    public ControllerRosesKey(Model model, View view, Controller control) {
        super(model, view, control);
        this.stage = view.getStage();
    }

    public void handle(KeyEvent e) {
        if (!model.isCaptureKeyEvent()) return;
        if (e.getEventType() == KeyEvent.KEY_PRESSED) {
            if (e.getCode() == KeyCode.BACK_SPACE) {
                model.setCaptureKeyEvent(false);
                Dialog<ButtonType> pause = new Dialog<>();
                pause.initModality(Modality.APPLICATION_MODAL);
                pause.setTitle("Pause");
                ButtonType resume = new ButtonType("Resume");
                ButtonType restart = new ButtonType("Restart");
                ButtonType stopGame = new ButtonType("Exit");

                Button resumeButton = new Button(resume.getText());
                resumeButton.setOnAction(event -> pause.setResult(resume));
                Button restartButton = new Button(restart.getText());
                restartButton.setOnAction(event -> pause.setResult(restart));
                Button stopGameButton = new Button(stopGame.getText());
                stopGameButton.setOnAction(event -> pause.setResult(stopGame));

                VBox pauseVBox = new VBox();
                pauseVBox.getChildren().addAll(resumeButton, restartButton, stopGameButton);

                pause.getDialogPane().setContent(pauseVBox);
                stage.setFullScreen(false);
                Optional<ButtonType> result = pause.showAndWait();
                stage.setFullScreen(true);

                if (result.isPresent()) {
                    ButtonType clickedButton = result.get();
                    if (clickedButton == resume) {
                        model.setCaptureKeyEvent(true);
                    } else if (clickedButton == restart) {
                        model.setCaptureKeyEvent(true);
                    } else if (clickedButton == stopGame) {
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

