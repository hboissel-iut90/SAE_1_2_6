import boardifier.model.GameStageModel;
import model.RosesCard;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

public class TestSurRosesCard {

    @Test
    public void testGetValue() {
        RosesCard card = new RosesCard(1, "N", Mockito.mock(GameStageModel.class));
        RosesCard card2 = new RosesCard(2, "N", Mockito.mock(GameStageModel.class));
        RosesCard card3 = new RosesCard(3, "N", Mockito.mock(GameStageModel.class));
        RosesCard card4 = new RosesCard(0, Mockito.mock(GameStageModel.class));

        assertEquals(1, card.getValue());
        assertEquals(2, card2.getValue());
        assertEquals(3, card3.getValue());
        assertEquals(0, card4.getValue());
    }

    @Test
    public void testGetDirection() {
        RosesCard card = new RosesCard(1, "N", Mockito.mock(GameStageModel.class));
        RosesCard card2 = new RosesCard(2, "N-E", Mockito.mock(GameStageModel.class));
        RosesCard card3 = new RosesCard(3, "E", Mockito.mock(GameStageModel.class));
        RosesCard card4 = new RosesCard(1, "S-E", Mockito.mock(GameStageModel.class));
        RosesCard card5 = new RosesCard(2, "S", Mockito.mock(GameStageModel.class));
        RosesCard card6 = new RosesCard(3, "S-W", Mockito.mock(GameStageModel.class));
        RosesCard card7 = new RosesCard(1, "W", Mockito.mock(GameStageModel.class));
        RosesCard card8 = new RosesCard(2, "N-W", Mockito.mock(GameStageModel.class));
        RosesCard card9 = new RosesCard(0, Mockito.mock(GameStageModel.class));

        assertEquals("N", card.getDirection());
        assertEquals("N-E", card2.getDirection());
        assertEquals("E", card3.getDirection());
        assertEquals("S-E", card4.getDirection());
        assertEquals("S", card5.getDirection());
        assertEquals("S-W", card6.getDirection());
        assertEquals("W", card7.getDirection());
        assertEquals("N-W", card8.getDirection());
        assertEquals("", card9.getDirection());
    }

    @Test
    public void testGetCardType() {
        RosesCard cardH1 = new RosesCard(0, Mockito.mock(GameStageModel.class));
        RosesCard cardH2 = new RosesCard(1, Mockito.mock(GameStageModel.class));
        RosesCard cardM = new RosesCard(1, "N", Mockito.mock(GameStageModel.class));

        assertEquals("HEROS", cardH1.getCardType());
        assertEquals("HEROS", cardH2.getCardType());
        assertEquals("MOUVEMENT", cardM.getCardType());
    }


    @Test
    public void testGetColor() {
        RosesCard card1 = new RosesCard(0, Mockito.mock(GameStageModel.class));
        RosesCard card2 = new RosesCard(1, Mockito.mock(GameStageModel.class));
        RosesCard card3 = new RosesCard(1, "N", Mockito.mock(GameStageModel.class));

        assertTrue(card1.getColor() == card1.CARD_BLUE);
        assertTrue(card2.getColor() == card2.CARD_RED);
        assertTrue(card3.getColor() == card3.CARD_WHITE);
    }

    @Test
    @AfterAll
    public void testFlip() {
        RosesCard card = new RosesCard(1, Mockito.mock(GameStageModel.class));
        RosesCard card2 = new RosesCard(1, Mockito.mock(GameStageModel.class));

        card.flip(); // Utilisation de la carte héro

        assertTrue(card.isFlipped() == true); // La carte est utilisée
        assertTrue(card2.isFlipped() == false); // La carte n'est pas encore utilisée

        card2.flip(); // Utilisation de la 2 ème carte hero

        assertTrue(card2.isFlipped() == true); // La carte est utilisée
    }

    @Test
    public void testIsFlipped() {
        RosesCard card = new RosesCard(0, Mockito.mock(GameStageModel.class));

        assertTrue(card.isFlipped() == false);
        assertFalse(card.isFlipped() == true);
    }

}