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

    public void setValidCells(int number) {
        resetReachableCells(false);
        List<Point> valid = computeValidCells(number);
        if (valid != null) {
            for(Point p : valid) {
                reachableCells[p.y][p.x] = true;
            }
        }
        addChangeFaceEvent();
    }

    public void setValidCells(List<Point> valid) {
        Logger.debug("called", this);
        resetReachableCells(true);
        for (Point p : valid) {
            reachableCells[p.y][p.x] = true;
        }
    }

    public List<Point> computeValidCells(int number) {
        List<Point> lst = new ArrayList<>();
        RosesPawn p = null;
        // if the grid is empty, is it the first turn and thus, all cells are valid
        if (isEmpty()) {
            // i are rows
            for(int i=0;i<9;i++) {
                // j are cols
                for (int j = 0; j < 9; j++) {
                    // cols is in x direction and rows are in y direction, so create a point in (j,i)
                    lst.add(new Point(j,i));
                }
            }
            return lst;
        }
        // else, take each empty cell and check if it is valid
        for(int i=0;i<9;i++) {
            for(int j=0;j<9;j++) {
                if (isEmptyAt(i,j)) {
                    // check adjacence in row-1
                    if (i-1 >= 0) {
                        if (j-1>=0) {
                            p = (RosesPawn)getElement(i-1,j-1);

                            // check if same parity
                            if ((p != null) && ( p.getNumber()%2 == number%2)) {
                                lst.add(new Point(j,i));
                                continue; // go to the next point
                            }
                        }
                        p = (RosesPawn)getElement(i-1,j);
                        // check if different parity
                        if ((p != null) && ( p.getNumber()%2 != number%2)) {
                            lst.add(new Point(j,i));
                            continue; // go to the next point
                        }
                        if (j+1<=8) {
                            p = (RosesPawn)getElement(i-1,j+1);
                            // check if same parity
                            if ((p != null) && ( p.getNumber()%2 == number%2)) {
                                lst.add(new Point(j,i));
                                continue; // go to the next point
                            }
                        }
                    }
                    // check adjacence in row+1
                    if (i+1 <= 8) {
                        if (j-1>=0) {
                            p = (RosesPawn)getElement(i+1,j-1);
                            // check if same parity
                            if ((p != null) && ( p.getNumber()%2 == number%2)) {
                                lst.add(new Point(j,i));
                                continue; // go to the next point
                            }
                        }
                        p = (RosesPawn)getElement(i+1,j);
                        // check if different parity
                        if ((p != null) && ( p.getNumber()%2 != number%2)) {
                            lst.add(new Point(j,i));
                            continue; // go to the next point
                        }
                        if (j+1<=8) {
                            p = (RosesPawn)getElement(i+1,j+1);
                            // check if same parity
                            if ((p != null) && ( p.getNumber()%2 == number%2)) {
                                lst.add(new Point(j,i));
                                continue; // go to the next point
                            }
                        }
                    }
                    // check adjacence in row
                    if (j-1>=0) {
                        p = (RosesPawn)getElement(i,j-1);
                        // check if different parity
                        if ((p != null) && ( p.getNumber()%2 != number%2)) {
                            lst.add(new Point(j,i));
                            continue; // go to the next point
                        }
                    }
                    if (j+1<=8) {
                        p = (RosesPawn)getElement(i,j+1);
                        // check if different parity
                        if ((p != null) && ( p.getNumber()%2 != number%2)) {
                            lst.add(new Point(j,i));
                            continue; // go to the next point
                        }

                    }
                }
            }
        }
        return lst;
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

}
