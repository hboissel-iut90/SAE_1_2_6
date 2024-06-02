package view;

import boardifier.model.GameElement;
import boardifier.view.ElementLook;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.RosesCard;

import javafx.scene.shape.Rectangle;
import java.util.Objects;

import static model.RosesCard.CARD_BLUE;
import static model.RosesCard.CARD_RED;

public class RosesCardLook extends ElementLook {
    private Rectangle rectangle;
    private int width;
    private int height;

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
                    Image imageN = new Image("SAE_1_2_6/SAE_desktopFX/images/N_card.png");
                    ImageView imageViewN = new ImageView();
                    imageViewN.setImage(imageN);
                    getGroup().getChildren().add(imageViewN);
                    break;
                case "N-E":
                    Image imageNE = new Image("SAE_1_2_6/SAE_desktopFX/images/NE_card.png");
                    ImageView imageViewNE = new ImageView();
                    imageViewNE.setImage(imageNE);
                    getGroup().getChildren().add(imageViewNE);
                case "E":
                    Image imageE = new Image("SAE_1_2_6/SAE_desktopFX/images/E_card.png");
                    ImageView imageViewE = new ImageView();
                    imageViewE.setImage(imageE);
                    getGroup().getChildren().add(imageViewE);
                case "S-E":
                    Image imageSE = new Image("SAE_1_2_6/SAE_desktopFX/images/SE_card.png");
                    ImageView imageViewSE = new ImageView();
                    imageViewSE.setImage(imageSE);
                    getGroup().getChildren().add(imageViewSE);
                case "S":
                    Image imageS = new Image("SAE_1_2_6/SAE_desktopFX/images/S_card.png");
                    ImageView imageViewS = new ImageView();
                    imageViewS.setImage(imageS);
                    getGroup().getChildren().add(imageViewS);
                case "S-W":
                    Image imageSW = new Image("SAE_1_2_6/SAE_desktopFX/images/SO_card.png");
                    ImageView imageViewSW = new ImageView();
                    imageViewSW.setImage(imageSW);
                    getGroup().getChildren().add(imageViewSW);
                case "W":
                    Image imageW = new Image("SAE_1_2_6/SAE_desktopFX/images/O_card.png");
                    ImageView imageViewW = new ImageView();
                    imageViewW.setImage(imageW);
                    getGroup().getChildren().add(imageViewW);
                default:
                    Image imageNW = new Image("SAE_1_2_6/SAE_desktopFX/images/NO_card.png");
                    ImageView imageViewNW = new ImageView();
                    imageViewNW.setImage(imageNW);
                    getGroup().getChildren().add(imageViewNW);
            }
            Text text = new Text(String.valueOf(card.getValue()));
            text.setFont(new Font(24));
            rectangle.setFill(Color.WHITE);
            addShape(rectangle);
            addShape(text);
            //if (card.isFlipped()) {
             //   shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + " " + ConsoleColor.RESET;
               // shape[1][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + " " + ConsoleColor.RESET;
            //}
        }else{
            Text text = new Text("HERO");
            text.setFont(new Font(24));
            if (card.getColor() == CARD_BLUE) {
                rectangle.setFill(Color.BLUE);
            } else if (card.getColor() == CARD_RED){
                rectangle.setFill(Color.RED);
            }
//            if (card.isFlipped()) {
//                shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + " " + ConsoleColor.RESET;
//            }
            addShape(rectangle);
            addShape(text);
        }
        addShape(rectangle);
    }














}

