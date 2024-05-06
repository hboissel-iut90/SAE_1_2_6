package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;

/**
 * A basic pawn element, with only 2 fixed parameters : number and color
 * There are no setters because the state of a Hole pawn is fixed.
 */
public class RosesPawn extends GameElement {

    private int number;
    private int color;
    public static int PAWN_BLUE = 0;
    public static int PAWN_RED = 1;

    public static int PAWN_YELLOW = 2;

    public RosesPawn(int number, int color, GameStageModel gameStageModel) {
        super(gameStageModel);
        // registering element types defined especially for this game
        ElementTypes.register("pawn",50);
        type = ElementTypes.getType("pawn");
        this.number = number;
        this.color = color;
    }

    public RosesPawn(int color, GameStageModel gameStageModel) {
        super(gameStageModel);
        // registering element types defined especially for this game
        ElementTypes.register("pawn",50);
        type = ElementTypes.getType("pawn");
        this.color = color;
    }

    public int getNumber() {
        return number;
    }

    public int getColor() {
        return color;
    }

    public void setNumber(int number) {
        this.number = number;
    }

}
