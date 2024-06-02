package view;

import boardifier.view.RootPane;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RosesModePane extends RootPane {
    private Button PvP;
    private Button PvC;
    private Button CvC;
    private Button back;

    public RosesModePane() {
        super();
    }

    @Override
    protected void createDefaultGroup() {
        Rectangle frame = new Rectangle(600, 600, Color.LIGHTGREY);
        Text title = new Text("King of roses");
        title.setFont(new Font(30));
        title.setX(200);
        title.setY(100);
        PvP = new Button("Player vs Player");
        PvP.setLayoutX(200);
        PvP.setLayoutY(200);
        PvC = new Button("Player vs Computer");
        PvC.setLayoutX(200);
        PvC.setLayoutY(250);
        CvC = new Button("Computer vs Computer");
        CvC.setLayoutX(200);
        CvC.setLayoutY(300);
        back = new Button("Back");
        back.setLayoutX(200);
        back.setLayoutY(350);
        group.getChildren().clear();
        group.getChildren().addAll(frame, title, PvP, PvC, CvC, back);
    }

    public Button getPvPButton() {
        return PvP;
    }

    public Button getPvCButton() {
        return PvC;
    }

    public Button getCvCButton() {
        return CvC;
    }

    public Button getBackButton() {
        return back;
    }

}
