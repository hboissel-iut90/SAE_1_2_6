package view;

import boardifier.view.RootPane;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import java.io.File;
import java.io.FileInputStream;

public class RosesRulesPane extends RootPane {
    private final double width;
    private final double height;
    private Button back;
    public RosesRulesPane(double width, double height) {
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
        ScrollPane rulesMainContainer = new ScrollPane();
        VBox rulesContainer = new VBox();
        Text title1 = new Text("I/ Game Actions\n");
        Text content1 = new Text("There are 3 possible actions :\n"
                + "\t - Draw a movement card\n"
                + "\t - Play a movement card alone\n"
                + "\t - Play a movement card along with a hero card\n");

        Text title2 = new Text("1.1/ Draw a movement card\n");
        Text content2 = new Text("This action is only possible if the player has fewer than 5 movement cards in front of them.\n");

        Text title3 = new Text("1.2/ Play a movement card alone.\n");
        Text content3 = new Text("The king will be moved in the direction indicated by the card with the number shown.\n \n"
                + "A card cannot be played :\n"
                + "\t - In a direction other than that indicated by the card\n"
                + "\t - With fewer spaces than the number indicated on the card\n"
                + "\t - If the movement takes the king off the board\n"
                + "\t - If the movement places the king on a piece (either the current player's or the opponent's)\n"
                + "Note that the king can \"jump\" over pieces during its movement.\n \n"
                + "If the card chosen by the player can be played, then :\n"
                + "\t - It will be placed in a discard pile next to the board\n"
                + "\t - A piece is placed on the destination square\n"
                + "\t - The king piece will be on the piece that has just been placed\n");

        Text title4 = new Text("1.3/ Play a movement card along with a hero card\n");
        Text content4 = new Text("This action allows the player to land on the opponent's piece to \"steal\" it.\n \n"
                + "Such a pair cannot be played :\n"
                + "\t - In a direction other than that indicated by the movement card\n"
                + "\t - With fewer spaces than the number indicated on the movement card\n"
                + "\t - If the movement does not place the king on an opponent's piece\n"
                + "Note that the king can \"jump\" over pieces during its movement.\n \n"
                + "If the pair of cards chosen by the player can be played, then :\n"
                + "\t - The movement card will go to the discard pile and the hero card will no longer be playable\n"
                + "\t - The piece will change color when it reaches the opponent's piece square\n"
                + "\t - The king will be on the piece that has just been placed\n");

        Text title5 = new Text("II/ End of the Game\n");
        Text content5 = new Text("The game ends in 2 cases :\n"
                + "\t - If a player is no longer able to move using the cards in front of them\n"
                + "\t - As soon as the last available piece has been placed\n \n"
                + "To determine the winner, the following must be calculated :\n"
                + "\t - The number of \"adjacency\" zones, a set of squares whose tokens are the same color AND which touch each other side-to-side (not corner-to-corner)\n"
                + "\t - Count the number of pieces in each zone and square this number to get the value of the zone\n"
                + "\t - Sum the values of the zones\n"
                + "\t - The player with the highest sum wins the game\n"
                + "\t - In case of a tie, the winner is the player with the most pieces of their color on the board\n"
                + "\t - If the number of pieces is equal, the game is a draw and there are no winners\n");


        title1.setFont(new Font(size/3));
        content1.setFont(new Font(size/4));
        title2.setFont(new Font(size/3));
        content2.setFont(new Font(size/4));
        title3.setFont(new Font(size/3));
        content3.setFont(new Font(size/4));
        title4.setFont(new Font(size/3));
        content4.setFont(new Font(size/4));
        title5.setFont(new Font(size/3));
        content5.setFont(new Font(size/4));
        Text pageTitle = new Text("Rules");
        back = new Button("Back");
        try {
            File fontFile = new File("src/menu_assets/RoyalKing-Free.ttf");
            String absolutePath = fontFile.getAbsolutePath();
            pageTitle.setFont(Font.loadFont(new FileInputStream(absolutePath), size));
            back.setFont(Font.loadFont(new FileInputStream(absolutePath), size));
        } catch (Exception e) {
            pageTitle.setFont(new Font(size));
            back.setFont(new Font(size));
        }
        pageTitle.setX(width/4);
        pageTitle.setY(height/4.5);
        pageTitle.setFill(Color.WHITE);
        rulesContainer.getChildren().addAll(title1, content1, title2, content2, title3, content3, title4, content4, title5, content5);
        rulesMainContainer.setContent(rulesContainer);
        rulesMainContainer.setPrefSize(width/1.5, height/2);
        rulesMainContainer.setLayoutX(width/6);
        rulesMainContainer.setLayoutY(height/4);
        back.setPrefSize(width/5, height/10);
        back.setLayoutX(width/2.5);
        back.setLayoutY(height/1.3);
        group.getChildren().clear();
        group.getChildren().addAll(pageTitle, rulesMainContainer, back);
    }
    public Button getBackButton() {
        return back;
    }
}
