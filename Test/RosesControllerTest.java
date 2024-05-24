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
    public void testStageLoop(){

    }

    @Test
    public void testPlayTurn(){}

    @Test
    public void testEndOfTurn(){}

    @Test
    public void testAnalyseAndPlay(){}

    @Test
    public void testCheckIfPlayerPlay(){}
}