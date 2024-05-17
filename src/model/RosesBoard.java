package model;

import boardifier.control.Logger;
import boardifier.model.GameStageModel;
import boardifier.model.ContainerElement;

import java.util.ArrayList;
import java.util.List;
import java.awt.*;

public class RosesBoard extends ContainerElement {
    public RosesBoard(int x, int y, GameStageModel gameStageModel) {
        super("RoseBoard", x, y, 9, 9, gameStageModel);
    }

    public void setValidCells(String string, int id) {
        Logger.debug("called", this);
        resetReachableCells(true);
        List<Point> valid = computeValidCells(string, id); // Appel de la méthode avec le nombre en paramètre
        if (valid != null) {
            for (Point p : valid) {
                reachableCells[p.y][p.x] = true;
            }
        }
    }

    public void setValidCells(List<Point> valid) {
        Logger.debug("called", this);
        resetReachableCells(true);
        for (Point p : valid) {
            reachableCells[p.y][p.x] = true;
        }
    }

    public List<Point> computeValidCells(String string, int id) { // Ajout du paramètre ici
        List<Point> lst = new ArrayList<>();
        // if the grid is empty, all cells are valid
        if (isEmpty() && string.equals("H")) {
            return lst;
        }
        if (string.equals("H")) {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (isElementAt(j,i)){
                        if (id == 0) {
                            RosesPawn p = (RosesPawn)getElement(j,i);
                            if (p.getColor() == 1) {
                                lst.add(new Point(j, i));
                            }
                        }
                        if (id == 1) {
                            RosesPawn p = (RosesPawn)getElement(j,i);
                            if (p.getColor() == 0) {
                                lst.add(new Point(j, i));
                            }
                        }
                    }
                }
            }
        }
        // else, take each empty cell and add it to the valid list
        if (string.equals("M")) {
            if (isEmpty()) {
                for (int i = 0; i < 9; i++) {
                    for (int j = 0; j < 9; j++) {
                        lst.add(new Point(j, i));
                    }
                }
                return lst;
            }
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (isEmptyAt(i, j)) {
                        lst.add(new Point(j, i));
                    }
                }
            }
            return lst;
        }
        return lst;
    }
<<<<<<< Updated upstream
}

=======
}
>>>>>>> Stashed changes
