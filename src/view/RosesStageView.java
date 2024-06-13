package view;

import boardifier.model.GameStageModel;
import boardifier.view.*;
import javafx.stage.Screen;
import model.RosesStageModel;

public class RosesStageView extends GameStageView {
    public RosesStageView(String name, GameStageModel gameStageModel) {
        super(name, gameStageModel);
        width = (int) Screen.getPrimary().getBounds().getWidth(); // get the resolution of the user and set it to the stage
        height = (int) Screen.getPrimary().getBounds().getHeight();
    }

    @Override
    public void createLooks() {
        RosesStageModel model = (RosesStageModel)gameStageModel;

        addLook(new RosesBoardLook(180, model.getBoard()));
        addLook(new BluePawnPotLook(model.getBluePot()));
        addLook(new RedPawnPotLook(model.getRedPot()));

        for(int i=0;i<26;i++) {
            addLook(new PawnLook(22,model.getBluePawns()[i]));
            addLook(new PawnLook(22, model.getRedPawns()[i]));
        }

        addLook(new TextLook(50,"0x000000", model.getPlayerName()));
        addLook(new TextLook(30,"0x000000", model.getPick()));
        addLook(new TextLook(30,"#FF0000", model.getRedPawnsCounter()));
        addLook(new TextLook(30,"#0000FF", model.getBluePawnsCounter()));
        addLook(new TextLook(30, "#303030", model.getCardPickCounter()));
        addLook(new TextLook(30, "#0000FF", model.getBlueHeroCardsCounter()));
        addLook(new TextLook(30, "#FF0000", model.getRedHeroCardsCounter()));
        addLook(new RosesCardPotLook(150, 100, model.getMoovBluePot()));
        addLook(new RosesCardPotLook(150, 100, model.getMoovRedPot()));
        addLook(new RosesCardPotLook(150, 100, model.getBlueHeroPot()));
        addLook(new RosesCardPotLook(150, 100, model.getRedHeroPot()));
        addLook(new RosesCardPotLook(150, 100, model.getDiscardPot()));
        addLook(new RosesCardPotLook(150, 100, model.getPickPot()));
        addLook(new PawnLook(17, model.getCrownPawn()));

        for (int i = 0; i < 5; i++) {
            addLook(new RosesCardLook(80, 110, model.getPlayer1MovementCards()[i], model));
            addLook(new RosesCardLook(80, 110, model.getPlayer2MovementCards()[i], model));
        }

        for (int i = 0; i < model.getPickCards().length; i++) {
            addLook(new RosesCardLook(80, 110, model.getPickCards()[i], model));
        }

        for (int i = 0; i < 4; i++) {
            addLook(new RosesCardLook(80, 110, model.getPlayer1HeroCards()[i], model));
            addLook(new RosesCardLook(80, 110, model.getPlayer2HeroCards()[i], model));
        }

        /* Example to show how to set a global container to layout all looks in the root pane
           Must also uncomment lines in HoleStageFactory and HoleStageModel
        ContainerLook mainLook = new ContainerLook(model.getRootContainer(), -1);
        mainLook.setPadding(10);
        mainLook.setVerticalAlignment(ContainerLook.ALIGN_MIDDLE);
        mainLook.setHorizontalAlignment(ContainerLook.ALIGN_CENTER);
        addLook(mainLook);

         */
    }
}
