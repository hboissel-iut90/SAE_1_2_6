package model;

import boardifier.control.Logger;
import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;
import boardifier.model.animation.Animation;
import boardifier.model.animation.AnimationStep;

public class RosesPawn extends GameElement {

    private int number;
    private int color;
    private String text;
    public static int PAWN_BLUE = 0;
    public static int PAWN_RED = 1;
    public static int PAWN_YELLOW = 2;

    public RosesPawn(String text, int color, GameStageModel gameStageModel) {
        super(gameStageModel);
        // registering element types defined especially for this game
        ElementTypes.register("pawn",50);
        type = ElementTypes.getType("pawn");
        this.text = text;
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
        return this.number;
    }

    public String getText() {
        return this.text;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
        update();
    }

    public void update() {
        // if must be animated, move the pawn
        if (animation != null) {
            AnimationStep step = animation.next();
            if (step == null) {
                animation = null;
            }
            else if (step == Animation.NOPStep) {
                Logger.debug("nothing to do", this);
            }
            else {
                Logger.debug("move animation", this);
                setLocation(step.getInt(0), step.getInt(1));
            }
        }
    }

}
