package view;

import boardifier.view.RootPane;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RosesDiffPane extends RootPane {
    private Button easy;
    private Button hard;
    private Button EvE;
    private Button EvH;
    private Button back;
    private int mode;

    public RosesDiffPane(int mode) {
        super();
        this.mode = mode;
    }

    public int getMode() {
        return mode;
    }

    @Override
    protected void createDefaultGroup(){
        Rectangle frame = new Rectangle (600, 600, Color.LIGHTGREY);
        Text title = new Text("Choose difficulty");
        title.setFont(new Font(30));
        title.setX(200);
        title.setY(100);
        if (this.getMode() == 1) {
            easy = new Button("Easy");
            easy.setLayoutX(200);
            easy.setLayoutY(200);
            hard = new Button("Hard");
            hard.setLayoutX(200);
            hard.setLayoutY(250);
            back = new Button("Back");
            back.setLayoutX(200);
            back.setLayoutY(300);
            group.getChildren().clear();
            group.getChildren().addAll(frame, title, easy, hard, back);
        } else {
            EvE = new Button("Easy vs Easy");
            EvE.setLayoutX(200);
            EvE.setLayoutY(200);
            EvH = new Button("Easy vs Hard");
            EvH.setLayoutX(200);
            EvH.setLayoutY(250);
            back = new Button("Back");
            back.setLayoutX(200);
            back.setLayoutY(300);
            group.getChildren().clear();
            group.getChildren().addAll(frame, title, EvE, EvH, back);
        }
    }

    public Button getEasyButton() {
        return easy;
    }

    public Button getHardButton() {
        return hard;
    }

    public Button getEvEButton() {
        return EvE;
    }

    public Button getEvHButton() {
        return EvH;
    }

    public Button getBackButton() {
        return back;
    }
}
