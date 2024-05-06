package view;

import boardifier.model.GameElement;
import boardifier.view.ConsoleColor;
import boardifier.view.ElementLook;
import model.RosesCard;

import java.util.Objects;

import static model.RosesCard.CARD_BLUE;

public class RosesCardLook extends ElementLook {

    public RosesCardLook(GameElement element) {
        super(element, 1, 1);
        setAnchorType(ANCHOR_TOPLEFT);
    }

    protected void render() {
        RosesCard card = (RosesCard) element;
        if (Objects.equals(card.getCardType(), "MOUVEMENT")) {
            this.setHeight(2);
            switch (card.getDirection()) {
                case "N":
                    shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + "⬆" + ConsoleColor.RESET;
                    break;
                case "N-E":
                    shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + "⬈" + ConsoleColor.RESET;
                    break;
                case "E":
                    shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + "➡" + ConsoleColor.RESET;
                    break;
                case "S-E":
                    shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + "⬊" + ConsoleColor.RESET;
                    break;
                case "S":
                    shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + "⬇" + ConsoleColor.RESET;
                    break;
                case "S-W":
                    shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + "⬋" + ConsoleColor.RESET;
                    break;
                case "W":
                    shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + "⬅" + ConsoleColor.RESET;
                    break;
                default:
                    shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + "⬉" + ConsoleColor.RESET;
                    break;
            }
            shape[1][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + card.getValue() + ConsoleColor.RESET;
            if (card.isFlipped()) {
                shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + " " + ConsoleColor.RESET;
                shape[1][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + " " + ConsoleColor.RESET;
            }
        }else{
            if (card.getColor() == CARD_BLUE) {
                shape[0][0] = ConsoleColor.BLACK + ConsoleColor.BLUE_BACKGROUND + "H" + ConsoleColor.RESET;
            } else {
                shape[0][0] = ConsoleColor.BLACK + ConsoleColor.RED_BACKGROUND + "H" + ConsoleColor.RESET;
            }
            if (card.isFlipped()) {
                shape[0][0] = ConsoleColor.BLACK + ConsoleColor.WHITE_BACKGROUND + " " + ConsoleColor.RESET;
            }
        }

    }
}

