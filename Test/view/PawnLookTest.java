package view;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import boardifier.model.GameElement;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import model.RosesPawn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PawnLookTest {

    private RosesPawn rosesPawnMock;
    private PawnLook pawnLook;

    @BeforeEach
    void setUp() {
        rosesPawnMock = mock(RosesPawn.class);
        when(rosesPawnMock.getText()).thenReturn("Test");
        when(rosesPawnMock.getNumber()).thenReturn(1);
        when(rosesPawnMock.getColor()).thenReturn(RosesPawn.PAWN_BLUE);

        pawnLook = new PawnLook(10, rosesPawnMock);
    }

    @Test
    void testConstructorAndRender() {
        // Vérifiez que le cercle a la bonne couleur
        Circle circle = (Circle) pawnLook.getShapes().get(0);
        assertEquals(Color.BLUE, circle.getFill(), "Le cercle doit être bleu.");

        // Vérifiez que le texte est correctement rendu
        Text crown = (Text) pawnLook.getShapes().get(1);
        assertEquals("C", crown.getText(), "Le texte de la couronne doit être 'C'.");

        Text numberText = (Text) pawnLook.getShapes().get(2);
        assertEquals("1", numberText.getText(), "Le texte du numéro doit être '1'.");
        assertEquals(Color.WHITE, numberText.getFill(), "La couleur du texte doit être blanche.");
    }

    @Test
    void testOnSelectionChange() {
        // Simulez que le pion est sélectionné
        when(rosesPawnMock.isSelected()).thenReturn(true);
        pawnLook.onSelectionChange();
        Circle circle = (Circle) pawnLook.getShapes().get(0);
        assertEquals(4, circle.getStrokeWidth(), "La largeur du trait doit être 4 lorsqu'il est sélectionné.");

        // Simulez que le pion n'est pas sélectionné
        when(rosesPawnMock.isSelected()).thenReturn(false);
        pawnLook.onSelectionChange();
        assertEquals(2, circle.getStrokeWidth(), "La largeur du trait doit être 2 lorsqu'il n'est pas sélectionné.");
    }
}

