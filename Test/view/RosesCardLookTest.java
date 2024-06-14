package view;

import static model.RosesCard.CARD_BLUE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import boardifier.model.GameElement;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import model.RosesCard;
import model.RosesStageModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RosesCardLookTest {

    private RosesCard rosesCardMock;
    private RosesStageModel stageModelMock;
    private RosesCardLook rosesCardLook;

    @BeforeEach
    void setUp() {
        rosesCardMock = mock(RosesCard.class);
        stageModelMock = mock(RosesStageModel.class);

        when(rosesCardMock.getX()).thenReturn(50.0);
        when(rosesCardMock.getY()).thenReturn(50.0);
    }

    @Test
    void testConstructorAndRenderMovementCard() {
        when(rosesCardMock.getCardType()).thenReturn("MOUVEMENT");
        when(rosesCardMock.getDirection()).thenReturn("N");
        when(rosesCardMock.getValue()).thenReturn(1);
        when(rosesCardMock.isFlipped()).thenReturn(false);
        when(rosesCardMock.getContainer()).thenReturn(stageModelMock.getMoovBluePot());

        rosesCardLook = new RosesCardLook(100, 150, rosesCardMock, stageModelMock);

        assertFalse(rosesCardLook.getGroup().getChildren().isEmpty(), "Le groupe de noeuds ne doit pas être vide.");

        // Assurez-vous que le rendu contient une image et une bordure.
        assertEquals(2, rosesCardLook.getGroup().getChildren().size(), "Le groupe doit contenir l'image et la bordure.");
    }

    @Test
    void testConstructorAndRenderHeroCard() {
        when(rosesCardMock.getCardType()).thenReturn("HERO");
        when(rosesCardMock.getColor()).thenReturn(CARD_BLUE);

        rosesCardLook = new RosesCardLook(100, 150, rosesCardMock, stageModelMock);

        assertFalse(rosesCardLook.getGroup().getChildren().isEmpty(), "Le groupe de noeuds ne doit pas être vide.");

        // Assurez-vous que le rendu contient un rectangle et du texte.
        assertEquals(3, rosesCardLook.getGroup().getChildren().size(), "Le groupe doit contenir le rectangle, le texte, et la bordure.");
    }

    @Test
    void testOnSelectionChange() {
        when(rosesCardMock.isSelected()).thenReturn(true);
        rosesCardLook = new RosesCardLook(100, 150, rosesCardMock, stageModelMock);

        rosesCardLook.onSelectionChange();
        Rectangle border = (Rectangle) rosesCardLook.getGroup().getChildren().get(1);
        assertEquals(4, border.getStrokeWidth(), "La largeur du trait doit être 4 lorsqu'il est sélectionné.");

        when(rosesCardMock.isSelected()).thenReturn(false);
        rosesCardLook.onSelectionChange();
        assertEquals(2, border.getStrokeWidth(), "La largeur du trait doit être 2 lorsqu'il n'est pas sélectionné.");
    }
}

