import boardifier.model.Model;
import boardifier.view.View;
import control.RosesController;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RosesControllerTest {
    Model model = Mockito.mock(Model.class);
    View view = Mockito.mock(View.class);
    RosesController rosesController = new RosesController(model, view);

    @Test
    public void testStageLoop(String cardType, int row, int col){   }

    @Test
    public void testPlayTurn(String cardType, int row, int col){}

    @Test
    public void testEndOfTurn(String cardType, int row, int col){}

    @Test
    public void testAnalyseAndPlay(String cardType, int row, int col){}

    @Test
    public void testCheckIfPlayerPlay(String cardType, int row, int col){}
}