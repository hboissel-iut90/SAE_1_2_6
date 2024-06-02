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

        addLook(new RosesBoardLook(150, model.getBoard()));
        addLook(new RosesBluePawnPotLook(model.getBluePot()));
        addLook(new RosesRedPawnPotLook(model.getRedPot()));

        for(int i=0;i<26;i++) {
            addLook(new RosesPawnLook(17,model.getBluePawns()[i]));
            addLook(new RosesPawnLook(17, model.getRedPawns()[i]));
        }

        addLook(new TextLook(24, "0x000000", model.getPlayerName()));
        addLook(new RosesCardPotLook(70, 55, model.getMoovBluePot()));
        addLook(new RosesCardPotLook(70, 55, model.getMoovRedPot()));
        addLook(new RosesCardPotLook(80, 65, model.getBlueHeroPot()));
        addLook(new RosesCardPotLook(80, 65, model.getRedHeroPot()));
        addLook(new RosesCardPotLook(80, 40, model.getDiscardPot()));
        addLook(new RosesCardPotLook(80, 40, model.getPickPot()));

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
