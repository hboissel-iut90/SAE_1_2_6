package view;

import boardifier.view.RootPane;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.io.FileInputStream;

public class RosesModePane extends RootPane {
    private final double width;
    private final double height;

    private Button PvP;
    private Button PvC;
    private Button CvC;
    private Button back;

    public RosesModePane(double width, double height) {
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
        BackgroundSize backgroundSize = new BackgroundSize(width, height, true, true, true, true);

        BackgroundImage backgroundImg = new BackgroundImage(image, null, null, null, backgroundSize);
        this.setBackground(new Background(backgroundImg));
        Text title = new Text("Choose mode");
        PvP = new Button("Player vs Player");
        PvC = new Button("Player vs Computer");
        CvC = new Button("Computer vs Computer");
        back = new Button("Back");
        try{
            File fontFile = new File("src/menu_assets/RoyalKing-Free.ttf");
            String absolutePath = fontFile.getAbsolutePath();
            title.setFont(Font.loadFont(new FileInputStream(absolutePath), size));
            PvP.setFont(Font.loadFont(new FileInputStream(absolutePath), size/2));
            PvC.setFont(Font.loadFont(new FileInputStream(absolutePath), size/2));
            CvC.setFont(Font.loadFont(new FileInputStream(absolutePath), size/2));
            back.setFont(Font.loadFont(new FileInputStream(absolutePath), size/2));
        } catch (Exception e) {
            title.setFont(new Font(size));
            PvP.setFont(new Font(size/2));
            PvC.setFont(new Font(size/2));
            CvC.setFont(new Font(size/2));
            back.setFont(new Font(size/2));
        }
        title.setFill(Color.WHITE);
        title.setX(width/2.9);
        title.setY(height/5);

        PvP.setPrefSize(width/3, height/10);
        PvP.setLayoutX(width/3);
        PvP.setLayoutY(height/3);

        PvC.setPrefSize(width/3, height/10);
        PvC.setLayoutX(width/3);
        PvC.setLayoutY(height/2.21);

        CvC.setPrefSize(width/3, height/10);
        CvC.setLayoutX(width/3);
        CvC.setLayoutY(height/1.75);

        back.setPrefSize(width/3, height/10);
        back.setLayoutX(width/3);
        back.setLayoutY(height/1.2);
        group.getChildren().clear();
        group.getChildren().addAll(title, PvP, PvC, CvC, back);
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
