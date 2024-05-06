package model;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;

public class RosesCardPot extends ContainerElement {
    public RosesCardPot(int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 4x1 grid, named "cardpot", and in x,y in space
        super("cardpot", x, y, 1, 1, gameStageModel);
    }
}
