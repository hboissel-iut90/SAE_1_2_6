package model;

import boardifier.control.Logger;
import boardifier.model.GameStageModel;
import boardifier.model.ContainerElement;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class RosesBoard extends ContainerElement {
    public RosesBoard(int x, int y, GameStageModel gameStageModel) {
        super("RoseBoard", x, y, 9 , 9, gameStageModel);
    }

    public void setValidCells(int number) {
        Logger.debug("called",this);
        resetReachableCells(false);
        List<Point> valid = computeValidCells(number); // Appel de la méthode avec le nombre en paramètre
        if (valid != null) {
            for(Point p : valid) {
                reachableCells[p.y][p.x] = true;
            }
        }
    }

    public List<Point> computeValidCells(int number) { // Ajout du paramètre ici
        List<Point> lst = new ArrayList<>();
        // if the grid is empty, all cells are valid
        if (isEmpty()) {
            for(int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    lst.add(new Point(j,i));
                }
            }
            return lst;
        }
        // else, take each empty cell and add it to the valid list
        for(int i = 0; i < 9; i++) {
            for(int j = 0; j < 9; j++) {
                if (isEmptyAt(i,j)) {
                    lst.add(new Point(j,i));
                }
            }
        }
        return lst;
    }
}
