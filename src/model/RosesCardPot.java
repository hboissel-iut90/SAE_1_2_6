package model;

import boardifier.model.ContainerElement;
import boardifier.model.GameStageModel;

public class RosesCardPot extends ContainerElement {
    public int width;
    public int height;

    public RosesCardPot(int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 4x1 grid, named "cardpot", and in x,y in space
        super("cardpot", x, y, 1, 5, gameStageModel);
    }


    public RosesCardPot(int x, int y, int width, int height, GameStageModel gameStageModel) {
        // call the super-constructor to create a grid with specified width and height,
        // named "cardpot", and at position x,y in space
        super("moovcardpot", x, y, width, height, gameStageModel);
        this.width = width;
        this.height = height;
    }
}
