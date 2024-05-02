package view;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.RosesPawn;

/**
 * The look of the Pawn is fixed, with a single characters representing the value of the pawn
 * and a black or red background.
 */
public class RosesPawnLook extends ElementLook {

    public RosesPawnLook(GameElement element) {
        super(element, 1, 1);
    }

    protected void render() {

        RosesPawn pawn = (RosesPawn)element;
        if (pawn.getColor() == RosesPawn.PAWN_BLUE) {
            shape[0][0] = ConsoleColor.WHITE + ConsoleColor.BLUE_BACKGROUND + pawn.getNumber() + ConsoleColor.RESET;
        }
        else {
            shape[0][0] = ConsoleColor.BLACK + ConsoleColor.RED_BACKGROUND + pawn.getNumber() + ConsoleColor.RESET;
        }
    }
}
