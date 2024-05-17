package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;

/**
 * A basic pawn element, with only 2 fixed parameters : number and color
 * There are no setters because the state of a Roses pawn is fixed.
 */
public class RosesPawn extends GameElement {

    private int number;
    private int color;

    private String caracter;
    public static int PAWN_BLUE = 0;
    public static int PAWN_RED = 1;

    public static int PAWN_YELLOW = 2;

    public RosesPawn(int color, GameStageModel gameStageModel) {
        super(gameStageModel);
        // registering element types defined especially for this game
        ElementTypes.register("pawn",50);
        type = ElementTypes.getType("pawn");
        this.color = color;
    }

    public RosesPawn(String caracter, int color, GameStageModel gameStageModel) {
        super(gameStageModel);
        // registering element types defined especially for this game
        ElementTypes.register("pawn",50);
        type = ElementTypes.getType("pawn");
        this.color = color;
        this.caracter = caracter;
    }

    public int getNumber() { return number; }

    public int getColor() {
        return color;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setCaracter(String caracter) {
        this.caracter = caracter;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getCaracter() {
        return this.caracter;
    }

}
