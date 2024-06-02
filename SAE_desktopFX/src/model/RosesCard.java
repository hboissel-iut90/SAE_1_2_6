package model;

import boardifier.model.ElementTypes;
import boardifier.model.GameElement;
import boardifier.model.GameStageModel;

import javax.lang.model.type.NullType;

public class RosesCard extends GameElement {
    private int value = 0;
    private String direction = "";
    private String cardType;
    private int color;

    private boolean isFlipped = true;

    public static int CARD_BLUE = 0;

    public static int CARD_RED = 1;

    public static int CARD_WHITE = 2;

    public RosesCard (int color, GameStageModel gameStageModel){
        super(gameStageModel);
        ElementTypes.register("card",60);
        type = ElementTypes.getType("card");
        this.cardType = "HEROS";
        this.color = color;
        // this.isFlipped = false;
    }

    public RosesCard (int value, String direction, GameStageModel gameStageModel){
        super(gameStageModel);
        ElementTypes.register("card",60);
        type = ElementTypes.getType("card");
        this.value = value;
        this.direction = direction;
        this.cardType = "MOUVEMENT";
        this.color = CARD_WHITE;
    }

    public RosesCard(RosesCard other) {
        super(other.getGameStage());
        ElementTypes.register("card", 60);
        type = ElementTypes.getType("card");
        this.value = other.getValue();
        this.direction = other.getDirection();
        this.cardType = other.getCardType();
        this.color = other.getColor();
    }




    public int getValue(){
        return value;
    }

    public String getDirection(){
        return direction;
    }

    public String getCardType(){
        return cardType;
    }

    public int getColor(){
        return color;
    }

   public void flip(){
        isFlipped = !isFlipped;
    }

    public boolean isFlipped(){
        return isFlipped;
    }
}
