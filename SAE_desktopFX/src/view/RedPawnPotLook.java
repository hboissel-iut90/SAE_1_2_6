package view;

import boardifier.model.ContainerElement;
import boardifier.view.TableLook;
import javafx.scene.paint.Color;

/**
 * create a subclass of TableLook, to demonstrate how the table structure changes
 * when the content changes.
 */
public class RedPawnPotLook extends TableLook {

    public RedPawnPotLook(ContainerElement element) {
        super(element, -1, 2, Color.BLACK);
        setPadding(10);
    }

}
