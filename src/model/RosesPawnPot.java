package model;

import boardifier.model.GameStageModel;
import boardifier.model.ContainerElement;

/**
 * King of roses pot for pawns represent the element where pawns are stored at the beginning of the party.
 * Thus, a simple ContainerElement with 4 rows and 1 column is needed.
 */
public class RosesPawnPot extends ContainerElement {
    public RosesPawnPot(int x, int y, GameStageModel gameStageModel) {
        // call the super-constructor to create a 4x1 grid, named "pawnpot", and in x,y in space
        super("pawnpot", x, y, 3, 2, gameStageModel);
    }
}
