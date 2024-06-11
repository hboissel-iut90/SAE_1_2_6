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
                    switch (card.getValue()) {
                        case 1:
                            Image imageN1 = new Image("file:src/images/N_1_card.png");
                            ImageView imageViewN1 = new ImageView();
                            imageViewN1.setX(1000);
                            imageViewN1.setY(50);
                            imageViewN1.setFitHeight(200);
                            imageViewN1.setFitWidth(100);
                            imageViewN1.setImage(imageN1);
                            getGroup().getChildren().add(imageViewN1);
                            break;
                        case 2:
                            Image imageN2 = new Image("file:src/images/N_2_card.png");
                            ImageView imageViewN2 = new ImageView();
                            imageViewN2.setX(1150);
                            imageViewN2.setY(50);
                            imageViewN2.setFitHeight(200);
                            imageViewN2.setFitWidth(100);
                            imageViewN2.setImage(imageN2);
                            getGroup().getChildren().add(imageViewN2);
                            break;
                        case 3:
                            Image imageN3 = new Image("file:src/images/N_3_card.png");
                            ImageView imageViewN3 = new ImageView();
                            imageViewN3.setX(1300);
                            imageViewN3.setY(50);
                            imageViewN3.setFitHeight(200);
                            imageViewN3.setFitWidth(100);
                            imageViewN3.setImage(imageN3);
                            getGroup().getChildren().add(imageViewN3);
                            break;
                    }
                case "N-E":
                    switch (card.getValue()) {
                        case 1:
                            Image imageNE1 = new Image("file:src/images/N-E_1_card.png");
                            ImageView imageViewNE1 = new ImageView();
                            imageViewNE1.setX(1000);
                            imageViewNE1.setY(250);
                            imageViewNE1.setFitHeight(200);
                            imageViewNE1.setFitWidth(100);
                            imageViewNE1.setImage(imageNE1);
                            getGroup().getChildren().add(imageViewNE1);
                            break;
                        case 2:
                            Image imageNE2 = new Image("file:src/images/N-E_2_card.png");
                            ImageView imageViewNE2 = new ImageView();
                            imageViewNE2.setX(1150);
                            imageViewNE2.setY(250);
                            imageViewNE2.setFitHeight(200);
                            imageViewNE2.setFitWidth(100);
                            imageViewNE2.setImage(imageNE2);
                            getGroup().getChildren().add(imageViewNE2);
                            break;
                        case 3:
                            Image imageNE3 = new Image("file:src/images/N-E_3_card.png");
                            ImageView imageViewNE3 = new ImageView();
                            imageViewNE3.setX(1300);
                            imageViewNE3.setY(250);
                            imageViewNE3.setFitHeight(200);
                            imageViewNE3.setFitWidth(100);
                            imageViewNE3.setImage(imageNE3);
                            getGroup().getChildren().add(imageViewNE3);
                            break;
                    }
                case "E":
                    switch (card.getValue()) {
                        case 1:
                            Image imageE1 = new Image("file:src/images/E_1_card.png");
                            ImageView imageViewE1 = new ImageView();
                            imageViewE1.setX(1000);
                            imageViewE1.setY(450);
                            imageViewE1.setFitHeight(200);
                            imageViewE1.setFitWidth(100);
                            imageViewE1.setImage(imageE1);
                            getGroup().getChildren().add(imageViewE1);
                            break;
                        case 2:
                            Image imageE2 = new Image("file:src/images/E_2_card.png");
                            ImageView imageViewE2 = new ImageView();
                            imageViewE2.setX(1150);
                            imageViewE2.setY(450);
                            imageViewE2.setFitHeight(200);
                            imageViewE2.setFitWidth(100);
                            imageViewE2.setImage(imageE2);
                            getGroup().getChildren().add(imageViewE2);
                            break;
                        case 3:
                            Image imageE3 = new Image("file:src/images/E_3_card.png");
                            ImageView imageViewE3 = new ImageView();
                            imageViewE3.setX(1300);
                            imageViewE3.setY(450);
                            imageViewE3.setFitHeight(200);
                            imageViewE3.setFitWidth(100);
                            imageViewE3.setImage(imageE3);
                            getGroup().getChildren().add(imageViewE3);
                            break;
                    }
                case "S-E":
                    switch (card.getValue()) {
                        case 1:
                            Image imageSE1 = new Image("file:src/images/S-E_1_card.png");
                            ImageView imageViewSE1 = new ImageView();
                            imageViewSE1.setX(1000);
                            imageViewSE1.setY(650);
                            imageViewSE1.setFitHeight(200);
                            imageViewSE1.setFitWidth(100);
                            imageViewSE1.setImage(imageSE1);
                            getGroup().getChildren().add(imageViewSE1);
                            break;
                        case 2:
                            Image imageSE2 = new Image("file:src/images/S-E_2_card.png");
                            ImageView imageViewSE2 = new ImageView();
                            imageViewSE2.setX(1150);
                            imageViewSE2.setY(650);
                            imageViewSE2.setFitHeight(200);
                            imageViewSE2.setFitWidth(100);
                            imageViewSE2.setImage(imageSE2);
                            getGroup().getChildren().add(imageViewSE2);
                            break;
                        case 3:
                            Image imageSE3 = new Image("file:src/images/S-E_3_card.png");
                            ImageView imageViewSE3 = new ImageView();
                            imageViewSE3.setX(1300);
                            imageViewSE3.setY(650);
                            imageViewSE3.setFitHeight(200);
                            imageViewSE3.setFitWidth(100);
                            imageViewSE3.setImage(imageSE3);
                            getGroup().getChildren().add(imageViewSE3);
                            break;
                    }
                case "S":
                    switch (card.getValue()) {
                        case 1:
                            Image imageS1 = new Image("file:src/images/S_1_card.png");
                            ImageView imageViewS1 = new ImageView();
                            imageViewS1.setX(1450);
                            imageViewS1.setY(50);
                            imageViewS1.setFitHeight(200);
                            imageViewS1.setFitWidth(100);
                            imageViewS1.setImage(imageS1);
                            getGroup().getChildren().add(imageViewS1);
                            break;
                        case 2:
                            Image imageS2 = new Image("file:src/images/S_2_card.png");
                            ImageView imageViewS2 = new ImageView();
                            imageViewS2.setX(1600);
                            imageViewS2.setY(50);
                            imageViewS2.setFitHeight(200);
                            imageViewS2.setFitWidth(100);
                            imageViewS2.setImage(imageS2);
                            getGroup().getChildren().add(imageViewS2);
                            break;
                        case 3:
                            Image imageS3 = new Image("file:src/images/S_3_card.png");
                            ImageView imageViewS3 = new ImageView();
                            imageViewS3.setX(1750);
                            imageViewS3.setY(50);
                            imageViewS3.setFitHeight(200);
                            imageViewS3.setFitWidth(100);
                            imageViewS3.setImage(imageS3);
                            getGroup().getChildren().add(imageViewS3);
                            break;
                    }
                case "S-W":
                    switch (card.getValue()) {
                        case 1:
                            Image imageSW1 = new Image("file:src/images/S-W_1_card.png");
                            ImageView imageViewSW1 = new ImageView();
                            imageViewSW1.setX(1450);
                            imageViewSW1.setY(250);
                            imageViewSW1.setFitHeight(200);
                            imageViewSW1.setFitWidth(100);
                            imageViewSW1.setImage(imageSW1);
                            getGroup().getChildren().add(imageViewSW1);
                            break;
                        case 2:
                            Image imageSW2 = new Image("file:src/images/S-W_2_card.png");
                            ImageView imageViewSW2 = new ImageView();
                            imageViewSW2.setX(1600);
                            imageViewSW2.setY(250);
                            imageViewSW2.setFitHeight(200);
                            imageViewSW2.setFitWidth(100);
                            imageViewSW2.setImage(imageSW2);
                            getGroup().getChildren().add(imageViewSW2);
                            break;
                        case 3:
                            Image imageSW3 = new Image("file:src/images/S-W_3_card.png");
                            ImageView imageViewSW3 = new ImageView();
                            imageViewSW3.setX(1750);
                            imageViewSW3.setY(250);
                            imageViewSW3.setFitHeight(200);
                            imageViewSW3.setFitWidth(100);
                            imageViewSW3.setImage(imageSW3);
                            getGroup().getChildren().add(imageViewSW3);
                            break;
                    }
                case "W":
                    switch (card.getValue()) {
                        case 1:
                            Image imageW1 = new Image("file:src/images/W_1_card.png");
                            ImageView imageViewW1 = new ImageView();
                            imageViewW1.setX(1450);
                            imageViewW1.setY(450);
                            imageViewW1.setFitHeight(200);
                            imageViewW1.setFitWidth(100);
                            imageViewW1.setImage(imageW1);
                            getGroup().getChildren().add(imageViewW1);
                            break;
                        case 2:
                            Image imageW2 = new Image("file:src/images/W_2_card.png");
                            ImageView imageViewW2 = new ImageView();
                            imageViewW2.setX(1600);
                            imageViewW2.setY(450);
                            imageViewW2.setFitHeight(200);
                            imageViewW2.setFitWidth(100);
                            imageViewW2.setImage(imageW2);
                            getGroup().getChildren().add(imageViewW2);
                            break;
                        case 3:
                            Image imageW3 = new Image("file:src/images/W_3_card.png");
                            ImageView imageViewW3 = new ImageView();
                            imageViewW3.setX(1750);
                            imageViewW3.setY(450);
                            imageViewW3.setFitHeight(200);
                            imageViewW3.setFitWidth(100);
                            imageViewW3.setImage(imageW3);
                            getGroup().getChildren().add(imageViewW3);
                            break;
                    }
                case "N-W":
                    switch (card.getValue()) {
                        case 1:
                            Image imageNW1 = new Image("file:src/images/N-W_1_card.png");
                            ImageView imageViewNW1 = new ImageView();
                            imageViewNW1.setX(1450);
                            imageViewNW1.setY(650);
                            imageViewNW1.setFitHeight(200);
                            imageViewNW1.setFitWidth(100);
                            imageViewNW1.setImage(imageNW1);
                            getGroup().getChildren().add(imageViewNW1);
                            break;
                        case 2:
                            Image imageNW2 = new Image("file:src/images/N-W_2_card.png");
                            ImageView imageViewNW2 = new ImageView();
                            imageViewNW2.setX(1600);
                            imageViewNW2.setY(650);
                            imageViewNW2.setFitHeight(200);
                            imageViewNW2.setFitWidth(100);
                            imageViewNW2.setImage(imageNW2);
                            getGroup().getChildren().add(imageViewNW2);
                            break;
                        case 3:
                            Image imageNW3 = new Image("file:src/images/N-W_3_card.png");
                            ImageView imageViewNW3 = new ImageView();
                            imageViewNW3.setX(1750);
                            imageViewNW3.setY(650);
                            imageViewNW3.setFitHeight(200);
                            imageViewNW3.setFitWidth(100);
                            imageViewNW3.setImage(imageNW3);
                            getGroup().getChildren().add(imageViewNW3);
                            break;
                    }
            }
            if (card.isFlipped()) {
               Rectangle whiteMovementCard = new Rectangle();
               whiteMovementCard.setWidth(100);
               whiteMovementCard.setHeight(200);
               whiteMovementCard.setArcHeight(25);
               whiteMovementCard.setArcWidth(25);
               whiteMovementCard.setFill(Color.WHITE);
            }
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
                if (card.isFlipped()) {
                    Rectangle blueHeroCardFlipped = new Rectangle();
                    blueHeroCardFlipped.setWidth(100);
                    blueHeroCardFlipped.setHeight(200);
                    blueHeroCardFlipped.setArcHeight(25);
                    blueHeroCardFlipped.setArcWidth(25);
                    blueHeroCardFlipped.setFill(Color.BLUE);
                }
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
                if (card.isFlipped()) {
                    Rectangle redHeroCardFlipped = new Rectangle();
                    redHeroCardFlipped.setWidth(100);
                    redHeroCardFlipped.setHeight(200);
                    redHeroCardFlipped.setArcHeight(25);
                    redHeroCardFlipped.setArcWidth(25);
                    redHeroCardFlipped.setFill(Color.RED);
                }
            }
        }
    }
}