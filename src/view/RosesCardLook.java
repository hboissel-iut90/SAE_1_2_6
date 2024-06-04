package view;

import boardifier.model.GameElement;
import boardifier.model.Model;
import boardifier.view.ElementLook;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.RosesCard;

import javafx.scene.shape.Rectangle;
import model.RosesStageModel;

import java.util.Objects;

import static model.RosesCard.CARD_BLUE;
import static model.RosesCard.CARD_RED;

public class RosesCardLook extends ElementLook {
    private Rectangle rectangle;
    private int width;
    private int height;

    Model model = ((GameElement)element).getModel();




    public RosesCardLook(int width, int weight, GameElement element) {
        super(element, 1);
        this.width = width;
        this.height = weight;
        render();
    }

    @Override
    public void onSelectionChange() {
        RosesCard card = (RosesCard) element;
        if (card.isSelected()) {
            rectangle.setStrokeWidth(4);
            rectangle.setStrokeMiterLimit(10);
            rectangle.setStrokeType(StrokeType.CENTERED);
            rectangle.setStroke(Color.valueOf("0x333333"));
        } else {
            rectangle.setStrokeWidth(2);
        }

    }

    @Override
    public void onFaceChange() {
    }

    protected void render() {
        RosesCard card = (RosesCard) element;
        rectangle = new Rectangle();
        rectangle.setWidth(width);
        rectangle.setHeight(height);
        rectangle.setStrokeWidth(2);
        rectangle.setStrokeType(StrokeType.CENTERED);
        rectangle.setStroke(Color.valueOf("0x333333"));
        if (Objects.equals(card.getCardType(), "MOUVEMENT")) {
            rectangle.setWidth(1);
            rectangle.setHeight(2);
            switch (card.getDirection()) {
                case "N":
                    Image imageN = new Image("file:src/images/N_card.png");
                    ImageView imageViewN = new ImageView();
                    imageViewN.setX(1200);
                    imageViewN.setY(200);
                    imageViewN.setImage(imageN);
                    getGroup().getChildren().add(imageViewN);
                case "N-E":
                    Image imageNE = new Image("file:src/images/NE_card.png");
                    ImageView imageViewNE = new ImageView();
                    imageViewNE.setX(1200);
                    imageViewNE.setY(300);
                    imageViewNE.setImage(imageNE);
                    getGroup().getChildren().add(imageViewNE);
                case "E":
                    Image imageE = new Image("file:src/images/E_card.png");
                    ImageView imageViewE = new ImageView();
                    imageViewE.setX(1200);
                    imageViewE.setY(400);
                    imageViewE.setImage(imageE);
                    getGroup().getChildren().add(imageViewE);
                case "S-E":
                    Image imageSE = new Image("file:src/images/SE_card.png");
                    ImageView imageViewSE = new ImageView();
                    imageViewSE.setX(1200);
                    imageViewSE.setY(500);
                    imageViewSE.setImage(imageSE);
                    getGroup().getChildren().add(imageViewSE);
                case "S":
                    Image imageS = new Image("file:src/images/S_card.png");
                    ImageView imageViewS = new ImageView();
                    imageViewS.setX(1000);
                    imageViewS.setY(200);
                    imageViewS.setImage(imageS);
                    getGroup().getChildren().add(imageViewS);
                case "S-W":
                    Image imageSW = new Image("file:src/images/SO_card.png");
                    ImageView imageViewSW = new ImageView();
                    imageViewSW.setX(1000);
                    imageViewSW.setY(300);
                    imageViewSW.setImage(imageSW);
                    getGroup().getChildren().add(imageViewSW);
                case "W":
                    Image imageW = new Image("file:src/images/O_card.png");
                    ImageView imageViewW = new ImageView();
                    imageViewW.setX(1000);
                    imageViewW.setY(400);
                    imageViewW.setImage(imageW);
                    getGroup().getChildren().add(imageViewW);
                default:
                    Image imageNW = new Image("file:src/images/NO_card.png");
                    ImageView imageViewNW = new ImageView();
                    imageViewNW.setX(1000);
                    imageViewNW.setY(500);
                    imageViewNW.setImage(imageNW);
                    getGroup().getChildren().add(imageViewNW);
                    break;
            }
            Text text = new Text(String.valueOf(card.getValue()));
            text.setFont(new Font(24));
            text.setFill(Color.BLACK);
            getGroup().getChildren().add(text);
            //if (card.isFlipped()) {
            //   shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + " " + ConsoleColor.RESET;
            // shape[1][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + " " + ConsoleColor.RESET;
            //}
        } else {
            if (card.getColor() == RosesCard.CARD_BLUE) {
                Rectangle blueHeroCard = new Rectangle();
                Text text = new Text("HERO");
                blueHeroCard.setX(-25);
                blueHeroCard.setY(-30);
                blueHeroCard.setWidth(50);
                blueHeroCard.setHeight(60);
                blueHeroCard.setArcHeight(25);
                blueHeroCard.setArcWidth(25);
                blueHeroCard.setFill(Color.BLUE);
                text.setRotate(90);
                text.setFont(new Font(18));
                text.setX(-25);
                text.setY(5);
                text.setFill(Color.WHITE);
                getGroup().getChildren().addAll(blueHeroCard, text);
            } else if (card.getColor() == RosesCard.CARD_RED) {
                Rectangle redHeroCard = new Rectangle();
                Text text = new Text("HERO");
                redHeroCard.setX(-25);
                redHeroCard.setY(-30);
                redHeroCard.setWidth(50);
                redHeroCard.setHeight(60);
                redHeroCard.setArcHeight(25);
                redHeroCard.setArcWidth(25);
                redHeroCard.setFill(Color.RED);
                text.setRotate(90);
                text.setFont(new Font(18));
                text.setX(-25);
                text.setY(5);
                text.setFill(Color.WHITE);
                getGroup().getChildren().addAll(redHeroCard, text);
            }
//            if (card.isFlipped()) {
//                shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + " " + ConsoleColor.RESET;
//            }
        }
    }
}