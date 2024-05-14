import boardifier.model.GameStageModel;
import model.RosesCard;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestSurRosesCard {

    @Mock
    GameStageModel gameStageModel;

    @Test
    public void testGetValue() {
        RosesCard card = new RosesCard(5, "north", gameStageModel);
        assertEquals(5, card.getValue());
    }

    @Test
    public void testGetDirection() {
        RosesCard card = new RosesCard(5, "north", gameStageModel);
        assertEquals("north", card.getDirection());
    }

    @Test
    public void testGetCardTypeMovment() {
        RosesCard card = new RosesCard(5, "north", gameStageModel);
        assertEquals("MOUVEMENT", card.getCardType());
    }


    @Test
    public void testGetColor() {
        RosesCard card = new RosesCard(5, "north", gameStageModel);
        assertEquals(RosesCard.CARD_WHITE, card.getColor());
    }

    @Test
    public void testFlip() {
        RosesCard card = new RosesCard(5, "north", gameStageModel);
        card.flip();
        assertFalse(card.isFlipped());
    }

    @Test
    public void testIsFlipped() {
        RosesCard card = new RosesCard(5, "north", gameStageModel);
        assertTrue(card.isFlipped());
    }

    @Test
    public void testGetCardTypeHeros() {
        RosesCard card = new RosesCard( 0, gameStageModel);
        assertEquals("HEROS", card.getCardType());
    }

    @Test
    public void testGetColorHero() {
        RosesCard card = new RosesCard(0, gameStageModel);
        assertEquals(RosesCard.CARD_BLUE, card.getColor());
    }

}