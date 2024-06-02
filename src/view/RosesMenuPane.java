package view;

import boardifier.view.RootPane;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class RosesMenuPane extends RootPane {
    private Button start;
    private Button help;

    private Button quit;

    public RosesMenuPane() {
        super();
    }

    @Override
    protected void createDefaultGroup() {
        Rectangle frame = new Rectangle (600, 600, Color.LIGHTGREY);
        Text title = new Text("Choose mode");
        title.setFont(new Font(30));
        title.setX(200);
        title.setY(100);
        start = new Button("Start Game");
        start.setLayoutX(200);
        start.setLayoutY(200);
        help = new Button("How to play");
        help.setLayoutX(200);
        help.setLayoutY(250);
        quit = new Button("Quit");
        quit.setLayoutX(200);
        quit.setLayoutY(300);
        group.getChildren().clear();
        group.getChildren().addAll(frame, title, start, help, quit);
    }

    public Button getStartButton() {
        return start;
    }

    public Button getHelpButton() {
        return help;
    }

    public Button getQuitButton() {
        return quit;
    }
}
