package view;

import boardifier.view.RootPane;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;

public class RosesMenuPane extends RootPane {
    private Button start;
    private Button help;

    private final double width;
    private final double height;

    private Button quit;

    public RosesMenuPane(double width, double height) {
        super();
        this.width = width;
        this.height = height;
        resetToDefault();
    }

    public double Width() {
        return width;
    }

    public double Height() {
        return height;
    }
    @Override
    protected void createDefaultGroup() {
        double size = width*0.05;
        this.setPrefSize(width, height);
        Image image = new Image("file:src/menu_assets/velours.jpg");
        BackgroundImage backgroundImg = new BackgroundImage(image, null, null, null, null);

        this.setBackground(new Background(backgroundImg));
        Text title = new Text("Welcome To King Of Roses");
        start = new Button("Start Game");
        help = new Button("How to play");
        quit = new Button("Quit");
        try{
            File fontFile = new File("src/menu_assets/RoyalKing-Free.ttf");
            String absolutePath = fontFile.getAbsolutePath();
            title.setFont(Font.loadFont(new FileInputStream(absolutePath), size));
            start.setFont(Font.loadFont(new FileInputStream(absolutePath), size/2));
            help.setFont(Font.loadFont(new FileInputStream(absolutePath), size/2));
            quit.setFont(Font.loadFont(new FileInputStream(absolutePath), size/2));
        } catch (Exception e) {
            title.setFont(new Font(size));
            start.setFont(new Font(size/2));
            help.setFont(new Font(size/2));
            quit.setFont(new Font(size/2));
        }
        title.setX(width/5);
        title.setY(height/4);
        title.setFill(Color.WHITE);
        start.setPrefSize(width/3, height/10);
        start.setLayoutX(width/3);
        start.setLayoutY(height/2.5);
        help.setPrefSize(width/3, height/10);
        help.setLayoutX(width/3);
        help.setLayoutY(height/1.75);
        quit.setPrefSize(width/3, height/10);
        quit.setLayoutX(width/3);
        quit.setLayoutY(height/1.35);
        group.getChildren().clear();
        group.getChildren().addAll( title, start, help, quit);
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
