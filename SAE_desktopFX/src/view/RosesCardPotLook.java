package view;

import boardifier.control.Logger;
import boardifier.model.ContainerElement;
import boardifier.view.GridLook;
import javafx.scene.paint.Color;
import model.RosesCardPot;

/**
 * Pick pot inherits from GridLook but overrides the renderBorders() method
 * so that a special look is given to borders copared to the default look defined
 * in GridLook. Moreover, cells have a fixed size, meaning that if an element is
 * too big to fit within a cell, it will overlap neighbors cells.
 * The default alignment is also changed and set to the middle of the cells.
 */
public class RosesCardPotLook extends GridLook {

    public RosesCardPotLook(int rowHeight, int colWidth, ContainerElement containerElement) {
        super(rowHeight, colWidth, containerElement, -1, 3, Color.BLACK);
        setVerticalAlignment(ALIGN_MIDDLE);
        setHorizontalAlignment(ALIGN_CENTER);
    }
}