package view;

import boardifier.model.Model;
import boardifier.view.RootPane;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.File;
import java.util.List;

public class RosesDiffPane extends RootPane {
    private final double width;
    private final double height;
    private Button easy;
    private Button hard;
    private Button EvE;
    private Button EvH;

    private Button HvH;
    private Button back;
    public int mode;
    private List<String> players_names;

    public RosesDiffPane(int mode, double width, double height) {
        super();
        this.mode = mode;
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

    public List<String> getPlayers_names() {
        return players_names;
    }

    @Override
    protected void createDefaultGroup(){
        int mode = this.mode;
        double size = width*0.05;
        this.setPrefSize(width, height);
        Image image = new Image("file:src/menu_assets/velours.jpg");
        BackgroundImage backgroundImage = new BackgroundImage(image, null, null, null, null);

        this.setBackground(new Background(backgroundImage));
        Text title = new Text("Choose difficulty");
        File fontFile = new File("src/menu_assets/RoyalKing-Free.ttf");
        String absolutePath = fontFile.getAbsolutePath();
        try{
            title.setFont(Font.loadFont(new java.io.FileInputStream(absolutePath), size));
        } catch (Exception e) {
            title.setFont(new Font(size));
        }
        title.setX(width/3.5);
        title.setY(height/4);
        title.setFill(Color.WHITE);
        if (mode == 1) {
            easy = new Button("Easy");
            hard = new Button("Hard");
            back = new Button("Back");
            try{
                easy.setFont(Font.loadFont(new java.io.FileInputStream(absolutePath), size/2));
                hard.setFont(Font.loadFont(new java.io.FileInputStream(absolutePath), size/2));
                back.setFont(Font.loadFont(new java.io.FileInputStream(absolutePath), size/2));
            } catch (Exception e) {
                easy.setFont(new Font(size/2));
                hard.setFont(new Font(size/2));
                back.setFont(new Font(size/2));
            }

            easy.setPrefSize(width/3, height/10);
            easy.setLayoutX(width/3);
            easy.setLayoutY(height/2.5);

            hard.setPrefSize(width/3, height/10);
            hard.setLayoutX(width/3);
            hard.setLayoutY(height/1.9);

            back.setPrefSize(width/3, height/10);
            back.setLayoutX(width/3);
            back.setLayoutY(height/1.35);

            group.getChildren().clear();
            group.getChildren().addAll(title, easy, hard, back);
        } else {
            EvE = new Button("Easy vs Easy");
            EvH = new Button("Easy vs Hard");
            HvH = new Button("Hard vs Hard");
            back = new Button("Back");
            try{
                EvE.setFont(Font.loadFont(new java.io.FileInputStream(absolutePath), size/2));
                EvH.setFont(Font.loadFont(new java.io.FileInputStream(absolutePath), size/2));
                HvH.setFont(Font.loadFont(new java.io.FileInputStream(absolutePath), size/2));
                back.setFont(Font.loadFont(new java.io.FileInputStream(absolutePath), size/2));
            } catch (Exception e) {
                EvE.setFont(new Font(size/2));
                EvH.setFont(new Font(size/2));
                HvH.setFont(new Font(size/2));
                back.setFont(new Font(size/2));
            }

            EvE.setPrefSize(width/3, height/10);
            EvE.setLayoutX(width/3);
            EvE.setLayoutY(height/2.5);

            EvH.setPrefSize(width/3, height/10);
            EvH.setLayoutX(width/3);
            EvH.setLayoutY(height/1.9);

            HvH.setPrefSize(width/3, height/10);
            HvH.setLayoutX(width/3);
            HvH.setLayoutY(height/1.52);

            back.setPrefSize(width/3, height/10);
            back.setLayoutX(width/3);
            back.setLayoutY(height/1.2);

            group.getChildren().clear();
            group.getChildren().addAll(title, EvE, EvH, HvH, back);
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

    public Button getHvHButton() {
        return HvH;
    }

    public Button getBackButton() {
        return back;
    }
}
