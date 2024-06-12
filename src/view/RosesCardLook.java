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
    private Rectangle border;
    private RosesStageModel stageModel;

    public RosesCardLook(int width, int height, GameElement element, RosesStageModel stageModel) {
        super(element, 1);
        this.width = width;
        this.height = height;
        this.stageModel = stageModel;
        render();
    }

    @Override
    public void onSelectionChange() {
        RosesCard card = (RosesCard) element;
        if (card.isSelected()) {
            border.setStroke(Color.valueOf("0x333333"));
            border.setStrokeWidth(4);
            border.setStrokeMiterLimit(4);
            border.setStrokeType(StrokeType.CENTERED);
        } else {
            border.setStrokeWidth(2);
        }
    }

    @Override
    public void onFaceChange() {
        // Implement if needed
    }

    private void loadImageAndPlace(String imagePath, double x, double y, boolean isReturned) {
        Image image = new Image(imagePath);
        ImageView imageView = new ImageView(image);
        imageView.setX(x - width / 2);
        imageView.setY(y - height / 2);
        imageView.setFitHeight(110);
        imageView.setFitWidth(80);
        if (isReturned) imageView.setRotate(180);
        getGroup().getChildren().add(imageView);

        // Add border rectangle
        border = new Rectangle(imageView.getX(), imageView.getY(), 80, 110);
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(2);
        border.setFill(Color.TRANSPARENT);
        getGroup().getChildren().add(border);
    }

    private void handleMovementCard(RosesCard card) {
        if (card.isFlipped()) {
            Rectangle whiteMovementCard = new Rectangle(card.getX() - 55, card.getY() - 40, 110, 80);
            whiteMovementCard.setFill(Color.WHITE);
            whiteMovementCard.setArcHeight(25);
            whiteMovementCard.setArcWidth(25);
            whiteMovementCard.setStroke(Color.BLACK);
            whiteMovementCard.setStrokeWidth(2);
            whiteMovementCard.setRotate(90);
            getGroup().getChildren().add(whiteMovementCard);
            return;
        }

        String direction = card.getDirection();
        int value = card.getValue();
        String basePath = "file:src/images/";

        String imagePath = switch (direction) {
            case "N" -> basePath + "N_" + value + "_card.png";
            case "N-E" -> basePath + "N-E_" + value + "_card.png";
            case "E" -> basePath + "E_" + value + "_card.png";
            case "S-E" -> basePath + "S-E_" + value + "_card.png";
            case "S" -> basePath + "S_" + value + "_card.png";
            case "S-W" -> basePath + "S-W_" + value + "_card.png";
            case "W" -> basePath + "W_" + value + "_card.png";
            case "N-W" -> basePath + "N-W_" + value + "_card.png";
            default -> throw new IllegalStateException("Unexpected value: " + direction);
        };

        if (card.getContainer() == stageModel.getMoovBluePot()) {
            loadImageAndPlace(imagePath, card.getX(), card.getY(), false);
        } else {
            loadImageAndPlace(imagePath, card.getX(), card.getY(), true);
        }
    }

    private void handleHeroCard(RosesCard card, Color color, String text) {
        Rectangle heroCard = new Rectangle(90, 130, color);
        heroCard.setArcHeight(15);
        heroCard.setArcWidth(15);
        heroCard.setStroke(Color.BLACK);
        heroCard.setX(card.getX() - 45);
        heroCard.setY(card.getY() - 65);

        Text heroText = new Text(text);
        heroText.setFont(new Font(35));
        heroText.setFill(Color.WHITE);
        if (color == Color.BLUE)
        heroText.setRotate(90);
        else heroText.setRotate(270);
        heroText.setX(card.getX() - 50);
        heroText.setY(card.getY() + 13);

        getGroup().getChildren().addAll(heroCard, heroText);

        // Add border rectangle
        Rectangle border = new Rectangle(heroCard.getX(), heroCard.getY(), 90, 130);
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(2);
        border.setFill(Color.TRANSPARENT);
        getGroup().getChildren().add(border);
    }

    protected void render() {
        RosesCard card = (RosesCard) element;
        rectangle = new Rectangle(width, height);
        rectangle.setStrokeWidth(2);
        rectangle.setStrokeType(StrokeType.CENTERED);
        rectangle.setStroke(Color.valueOf("0x333333"));

        if (Objects.equals(card.getCardType(), "MOUVEMENT")) {
            handleMovementCard(card);
        } else {
            if (card.getColor() == CARD_BLUE) {
                handleHeroCard(card, Color.BLUE, "Héros");
            } else if (card.getColor() == CARD_RED) {
                handleHeroCard(card, Color.RED, "Héros");
            }
        }
    }
}
