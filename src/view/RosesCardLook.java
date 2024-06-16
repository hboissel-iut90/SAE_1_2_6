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
        imageView.setX(x - width / 1.55);
        imageView.setY(y - height / 1.45);
        imageView.setFitHeight(150);
        imageView.setFitWidth(100);
        if (isReturned) imageView.setRotate(180);
        getGroup().getChildren().add(imageView);

        // Add border rectangle
        border = new Rectangle(imageView.getX(), imageView.getY(), 100, 150);
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(2);
        border.setFill(Color.TRANSPARENT);
        getGroup().getChildren().add(border);
    }

    private void handleMovementCard(RosesCard card) {
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

        if (card.getContainer() == stageModel.getMoveBluePot()
                || card.getContainer() == stageModel.getPickPot()
                || card.getContainer() == stageModel.getDiscardPot()) {
            loadImageAndPlace(imagePath, card.getX(), card.getY(), false);
        } else if (card.getContainer() == stageModel.getMoveRedPot()) {
            loadImageAndPlace(imagePath, card.getX(), card.getY(), true);
        }
    }

    private void handleHeroCard(RosesCard card, Color color, String text) {
        Rectangle heroCard = new Rectangle(100, 150, color);
        heroCard.setArcHeight(15);
        heroCard.setArcWidth(15);
        heroCard.setStroke(Color.BLACK);
        heroCard.setX(card.getX() - 50);
        heroCard.setY(card.getY() - 75);

        Text heroText = new Text(text);
        heroText.setFont(new Font(35));
        heroText.setFill(Color.WHITE);
        if (color == Color.BLUE) {
            heroText.setRotate(90);
            heroText.setX(card.getX() - 45);
            heroText.setY(card.getY() + 13);
        }
        else  {
            heroText.setRotate(270);
            heroText.setX(card.getX() - 45);
            heroText.setY(card.getY() + 13);
        }

        getGroup().getChildren().addAll(heroCard, heroText);

        // Add border rectangle
        Rectangle border = new Rectangle(heroCard.getX(), heroCard.getY(), 100, 150);
        border.setStroke(Color.BLACK);
        border.setStrokeWidth(2);
        border.setFill(Color.TRANSPARENT);
        getGroup().getChildren().add(border);
    }

    public void update(GameElement element) {
        RosesCard card = (RosesCard) element;
        if (card.isFlipped()) {
            Rectangle whiteMovementCard = new Rectangle(card.getX() - 75, card.getY() - 50, 150, 100);
            whiteMovementCard.setFill(Color.WHITE);
            whiteMovementCard.setStroke(Color.BLACK);
            whiteMovementCard.setStrokeWidth(2);
            whiteMovementCard.setRotate(90);
            getGroup().getChildren().add(whiteMovementCard);
            handleMovementCard(card);
        } else {
            handleMovementCard(card);
        }
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
                handleHeroCard(card, Color.BLUE, "HERO");
            } else if (card.getColor() == CARD_RED) {
                handleHeroCard(card, Color.RED, "HERO");
            }
        }
    }
}
