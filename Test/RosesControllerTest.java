import boardifier.model.Model;
import boardifier.model.Player;
import boardifier.model.TextElement;
import boardifier.view.View;
import control.RosesControllerTool;
import model.RosesBoard;
import model.RosesCard;
import model.RosesStageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RosesControllerToolTest {

    Model model;
    View view;
    RosesControllerTool rosesControllerTool;
    RosesStageModel rosesStageModel;

    @BeforeEach
    void setup() {
        // Mocking the dependencies
        model = Mockito.mock(Model.class);
        view = Mockito.mock(View.class);
        rosesStageModel = Mockito.mock(RosesStageModel.class);
        when(model.getGameStage()).thenReturn(rosesStageModel);

        // Creating the instance of the class under test
        rosesControllerTool = new RosesControllerTool(model, view, "E");
    }

    @Test
    void testStageLoop() throws Exception {
        // Mocking the RosesControllerTool
        RosesControllerTool rosesControllerTool = Mockito.mock(RosesControllerTool.class);

        // Stubbing method calls
        when(model.isEndStage()).thenReturn(false, true);
        doNothing().when(rosesControllerTool).playTurnTool();
        doNothing().when(rosesControllerTool).endOfTurnTool();
        doNothing().when(rosesControllerTool).update();
        doNothing().when(rosesControllerTool).endGame();

        // Invoking the method under test
        rosesControllerTool.stageLoop();

        // Verifying method invocations
        verify(rosesControllerTool, times(1)).playTurnTool();
        verify(rosesControllerTool, times(1)).endOfTurnTool();
        verify(rosesControllerTool, times(2)).update();
        verify(rosesControllerTool, times(1)).endGame();
    }

    @Test
    void testPlayTurn() throws Exception {
        // Mocking dependencies
        Player player = Mockito.mock(Player.class);
        when(model.getCurrentPlayer()).thenReturn(player);
        when(player.getType()).thenReturn(Player.HUMAN);

        // Mocking getPlayer1MovementCards() to return a non-null array
        RosesCard[] player1MovementCards = new RosesCard[1]; // Or any desired size
        when(rosesStageModel.getPlayer1MovementCards()).thenReturn(player1MovementCards);

        // Mocking the RosesBoard instance
        RosesBoard rosesBoard = Mockito.mock(RosesBoard.class);
        when(rosesStageModel.getBoard()).thenReturn(rosesBoard);
        // Stubbing the necessary method calls on RosesBoard if needed

        // Mocking the console input
        ByteArrayInputStream inputStream = new ByteArrayInputStream("stop\n".getBytes());
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        // Setting the consoleIn field in the RosesControllerTool
        rosesControllerTool.consoleIn = br;

        // Invoking the method under test
        rosesControllerTool.playTurnTool();

        // Verifying method invocations
        verify(rosesStageModel, times(1)).computePartyResult();
    }

    @Test
    void testEndOfTurn() {
        Player player = Mockito.mock(Player.class);
        when(model.getCurrentPlayer()).thenReturn(player);

        // Mocking the TextElement for player name
        TextElement playerName = Mockito.mock(TextElement.class);
        when(rosesStageModel.getPlayerName()).thenReturn(playerName);

        when(model.getGameStage()).thenReturn(rosesStageModel);

        // Invoking the method under test
        rosesControllerTool.endOfTurnTool();

        // Verifying method invocations
        verify(model, times(1)).setNextPlayer();
        verify(playerName, times(1)).setText(rosesStageModel.getName()); // Verify setText on playerName
        verify(rosesStageModel, times(1)).update();
    }

    @Test
    void testAnalyseAndPlay() throws Exception {
        // Mocking dependencies
        RosesCard[] player1MovementCards = new RosesCard[1];
        player1MovementCards[0] = Mockito.mock(RosesCard.class); // Mocking the RosesCard instance
        when(rosesStageModel.getPlayer1MovementCards()).thenReturn(player1MovementCards);

        // Mocking the necessary dependencies for the method under test
        RosesBoard rosesBoard = Mockito.mock(RosesBoard.class);
        when(rosesStageModel.getBoard()).thenReturn(rosesBoard);
        // Stubbing the necessary method calls on RosesBoard if needed

        // Stubbing the return value of computeValidCells() on RosesBoard
        when(rosesBoard.computeValidCells(anyString(), anyInt())).thenReturn(null);

        // Stubbing the return value of getPickCards() to return a non-null array
        RosesCard[] pickCards = new RosesCard[1];
        when(rosesStageModel.getPickCards()).thenReturn(pickCards);

        // Invoking the method under test
        boolean result = rosesControllerTool.analyseAndPlayTool("P");

        // Verifying the result
        assertFalse(result);
    }

    @Test
    void testCheckIfPlayerPlay() {
        // Mocking the RosesCard array
        RosesCard[] player1MovementCards = new RosesCard[1];
        when(rosesStageModel.getPlayer1MovementCards()).thenReturn(player1MovementCards);

        // Mocking the RosesBoard object
        RosesBoard rosesBoard = Mockito.mock(RosesBoard.class);
        when(rosesStageModel.getBoard()).thenReturn(rosesBoard);
        when(rosesBoard.getNbCols()).thenReturn(9);
        when(rosesBoard.getNbRows()).thenReturn(9);

        // Mocking getElement() to return null
        when(rosesBoard.getElement(anyInt(), anyInt(), anyInt())).thenReturn(null);

        // Invoking the method under test
        rosesControllerTool.checkIfPlayerPlayTool();

        // Verify that setChecked() is called
        verify(rosesStageModel, times(1)).setChecked(anyBoolean());
    }
}
