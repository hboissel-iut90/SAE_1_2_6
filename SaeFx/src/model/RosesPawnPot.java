package model;

import boardifier.model.GameStageModel;
import boardifier.model.ContainerElement;

public class RosesPawnPot extends ContainerElement {
    public RosesPawnPot(int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 4x1 grid, named "pawnpot", and in x,y in space
        super("pawnpot", x, y, 1, 1, gameStageModel);
    }
}
