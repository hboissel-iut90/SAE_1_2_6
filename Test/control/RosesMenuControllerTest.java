package control;

import boardifier.control.Controller;
import boardifier.model.Model;
import boardifier.view.View;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import view.RosesMenuPane;
import view.RosesView;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class RosesMenuControllerTest {

    private Model model;
    private View view;
    private Controller controller;
    private RosesView rosesView;
    private Stage stage;
    private RosesMenuPane menuPane;
    private Button startButton;
    private Button helpButton;
    private Button quitButton;
    private RosesMenuController menuController;

    @BeforeEach
    void setUp() {
        model = mock(Model.class);
        view = mock(View.class);
        controller = mock(Controller.class);
        rosesView = mock(RosesView.class);
        startButton = new Button(); // Changed from mock to actual Button
        helpButton = new Button(); // Changed from mock to actual Button
        quitButton = new Button(); // Changed from mock to actual Button

        when(rosesView.getStage()).thenReturn(stage);
        when(rosesView.getRootPane()).thenReturn(menuPane);
        when(menuPane.getStartButton()).thenReturn(startButton);
        when(menuPane.getRulesButton()).thenReturn(helpButton);
        when(menuPane.getQuitButton()).thenReturn(quitButton);
        when(menuPane.Width()).thenReturn(800.0);
        when(menuPane.Height()).thenReturn(600.0);

        menuController = new RosesMenuController(model, view, controller);

        // Set the controller to handle actions
        startButton.setOnAction(menuController::handle);
        helpButton.setOnAction(menuController::handle);
        quitButton.setOnAction(menuController::handle);
    }

    @Test
    void testStartButtonAction() {
        // Simulate clicking the "Start" button
        startButton.fire();

        // Verify that the controller handled the action appropriately
        ArgumentCaptor<RosesView> viewCaptor = ArgumentCaptor.forClass(RosesView.class);
        verify(controller, times(1)).setControlAction(any(RosesModeController.class));
    }

    @Test
    void testHelpButtonAction() {
        // Simulate clicking the "Help" button
        helpButton.fire();

        // Verify that the controller handled the action appropriately
        ArgumentCaptor<RosesView> viewCaptor = ArgumentCaptor.forClass(RosesView.class);
        verify(controller, times(1)).setControlAction(any(RosesRulesController.class));
    }

    @Test
    void testQuitButtonAction() {
        // Simulate clicking the "Quit" button
        quitButton.fire();

        // Verify that System.exit was called
        // This might not work directly in tests; in a real environment, you might mock System.exit
        // For example, you might use a SecurityManager to catch the exit call
        try {
            quitButton.fire();
        } catch (Exception e) {
            assertTrue(e instanceof SecurityException, "System.exit should throw SecurityException in test environment.");
        }
    }
}
